package com.zeva.tlGen.xml.dsig;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.Security;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Xades4jVerificationTest {

	private static final String KEYSTORE_TYPE = "PKCS12";
	private static final String KEYSTORE_FILEPATH = "resources/tom.pfx";
	private static final String KEYSTORE_PASSWORD = "123";
	
	@Test
	public void XmlVeridier_OnVerifyingASignedXml_ShouldPassWithNoException() {
		
		Security.addProvider(new BouncyCastleProvider());
		
		Document xmlDocument = null;
		try {
			xmlDocument = ConvertToXmlDocument(GetSampleSignedXmlBytes());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		Xades4jVerifier xades4jSigner = null;
		try {
			xades4jSigner = new Xades4jVerifier(CertificateValidationProviderFactory.Create(KEYSTORE_TYPE, KEYSTORE_FILEPATH, KEYSTORE_PASSWORD));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		try {
			xades4jSigner.Verify(xmlDocument);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	private byte[] GetSampleSignedXmlBytes() throws IOException {
		
		File file = new File("resources/signed.xml");
		
		return FileUtils.readFileToByteArray(file);
	}
	
	private Document ConvertToXmlDocument(byte[] xmlAsBytes)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new ByteArrayInputStream(xmlAsBytes));
	}
}
