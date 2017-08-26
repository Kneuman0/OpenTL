package com.zeva.temp.dataModellib;

import java.util.HashMap;
import java.util.Map;

public class AdditionalInfoPointerBean {
	
	String tslType;
	String schemeTerritory;
	String mimeType;
	Map<String, String> opNames;
	Map<String, String> uris;
	
	public AdditionalInfoPointerBean(){
		this.opNames = new HashMap<>();
		this.uris = new HashMap<>();
	}
	
	/**
	 * @return the tslType
	 */
	public String getTslType() {
		return tslType;
	}
	/**
	 * @param tslType the tslType to set
	 */
	public void setTslType(String tslType) {
		this.tslType = tslType;
	}
	/**
	 * @return the schemeTerritory
	 */
	public String getSchemeTerritory() {
		return schemeTerritory;
	}
	/**
	 * @param schemeTerritory the schemeTerritory to set
	 */
	public void setSchemeTerritory(String schemeTerritory) {
		this.schemeTerritory = schemeTerritory;
	}
	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}
	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	/**
	 * @return the opNames
	 */
	public Map<String, String> getOpNames() {
		return opNames;
	}
	/**
	 * @param opNames the opNames to set
	 */
	public void addOpName(String uri, String lang) {
		this.opNames.put(uri, lang);
	}
	/**
	 * @return the uris
	 */
	public Map<String, String> getUris() {
		return uris;
	}
	/**
	 * @param uris the uris to set
	 */
	public void addURI(String uri, String lang) {
		this.uris.put(uri, lang);
	}
	
	

}
