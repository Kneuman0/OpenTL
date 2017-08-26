package com.zeva.tlGen.dataModel;

import javafx.beans.property.SimpleObjectProperty;

public abstract class TLPointer {	
	
	public abstract SimpleObjectProperty<TLPointer> nameProperty();
	
	public abstract TLPointer initialize();
	
	public abstract <Type extends TLPointer> Type getEncapsulatedBean();
	
	public abstract String toString();
	
	public abstract String getStringName();

}
