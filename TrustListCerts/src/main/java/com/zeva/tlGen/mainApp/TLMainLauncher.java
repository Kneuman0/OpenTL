package com.zeva.tlGen.mainApp;

import biz.ui.launchers.generic.PopupLauncher;

import com.zeva.tlGen.controllers.SplashScreenLauncher;
import com.zeva.tlGen.mainApp.TLGenController;

import javafx.application.Application;
import javafx.stage.Stage;

public class TLMainLauncher extends Application {

	@Override
	public void init(){
		// TODO should anything be added here?
	}
	
	/**
	 * Fires up the application main thread and initializes the GUI by parsing
	 * the FXML file
	 */
	@Override
	public void start(Stage stage) {
		PopupLauncher<TLGenController> mainWindow = new PopupLauncher<TLGenController>
			(stage, "Trust List Generator", getClass().getResource("/resources/TLGenGUI.fxml"));
		
		mainWindow.getStage().setResizable(false);
				
		// close splash screen
		SplashScreenLauncher.closeSplashScreen();
		// registers a listener for when the window is closed. This will save
		// any user preferences
		mainWindow.getStage().getScene().getWindow().setOnCloseRequest(new TLGenController.OnCloseWindow());
		
		mainWindow.show();
	}
}
