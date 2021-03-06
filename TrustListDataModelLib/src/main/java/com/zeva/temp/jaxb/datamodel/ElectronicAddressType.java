//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.16 at 11:23:28 AM EST 
//


package com.zeva.temp.jaxb.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.zeva.tlGen.dataModel.abstraction.SettingsDataType;


/**
 * <p>Java class for ElectronicAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElectronicAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="URI" type="{http://uri.etsi.org/02231/v2#}NonEmptyURIType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElectronicAddressType", propOrder = {
    "uri"
})
public class ElectronicAddressType {

    @XmlElement(name = "URI", required = true)
    @XmlSchemaType(name = "anyURI")
    protected List<String> uri;
    
    @XmlTransient
    private List<UriBeanType> uris;

    /**
     * Gets the value of the uri property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uri property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getURI().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getURI() {
        if (uri == null) {
            uri = new ArrayList<String>();
        }
        return this.uri;
    }
    
    public List<UriBeanType> getURIBeans(){
    	getUris().clear();
    	for(String uri : getURI()){
    		uris.add(new UriBeanType(uri));
    	}
    	
    	return uris;
    }
    
    public void update(List<UriBeanType> beans){
    	getURI().clear();
    	for(UriBeanType uri : beans){
    		getURI().add(uri.getUri());
    	}
    }
    
    public void unwrapUris(){
    	getURI().clear();
    	for(UriBeanType uri : uris){
    		getURI().add(uri.getUri());
    	}
    }


	public List<UriBeanType> getUris() {
		if(uris == null){
			uris = new ArrayList<UriBeanType>();
			return uris;
		}
		
		return uris;
	}

	public void setUris(List<UriBeanType> uris) {
		this.uris = uris;
	}
	
	

}
