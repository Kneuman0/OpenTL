package com.zeva.tlGen.controllers;

import biz.ui.launchers.generic.SplashScreen;

public class SplashScreenLauncher extends SplashScreen<SplashController>{

	@Override
	protected String fxmlLocation() {
		return "/resources/splashScreen.fxml";
	}
}
