package com.zeva.tlGen.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SplashController {

    @FXML
    private ImageView zevaLogo;
    
    public void initialize(){    	
    	zevaLogo.setImage(new Image(getClass().getResourceAsStream(
				"/resources/zeva.png")));
    }
        
}
