package com.zeva.tlGen.mainApp;

import java.io.IOException;

import com.zeva.tlGen.controllers.SplashScreen;
import com.zeva.tlGen.mainApp.TLGenController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TLMainLauncher extends Application {

	/**
	 * Fires up the application main thread and initializes the GUI by parsing
	 * the FXML file
	 */
	public void start(Stage stage) {
//		stage.setResizable(false);
		FXMLLoader loader = null;
		Parent parent = null;
		try {
			// Reads in the FXML file and initializes the fields in the
			// controller/GUI
			loader = new FXMLLoader(getClass().getResource(
					"/resources/TLGenGUI.fxml"));
			parent = loader.<Parent> load();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Retrieves the instance of the controller
		@SuppressWarnings("unused")
		TLGenController controller = loader
				.<TLGenController> getController();

		Scene scene = new Scene(parent);

		// window title
		stage.setTitle("Trust List Generator");
		stage.setScene(scene);
		
		// close splash screen
		new SplashScreen.OnCloseSplashScreen();
		stage.show();

		// registers a listener for when the window is closed. This will save
		// any user preferences
		scene.getWindow().setOnCloseRequest(new TLGenController.OnCloseWindow());
	}
}
