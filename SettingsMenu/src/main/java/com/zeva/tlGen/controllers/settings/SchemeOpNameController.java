package com.zeva.tlGen.controllers.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import biz.ui.features.AddButton;
import biz.ui.features.AutoSizeTextBox;
import biz.ui.features.IndexedGridPane;

import com.zeva.temp.jaxb.datamodel.InternationalNamesType;
import com.zeva.temp.jaxb.datamodel.MultiLangNormStringType;
import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.tlGen.dataModel.abstraction.EncapsulatedDataType;
import com.zeva.tlGen.dataModel.settings.MultiLangNormBean;

public class SchemeOpNameController extends MultiValueController<MultiLangNormBean, MultiLangNormStringType>{


	@Override
	public void save() {
		if(contentVbox.getChildren().size() == 0) return;
		
		IndexedGridPane grid = (IndexedGridPane)contentVbox.getChildren().get(0);
		MultiLangNormBean bean = encappedTypesTable.getSelectionModel().getSelectedItem();
		@SuppressWarnings("unchecked")
		ComboBox<String> box = (ComboBox<String>)grid.get(0, 3);
		String lang = box.getSelectionModel().getSelectedItem();
		AutoSizeTextBox text = (AutoSizeTextBox)grid.get(0, 1);
		if(lang == null || text.getText().isEmpty()){
			//TODO: display error message
			return;
		}
		
		bean.setName(text.getText(), lang);
		storeDataList(beans);
		
	}

	@Override
	protected void displayComponents(MultiLangNormBean bean) {
		contentVbox.getChildren().clear();
		
		String name = bean.getDataType().getValue();
		
		IndexedGridPane grid = new IndexedGridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(5, 5, 5, 5));
		
		Label nameLabel = new Label("Enter Name");
		AutoSizeTextBox textBox = new AutoSizeTextBox(name == null ? "" : name);
		Label langLabel = new Label("Select Language");
		
		ComboBox<String> lang = new ComboBox<String>();
		lang.setPromptText("Please Select a Language");
		lang.setItems(getlangs());
		selectComboBoxValue(bean.getDataType().getLang(), lang);
		
		grid.add(nameLabel, 0, 0);
		grid.add(textBox, 0, 1);
		grid.add(langLabel, 0, 2);
		grid.add(lang, 0, 3);
		contentVbox.getChildren().add(grid);
	}

	@Override
	protected void handleNullDataType(TSLSchemeInformationType type) {
		InternationalNamesType names = new InternationalNamesType();			
		names.setNames(new ArrayList<MultiLangNormStringType>());
		settings.setSchemeOperatorName(names);
	}

	@Override
	protected MultiLangNormBean encapsulateDataType(MultiLangNormStringType type) {
		return new MultiLangNormBean(type);
	}

	@Override
	protected List<MultiLangNormStringType> getDataModelTypeList(
			TSLSchemeInformationType settings) {
		return settings.getSchemeOperatorName().getName();
	}

	@Override
	protected MultiLangNormBean initEmptyBean() {
		MultiLangNormStringType name = new MultiLangNormStringType();
		name.setLang("en");
		name.setValue("Enter Name");
		return new MultiLangNormBean(name);
	}

}
