package com.zeva.tlGen.controllers.settings;

import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.tlGen.dataModel.settings.SingleComponentController;

public abstract class SingleValueController <DataType> extends ComponentController implements SingleComponentController{
	
	protected DataType type;
	
	@Override
	public abstract void initialize();

	@Override
	public void setDataModelItem(TSLSchemeInformationType modelItem) {
		this.type = getDataTypeFromModel(modelItem);
	}
	
	protected abstract DataType getDataTypeFromModel(TSLSchemeInformationType modelItem);

	@Override
	public abstract void save();

}
