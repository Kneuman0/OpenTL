package com.zeva.tlGen.controllers.settings;

import java.io.File;
import java.io.IOException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.JAXBException;

import biz.ui.controller.utils.ContentSavedController;
import biz.ui.controller.utils.IPopUpSaveController;
import biz.ui.controller.utils.IPopupController;
import biz.ui.launchers.generic.PopUpComponentLauncher;

import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;
import com.zeva.temp.jaxb.datamodel.TrustStatusListType;
import com.zeva.tlGen.controllers.settings.SchemeOpNameController;
import com.zeva.tlGen.controllers.settings.TslTypeController;
import com.zeva.tlGen.dataModel.settings.ComponentBean;
import com.zeva.tlGen.dataModel.settings.SettingBean;
import com.zeva.tlGen.utils.xml.IPrinter;
import com.zeva.tlGen.utils.xml.JAXBTrustListMarshallerV5;
import com.zeva.tlGen.utils.xml.JAXBTrustListUnmarshallerV5;
import com.zeva.tlGen.utils.xml.TrustListXMLPrinter;
import com.zeva.tlGen.utils.xml.XMLTrustListMarshaller;
import com.zeva.tlGen.utils.xml.XMLTrustListUnmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DefaultSettingsController extends ContentSavedController{

    @FXML
    private TextArea settingDisplayArea;

    @FXML
    private TableView<ComponentBean<? extends IPopUpSaveController>> settingsTable;

    @FXML
    private Button saveButton;
    
    @FXML
    private TableColumn<ComponentBean<? extends IPopUpSaveController>, String> settingCol;
    
    @FXML
    private GridPane gridBackground;
    
    @FXML
    private Label userLabel;
        
    private TrustStatusListType tl;
    
    private Stage stage;
    
    private TSLSchemeInformationType settings;
    
    
	
	
	public void initialize(){
		setSettings();
		initializeSettingTable();
	}
	
	public void onSelectSetting(MouseEvent event){
		ComponentBean<? extends IPopupController> bean = settingsTable.getSelectionModel().getSelectedItem();
		if(bean == null) return;
		gridBackground.getChildren().clear();
		gridBackground.getChildren().add(bean.getComponent());
	}
	
	public void onSaveSettingButton(){
		ComponentBean<? extends IPopUpSaveController> bean = settingsTable.getSelectionModel().getSelectedItem();
		if(bean == null) return;
		bean.getController().save();
//		Platform.runLater(new TimedTextDisplay(bean.getSetting().getName() + " has been saved.",
//				"", userLabel, 5 * 1000));
		userLabel.setText(bean.getSetting().getName() + " has been saved.");
	}
	

	
	private void initializeSettingTable(){
		settingCol.setCellValueFactory(
				new PropertyValueFactory<ComponentBean<? extends IPopUpSaveController>, String>("name"));
		settingsTable.setItems(initComponentBeans());
	}

	
	/**
	 * Called to initialize the the TL that the settings are pulled from. These settings are only the
	 * StatusInformation
	 * @return
	 */
	private ObservableList<ComponentBean<? extends IPopUpSaveController>> initComponentBeans(){
		
		ObservableList<ComponentBean<? extends IPopUpSaveController>> beans = FXCollections
				.<ComponentBean<? extends IPopUpSaveController>>observableArrayList();
		
		// load tsl Type controller
		SettingBean tslType = new SettingBean("TSL Type", settings);
		PopUpComponentLauncher<TslTypeController, GridPane> launcherTSLType = 
				new PopUpComponentLauncher<TslTypeController, GridPane>(getClass()
						.getResource("/resources/TSLTypeComponent.fxml"));		
		beans.add(new ComponentBean<TslTypeController>(tslType, launcherTSLType));
		
		// Load Scheme Operater Name Controller
		SettingBean schemeOpName = new SettingBean("Scheme Operator Name", settings);
		PopUpComponentLauncher<SchemeOpNameController, GridPane> launcherSchemeOpName = 
				new PopUpComponentLauncher<SchemeOpNameController, GridPane>(getClass()
						.getResource("/resources/SchemeOpName.fxml"));	
		beans.add(new ComponentBean<SchemeOpNameController>(schemeOpName, launcherSchemeOpName));
		
		SettingBean schemeOpAddress = new SettingBean("Scheme Operator Address", settings);
		PopUpComponentLauncher<SchemeOpPostalAddressController, GridPane> launcherPostalAddr = 
				new PopUpComponentLauncher<SchemeOpPostalAddressController, GridPane>(getClass()
						.getResource("/resources/SchemeOpPostAddr.fxml"));	
		beans.add(new ComponentBean<SchemeOpPostalAddressController>(schemeOpAddress, launcherPostalAddr)); // SchemeOpElecAddr.fxml
		
		SettingBean elecAddr = new SettingBean("Electronic Address", settings);
		PopUpComponentLauncher<ElectAddrController, GridPane> launcherElecAddr = 
				new PopUpComponentLauncher<ElectAddrController, GridPane>(getClass()
						.getResource("/resources/SchemeOpElecAddr.fxml"));
		beans.add(new ComponentBean<ElectAddrController>(elecAddr, launcherElecAddr));
		
		SettingBean legNotice = new SettingBean("Legal Notice", settings);
		PopUpComponentLauncher<LegalNoticeContoller, GridPane> launcherLegal = 
				new PopUpComponentLauncher<LegalNoticeContoller, GridPane>(getClass()
						.getResource("/resources/LegalNotice.fxml"));
		beans.add(new ComponentBean<LegalNoticeContoller>(legNotice, launcherLegal));
		
		SettingBean polNotice = new SettingBean("Policy Notice", settings);
		PopUpComponentLauncher<PolicyController, GridPane> launcherPolicy = 
				new PopUpComponentLauncher<PolicyController, GridPane>(getClass()
						.getResource("/resources/PolicyNotice.fxml"));
		beans.add(new ComponentBean<PolicyController>(polNotice, launcherPolicy));
		
		SettingBean issueDateTime = new SettingBean("List Issue Date and Time", settings);
		PopUpComponentLauncher<ListIssueDateController, GridPane> launcherListIssueDate = 
				new PopUpComponentLauncher<ListIssueDateController, GridPane>(getClass()
						.getResource("/resources/ListIssueDate.fxml"));
		beans.add(new ComponentBean<ListIssueDateController>(issueDateTime, launcherListIssueDate));
		
		SettingBean nextUpdate = new SettingBean("Next Update", settings);
		PopUpComponentLauncher<NextUpdateController, GridPane> launcherNextUpdate = 
				new PopUpComponentLauncher<NextUpdateController, GridPane>(getClass()
						.getResource("/resources/NextUpdateComponent.fxml"));
		beans.add(new ComponentBean<NextUpdateController>(nextUpdate, launcherNextUpdate));
		
		SettingBean distPoints = new SettingBean("Distribution Points", settings);
		PopUpComponentLauncher<DistroPointsController, GridPane> launcherDistPoints = 
				new PopUpComponentLauncher<DistroPointsController, GridPane>(getClass()
						.getResource("/resources/DistroPointsComponent.fxml"));
		beans.add(new ComponentBean<DistroPointsController>(distPoints, launcherDistPoints));
		
		return beans;
	}

	
	/**
	 * Returns the default setting trust list generated form this settings controller
	 * @return
	 */
	public TrustStatusListType getSettingsTL(){
		return tl;
	}
	
	private void setSettings(){
		try {
			XMLTrustListUnmarshaller um = new JAXBTrustListUnmarshallerV5(new File("defaultXML.xml"));
			this.settings = um.getTrustList().getSchemeInformation();
			this.tl = um.getTrustList();
		} catch (JAXBException e) {
			// TODO Display Error Message and then init with with a default from resources folder
		} catch (XMLParseException e) {
			// TODO Display Error Message and then init with with a default from resources folder
		}
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public Stage getStage(){
		return stage;
	}

	@Override
	public void save() {
		XMLTrustListMarshaller ma = new JAXBTrustListMarshallerV5(tl);
		ma.marshalTrustList();
		IPrinter printer = new TrustListXMLPrinter(ma.getMarshalledDocument());
		try {
			printer.print(new File("defaultXML.xml"));
		} catch (IOException e) {
			// TODO add popup message
		}
	}
	
//	private class UpdateLabel implements Runnable{
//
//		private Label label;
//		private String value;
//		
//		public UpdateLabel(Label label, String value) {
//			this.label = label;
//			this.value = value;
//		}
//		
//		@Override
//		public void run() {
//			Platform.runLater(runnable);
//		}
//		
//	}
		

}
