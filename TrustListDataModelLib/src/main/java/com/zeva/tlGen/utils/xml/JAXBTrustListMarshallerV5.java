package com.zeva.tlGen.utils.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

import com.zeva.temp.jaxb.datamodel.TrustStatusListType;

public class JAXBTrustListMarshallerV5 implements XMLTrustListMarshaller{

	private TrustStatusListType tl;
	private Document doc;
	
	public JAXBTrustListMarshallerV5(TrustStatusListType tl) {
		if(tl == null)
			throw new NullPointerException("Trust List cannot be null");
		else{
			this.tl = tl;
		}
		try{
			// initialize and build document
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.doc = builder.newDocument();
			this.doc.setXmlVersion("1.0");
			this.doc.setXmlStandalone(true);
			
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void marshalTrustList() {
		try {
			JAXBContext context = JAXBContext.newInstance(tl.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(tl, doc);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Document getMarshalledDocument() {
		return doc;
	}

}
