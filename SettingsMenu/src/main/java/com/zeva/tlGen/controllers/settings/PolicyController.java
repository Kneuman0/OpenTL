package com.zeva.tlGen.controllers.settings;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import biz.ui.features.IndexedGridPane;

import com.zeva.temp.jaxb.datamodel.NonEmptyMultiLangURIType;
import com.zeva.temp.jaxb.datamodel.PolicyOrLegalnoticeType;
import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.tlGen.dataModel.settings.NonNullURLBean;

public class PolicyController extends MultiValueController<NonNullURLBean, NonEmptyMultiLangURIType>{

	@Override
	public void save() {
		if(contentVbox.getChildren().size() == 0) return;		
		IndexedGridPane grid = (IndexedGridPane)contentVbox.getChildren().get(0);
		NonNullURLBean bean = encappedTypesTable.getSelectionModel().getSelectedItem();
		@SuppressWarnings("unchecked")
		ComboBox<String> box = (ComboBox<String>)grid.get(0, 3);
		String lang = box.getSelectionModel().getSelectedItem();
		TextField text = (TextField)grid.get(0, 1);
		if(lang == null || text.getText().isEmpty()){
			//TODO: display error message
			return;
		}
		
		bean.setName(text.getText(), lang);
		storeDataList(beans);
	}

	@Override
	protected void displayComponents(NonNullURLBean bean) {
		contentVbox.getChildren().clear();
		
		String name = bean.getDataType().getValue();
		
		IndexedGridPane grid = new IndexedGridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(5, 5, 5, 5));
		
		Label nameLabel = new Label("Enter Name");
		TextField textBox = new TextField(name == null ? "" : name);
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
		if(type.getPolicyOrLegalNotice() == null){
			type.setPolicyOrLegalNotice(new PolicyOrLegalnoticeType());
		}
	}

	@Override
	protected NonNullURLBean encapsulateDataType(NonEmptyMultiLangURIType type) {
		return new NonNullURLBean(type);
	}

	@Override
	protected List<NonEmptyMultiLangURIType> getDataModelTypeList(
			TSLSchemeInformationType settings) {
		return settings.getPolicyOrLegalNotice().getTSLPolicy();
	}

	@Override
	protected NonNullURLBean initEmptyBean() {
		NonEmptyMultiLangURIType name = new NonEmptyMultiLangURIType();
		name.setLang("en");
		name.setValue("Enter Name");
		return new NonNullURLBean(name);
	}

}
