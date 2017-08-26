package com.zeva.temp.dataModellib;

import java.util.ArrayList;
import java.util.List;

public class TrustList {
	String tsl;
	String XAdES;
	String ds;
	String xsi;
	String schemaLocation;
	String TSLTag;
	String id;
	// this is the info before the TSP list
	StatusInformationBean statusInfo;
	// this is the list of TSPs
	List<ProviderList> tsps;
	
	public TrustList(){
		this.tsps = new ArrayList<>();
	}
		
	/**
	 * @return the tsl
	 */
	public String getTsl() {
		return tsl;
	}
	/**
	 * @param tsl the tsl to set
	 */
	public void setTsl(String tsl) {
		this.tsl = tsl;
	}
	/**
	 * @return the xAdES
	 */
	public String getXAdES() {
		return XAdES;
	}
	/**
	 * @param xAdES the xAdES to set
	 */
	public void setXAdES(String xAdES) {
		XAdES = xAdES;
	}
	/**
	 * @return the ds
	 */
	public String getDs() {
		return ds;
	}
	/**
	 * @param ds the ds to set
	 */
	public void setDs(String ds) {
		this.ds = ds;
	}
	/**
	 * @return the xsi
	 */
	public String getXsi() {
		return xsi;
	}
	/**
	 * @param xsi the xsi to set
	 */
	public void setXsi(String xsi) {
		this.xsi = xsi;
	}
	/**
	 * @return the schemaLocation
	 */
	public String getSchemaLocation() {
		return schemaLocation;
	}
	/**
	 * @param schemaLocation the schemaLocation to set
	 */
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	/**
	 * @return the tSLTag
	 */
	public String getTSLTag() {
		return TSLTag;
	}
	/**
	 * @param tSLTag the tSLTag to set
	 */
	public void setTSLTag(String tSLTag) {
		TSLTag = tSLTag;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the statusInfo
	 */
	public StatusInformationBean getStatusInfo() {
		return statusInfo;
	}
	/**
	 * @param statusInfo the statusInfo to set
	 */
	public void setStatusInfo(StatusInformationBean statusInfo) {
		this.statusInfo = statusInfo;
	}
	/**
	 * @return the tsps
	 */
	public List<ProviderList> getTsps() {
		return tsps;
	}
	/**
	 * @param tsps the tsps to set
	 */
	public void setTsps(List<ProviderList> tsps) {
		this.tsps = tsps;
	}
	
	
}
