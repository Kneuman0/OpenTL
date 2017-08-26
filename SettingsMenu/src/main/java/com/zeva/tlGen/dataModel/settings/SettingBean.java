package com.zeva.tlGen.dataModel.settings;

import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;

import javafx.beans.property.SimpleStringProperty;

public class SettingBean {
	
	public enum SETTING_TYPE {
		TSL_TYPE,
		SCHEME_OP_NAME,
		SCHEME_OP_ADDRESS,
		POLICY_OR_LEGAL_NOTICE,
		LIST_ISSUE_DATE_TIME,
		NEXT_UPDATE,
		DISTRIBUTION_POINTS};
	
	private SimpleStringProperty name;	
	private SETTING_TYPE type;
	private TSLSchemeInformationType settings;
	
	
	public SettingBean(String settingName, TSLSchemeInformationType settings){
		this.name = new SimpleStringProperty(settingName);
		this.settings = settings;
	}
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	
	public SimpleStringProperty nameProperty(){
		return name;
	}
	

	/**
	 * @return the type
	 */
	public SETTING_TYPE getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SETTING_TYPE type) {
		this.type = type;
	}


	public TSLSchemeInformationType getSettings() {
		return settings;
	}


	public void setSettings(TSLSchemeInformationType settings) {
		this.settings = settings;
	}

	
	
//	@Override
//	public boolean equals(Object setting){
//		// first make sure object is setting bean
//		if(setting instanceof SettingBean){
//			SettingBean otherBean = (SettingBean)setting;
//			
//			// next check if they same type
////			if(otherBean.getType() == this.getType()){
////				// last check if they have the same tag name
//				return otherBean.getName().equals(this.getName());
////			}else{
////				return false;
////			}
//		}else{
//			return false;
//		}
//	}
	
}
