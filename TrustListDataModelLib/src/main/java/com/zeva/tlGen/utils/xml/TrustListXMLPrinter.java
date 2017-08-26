package com.zeva.tlGen.utils.xml;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import xades4j.XAdES4jException;

public class TrustListXMLPrinter implements IPrinter{
	private Document doc;
	
//	/**
//	 * initializes printer with TrustList
//	 * ***********need to use dependecy injection***************
//	 * @param list this Trust List type will determine the style of marshalling
//	 * @param key private key used to sign document
//	 * @param cert certificate used to validate signature at a later date
//	 */
//	public TrustListXMLPrinter(TrustList list, PrivateKey key, X509Certificate cert) {
//		this.key = key;
//		this.cert = cert;
//		try {
//			// initialize and build document
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			factory.setNamespaceAware(true);
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			doc = builder.newDocument();
//			doc.setXmlVersion("1.0");
//			doc.setXmlStandalone(true);
//		} catch (DOMException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		}
//		
//		buildDocument(list);
//	}
//	
//	/**
//	 * initializes printer with TrustListOfLists
//	 * @param list this TrustListOfLists type will determine the style of marshalling
//	 * @param key private key used to sign document
//	 * @param cert certificate used to validate signature at a later date
//	 */
//	public TrustListXMLPrinter(TrustListofLists listsOfLists, PrivateKey key, X509Certificate cert){
//		
//		
//		buildDocument(listsOfLists);
//	}
//	
//	// Strictly for testing
//	public TrustListXMLPrinter(PrivateKey key, X509Certificate cert){
//		this.key = key;
//		this.cert = cert;
//		try {
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			factory.setNamespaceAware(true);
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			doc = builder.newDocument();
//			doc.setXmlVersion("1.0");
//			doc.setXmlStandalone(true);
//		} catch (DOMException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		}
//		Element rootElement = doc.createElement("TrustServiceStatusList");
//		Element sub = doc.createElement("TestMe");
//		sub.setTextContent("Things");
//		rootElement.appendChild(sub);
//		doc.appendChild(rootElement);
//	}
	
	public TrustListXMLPrinter(Document doc){
		this.doc = doc;
	}
	
	/**
	 * Builds document with the TrustListOfList style
	 * @param listOfLists trust list of lists
	 */
//	private void buildDocument(TrustListofLists listOfLists){
//		try {
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			doc = builder.newDocument();
//		} catch (DOMException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		}
//		
//		
//	}
	
	/**
	 * Builds document with TrustList style
	 * Need to use dependency injection
	 * @param tl trust list
	 */
//	private void buildDocument(TrustList tl){
//		MarshalTrustListV3 buildTL = new MarshalTrustListV3(doc);
//		buildTL.marshalTrustList(tl);
//	}
	
	/**
	 * Returns document. Could be built and just needing a signature or
	 * only initialized depending on what methods have been called though the API
	 * @return the document at the current state
	 */
	public Document getDoc(){
		return this.doc;
	}
	
	/**
	 * Marshalls a signature and exports the XML to file
	 * @param absolutePath absolute file path location to save file including file name
	 * @throws XAdES4jException 
	 */
	public void print(File location){
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "8");
			
			
			// TODO: key and certificate should be passed here 
			//signer.signDocument();
			
			DOMSource source = new DOMSource(this.doc);
			StreamResult result = new StreamResult(location);
			transformer.transform(source, result);

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
