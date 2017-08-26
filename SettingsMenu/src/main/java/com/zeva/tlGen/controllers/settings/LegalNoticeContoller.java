package com.zeva.tlGen.controllers.settings;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import biz.ui.features.AutoSizeTextBox;
import biz.ui.features.IndexedGridPane;

import com.zeva.temp.jaxb.datamodel.InternationalNamesType;
import com.zeva.temp.jaxb.datamodel.MultiLangNormStringType;
import com.zeva.temp.jaxb.datamodel.MultiLangStringType;
import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.tlGen.dataModel.settings.MultiLangStringBean;
import com.zeva.tlGen.dataModel.settings.MultiLangNormBean;


public class LegalNoticeContoller extends MultiValueController<MultiLangStringBean, MultiLangStringType>{

	@Override
	public void save() {
		if(contentVbox.getChildren().size() == 0) return;		
		IndexedGridPane grid = (IndexedGridPane)contentVbox.getChildren().get(0);
		MultiLangStringBean bean = encappedTypesTable.getSelectionModel().getSelectedItem();
		@SuppressWarnings("unchecked")
		ComboBox<String> box = (ComboBox<String>)grid.get(0, 3);
		String lang = box.getSelectionModel().getSelectedItem();
		TextArea text = (TextArea)grid.get(0, 1);
		if(lang == null || text.getText().isEmpty()){
			//TODO: display error message
			return;
		}
		
		bean.setName(text.getText(), lang);
		storeDataList(beans);
	}

	@Override
	protected void displayComponents(MultiLangStringBean bean) {
		contentVbox.getChildren().clear();
		
		String name = bean.getDataType().getValue();
		
		IndexedGridPane grid = new IndexedGridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(5, 5, 5, 5));
		
		Label nameLabel = new Label("Enter Name");
		TextArea textBox = new TextArea(name == null ? "" : name);
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
	protected MultiLangStringBean encapsulateDataType(MultiLangStringType type) {
		return new MultiLangStringBean(type);
	}

	@Override
	protected List<MultiLangStringType> getDataModelTypeList(
			TSLSchemeInformationType settings) {
		return settings.getPolicyOrLegalNotice().getTSLLegalNotice();
	}

	@Override
	protected MultiLangStringBean initEmptyBean() {
		MultiLangStringType name = new MultiLangStringType();
		name.setLang("en");
		name.setValue("Enter Name");
		return new MultiLangStringBean(name);
	}

	
	
}
