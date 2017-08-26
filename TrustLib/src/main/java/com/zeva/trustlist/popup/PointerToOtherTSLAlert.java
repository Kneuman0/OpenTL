package com.zeva.trustlist.popup;


import com.zeva.temp.jaxb.datamodel.OtherTSLPointerType;

import biz.ui.launchers.generic.PopUpComponentLauncher;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.layout.GridPane;

public class PointerToOtherTSLAlert extends Alert{
	
	enum RESPONSE_TYPE {OK, CANCEL, ERROR};
	
	PointerToOtherTLController controller;
		
	public PointerToOtherTSLAlert(){
		super(AlertType.CONFIRMATION);
		System.out.println(PointerToOtherTSLAlert.class.getResource("/resources/pointerToOtherTLGUI.fxml").toString());
		PopUpComponentLauncher<PointerToOtherTLController, GridPane> grid = new
				PopUpComponentLauncher<PointerToOtherTLController, GridPane>
				(this.getClass().getResource("/resources/pointerToOtherTLGUI.fxml"));
		
		this.controller = grid.getController();
		System.out.println(controller);
		getDialogPane().setContent(grid.getParent());
//		setOnCloseRequest(new OnCloseRequest());
		setTitle("Add a Pointer");
		setHeaderText("Fill out the information below to add a pointer to another Trust List");
	}
	
	public String getSchemeOperatorName(){
		return controller.getOperatorName();
	}
	
	public String getTSLLocation(){
		return controller.getURLLocation();
	}
	
	public String getTSLType(){
		return controller.getTSLType();
	}
	
	public OtherTSLPointerType getPointer(){
		// this is garbage code. Doing this just for presenting
		// do not understand JAXB AnyType??? Cannot populate datamodel
		OtherTSLPointerType pointer = new OtherTSLPointerType();
		pointer.setTSLLocation(getTSLLocation());
		pointer.setTSLType(getTSLType());
		pointer.setSchemeOperatorName(getSchemeOperatorName());
		return pointer;
	}
	
	public class OnCloseRequest implements EventHandler<DialogEvent>{

		@Override
		public void handle(DialogEvent event) {
			if(getSchemeOperatorName().isEmpty()){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Pointer Error");
				alert.setHeaderText("Published By: may not be empty");
				alert.setContentText(null);
				alert.showAndWait();
				event.consume();
			}else if(getTSLLocation().isEmpty()){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Pointer Error");
				alert.setHeaderText("URL Location: may not be empty");
				alert.setContentText(null);
				alert.showAndWait();
				event.consume();
			}else if(getTSLType() != null){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Pointer Error");
				alert.setHeaderText("A Trust List type must be selected");
				alert.setContentText(null);
				alert.showAndWait();
				event.consume();
			} else{
				//allow the window to close
			}
		}
		
	}
	
	
	
}
