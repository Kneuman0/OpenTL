package com.zeva.tlGen.controllers.settings;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import biz.ui.features.AddButton;

import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.tlGen.dataModel.abstraction.EncapsulatedDataType;
import com.zeva.tlGen.dataModel.abstraction.SettingsDataType;

public abstract class MultiValueController <EncapedBean extends EncapsulatedDataType<DataModelType>, DataModelType extends SettingsDataType> extends ComponentController{
	
	 @FXML
	    protected VBox contentVbox;

	    @FXML
	    protected TableView<EncapedBean> encappedTypesTable;

	    @FXML
	    protected TableColumn<EncapedBean, String> encappedDataTypesTableCol;
	    
	    @FXML
	    protected HBox addHBox;
	    
	    protected ObservableList<EncapedBean> beans;
	    
	    @FXML
	    protected Label title;
		
		@Override
		public void initialize() {
			addHBox.getChildren().add(new AddName());
		}
		
		public void onTableItemClicked(){
			EncapedBean bean = encappedTypesTable.getSelectionModel().getSelectedItem();
			if(bean == null) return;
			displayComponents(bean);
		}
		
		public void onDelete(KeyEvent event){
			//TODO: display message before deleting
			if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
				EncapedBean bean = encappedTypesTable.getSelectionModel().getSelectedItem();
				if(bean == null) return;
				System.out.println(beans.indexOf(bean));
				beans.remove(beans.indexOf(bean));
				storeDataList(beans);
				contentVbox.getChildren().clear();
			}
		}

		@Override
		public abstract void save();

		@Override
		public void setDataModelItem(TSLSchemeInformationType modelItem) {
			settings = modelItem;
			encappedDataTypesTableCol.setCellValueFactory(new PropertyValueFactory<EncapedBean, String>("name"));
			beans = getEncapulatedBeans(modelItem);
			encappedTypesTable.setItems(beans);
			
		}
		
		public void setTitle(String title){
			this.title.setText(title);
		}
		
		public void setColumTitle(String title){
			encappedDataTypesTableCol.getColumns().get(0).setText(title);
		}
		
		/**
		 * Updates the data model to ensure persistance
		 * @param names
		 */
		protected void storeDataList(List<EncapedBean> beans){			
			getDataModelTypeList(settings).clear();
			for(EncapedBean bean : beans){
				getDataModelTypeList(settings).add(bean.getDataType());
			}
		}
		
		/**
		 * This method builds the actual GUI feature in displayed. Generally consites of 
		 * TextFields, Labels, ComboBoxes etc..
		 * 
		 * This method must add the compoenets to the componentVbox. It is best to wrap these
		 * components in a container
		 * @param bean
		 */
		protected abstract void displayComponents(EncapedBean bean);
		
		protected void selectComboBoxValue(String value, ComboBox<String> box){
			if(value == null) return;
			ObservableList<String> items = box.getItems();
			for(int i = 0; i < items.size(); i++){
				if(value.toLowerCase().trim().equals(items.get(i).toLowerCase().trim())){
					box.getSelectionModel().clearAndSelect(i);
				}
			}
		}
		
		/**
		 * This method is for building out null parts of the data model. 
		 * Ensure the path in the data model that leads to the encapsulated DataModelType is initialized
		 * @param type
		 */
		protected abstract void handleNullDataType(TSLSchemeInformationType type);
		
		/**
		 * Return an encapsulated bean
		 * @param type
		 * @return
		 */
		protected abstract EncapedBean encapsulateDataType(DataModelType type);
		
		/**
		 * Return the list of data model types from the data model
		 * @param settings
		 * @return List<DataModelType>
		 */
		protected abstract List<DataModelType> getDataModelTypeList(TSLSchemeInformationType settings);
		
		protected ObservableList<EncapedBean> getEncapulatedBeans(TSLSchemeInformationType settings){
			ObservableList<EncapedBean> nameList = FXCollections.observableArrayList();
			if(settings.getSchemeName() == null){
				handleNullDataType(settings);
			}
			for(DataModelType name : getDataModelTypeList(settings)){
				
				nameList.add(encapsulateDataType(name));
			}
			
			return nameList;
		}
		
		/**
		 * Create a new datamodel type and initialize it with any default values.
		 * Then pass return an initialized Encapsulated bean
		 * @return
		 */
		protected abstract EncapedBean initEmptyBean();
		
		public class AddName extends AddButton{

			@Override
			public void executeLogic(MouseEvent event) {
				EncapedBean bean = initEmptyBean();
				beans.add(bean);
				int index = beans.indexOf(bean);
				encappedTypesTable.getSelectionModel().clearAndSelect(index);
			}
			
		}
}
