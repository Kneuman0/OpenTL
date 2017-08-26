package com.zeva.tlGen.alert;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class NameAlert extends Alert{

	ComboBox<String> langs;
	
	public NameAlert() {
		super(AlertType.CONFIRMATION);
		setTitle("Choose Language");
		setHeaderText("Please choose a language");
		
		GridPane pane = new GridPane();
		langs = new ComboBox<String>();
		langs.setItems(getlangs());
		langs.setPromptText("Choose a language");
		pane.addRow(0, langs);
		setGraphic(pane);
	}
	
	private ObservableList<String> getlangs(){
		String[] langs = {"en", "bd", "bg-Latn", "ca", "cs"
				, "da", "de", "el", "el-Latn", "es", "et", "eu",
				"fi", "fr", "ga", "gl", "hr", "hu", "is",
				"it", "lv", "mt", "nb", "nl", "nn", "no",
				"pl", "pt", "ro", "sk", "sl", "sv", "tr"};
		return FXCollections.observableArrayList(Arrays.asList(langs));
	}
	
	public String getChoice(){
		return langs.getSelectionModel().getSelectedItem();
	}
	

}
