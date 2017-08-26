package com.zeva.tlGen.controllers.settings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;

public class TslTypeController extends ComponentController{
	
    @FXML
    private ComboBox<String> tslType;
	
	@Override
	public void initialize() {
		initTslTypes();// TODO: Need to get all possible types to put into drop down menu
	}
	
	@Override
	public void save() {
		String type = tslType.getSelectionModel().getSelectedItem();
		settings.setTSLType(type == null ? "" : type);
	}
	
	private void initTslTypes(){
		ObservableList<String> types = FXCollections.<String>observableArrayList();
		types.add("Trust List");
		types.add("Trust List of Lists");
		tslType.setItems(types);
	}
	
	@Override
	public void setDataModelItem(TSLSchemeInformationType modelItem) {
		this.settings = modelItem;
		selectValue(settings.getTSLType(), tslType);
	}
	
	

}
