package com.zeva.tlGen.dataModel.settings;

import com.zeva.tlGen.dataModel.settings.SettingBean;
import com.zeva.tlGen.dataModel.settings.SingleComponentController;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.GridPane;
import biz.ui.controller.utils.IPopupController;
import biz.ui.launchers.generic.PopUpComponentLauncher;

public class ComponentBean <Controller extends SingleComponentController>{
	
	private PopUpComponentLauncher<Controller, ? extends GridPane> launcher;
	private Controller controller;
	private SettingBean setting;
	private GridPane component;
	private SimpleStringProperty name;

	public ComponentBean(SettingBean bean, 
			PopUpComponentLauncher<Controller, ? extends GridPane> launcher){
		this.launcher = launcher;
		this.setting = bean;
		this.controller = launcher.getController();
		this.controller.setDataModelItem(bean.getSettings());
		this.component = launcher.getParent();
		this.name = new SimpleStringProperty(bean.getName());
	}

	public PopUpComponentLauncher<Controller, ? extends GridPane> getLauncher() {
		return launcher;
	}

	public void setLauncher(
			PopUpComponentLauncher<Controller, ? extends GridPane> launcher) {
		this.launcher = launcher;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public SettingBean getSetting() {
		return setting;
	}

	public void setSetting(SettingBean setting) {
		this.setting = setting;
	}

	public GridPane getComponent() {
		return component;
	}

	public void setComponent(GridPane component) {
		this.component = component;
	}
	
	public SimpleStringProperty nameProperty(){
		return name;
	}
	

}
