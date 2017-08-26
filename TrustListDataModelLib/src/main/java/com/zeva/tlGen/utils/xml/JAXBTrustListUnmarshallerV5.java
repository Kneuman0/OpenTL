package com.zeva.tlGen.utils.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

import com.zeva.temp.jaxb.datamodel.OtherTSLPointerType;
import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.temp.jaxb.datamodel.TSPServiceType;
import com.zeva.temp.jaxb.datamodel.TSPType;
import com.zeva.temp.jaxb.datamodel.TrustStatusListType;

public class JAXBTrustListUnmarshallerV5 implements XMLTrustListUnmarshaller{
	
	private Document doc;
	Unmarshaller um;
	private TrustStatusListType tl;
	

	@SuppressWarnings("unchecked")
	public JAXBTrustListUnmarshallerV5(File file) throws JAXBException, XMLParseException {
		JAXBContext context = JAXBContext.newInstance(TrustStatusListType.class);
		um = context.createUnmarshaller();
		um.setSchema(null);
		JAXBElement<TrustStatusListType> fact = null;
		try {
			fact = (JAXBElement<TrustStatusListType>) um.unmarshal(file);
			this.tl = (TrustStatusListType)fact.getValue();
		} catch (ClassCastException e) {
			System.out.println("Trying alternate unmarshalling method");
			try {
				Source source = new StreamSource(new FileInputStream(file));
				fact = um.unmarshal(source, TrustStatusListType.class);
				this.tl = fact.getValue();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(tl == null) throw new XMLParseException("XML could not be parsed");
	}


	@Override
	public List<OtherTSLPointerType> getOtherTSLPointers() {
		return getSchemeInfoBean().getPointersToOtherTSL().getOtherTSLPointer();
	}


	@Override
	public List<TSPServiceType> getServiceBeans(int providerIndex){
		return getProviderList().get(providerIndex).getTSPServices().getTSPService();
	}


	@Override
	public TSLSchemeInformationType getSchemeInfoBean() {
		return tl.getSchemeInformation();
	}


	@Override
	public List<TSPType> getProviderList() {
		return tl.getTrustServiceProviderList().getTrustServiceProvider();
	}


	@Override
	public TrustStatusListType getTrustList(){
		return tl;
	}


	@Override
	public Document getDocument() {
		return doc;
	}

	
	

}
