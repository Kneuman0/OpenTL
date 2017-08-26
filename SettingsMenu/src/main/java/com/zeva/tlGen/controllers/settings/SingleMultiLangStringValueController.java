package com.zeva.tlGen.controllers.settings;

import java.awt.TextField;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public abstract class SingleMultiLangStringValueController <DataType> extends SingleValueController<DataType>{

	@FXML
	protected TextField field;
	
	@FXML
	protected ComboBox<String> lang;
	
	protected void selectValue(String value, ComboBox<String> box){
		ObservableList<String> items = box.getItems();
		for(int i = 0; i < items.size(); i++){
			if(value.toLowerCase().trim().equals(items.get(i).toLowerCase().trim())){
				box.getSelectionModel().clearAndSelect(i);
			}
		}
	}

}
