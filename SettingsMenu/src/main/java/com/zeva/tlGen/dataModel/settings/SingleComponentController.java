package com.zeva.tlGen.dataModel.settings;

import biz.ui.controller.utils.IPopUpSaveController;

import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;

public interface SingleComponentController extends IPopUpSaveController{
	
	TSLSchemeInformationType getDataModelItem();
	
	void setDataModelItem(TSLSchemeInformationType modelItem);
	
}
