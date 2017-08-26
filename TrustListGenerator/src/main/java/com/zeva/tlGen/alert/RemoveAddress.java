package com.zeva.tlGen.alert;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class RemoveAddress extends Alert{
	
	ComboBox<String> langs;
	
	public RemoveAddress(ObservableList<String> streetAddresses) {
		super(AlertType.CONFIRMATION);
		setTitle("Delete Address");
		setHeaderText("Please choose a street addresss to delete");
		
		GridPane pane = new GridPane();
		langs = new ComboBox<String>();
		langs.setItems(streetAddresses);
		langs.setPromptText("Choose a street address");
		pane.addRow(0, langs);
		setGraphic(pane);
	}
	
	
	public String getChoice(){
		return langs.getSelectionModel().getSelectedItem();
	}
}
