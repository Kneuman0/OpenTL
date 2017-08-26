package com.zeva.tlGen.maintest;

import java.io.File;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.JAXBException;

import com.zeva.temp.jaxb.datamodel.TrustStatusListType;
import com.zeva.tlGen.utils.xml.JAXBTrustListUnmarshallerV5;
import com.zeva.tlGen.utils.xml.XMLTrustListUnmarshaller;

public class testTrustListDataModel {

	
	public static void main(String[] args) throws JAXBException, XMLParseException{
		XMLTrustListUnmarshaller um = new JAXBTrustListUnmarshallerV5(
				new File("C:/Users/Karottop/Desktop/EU_71(Sn158).xml"));
		TrustStatusListType tl = um.getTrustList();
		System.out.println(tl.getSchemeInformation()
				.getPointersToOtherTSL().getOtherTSLPointer().get(0).getTSLLocation());
		
		
	}
}
