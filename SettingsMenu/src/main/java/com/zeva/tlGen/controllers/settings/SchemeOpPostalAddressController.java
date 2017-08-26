package com.zeva.tlGen.controllers.settings;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import biz.ui.features.AutoSizeTextBox;
import biz.ui.features.IndexedGridPane;

import com.zeva.temp.jaxb.datamodel.PostalAddressType;
import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.tlGen.dataModel.settings.SchemeOpPostalBean;
import com.zeva.tlGen.dataModel.utils.DataModelHandler;

public class SchemeOpPostalAddressController extends MultiValueController<SchemeOpPostalBean, PostalAddressType> {
	
	@SuppressWarnings("unchecked")
	@Override
	public void save() {
		if(contentVbox.getChildren().size() == 0) return;		
		
		IndexedGridPane grid = (IndexedGridPane)contentVbox.getChildren().get(0);
		SchemeOpPostalBean bean = encappedTypesTable.getSelectionModel().getSelectedItem();
		
		String lang = ((ComboBox<String>)grid.get(0, 11)).getSelectionModel().getSelectedItem();
		
		if(lang == null || 												 // Language
				((AutoSizeTextBox)grid.get(0, 1)).getText().isEmpty() || // Street
				((AutoSizeTextBox)grid.get(0, 3)).getText().isEmpty() || // City
				((AutoSizeTextBox)grid.get(0, 5)).getText().isEmpty() || // State
				((AutoSizeTextBox)grid.get(0, 7)).getText().isEmpty() || // Zip
				((AutoSizeTextBox)grid.get(0, 9)).getText().isEmpty()){  // Country
			//TODO: display error message
			return;
		}
		
		System.out.println(((AutoSizeTextBox)grid.get(0, 3)).getText());
		
		bean.storeDataValue(
				((AutoSizeTextBox)grid.get(0, 1)).getText(), // Street
				((AutoSizeTextBox)grid.get(0, 3)).getText(), // City
				((AutoSizeTextBox)grid.get(0, 5)).getText(), // State
				((AutoSizeTextBox)grid.get(0, 7)).getText(), // Zip
				((AutoSizeTextBox)grid.get(0, 9)).getText(), // Country
				lang); // Language
		
		storeDataList(beans);
	}
	

//	@Override
//	public void storeDataList(List<SchemeOpPostalBean> names) {
//		List<PostalAddressType> addrs = new ArrayList<PostalAddressType>();
//		
//		for(SchemeOpPostalBean bean : names){
//			addrs.add(bean.getDataType());
//		}
//		
//		addrs.
//	}

	@Override
	protected void displayComponents(SchemeOpPostalBean bean) {
		contentVbox.getChildren().clear();
		
		String street = bean.getDataType().getStreetAddress();
		String city = bean.getDataType().getLocality();
		String state = bean.getDataType().getStateOrProvince();
		String zip = bean.getDataType().getPostalCode();
		String country = bean.getDataType().getCountryName();
		
		IndexedGridPane grid = new IndexedGridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(5, 5, 5, 5));
		
		Label streetLabel = new Label("Enter Street Address");
		Label cityLabel = new Label("Enter City");
		Label stateLabel = new Label("Enter State or Province");		
		Label zipLabel = new Label("Enter Postal Code");
		Label countryLabel = new Label("Enter Country");
		Label langLabel = new Label("Select Language");
		
		AutoSizeTextBox streetText = new AutoSizeTextBox(street == null ? "" : street);
		AutoSizeTextBox cityText = new AutoSizeTextBox(city == null ? "" : city);
		AutoSizeTextBox stateText = new AutoSizeTextBox(state == null ? "" : state);
		AutoSizeTextBox zipText = new AutoSizeTextBox(zip == null ? "" : zip);
		AutoSizeTextBox countryText = new AutoSizeTextBox(country == null ? "" : country);
		
		
		ComboBox<String> langCombo = new ComboBox<String>();
		langCombo.setPromptText("Please Select a Language");
		langCombo.setItems(getlangs());
		selectComboBoxValue(bean.getDataType().getLang(), langCombo);
		
		grid.add(streetLabel, 0, 0);
		grid.add(streetText, 0, 1);
		grid.add(cityLabel, 0, 2);
		grid.add(cityText, 0, 3);
		grid.add(stateLabel, 0, 4);
		grid.add(stateText, 0, 5);
		grid.add(zipLabel, 0, 6);
		grid.add(zipText, 0, 7);
		grid.add(countryLabel, 0, 8);
		grid.add(countryText, 0, 9);
		grid.add(langLabel, 0, 10);
		grid.add(langCombo, 0, 11);
		contentVbox.getChildren().add(grid);
	}

	@Override
	protected void handleNullDataType(TSLSchemeInformationType type) {
		if(type.getSchemeOperatorAddress() == null){
			type.setSchemeOperatorAddress(DataModelHandler.buildAddressType());
		}
	}

	@Override
	protected SchemeOpPostalBean encapsulateDataType(PostalAddressType type) {
		return new SchemeOpPostalBean(type);
	}

	@Override
	protected List<PostalAddressType> getDataModelTypeList(
			TSLSchemeInformationType settings) {
		return settings.getSchemeOperatorAddress().getPostalAddresses().getPostalAddress();
	}

	@Override
	protected SchemeOpPostalBean initEmptyBean() {
		PostalAddressType type = new PostalAddressType();
		type.setCountryName("[Country Name]");
		type.setLang("en");
		type.setLocality("[City]");
		type.setPostalCode("[zip code]");
		type.setStateOrProvince("[State/prov.]");
		type.setStreetAddress("[Stree Address]");
		
		return new SchemeOpPostalBean(type);
	}

}
