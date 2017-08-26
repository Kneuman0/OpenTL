package com.zeva.tlGen.controllers.settings;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import javax.activation.DataHandler;

import biz.ui.features.AutoSizeTextBox;
import biz.ui.features.IndexedGridPane;

import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.temp.jaxb.datamodel.UriBeanType;
import com.zeva.tlGen.dataModel.settings.ElectAddrBean;
import com.zeva.tlGen.dataModel.utils.DataModelHandler;

public class ElectAddrController extends MultiValueController<ElectAddrBean, UriBeanType>{

	@Override
	public void save() {
		if(contentVbox.getChildren().size() == 0) return;
		
		IndexedGridPane grid = (IndexedGridPane)contentVbox.getChildren().get(0);
		ElectAddrBean bean = encappedTypesTable.getSelectionModel().getSelectedItem();
		
		String uri = ((AutoSizeTextBox)grid.get(0, 1)).getText();
		if(uri == null || uri.isEmpty()){
			//TODO: display error message
			return;
		}
		
		storeDataList(beans);
		bean.storeDataValue(uri, settings.getSchemeOperatorAddress().getElectronicAddress());
	}
	
	@Override
	public void onDelete(KeyEvent event){
		super.onDelete(event);
		List<UriBeanType> uris = new ArrayList<UriBeanType>();
		for(ElectAddrBean bean : beans){
			uris.add(bean.getDataType());
		}
		settings.getSchemeOperatorAddress().getElectronicAddress().update(uris);
	}

	@Override
	protected void displayComponents(ElectAddrBean bean) {
		contentVbox.getChildren().clear();
		
		String uri = bean.getDataType().getUri();
		
		IndexedGridPane grid = new IndexedGridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(5, 5, 5, 5));
		
		Label nameLabel = new Label("Enter URI");
		AutoSizeTextBox textBox = new AutoSizeTextBox(uri == null ? "" : uri);
		grid.add(nameLabel, 0, 0);
		grid.add(textBox, 0, 1);
		contentVbox.getChildren().add(grid);
	}

	@Override
	protected void handleNullDataType(TSLSchemeInformationType type) {
		if(type.getSchemeOperatorAddress() == null){
			type.setSchemeOperatorAddress(DataModelHandler.buildAddressType());
		}
	}

	@Override
	protected ElectAddrBean encapsulateDataType(UriBeanType type) {
		return new ElectAddrBean(type);
	}

	@Override
	protected List<UriBeanType> getDataModelTypeList(
			TSLSchemeInformationType settings) {
		return settings.getSchemeOperatorAddress().getElectronicAddress().getURIBeans();
	}

	@Override
	protected ElectAddrBean initEmptyBean() {
		UriBeanType bean = new UriBeanType("[URI]");
		return new ElectAddrBean(bean);
	}

}
