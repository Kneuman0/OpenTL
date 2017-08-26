package com.zeva.tlGen.controllers.settings;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import biz.ui.features.AutoSizeTextBox;
import biz.ui.features.IndexedGridPane;

import com.zeva.temp.jaxb.datamodel.ElectronicAddressType;
import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.temp.jaxb.datamodel.UriBeanType;
import com.zeva.tlGen.dataModel.settings.ElectAddrBean;

public class DistroPointsController extends MultiValueController<ElectAddrBean, UriBeanType>{

	@Override
	public void save() {
if(contentVbox.getChildren().size() == 0) return;
		
		IndexedGridPane grid = (IndexedGridPane)contentVbox.getChildren().get(0);
		ElectAddrBean bean = encappedTypesTable.getSelectionModel().getSelectedItem();
		
		String uri = ((TextArea)grid.get(0, 1)).getText();
		
		if(uri == null || uri.isEmpty()) return;
		
		bean.storeDataValue(uri, settings.getDistributionPoints());
		storeDataList(beans);
	}

	@Override
	protected void displayComponents(ElectAddrBean bean) {
		contentVbox.getChildren().clear();
		String uri = bean.getDataType().getUri();
		
		IndexedGridPane grid = new IndexedGridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(5, 5, 5, 5));
		
		Label nameLabel = new Label("Please enter a URI that indicates the location of the list");
		nameLabel.wrapTextProperty().set(true);
		TextArea textBox = new TextArea(uri == null ? "" : uri);
		grid.add(nameLabel, 0, 0);
		grid.add(textBox, 0, 1);
		contentVbox.getChildren().add(grid);
	}
	
	@Override
	public void onDelete(KeyEvent event){
		super.onDelete(event);
		List<UriBeanType> uris = new ArrayList<UriBeanType>();
		for(ElectAddrBean bean : beans){
			uris.add(bean.getDataType());
		}
		settings.getDistributionPoints().update(uris);
	}

	@Override
	protected void handleNullDataType(TSLSchemeInformationType type) {
		if(type.getDistributionPoints() == null){
			type.setDistributionPoints(new ElectronicAddressType());
		}
	}

	@Override
	protected ElectAddrBean encapsulateDataType(UriBeanType type) {
		return new ElectAddrBean(type);
	}

	@Override
	protected List<UriBeanType> getDataModelTypeList(
			TSLSchemeInformationType settings) {
		return settings.getDistributionPoints().getURIBeans();
	}

	@Override
	protected ElectAddrBean initEmptyBean() {
		return new ElectAddrBean(new UriBeanType("[URI]"));
	}
	


}
