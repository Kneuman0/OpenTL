package com.zeva.tlGen.controllers;

import biz.ui.controller.utils.IPopUpSaveController;
import biz.ui.controller.utils.IPopupController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SplashController implements IPopUpSaveController{

    @FXML
    private ImageView zevaLogo;
    
    @SuppressWarnings("unused")
	private Stage stage;
    
    public void initialize(){    	
    	zevaLogo.setImage(new Image(getClass().getResourceAsStream(
				"/resources/zeva.png")));
    }

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
        
}
