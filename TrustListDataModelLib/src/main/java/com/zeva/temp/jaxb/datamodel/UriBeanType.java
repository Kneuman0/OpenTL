package com.zeva.temp.jaxb.datamodel;

import com.zeva.tlGen.dataModel.abstraction.SettingsDataType;

public class UriBeanType implements SettingsDataType{

	private String uri;
	
	public UriBeanType(String uri) {
		this.uri = uri;
	}
	
	@Override
	public String getName() {
		return uri;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	

}
