package com.zeva.tlGen.dataModel.settings;

import com.zeva.temp.jaxb.datamodel.MultiLangStringType;
import com.zeva.tlGen.dataModel.abstraction.EncapsulatedDataType;

public class MultiLangStringBean extends EncapsulatedDataType<MultiLangStringType>{

	public MultiLangStringBean(MultiLangStringType dataType) {
		super(dataType);
		// TODO Auto-generated constructor stub
	}

	public void setName(String name, String lang){
		dataType.setLang(lang);
		dataType.setValue(name);
		this.name.set(name);
	}

	
	@Override
	public boolean equals(Object o){
		if(o instanceof MultiLangStringBean){
			
			MultiLangStringBean other = (MultiLangStringBean)o;
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
