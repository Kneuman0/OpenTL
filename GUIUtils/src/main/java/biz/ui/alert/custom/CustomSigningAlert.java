package biz.ui.alert.custom;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;
import biz.ui.controller.utils.ControllerUtils;
import biz.ui.controller.utils.IPopupController;
import biz.ui.controllers.generic.SigningCredsController;
import biz.ui.launchers.generic.PopUpComponentLauncher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

public class CustomSigningAlert extends Alert{
	
	private SigningCredsController controller;
	
	public CustomSigningAlert() {
		super(AlertType.CONFIRMATION);
		setTitle("Import Signing Certificate");
		setHeaderText("Please fill in the appropriate content");
		PopUpComponentLauncher<SigningCredsController, GridPane> grid = 
				new PopUpComponentLauncher<SigningCredsController, GridPane>
				(getClass().getResource("/resources/signingCredGUI.fxml"));
		
		this.controller = grid.getController();
		getDialogPane().setContent(grid.getParent());
	}
	
	public File getCredLoc(){
		return controller.getCerdential();
	}
	
	public String getPassword(){
		return controller.getPassword();
	}
	
	
}
