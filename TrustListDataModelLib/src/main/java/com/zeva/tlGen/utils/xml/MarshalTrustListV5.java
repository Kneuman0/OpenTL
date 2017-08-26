package com.zeva.tlGen.utils.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.zeva.temp.dataModellib.AddressBean;
import com.zeva.temp.dataModellib.OtherTSLPointer;
import com.zeva.temp.dataModellib.PhysicalAddressBean;
import com.zeva.temp.dataModellib.ProviderList;
import com.zeva.temp.dataModellib.ServiceBean;
import com.zeva.temp.dataModellib.StatusInformationBean;
import com.zeva.temp.dataModellib.TrustList;

public class MarshalTrustListV5 implements XMLTrustListMarshaller{
	Document doc;
	Element rootElement;
	private TrustList tl;
	
	public MarshalTrustListV5(Document doc){
		this.doc = doc;
		rootElement = doc.createElement("TrustServiceStatusList");
		doc.appendChild(rootElement);
	}
	
	public MarshalTrustListV5(TrustList tl){
		this.tl = tl;
		try{
			// initialize and build document
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.doc = builder.newDocument();
			this.doc.setXmlVersion("1.0");
			this.doc.setXmlStandalone(true);
			this.rootElement = this.doc.createElement("TrustServiceStatusList");
			this.doc.appendChild(this.rootElement);
			
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public Document marshalTrustList(TrustList tl){
		Attr tsl = doc.createAttribute("xmlns:tsl");
		tsl.setValue(tl.getTsl());
		rootElement.setAttributeNode(tsl);
		
		Attr xAdES = doc.createAttribute("xmlns:XAdES");
		xAdES.setValue(tl.getXAdES());
		rootElement.setAttributeNode(xAdES);
		
		Attr ds = doc.createAttribute("xmlns:ds");
		ds.setValue(tl.getDs());
		rootElement.setAttributeNode(ds);
		
		Attr xsi = doc.createAttribute("xmlns:xsi");
		xsi.setValue(tl.getXsi());
		rootElement.setAttributeNode(xsi);
		
		Attr schemaLocation = doc.createAttribute("xsi:schemaLocation");
		schemaLocation.setValue(tl.getSchemaLocation());
		rootElement.setAttributeNode(schemaLocation);
		
		Attr tSLTag = doc.createAttribute("TSLTag");
		tSLTag.setValue(tl.getTSLTag());
		rootElement.setAttributeNode(tSLTag);
		
		Attr id = doc.createAttribute("Id");
		id.setValue(tl.getId());
		rootElement.setAttributeNode(id);
		
		insertStatusInfo(tl.getStatusInfo());
		
		Element pList = doc.createElement("TrustServiceProviderList");
		rootElement.appendChild(pList);
		insertProviderList(pList, tl.getTsps());
		
		return doc;
	}
	
	public void insertProviderList(Element parent, List<ProviderList> list){
		for(ProviderList prov : list){
			Element provider = doc.createElement("TrustServiceProvider");
			parent.appendChild(provider);
			
			Element tspInfo = doc.createElement("TSPInformation");
			provider.appendChild(tspInfo);
			
			Element tspName = doc.createElement("TSPName");
			tspInfo.appendChild(tspName);
			
			Element name = doc.createElement("Name");
			name.setAttribute("xml:lang", prov.getLanguage());
			name.setTextContent(prov.getName());
			tspName.appendChild(name);
			
			Element tspAddr = doc.createElement("TSPAddress");
			insertAddress(tspAddr, prov.getAddress());
			tspInfo.appendChild(tspAddr);
			
			Element tspAddressURI = doc.createElement("TSPInformationURI");
			appendMapValues("URI", prov.getTspInformationURI(), tspAddressURI);
			tspInfo.appendChild(tspAddressURI);
			
			Element tspServices = doc.createElement("TSPServices");
			provider.appendChild(tspServices);
			insertServices(tspServices, prov.getTspServices());
		}
	}
	
	public void insertServices(Element parent, List<ServiceBean> services){
		for(ServiceBean service : services){
			Element serv = doc.createElement("TSPService");
			parent.appendChild(serv);
			
			Element servInfo = doc.createElement("ServiceInformation");
			serv.appendChild(servInfo);
			
			Element servTypeID = doc.createElement("ServiceTypeIdentifier");
			servTypeID.setTextContent(service.getServiceTypeIdentifier());
			servInfo.appendChild(servTypeID);
			
			Element servName = doc.createElement("ServiceName");
			servInfo.appendChild(servName);
			Element name = doc.createElement("Name");
			name.setTextContent(service.getServiceName());
			name.setAttribute("xml:lang", service.getLanguage());
			servName.appendChild(name);
			
			Element digID = doc.createElement("ServiceDigitalIdentity");
			servInfo.appendChild(digID);
			
			Element x509Cert = doc.createElement("X509Certificate");
			x509Cert.setTextContent(service.getBase64EncodedCert());
			insertDigitalID(digID, x509Cert);
			
			Element ski = doc.createElement("X509SKI");
			ski.setTextContent(service.getX509SKI());
			insertDigitalID(digID, ski);
			
			Element x509Subject = doc.createElement("X509SubjectName");
			x509Subject.setTextContent(service.getX509SubjectName());
			insertDigitalID(digID, x509Subject);
			
			Element keyVal = doc.createElement("ds:KeyValue");
			keyVal.setTextContent(service.getKeyValue());
			insertDigitalID(digID, keyVal);
			
			Element serStatus = doc.createElement("ServiceStatus");
			serStatus.setTextContent(service.getServiceStatus());
			servInfo.appendChild(serStatus);
			
			Element statusStart = doc.createElement("StatusStartingTime");
			statusStart.setTextContent(
					service.getStatusStartingTime().format(XMLTrustListUnmarshaller.dateFormatter));
			servInfo.appendChild(statusStart);
			
			Element serInfoExt = doc.createElement("ServiceInformationExtensions");
			servInfo.appendChild(serInfoExt);
			// need to loop this
			Element exts = doc.createElement("Extension");
			exts.setTextContent(service.getExtension());
			serInfoExt.appendChild(exts);
		}
		
	}
	
	public void insertDigitalID(Element parent, Element child){
		Element digitalID = doc.createElement("DigitalId");
		parent.appendChild(digitalID);
		digitalID.appendChild(child);
	}
	
	
	public void insertStatusInfo(StatusInformationBean status){
		Element statusEl = doc.createElement("SchemeInformation");
		rootElement.appendChild(statusEl);
		
		Element tslVersId = doc.createElement("TSLVersionIdentifier");
		tslVersId.setTextContent(status.getTslVersionIdentifier());
		statusEl.appendChild(tslVersId);
		
		Element tslSeqNum = doc.createElement("TSLSequenceNumber");
		tslSeqNum.setTextContent(status.getTslSequenceNumber());
		statusEl.appendChild(tslSeqNum);
		
		Element tslType = doc.createElement("TSLType");
		tslType.setTextContent(status.getTslType());
		statusEl.appendChild(tslType);
		
		Element schemeOpName = doc.createElement("SchemeOperatorName");
		appendMapValues("Name", status.getSchemeOperatorName(), schemeOpName);
		statusEl.appendChild(schemeOpName);
		
		Element schemePostOp = doc.createElement("SchemeOperatorAddress");
		insertAddress(schemePostOp, status.getAddress());
		statusEl.appendChild(schemePostOp);
		
		Element schemeName = doc.createElement("SchemeName");
		appendMapValues("Name", status.getSchemeName(), schemeName);
		statusEl.appendChild(schemeName);
		
		Element schemeInfoURI = doc.createElement("SchemeInformationURI");
		appendMapValues("URI", status.getSchemeInformationURI(), schemeInfoURI);
		statusEl.appendChild(schemeInfoURI);
		
		Element statDetApp = doc.createElement("StatusDeterminationApproach");
		statDetApp.setTextContent(status.getStatusDeterminationApproach());
		statusEl.appendChild(statDetApp);
		
		Element schemeComunRules = doc.createElement("SchemeTypeCommunityRules");
		appendMapValues("URI", status.getSchemeTypeCommunityRules(), schemeComunRules);
		statusEl.appendChild(schemeComunRules);
		
		Element schemeTerr = doc.createElement("SchemeTerritory");
		schemeTerr.setTextContent(status.getSchemeTerritory());
		statusEl.appendChild(schemeTerr);
		
		Element policLegNot = doc.createElement("PolicyOrLegalNotice");
		appendMapValues("TSLLegalNotice", status.getPolicyOrLegalNotice(), policLegNot);
		statusEl.appendChild(policLegNot);
		
		Element histInfoPer = doc.createElement("HistoricalInformationPeriod");
		histInfoPer.setTextContent(status.getHistoricalInformationPeriod());
		statusEl.appendChild(histInfoPer);
		
		Element pointersToOtherTSL = doc.createElement("PointersToOtherTSL");
		insertPointers(pointersToOtherTSL, status.getPointers());
		statusEl.appendChild(pointersToOtherTSL);
		
		Element listIssuDate = doc.createElement("ListIssueDateTime");
		listIssuDate.setTextContent(status.getListIssueDateTime().format(
				XMLTrustListUnmarshaller.dateFormatter));
		statusEl.appendChild(listIssuDate);
		
		Element nextUpdate = doc.createElement("NextUpdate");
		statusEl.appendChild(nextUpdate);
		Element dateTime = doc.createElement("dateTime");
		dateTime.setTextContent(status.getNextUpdate().format(
				XMLTrustListUnmarshaller.dateFormatter));
		nextUpdate.appendChild(dateTime);
	}
	
	public void insertAddress(Element el, AddressBean adds){
		// append "addresses" to previous element
		Element postAdds = doc.createElement("PostalAddresses");
		el.appendChild(postAdds);
		
		for(PhysicalAddressBean add : adds.getPhysAddr()){
		
			// Append "Address" to addresses
			Element postAdd = doc.createElement("PostalAddress");
			postAdds.setAttribute("xml:lang", add.getLang());
			postAdds.appendChild(postAdd);
			
			// append address info to address
			Element street = doc.createElement("StreetAddress");
			street.setTextContent(add.getStreetAddress());
			postAdd.appendChild(street);
			
			Element loc = doc.createElement("Locality");
			loc.setTextContent(add.getLocality());
			postAdd.appendChild(loc);
			
			Element postCode = doc.createElement("PostalCode");
			postCode.setTextContent(add.getPostalCode());
			postAdd.appendChild(postCode);
			
			Element countryName = doc.createElement("CountryName");
			countryName.setTextContent(add.getCountryName());
			postAdd.appendChild(countryName);
		}
		
		// append electonic address to parent node
		Element elecAddr = doc.createElement("ElectronicAddress");
		el.appendChild(elecAddr);
		appendMapValues("URI", adds.getElecAddr(), elecAddr);
		
	}
	
	public void insertPointers(Element parent, List<OtherTSLPointer> pointers){
		for(OtherTSLPointer point : pointers){
			Element othertslPointer = doc.createElement("OtherTSLPointer");
			parent.appendChild(othertslPointer);
			
			Element addInfo = doc.createElement("AdditionalInformation");
			othertslPointer.appendChild(addInfo);
			
			Element textInfo = doc.createElement("TextualInformation");
			textInfo.setTextContent(point.getTextualInformation());
			addInfo.appendChild(textInfo);
		}
	}
	
	public void appendMapValues(String elementTagName, Map<String, String> values, Element parent){
		Iterator<String> itr = values.keySet().iterator();
		while(itr.hasNext()){
			Element value = doc.createElement(elementTagName);
			String text = itr.next();
			String lang = values.get(text);
			value.setAttribute("xml:lang", lang);
			value.setTextContent(text);
			parent.appendChild(value);
		}
	}

	@Override
	public Document getMarshalledDocument() {
		return doc;
	}

	@Override
	public void marshalTrustList() {
		Attr tsl = doc.createAttribute("xmlns:tsl");
		tsl.setValue(tl.getTsl());
		rootElement.setAttributeNode(tsl);
		
		Attr xAdES = doc.createAttribute("xmlns:XAdES");
		xAdES.setValue(tl.getXAdES());
		rootElement.setAttributeNode(xAdES);
		
		Attr ds = doc.createAttribute("xmlns:ds");
		ds.setValue(tl.getDs());
		rootElement.setAttributeNode(ds);
		
		Attr xsi = doc.createAttribute("xmlns:xsi");
		xsi.setValue(tl.getXsi());
		rootElement.setAttributeNode(xsi);
		
		Attr schemaLocation = doc.createAttribute("xsi:schemaLocation");
		schemaLocation.setValue(tl.getSchemaLocation());
		rootElement.setAttributeNode(schemaLocation);
		
		Attr tSLTag = doc.createAttribute("TSLTag");
		tSLTag.setValue(tl.getTSLTag());
		rootElement.setAttributeNode(tSLTag);
		
		Attr id = doc.createAttribute("Id");
		id.setValue(tl.getId());
		rootElement.setAttributeNode(id);
		
		insertStatusInfo(tl.getStatusInfo());
		
		Element pList = doc.createElement("TrustServiceProviderList");
		rootElement.appendChild(pList);
		insertProviderList(pList, tl.getTsps());
		
	}
}
