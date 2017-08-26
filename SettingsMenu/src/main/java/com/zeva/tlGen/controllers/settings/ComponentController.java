package com.zeva.tlGen.controllers.settings;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import biz.ui.controller.utils.IPopUpSaveController;

import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.tlGen.dataModel.settings.SingleComponentController;

public abstract class ComponentController implements SingleComponentController, IPopUpSaveController{
	
	protected TSLSchemeInformationType settings;
	protected Stage stage;
	
    @FXML
    private ComboBox<String> tslType;
	
	public abstract void initialize();
	
	
	@Override
	public TSLSchemeInformationType getDataModelItem() {
		return settings;
	}

	public abstract void setDataModelItem(TSLSchemeInformationType modelItem);

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public abstract void save();
	
	protected ObservableList<String> getlangs(){
		String[] langs = {"en", "bd", "bg-Latn", "ca", "cs"
				, "da", "de", "el", "el-Latn", "es", "et", "eu",
				"fi", "fr", "ga", "gl", "hr", "hu", "is",
				"it", "lv", "mt", "nb", "nl", "nn", "no",
				"pl", "pt", "ro", "sk", "sl", "sv", "tr"};
		return FXCollections.observableArrayList(Arrays.asList(langs));
	}
	
	protected void selectValue(String value, ComboBox<String> box){
		ObservableList<String> items = box.getItems();
		for(int i = 0; i < items.size(); i++){
			if(value.toLowerCase().trim().equals(items.get(i).toLowerCase().trim())){
				box.getSelectionModel().clearAndSelect(i);
			}
		}
	}
}
