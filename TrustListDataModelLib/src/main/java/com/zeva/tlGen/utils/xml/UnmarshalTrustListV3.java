package com.zeva.tlGen.utils.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.time.ZonedDateTime;
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

public class UnmarshalTrustListV3 {
	private Document doc;
	
	public UnmarshalTrustListV3(File file) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, XMLSignatureException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(new FileInputStream(file), "UTF-8");
		
		doc.normalize();		
	}
	
	public List<OtherTSLPointer> getOtherTSLPointers(){
		NodeList nList = doc.getElementsByTagName("tsl:PointersToOtherTSL");
		List<OtherTSLPointer> pointers = new ArrayList<>();
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				pointers.add(new OtherTSLPointer(fix(el.getElementsByTagName(
						"tsl:TextualInformation").item(0).getTextContent())));
			}
		}
		
		return pointers;
		
	}
	
	public List<ServiceBean> getServiceBeans() throws CertificateException{
		NodeList nList = doc.getElementsByTagName("tsl:ServiceInformation");
		List<ServiceBean> services = new ArrayList<>();
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				ServiceBean service = new ServiceBean();
				
				service.setServiceTypeIdentifier(getElVal(el, "tsl:ServiceTypeIdentifier"));
				
				service.setServiceName(getElVal(el, "tsl:Name"));
				
				service.setLanguage(getLang());
				
				String begin = "-----BEGIN CERTIFICATE-----\n";
				String end = "\n-----END CERTIFICATE-----";
				String base64Cert = begin + getElVal(el, "tsl:X509Certificate")
						.replaceAll("[ \t\n]", "") + end;
				
				service.setBase64Cert(base64Cert);
									
				service.setX509Certificate(base64Cert.getBytes());
				
				service.setX509SKI(getElVal(el, "tsl:X509SKI"));
				
				service.setX509SubjectName(getElVal(el, "tsl:X509SubjectName"));
				
				service.setKeyValue(getElVal(el, "ds:KeyValue"));
				
				service.setServiceStatus(getElVal(el, "tsl:ServiceStatus"));
				
				service.setExtension(getElVal(el, "tsl:Extension"));
				
				service.setStatusStartingTime(ZonedDateTime.parse(
						getElVal(el, "tsl:StatusStartingTime"), XMLTrustListUnmarshaller.dateFormatter));
				
				services.add(service);
			// end of if	
			}
		// end of for loop
		}
		
		return services;
	}
	
	public StatusInformationBean getStatusInformationBean() throws DOMException, URISyntaxException{
		NodeList nList = doc.getElementsByTagName("tsl:SchemeInformation");
		StatusInformationBean status = new StatusInformationBean();
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				
				status.setTslVersionIdentifier(getElVal(el, "tsl:TSLVersionIdentifier"));
				
				status.setTslSequenceNumber(getElVal(el, "tsl:TSLSequenceNumber"));
				
				status.setTslType(getElVal(el, "tsl:TSLType"));
				
				Element schemeOperatorName = (Element)el.getElementsByTagName("tsl:SchemeOperatorName").item(0);
				status.setSchemeOperatorName(getMapValues("tsl:Name", schemeOperatorName));
				
				status.setAddress(getAddressBean("tsl:SchemeOperatorAddress"));
				
				Element schemeName = (Element)el.getElementsByTagName("tsl:SchemeName").item(0);
				status.setSchemeName(getMapValues("tsl:Name", schemeName));
				
				Element schemeInformationURI = (Element)el.getElementsByTagName("tsl:SchemeInformationURI").item(0);
				status.setSchemeInformationURI(getMapValues("tsl:URI", schemeInformationURI));
				
				status.setStatusDeterminationApproach(getElVal(el, "tsl:StatusDeterminationApproach"));
				
				status.setSchemeTerritory(getElVal(el, "tsl:SchemeTerritory"));
				
				Element schemeTypeCommunityRules = (Element)el.getElementsByTagName("tsl:SchemeTypeCommunityRules").item(0);
				status.setSchemeTypeCommunityRules(getMapValues("tsl:URI", schemeTypeCommunityRules));
				
				Element policyOrLegalNotice = (Element)el.getElementsByTagName("tsl:PolicyOrLegalNotice").item(0);
				status.setPolicyOrLegalNotice(getMapValues("tsl:TSLLegalNotice", policyOrLegalNotice));
				
				status.setHistoricalInformationPeriod(
						getElVal(el, "tsl:HistoricalInformationPeriod"));
				
				status.setPointers(getOtherTSLPointers());
				
				status.setListIssueDateTime(ZonedDateTime.parse(
						getElVal(el, "tsl:ListIssueDateTime"), XMLTrustListUnmarshaller.dateFormatter));
				
				status.setNextUpdate(ZonedDateTime.parse(
						getElVal(el, "tsl:dateTime"), XMLTrustListUnmarshaller.dateFormatter));
					
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
				
				address.setStreetAddress(getElVal(el, "tsl:StreetAddress"));
				
				address.setLocality(getElVal(el, "tsl:Locality"));
				
				address.setPostalCode(getElVal(el, "tsl:PostalCode"));
				
				address.setCountryName(getElVal(el, "tsl:CountryName"));
				
				addresses.addPhysAddress(address);
			}
		}
		
		// get ElectronicAddress node
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				
				NodeList elect = el.getElementsByTagName("tsl:ElectronicAddress");
				// get each URL node from ElectronicAddress node
				for(int index = 0; index < elect.getLength(); index++){
					if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
						
						addresses.setElecAddr(getMapValues("tsl:URL", (Element)nList.item(i)));
						
					}
				}
				
			}
		
		}
		
		return addresses;
	}
	
	public List<ProviderList> getProviderList() throws DOMException, URISyntaxException, CertificateException{
		NodeList nList = doc.getElementsByTagName("tsl:TrustServiceProvider");
		List<ProviderList> providers = new ArrayList<>();
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				ProviderList providerList = new ProviderList();
			
				providerList.setName(getElVal(el, "tsl:Name"));
				
				providerList.setLanguage(getLang());
				
				providerList.setAddress(getAddressBean("tsl:TSPAddress"));
				
				Element tSPInformationURI = (Element)el.getElementsByTagName("tsl:TSPInformationURI").item(0);				
				providerList.setTspInformationURI(getMapValues("tsl:URI", tSPInformationURI));
				
				providerList.setTspServices(getServiceBeans());
				
				providers.add(providerList);
				
			// end of if	
			}
		// end of for loop
		}
		
		return providers;
	}
	
	public TrustList getTrustList() throws DOMException, URISyntaxException, CertificateException{
		NodeList nList = doc.getElementsByTagName("tsl:TrustServiceStatusList");
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
	
	private static String fix(String text){
		return text == null ? null : text.replace("Node", "");
	}
	
	private String getLang(){
		NodeList nList = doc.getElementsByTagName("tsl:Name");
		
		for(int i = 0; i < nList.getLength(); i++){
			if(nList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element el = (Element)nList.item(i);
				return fix(el.getAttribute("xml:lang"));
			}
		}
		
		return null;
		
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
	
	public boolean signatureIsValid(IXmlSignatureVerifier verifier) throws Exception{
		// TODO: integate here
		verifier.Verify(null);
		return true;
	}

	public Document getDocument() {
		return doc;
	}
	
	
	

}
