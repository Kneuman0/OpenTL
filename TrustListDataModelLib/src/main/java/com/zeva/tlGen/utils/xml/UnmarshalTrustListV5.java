package com.zeva.tlGen.utils.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zeva.temp.dataModellib.AddressBean;
import com.zeva.temp.dataModellib.OtherTSLPointer;
import com.zeva.temp.dataModellib.PhysicalAddressBean;
import com.zeva.temp.dataModellib.ProviderList;
import com.zeva.temp.dataModellib.ServiceBean;
import com.zeva.temp.dataModellib.StatusInformationBean;
import com.zeva.temp.dataModellib.TrustList;
import com.zeva.tlGen.xml.dsig.IXmlSignatureVerifier;

public class UnmarshalTrustListV5 {
private Document doc;
	
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE_TIME;

	public UnmarshalTrustListV5(File file) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, XMLSignatureException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(new FileInputStream(file), "UTF-8");
		
		doc.normalize();		
		
	}
	
	public List<OtherTSLPointer> getOtherTSLPointers(){
		NodeList nList = doc.getElementsByTagName("PointersToOtherTSL");
		List<OtherTSLPointer> pointers = new ArrayList<>();
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				pointers.add(new OtherTSLPointer(fix(el.getElementsByTagName(
						"TextualInformation").item(0).getTextContent())));
			}
		}
		
		return pointers;
		
	}
	
	public List<ServiceBean> getServiceBeans() throws CertificateException{
		NodeList nList = doc.getElementsByTagName("ServiceInformation");
		List<ServiceBean> services = new ArrayList<>();
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				ServiceBean service = new ServiceBean();
				
				service.setServiceTypeIdentifier(getElVal(el, "ServiceTypeIdentifier"));
				
				service.setServiceName(getElVal(el, "Name"));
				
				service.setLanguage(getLang());
				
				String begin = "-----BEGIN CERTIFICATE-----\n";
				String end = "\n-----END CERTIFICATE-----";
				String base64Cert = begin + getElVal(el, "X509Certificate")
						.replaceAll("[ \t\n]", "") + end;
				
				service.setBase64Cert(base64Cert);
									
				service.setX509Certificate(base64Cert.getBytes());
				
				service.setX509SKI(getElVal(el, "X509SKI"));
				
				service.setX509SubjectName(getElVal(el, "X509SubjectName"));
				
				service.setKeyValue(getElVal(el, "ds:KeyValue"));
				
				service.setServiceStatus(getElVal(el, "ServiceStatus"));
				
				service.setExtension(getElVal(el, "Extension"));
				
				service.setStatusStartingTime(ZonedDateTime.parse(
						getElVal(el, "StatusStartingTime"), dateFormatter));
				
				services.add(service);
			// end of if	
			}
		// end of for loop
		}
		
		return services;
	}
	
	public StatusInformationBean getStatusInformationBean() throws DOMException, URISyntaxException{
		NodeList nList = doc.getElementsByTagName("SchemeInformation");
		StatusInformationBean status = new StatusInformationBean();
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				
				status.setTslVersionIdentifier(getElVal(el, "TSLVersionIdentifier"));
				
				status.setTslSequenceNumber(getElVal(el, "TSLSequenceNumber"));
				
				status.setTslType(getElVal(el, "TSLType"));
				
				Element schemeOperatorName = (Element)el.getElementsByTagName("SchemeOperatorName").item(0);
				status.setSchemeOperatorName(getMapValues("Name", schemeOperatorName));
				
				status.setAddress(getAddressBean("SchemeOperatorAddress"));
				
				Element schemeName = (Element)el.getElementsByTagName("SchemeName").item(0);
				status.setSchemeName(getMapValues("Name", schemeName));
				
				Element schemeInformationURI = (Element)el.getElementsByTagName("SchemeInformationURI").item(0);
				status.setSchemeInformationURI(getMapValues("URI", schemeInformationURI));
				
				status.setStatusDeterminationApproach(getElVal(el, "StatusDeterminationApproach"));
				
				status.setSchemeTerritory(getElVal(el, "SchemeTerritory"));
				
				Element schemeTypeCommunityRules = (Element)el.getElementsByTagName("SchemeTypeCommunityRules").item(0);
				status.setSchemeTypeCommunityRules(getMapValues("URI", schemeTypeCommunityRules));
				
				Element policyOrLegalNotice = (Element)el.getElementsByTagName("PolicyOrLegalNotice").item(0);
				status.setPolicyOrLegalNotice(getMapValues("TSLLegalNotice", policyOrLegalNotice));
				
				status.setHistoricalInformationPeriod(
						getElVal(el, "HistoricalInformationPeriod"));
				
				status.setPointers(getOtherTSLPointers());
				
				status.setListIssueDateTime(ZonedDateTime.parse(
						getElVal(el, "ListIssueDateTime"), XMLTrustListUnmarshaller.dateFormatter));
				
				status.setNextUpdate(ZonedDateTime.parse(
						getElVal(el, "dateTime"), XMLTrustListUnmarshaller.dateFormatter));
					
			// end of if
			}
		// end of for loop
		}
		
		return status;
	}
	
	public AddressBean getAddressBean(String tagName) throws DOMException, URISyntaxException{
		AddressBean addresses = new AddressBean();
		NodeList nList = doc.getElementsByTagName(tagName);
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
		
				PhysicalAddressBean address = new PhysicalAddressBean();
				
				address.setStreetAddress(getElVal(el, "StreetAddress"));
				
				address.setLocality(getElVal(el, "Locality"));
				
				address.setPostalCode(getElVal(el, "PostalCode"));
				
				address.setCountryName(getElVal(el, "CountryName"));
				
				addresses.addPhysAddress(address);
			}
		}
		
		// get ElectronicAddress node
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				
				NodeList elect = el.getElementsByTagName("ElectronicAddress");
				// get each URL node from ElectronicAddress node
				for(int index = 0; index < elect.getLength(); index++){
					if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
						
						addresses.setElecAddr(getMapValues("URL", (Element)nList.item(i)));
						
					}
				}
				
			}
		
		}
		
		return addresses;
	}
	
	public List<ProviderList> getProviderList() throws DOMException, URISyntaxException, CertificateException{
		NodeList nList = doc.getElementsByTagName("TrustServiceProvider");
		List<ProviderList> providers = new ArrayList<>();
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				ProviderList providerList = new ProviderList();
			
				providerList.setName(getElVal(el, "Name"));
				
				providerList.setLanguage(getLang());
				
				providerList.setAddress(getAddressBean("TSPAddress"));
				
				Element tSPInformationURI = (Element)el.getElementsByTagName("TSPInformationURI").item(0);				
				providerList.setTspInformationURI(getMapValues("URI", tSPInformationURI));
				
				providerList.setTspServices(getServiceBeans());
				
				providers.add(providerList);
				
			// end of if	
			}
		// end of for loop
		}
		
		return providers;
	}
	
	public TrustList getTrustList() throws DOMException, URISyntaxException, CertificateException{
		NodeList nList = doc.getElementsByTagName("TrustServiceStatusList");
		TrustList tl = new TrustList();
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				
				tl.setTsl(el.getAttribute("xmlns:tsl"));
				
				tl.setXAdES(el.getAttribute("xmlns:XAdES"));
				
				tl.setDs(el.getAttribute("xmlns:ds"));
				
				tl.setXsi(el.getAttribute("xmlns:xsi"));
				
				tl.setSchemaLocation(el.getAttribute("xsi:schemaLocation"));
				
				tl.setTSLTag(el.getAttribute("TSLTag"));
				
				tl.setId(el.getAttribute("Id"));
				
				tl.setStatusInfo(getStatusInformationBean());
				tl.setTsps(getProviderList());
			}
		}
		
		tl.setStatusInfo(getStatusInformationBean());
		tl.setTsps(getProviderList());
		return tl;
	}
	
	private static String fix(String text){
		return text == null ? null : text.replace("Node", "");
	}
	
	private String getLang(){
		NodeList nList = doc.getElementsByTagName("Name");
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				return fix(el.getAttribute("xml:lang"));
			}
		}
		
		return null;
		
	}
	
	private Map<String, String> getMapValues(String elName, Element parent){
		Map<String, String> values = new HashMap<String, String>();
		NodeList nList = parent.getElementsByTagName(elName);
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				String lang = el.getAttribute("xml:lang");
				String text = getElVal(parent, elName);
				values.put(text, lang);
			}
		}
		
		return values;
	}
	
	public static String getElVal(Element parent, String elementName){
		String unMarshalledText = null;
		try{
			unMarshalledText = fix(parent.getElementsByTagName(elementName)
					.item(0).getTextContent());
		} catch(NullPointerException e){
			e.printStackTrace();
		}
		
		return unMarshalledText;
	}
	
	public void dispose(){
		doc = null;
	}

	public Document getDocument() {
		return doc;
	}
	
	public boolean isValid(IXmlSignatureVerifier verifier) throws Exception{
		verifier.Verify(null);
		return true;
	}
	
	
	

}
