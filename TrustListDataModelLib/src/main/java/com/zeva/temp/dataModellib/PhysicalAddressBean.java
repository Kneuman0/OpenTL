package com.zeva.temp.dataModellib;

public class PhysicalAddressBean {
	String streetAddress;
	String locality;
	String postalCode;
	String countryName;
	String lang;
	
	public PhysicalAddressBean(String propertyValue){
		try {
			String[] values = propertyValue.split(";");
			this.streetAddress = values[0];
			this.locality = values[1];
			this.postalCode = values[2];
			this.countryName = values[3];
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			System.out.println("No Address or incomplete Address: " + e.getMessage());
		}
	}
	
	public PhysicalAddressBean(String streetAddress, String locality, 
			String postalCode, String countryCode, String lang){
		this.streetAddress = streetAddress;
		this.locality = locality;
		this.postalCode = postalCode;
		this.countryName = countryCode;
		this.lang = lang;
	}
	
	public PhysicalAddressBean(){}
	
	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}
	
	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}
	/**
	 * @param locality the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}
	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	
	
	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	@Override
	public String toString(){
		String value = "";
		value += "Street Address: " + streetAddress + "\n";
		value += "Locality: " + locality + "\n";
		value += "Postal Code: " + postalCode + "\n";
		value += "Country Name: " + countryName + "\n";
		
		return value;
	}
	
	
}
