package com.zeva.tlGen.dataModel.settings;

import com.zeva.temp.jaxb.datamodel.NonEmptyMultiLangURIType;

public class NonNullURLBean extends MultiLangBean<NonEmptyMultiLangURIType>{

	public NonNullURLBean(NonEmptyMultiLangURIType dataType) {
		super(dataType);
	}

	public void setName(String name, String lang){
		dataType.setLang(lang);
		dataType.setValue(name);
		this.name.set(name);
	}

	
	@Override
	public boolean equals(Object o){
		if(o instanceof NonNullURLBean){
			
			NonNullURLBean other = (NonNullURLBean)o;
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
