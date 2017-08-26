package com.zeva.tlGen.dataModel.settings;

import com.zeva.temp.jaxb.datamodel.MultiLangNormStringType;
import com.zeva.tlGen.dataModel.abstraction.EncapsulatedDataType;

import javafx.beans.property.SimpleStringProperty;

public class MultiLangNormBean extends EncapsulatedDataType<MultiLangNormStringType>{
		
	public MultiLangNormBean(MultiLangNormStringType name){
		super(name);
	}
		
	public void setName(String name, String lang){
		dataType.setLang(lang);
		dataType.setValue(name);
		this.name.set(name);
	}

	
	@Override
	public boolean equals(Object o){
		if(o instanceof MultiLangNormBean){
			
			MultiLangNormBean other = (MultiLangNormBean)o;
			if(other.getDataType().getLang() == dataType.getLang()
					&& other.getStringName() == getStringName()){
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}
	}
	
}
