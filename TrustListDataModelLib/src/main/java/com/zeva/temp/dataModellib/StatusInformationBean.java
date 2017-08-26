package com.zeva.temp.dataModellib;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zeva.temp.dataModellib.AddressBean;
import com.zeva.temp.dataModellib.OtherTSLPointer;

public class StatusInformationBean {
	String tslVersionIdentifier;
	String tslSequenceNumber;
	String tslType;
	Map<String, String> schemeOperatorName;
	AddressBean address;
	Map<String, String> schemeName;
	Map<String, String> schemeInformationURI;
	String statusDeterminationApproach;
	Map<String, String> schemeTypeCommunityRules;
	String schemeTerritory;
	Map<String, String> policyOrLegalNotice;
	// Might turn this into ZonedDateTime data type
	String historicalInformationPeriod;
	List<OtherTSLPointer> pointers;
	ZonedDateTime listIssueDateTime;
	// is a nested element where the parent is NextUpdate and this nested element is dateTime
	ZonedDateTime nextUpdate;
	
	public StatusInformationBean(){
		this.pointers = new ArrayList<OtherTSLPointer>();
		this.schemeOperatorName = new HashMap<>();
		this.schemeName = new HashMap<>();
		this.schemeInformationURI = new HashMap<>();
		this.schemeTypeCommunityRules = new HashMap<>();
		this.policyOrLegalNotice = new HashMap<>();
		this.listIssueDateTime = ZonedDateTime.now();
		// need to find out how many hours/days/etc.. the list will be active for
		this.nextUpdate = ZonedDateTime.now().plusHours(6);
	}
	
	/**
	 * @return the tslVersionIdentifier
	 */
	public String getTslVersionIdentifier() {
		return tslVersionIdentifier;
	}
	/**
	 * @param tslVersionIdentifier the tslVersionIdentifier to set
	 */
	public void setTslVersionIdentifier(String tslVersionIdentifier) {
		this.tslVersionIdentifier = tslVersionIdentifier;
	}
	/**
	 * @return the tslSequenceNumber
	 */
	public String getTslSequenceNumber() {
		return tslSequenceNumber;
	}
	/**
	 * @param tslSequenceNumber the tslSequenceNumber to set
	 */
	public void setTslSequenceNumber(String tslSequenceNumber) {
		this.tslSequenceNumber = tslSequenceNumber;
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
	 * @return the schemeOperatorName
	 */
	public Map<String, String> getSchemeOperatorName() {
		return schemeOperatorName;
	}
	
	/**
	 * @param schemeOperatorName the schemeOperatorName to set
	 */
	public void addSchemeOperatorName(String schemeOperatorName, String lang) {
		this.schemeOperatorName.put(schemeOperatorName, lang);
	}
	
	/**
	 * @param schemeOperatorName the schemeOperatorName to set
	 */
	public void setSchemeOperatorName(Map<String, String> schemeOperatorName) {
		this.schemeOperatorName = schemeOperatorName;
	}
	/**
	 * @return the address
	 */
	public AddressBean getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(AddressBean address) {
		this.address = address;
	}
	/**
	 * @return the schemeName
	 */
	public Map<String, String> getSchemeName() {
		return schemeName;
	}
	
	/**
	 * @param schemeName the schemeName to set
	 */
	public void addSchemeName(String schemeName, String lang) {
		this.schemeName.put(schemeName, lang);
	}
	
	/**
	 * @param schemeName the schemeName to set
	 */
	public void setSchemeName(Map<String, String> schemeName) {
		this.schemeName = schemeName;
	}
	/**
	 * @return the schemeInformationURI
	 */
	public Map<String, String> getSchemeInformationURI() {
		return schemeInformationURI;
	}
	/**
	 * @param schemeInformationURI the schemeInformationURI to set
	 */
	public void setSchemeInformationURI(Map<String, String> schemeInfoURI) {
		this.schemeInformationURI = schemeInfoURI;
	}
	
	public void addSchemeInformationURI(String schemeInformationURI, String lang) {
		this.schemeInformationURI.put(schemeInformationURI, lang);
	}
	/**
	 * @return the statusDeterminationApproach
	 */
	public String getStatusDeterminationApproach() {
		return statusDeterminationApproach;
	}
	/**
	 * @param statusDeterminationApproach the statusDeterminationApproach to set
	 */
	public void setStatusDeterminationApproach(String statusDeterminationApproach) {
		this.statusDeterminationApproach = statusDeterminationApproach;
	}
	/**
	 * @return the schemeTypeCommunityRules
	 */
	public Map<String, String> getSchemeTypeCommunityRules() {
		return schemeTypeCommunityRules;
	}
	
	/**
	 * @param schemeTypeCommunityRules the schemeTypeCommunityRules to set
	 */
	public void addSchemeTypeCommunityRules(String schemeTypeCommunityRules, String lang) {
		this.schemeTypeCommunityRules.put(schemeTypeCommunityRules, lang);
	}
	
	/**
	 * @param schemeTypeCommunityRules the schemeTypeCommunityRules to set
	 */
	public void setSchemeTypeCommunityRules(Map<String, String> schemeTypeCommunity) {
		this.schemeTypeCommunityRules = schemeTypeCommunity;
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
	 * @return the policyOrLegalNotice
	 */
	public Map<String, String> getPolicyOrLegalNotice() {
		return policyOrLegalNotice;
	}
	
	/**
	 * @param policyOrLegalNotice the policyOrLegalNotice to set
	 */
	public void setPolicyOrLegalNotice(Map<String, String> policy) {
		this.policyOrLegalNotice = policy;
	}
	
	/**
	 * @param policyOrLegalNotice the policyOrLegalNotice to set
	 */
	public void addPolicyOrLegalNotice(String policyOrLegalNotice, String lang) {
		this.policyOrLegalNotice.put( policyOrLegalNotice, lang);
	}
	/**
	 * @return the historicalInformationPeriod
	 */
	public String getHistoricalInformationPeriod() {
		return historicalInformationPeriod;
	}
	/**
	 * @param historicalInformationPeriod the historicalInformationPeriod to set
	 */
	public void setHistoricalInformationPeriod(String historicalInformationPeriod) {
		this.historicalInformationPeriod = historicalInformationPeriod;
	}
	/**
	 * @return the pointers
	 */
	public List<OtherTSLPointer> getPointers() {
		return pointers;
	}
	/**
	 * @param pointers the pointers to set
	 */
	public void setPointers(List<OtherTSLPointer> pointers) {
		this.pointers = pointers;
	}
	/**
	 * @return the listIssueDateTime
	 */
	public ZonedDateTime getListIssueDateTime() {
		return listIssueDateTime;
	}
	/**
	 * @param listIssueDateTime the listIssueDateTime to set
	 */
	public void setListIssueDateTime(ZonedDateTime listIssueDateTime) {
		this.listIssueDateTime = listIssueDateTime;
	}
	/**
	 * @return the nextUpdate
	 */
	public ZonedDateTime getNextUpdate() {
		return nextUpdate;
	}
	/**
	 * @param nextUpdate the nextUpdate to set
	 */
	public void setNextUpdate(ZonedDateTime nextUpdate) {
		this.nextUpdate = nextUpdate;
	}
	
	
}
