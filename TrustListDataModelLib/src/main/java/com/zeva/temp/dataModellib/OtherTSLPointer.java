package com.zeva.temp.dataModellib;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class OtherTSLPointer {
	// this is an attribute inside element AdditionalInformation
	String textualInformation;
	AdditionalInfoPointerBean additionalInfo;
	private List<X509Certificate> certs;
	
	public OtherTSLPointer(String info, AdditionalInfoPointerBean bean, List<X509Certificate> certs){
		this.textualInformation = info;
		this.additionalInfo = bean;
		this.certs = certs;
	}
	
	public OtherTSLPointer(){
		this.certs = new ArrayList<>();
	}
	
	/**
	 * This is a temporary constructor
	 * @param info
	 */
	public OtherTSLPointer(String info){
		this.certs = new ArrayList<>();
	}

	/**
	 * @return the textualInformation
	 */
	public String getTextualInformation() {
		return textualInformation;
	}

	/**
	 * @param textualInformation the textualInformation to set
	 */
	public void setTextualInformation(String textualInformation) {
		this.textualInformation = textualInformation;
	}

	/**
	 * @return the certs
	 */
	public List<X509Certificate> getCerts() {
		return certs;
	}

	/**
	 * @param certs the certs to set
	 */
	public void setCerts(List<X509Certificate> certs) {
		this.certs = certs;
	}
	
	
	
}
