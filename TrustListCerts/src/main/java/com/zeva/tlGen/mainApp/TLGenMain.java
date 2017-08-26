package com.zeva.tlGen.mainApp;

import com.sun.javafx.application.LauncherImpl;
import com.zeva.tlGen.controllers.SplashScreenLauncher;

@SuppressWarnings("restriction")
public class TLGenMain {
	
	public static void main(String[] args) {
		
		/**
		 * Entry point for the program. Run this class to run the program.
		 * TLMainLauncher is the main application, the SplashScreen is the preloader
		 */
		LauncherImpl.launchApplication(TLMainLauncher.class, SplashScreenLauncher.class, args);
		
	}

}
