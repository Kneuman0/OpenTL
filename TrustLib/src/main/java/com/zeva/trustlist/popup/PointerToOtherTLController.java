package com.zeva.trustlist.popup;

import biz.ui.controller.utils.IPopupController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PointerToOtherTLController implements IPopupController{

    @FXML
    private ComboBox<String> tlTypeCombo;

    @FXML
    private TextField operatorName, urlLoc;
    
    @SuppressWarnings("unused")
	private Stage stage;
    
    
    public void initialize(){
    	tlTypeCombo.setItems(getTrustListTypes());
    }
    
    private ObservableList<String> getTrustListTypes(){
    	ObservableList<String> tlTypes = FXCollections.<String>observableArrayList();
    	tlTypes.add("Trust List of Lists");
    	tlTypes.add("Trust List");
    	return tlTypes;
    }
    
    
    public String getTSLType(){
    	return tlTypeCombo.getSelectionModel().getSelectedItem().toString() == "Trust List of Lists" ? 
    			"http://uri.etsi.org/TrstSvc/TrustedList/TSLType/EUlistofthelists" :
    				"http://uri.etsi.org/TrstSvc/TrustedList/TSLType/EUgeneric";
    }
    
    public String getOperatorName(){
    	return operatorName.getText();
    }
    
    public String getURLLocation(){
    	return urlLoc.getText();
    }

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
