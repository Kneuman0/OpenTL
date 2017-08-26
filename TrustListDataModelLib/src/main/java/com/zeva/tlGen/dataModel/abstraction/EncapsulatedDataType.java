package com.zeva.tlGen.dataModel.abstraction;

import javafx.beans.property.SimpleStringProperty;

public abstract class EncapsulatedDataType <DataType extends SettingsDataType> {
	
	protected SimpleStringProperty name;
	protected DataType dataType;
	
	public EncapsulatedDataType(DataType dataType){
		this.name = new SimpleStringProperty(dataType.getName());
		this.dataType = dataType;
		
	}
	
	public String getStringName(){
		return name.get();
	}
	
	public SimpleStringProperty nameProperty(){
		return name;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
		
	@Override
	public abstract boolean equals(Object o);
	
	
}
