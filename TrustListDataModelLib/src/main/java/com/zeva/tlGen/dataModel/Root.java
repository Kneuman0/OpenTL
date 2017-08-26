package com.zeva.tlGen.dataModel;

import javafx.beans.property.SimpleObjectProperty;

public class Root extends ProviderAttribute{
	
	private String name;
	private SimpleObjectProperty<ProviderAttribute> nameProp;
	
	public Root(String name){
		this.name = name;
		this.nameProp = new SimpleObjectProperty<ProviderAttribute>(this);
	}

	@Override
	public SimpleObjectProperty<ProviderAttribute> nameProperty() {
		// TODO Auto-generated method stub
		return nameProp;
	}

	@Override
	public ProviderAttributeType getType() {
		// TODO Auto-generated method stub
		return ProviderAttributeType.ROOT;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Root getEncapsulatedBean() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getStringName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public ProviderAttribute initialize() {
		// TODO Auto-generated method stub
		return this;
	}

}
