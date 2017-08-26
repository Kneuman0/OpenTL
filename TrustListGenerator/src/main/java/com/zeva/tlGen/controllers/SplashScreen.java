package com.zeva.tlGen.controllers;

import java.io.IOException;




import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Preloader {
	
	private static Stage preloaderStage;

	@Override
	public void start(Stage stage) throws Exception {
		stage.initStyle(StageStyle.UNDECORATED);
		preloaderStage = stage;
		FXMLLoader loader = null;
		Parent parent = null;
		try {
			// Reads in the FXML file and initializes the fields in the
			// controller/GUI
			loader = new FXMLLoader(getClass().getResource(
					"/resources/splashScreen.fxml"));
			parent = loader.<Parent> load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(parent);

		// window title
		stage.setTitle("Certificate Validation Tool");
		stage.setScene(scene);
		stage.show();

	}
	   
	   public static class OnCloseSplashScreen{
		   public OnCloseSplashScreen(){
			   preloaderStage.close();
		   }
	   }

}
