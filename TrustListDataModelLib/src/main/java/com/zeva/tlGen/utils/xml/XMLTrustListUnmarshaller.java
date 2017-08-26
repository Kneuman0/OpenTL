package com.zeva.tlGen.utils.xml;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.w3c.dom.Document;

import com.zeva.temp.jaxb.datamodel.OtherTSLPointerType;
import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.temp.jaxb.datamodel.TSPServiceType;
import com.zeva.temp.jaxb.datamodel.TSPType;
import com.zeva.temp.jaxb.datamodel.TrustStatusListType;

public interface XMLTrustListUnmarshaller {
	
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE_TIME;
	
	public List<OtherTSLPointerType> getOtherTSLPointers();
	
	public List<TSPServiceType> getServiceBeans(int providerIndex);
	
	public TSLSchemeInformationType getSchemeInfoBean();
		
	public List<TSPType> getProviderList();
	
	public TrustStatusListType getTrustList();
	
	public Document getDocument();
	
}
