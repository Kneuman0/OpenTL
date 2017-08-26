package com.zeva.tlGen.xml.dsig;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyStoreException;
import java.security.Security;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Xades4jSigningTest {

	private static final String KEYSTORE_TYPE = "PKCS12";
	private static final String KEYSTORE_FILEPATH = "resources/tom.pfx";
	private static final String KEYSTORE_PASSWORD = "123";

	@Test
	public void XmlSigner_OnSigningAnXml_ShouldProduceASignedXmlDocument() {

		Security.addProvider(new BouncyCastleProvider());

		Xades4jSigner xades4jSigner = null;
		try {
			xades4jSigner = new Xades4jSigner(
					FileSystemKeyStoreKeyingDataProviderFactory
							.createFileSystemKeyingDataProvider(KEYSTORE_TYPE,
									KEYSTORE_FILEPATH, KEYSTORE_PASSWORD, false));
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		Document xmlDocument = null;
		try {
			xmlDocument = getDocument(GetSampleXmlBytes());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		Document signedXmlDocument = null;
		try {
			signedXmlDocument = xades4jSigner.signDocument(xmlDocument);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		Assert.assertNotNull(signedXmlDocument);

		String xmlAsString = convertDocumentToString(signedXmlDocument);

		Assert.assertTrue(xmlAsString.toLowerCase().contains(
				"ds:signature".toLowerCase()));
	}

	private byte[] GetSampleXmlBytes() throws IOException {
		File file = new File("resources/TestXML.xml");

		return FileUtils.readFileToByteArray(file);
	}

	private Document getDocument(byte[] xmlAsBytes)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new ByteArrayInputStream(xmlAsBytes));
	}

	private String convertDocumentToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			// below code to remove XML declaration
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
			// "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return null;
	}
}
