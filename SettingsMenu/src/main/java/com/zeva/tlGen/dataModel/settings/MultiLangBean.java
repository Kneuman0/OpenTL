package com.zeva.tlGen.dataModel.settings;

import com.zeva.tlGen.dataModel.abstraction.EncapsulatedDataType;
import com.zeva.tlGen.dataModel.abstraction.MultiLangType;

public abstract class MultiLangBean <Type extends MultiLangType> extends EncapsulatedDataType<Type>{

	public MultiLangBean(Type dataType) {
		super(dataType);
	}
	
	public void setName(String name, String lang){
		dataType.setLang(lang);
		dataType.setValue(name);
		this.name.set(name);
	}

	
	@Override
	public boolean equals(Object o){
		if(o instanceof MultiLangBean){
			
			@SuppressWarnings("unchecked")
			MultiLangBean<MultiLangType> other = (MultiLangBean<MultiLangType>)o;
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
