package com.zeva.tlGen.dataModel.abstraction;

public interface MultiLangType extends SettingsDataType{
	
	/**
	 * Gets the String Value
	 * @return
	 */
	String getValue();
	
	/**
	 * Gets the language
	 * @return
	 */
	String getLang();
	
	/**
	 * sets the value
	 * @param value
	 * @return
	 */
	void setValue(String value);
	
	/**
	 * sets the language
	 * @param lang
	 * @return
	 */
	void setLang(String lang);
}
