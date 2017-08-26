package com.zeva.tlGen.mainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.JAXBException;

import xades4j.XAdES4jException;

import com.zeva.temp.jaxb.datamodel.OtherTSLPointerType;
import com.zeva.temp.jaxb.datamodel.TSPServiceType;
import com.zeva.temp.jaxb.datamodel.TSPType;
import com.zeva.temp.jaxb.datamodel.TrustStatusListType;
import com.zeva.tlGen.controllers.ProviderController;
import com.zeva.tlGen.controllers.CallBackHandlers.TLTableCallbackHandler;
import com.zeva.tlGen.controllers.settings.DefaultSettingsController;
import com.zeva.tlGen.dataModel.CertificateBean;
import com.zeva.tlGen.dataModel.ProviderAttribute;
import com.zeva.tlGen.dataModel.ProviderAttribute.ProviderAttributeType;
import com.zeva.tlGen.dataModel.Root;
import com.zeva.tlGen.utils.TrustListUtilFactory;
import com.zeva.tlGen.utils.xml.IPrinter;
import com.zeva.tlGen.utils.xml.JAXBTrustListMarshallerV5;
import com.zeva.tlGen.utils.xml.JAXBTrustListUnmarshallerV5;
import com.zeva.tlGen.utils.xml.TrustListXMLPrinter;
import com.zeva.tlGen.utils.xml.XMLTrustListMarshaller;
import com.zeva.tlGen.utils.xml.XMLTrustListUnmarshaller;
import com.zeva.tlGen.xml.dsig.CertificateValidationProviderFactory;
import com.zeva.tlGen.xml.dsig.FileSystemKeyStoreKeyingDataProviderFactory;
import com.zeva.tlGen.xml.dsig.IXmlSignatureVerifier;
import com.zeva.tlGen.xml.dsig.IXmlSigner;
import com.zeva.tlGen.xml.dsig.Xades4jSigner;
import com.zeva.tlGen.xml.dsig.Xades4jVerifier;

public class TLGenController {
	
    @FXML
    private TreeTableView<ProviderAttribute> tlCertsTree, uploadedCertsTree;

    @FXML
    private TreeTableColumn<ProviderAttribute, ProviderAttribute> uploadedCertsTreeCol, tlCertsTreeCol;
    
    @FXML
    private TreeTableColumn<OtherTSLPointerType, CertificateBean> trustListsCol, listOfListsCol;
    
    @FXML
    private TreeTableView<OtherTSLPointerType> trustLists, listOfLists;

    @FXML
    private TextArea textArea, textArea1;
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private GridPane listOfListGridPane, tlGridPane;
    
    private XMLTrustListUnmarshaller um;
    
    
    TrustListUtilFactory tlFactory;
    	
	public void initialize(){
		
		// Remove me!
		listOfListGridPane.setDisable(true);
		
		try {
			this.um = new JAXBTrustListUnmarshallerV5(new File("defaultSettings.xml"));
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XMLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * Allows splash screen to load for a little while to give the
		 * Zeva logo a little air time
		 */
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		this.tlFactory = new TrustListUtilFactory();
		
		setBackgroundImages();
		
		/**
		 * Sets the content handlers for the two tables
		 */
		initializeTreeTables();
		
		// vestigial. An attempt to change the color of the tab header background
		setTabPaneBackground();
	}
	
	/*****************************************************************************
	 * 																			 *
	 * 																			 *
	 * 							First Tab Listeners								 *
	 * 																			 *
	 *																			 *
	 *****************************************************************************/
	
	public void exportCertsToPemListener(){
		
		List<TreeItem<ProviderAttribute>> attr = uploadedCertsTree.getRoot().getChildren();
		System.out.println(attr.get(0).getChildren().get(0));
//		// do nothing if there are no certs to export
//		List<TreeItem<CertificateBean>> tlCerts = new ArrayList<>();
//		for(ProviderAttribute attr : tlCertsTree.getRoot().getChildren())
//				tlCertsTree.getRoot().getChildren();
//		if(tlCerts == null || tlCerts.size() == 0) return;
//		
//		/**
//		 * Prompt the user for a destination file location
//		 */
//		FileChooser chooser = new FileChooser();
//		chooser.setTitle("PEM File Location");
//		chooser.setInitialFileName("TestMe");
//		
//		chooser.getExtensionFilters().add(
//				new ExtensionFilter("Privacy Enhanced Mail", "*.pem"));
//		
//		File certFile = chooser.showSaveDialog(tlGridPane.getScene()
//				.getWindow());
//
//		// if null, user clicked cancel so do nothing
//		if (certFile == null) {
//			return;
//		} else {
//			try {
//				// need to word on dependency injection for this.
//				tlFactory.exportNodesToPem(
//						tlCertsTree.getRoot().getChildren(), certFile);
//			} catch (IOException e) {
//				String header = "An error occured while exporting the .pem file";
//				displayErrorMessage("Export Error", header,	null, e);
//			} catch (CertificateEncodingException e) {
//				String header = "An error occured while exporting the .pem file";
//				displayErrorMessage("Export Error", header,	null, e);
//			}
//		}
//		
//		
	}
	
	public void importTrustListListener(){	
		/**
		 * Let user pick the file containing the trust list
		 */
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Excel File Location");
		chooser.setInitialFileName("TestMe");
		
		chooser.getExtensionFilters().add(
				new ExtensionFilter("XML Document", "*.xml"));
		
		List<File> trustLists = chooser.showOpenMultipleDialog(tlGridPane.getScene()
				.getWindow());

		// if null, user clicked cancel so do nothing
		if (trustLists == null) {
			return;
		} else {
			for(File tl : trustLists){
				try {
					// need to add code that checks for signature validity after
					// the certs have loaded
					// need to make a class that determines the version of unmarshaller
					XMLTrustListUnmarshaller um = new JAXBTrustListUnmarshallerV5(tl);
//					addCertsToTree(this.tlFactory.getUnmarshalledCertsFromTL(um));
					
					IXmlSignatureVerifier verifier = new Xades4jVerifier(
							CertificateValidationProviderFactory.Create("", "", "")
							);
					 
					
						verifier.Verify(um.getDocument());
							String header = "Signature invalid!";
							displayErrorMessage("Signature Error", header, null, null);
					
					
					
					
				
				} catch(Exception error){
					String title = "Upload Error";
					String header = "An error occured while reading one or more files\n"
							+ "Error: " + error.getMessage();
					displayErrorMessage(title, header, null, error);
				}
				
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void onRightClickTableCell(MouseEvent event){
		TreeTableView<ProviderAttribute> table = 
				(TreeTableView<ProviderAttribute>)event.getSource();
		TreeItem<ProviderAttribute> parent = table.getSelectionModel().getSelectedItem();
		
		
	}
	
	public void addCertsToTrustListButton(){
		/**
		 * Copies certificate beans from Certificate list to
		 * trust list
		 */
//		ObservableList<TreeItem<CertificateBean>> chosenCerts = 
//		uploadedCertsTree.getSelectionModel().getSelectedItems();
//		tlCertsTree.getRoot().getChildren().addAll(chosenCerts);
	}
	
	public void onExportTl(){
		/**
		 * Let user pick location to save TL file
		 */
		FileChooser chooser = new FileChooser();
		chooser.setTitle("XML Trust List File Location");
		chooser.setInitialFileName("TestMe");
		
		chooser.getExtensionFilters().add(
				new ExtensionFilter("XML Document", "*.xml"));
		
		File certFile = chooser.showSaveDialog(tlGridPane.getScene()
				.getWindow());

		// if null, user clicked cancel so do nothing
		if (certFile == null) {
			return;
		} else {
			// make list of CertificateBeans from items in TL table
			List<ProviderAttribute> beans = new ArrayList<>();
			List<TreeItem<ProviderAttribute>> nodes = tlCertsTree.getRoot().getChildren();
			if(nodes == null) return;
			for(TreeItem<ProviderAttribute> bean : nodes){
				beans.add(bean.getValue().getEncapsulatedBean());
			}
			
			
			TrustStatusListType tl = null;
//			try {
//				tl = tlFactory.buildTrustList(beans, "defaultValues.xml");
//			} catch (FileNotFoundException e) {
//				String title = "Load Error";
//				String header = "Default trust list values could not be found";
//				displayErrorMessage(title, header, null, e);
//			}
			
			// instantial marshaller
			XMLTrustListMarshaller v5 = new JAXBTrustListMarshallerV5(tl);
			
			// Marshall the trust list into the Document object
			v5.marshalTrustList();
			
			// get credentials
			PrivateKey key = tlFactory.getPrivateKey("password", "newKey");
			X509Certificate cert = tlFactory.getCertificate("newCert", "password");
			
			// instantiate signer
			try {
				IXmlSigner signer = new Xades4jSigner(FileSystemKeyStoreKeyingDataProviderFactory
						.createFileSystemKeyingDataProvider("", "", "", false));
			} catch (KeyStoreException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
//				signer.signDocument(v5.getMarshalledDocument(), key, cert);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// instantiate printer
			IPrinter printer = new TrustListXMLPrinter(v5.getMarshalledDocument());
			
			// print document
			try {
				printer.print(certFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void onDeleteCerts(KeyEvent event){
		if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
			// get tableview source
			TreeTableView<Object> bean = (TreeTableView<Object>)event.getSource();
			// make sure user really wants to delete
			Alert ensureDelete = new Alert(AlertType.CONFIRMATION);
			ensureDelete.setTitle("Confirm Delete");
			ensureDelete.setHeaderText("Deleting cannot be undone, are you sure you\n"
					+ "want to delete the highlighted certificates?");
			ensureDelete.setContentText(null);
			Optional<ButtonType> optional = ensureDelete.showAndWait();
			if(optional.get() == ButtonType.OK){
				// need to use dependency injection
				tlFactory.deleteSelectedItems(bean.getRoot().getChildren(),
						bean.getSelectionModel().getSelectedItems()); 
			}else{
				return;
			}
		}
		
	}
	
	public void importCertificatesButtonListener(){
		editProvider(tlFactory.createEmptyTSPType());
		//Let user specify file location/s
//		FileChooser chooser = new FileChooser();
//		chooser.setTitle("Excel File Location");
//		chooser.setInitialFileName("TestMe");
//		
//		chooser.getExtensionFilters().addAll(
//				tlFactory.getSupportedExtFilters());
//		
//		List<File> certFiles = chooser.showOpenMultipleDialog(tlGridPane.getScene()
//				.getWindow());
//
//		// if null, user clicked cancel so do nothing
//		if (certFiles == null) {
//			return;
//		} else {
//			// for each file, read in and add to list
//			for(File certFile : certFiles){
//				try {
//
//					CertificateEncapsulater encap = new CertificateEncapsulater(certFile);
//					addCertsToTree(encap.getEncapulatedCerts());
//					
//				} catch (CertificateException | IOException e) {
//					String title = "Upload Error";
//					String header = "An error occured while reading one or more files\n"
//							+ "Error: " + e.getMessage();
//					displayErrorMessage(title, header, null, e);
//				} catch (Base64DecodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
//				
//			}
//			
//		}
	}
	
	public void onSettingMenuSelected(){
		//Launch settings menu from FXML file
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UTILITY);
		FXMLLoader loader = null;
		Parent parent = null;
		try {
			loader = new FXMLLoader(DefaultSettingsController.class.getResource(
					"/resources/SettingsGUI.fxml"));
			
			parent = (Parent) loader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(parent);
		DefaultSettingsController controller = 
				loader.<DefaultSettingsController>getController();

		// window title
		stage.setTitle("Default Trust List Settings");
		stage.setScene(scene);
		scene.getWindow().setOnCloseRequest(new OnSettingClose(controller));
		stage.showAndWait();
	}
	
	@SuppressWarnings("unchecked")
	public void onSelectionChange(MouseEvent event){
//		int index = uploadedCertsTree.getSelectionModel().getSelectedIndex();
//		uploadedCertsTree.requestFocus();
//		uploadedCertsTree.getSelectionModel().focus(index);
//		uploadedCertsTree.getColumns().get(0).setVisible(false);
//		uploadedCertsTree.getColumns().get(0).setVisible(true);


		
		// if tableview, display the overriden certificate bean string
		if(event.getSource() instanceof TreeTableView ){
			try {
				TreeTableView<Object> tree = (TreeTableView<Object>)event.getSource();
				Object item = tree.getSelectionModel().getSelectedItem().getValue();
				if(event.getButton() != MouseButton.SECONDARY){
					if(item instanceof ProviderAttribute){
						textArea.setText(item.toString());
					}else{
						textArea1.setText(item.toString());
					}
					return;
				}				
				
				if(item instanceof Root){
					System.out.println("Made it");
					ContextMenu menu = new ContextMenu();
//					MenuItem item = new MenuItem("Add Provider");
					editProvider(tlFactory.createEmptyTSPType());
				}else if(item instanceof TSPServiceType){
					// ask to add Service
				}else{
					// ask to add Cert
				}
				
				
			} catch (NullPointerException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
		
	
	/*****************************************************************************
	 * 																			 *
	 * 																			 *
	 * 							Second Tab Listeners							 *
	 * 																			 *
	 *																			 *
	 *****************************************************************************/
	
	public void onUploadTrustLists(){
		// Let user pick trust list file location/s
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Excel File Location");
		
		chooser.getExtensionFilters().add(
				new ExtensionFilter("XML Document", "*.xml"));
		
		List<File> trustListFiles = chooser.showOpenMultipleDialog(tlGridPane.getScene()
				.getWindow());

		// if null, user clicked cancel so do nothing
		if (trustListFiles == null) {
			return;
		} else {
			// for each TL, read in service providers and add to trust list
//			for(File file : trustListFiles){
//				TrustListBean tl; 
//				try {
//					
//					// need to make class that determines the version of the tl
//					XMLTrustListUnmarshaller um = new JAXBTrustListUnmarshallerV5(file);
//					tl = new TrustListBean(um.getTrustList());
//					
//					trustLists.getRoot().getChildren().add(new TreeItem<TrustListBean>(tl));
//				} catch (Exception e) {
//					String header = "An error occured while uploading trust list:\n"
//							+ e.getMessage();
//					displayErrorMessage("Upload Error", header, null, e);
//				} 
//			}
		}
	}
		
	public void onAddButtonForTrustList(){
		// get all selected items from Certificates table
//		List<TreeItem<TrustListBean>> list = trustLists.getSelectionModel().getSelectedItems();
//		// if no certs were selected, do nothing
//		if(list == null || list.size() == 0) return;
//		
//		// add all certs
//		listOfLists.getRoot().getChildren().addAll(list);
	}
	
	
	/*****************************************************************************
	 * 																			 *
	 * 																			 *
	 * 						First Tab Helper Methods							 *
	 * 																			 *
	 *																			 *
	 *****************************************************************************/
	private TSPType editProvider(TSPType provider){
		Stage stage = new Stage();
		stage.setResizable(false);
		FXMLLoader loader = null;
		Parent parent = null;
		try {
			// Reads in the FXML file and initializes the fields in the
			// controller/GUI
			loader = new FXMLLoader(getClass().getResource(
					"/resources/providerGUI.fxml"));
			parent = loader.<Parent> load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		// Retrieves the instance of the controller
		@SuppressWarnings("unused")
		ProviderController controller = loader
				.<ProviderController> getController();
		System.out.println("Info: " + provider.getTSPInformation().getTSPInformationExtensions());
		controller.setProvider(provider);
		controller.setStage(stage);

		Scene scene = new Scene(parent);

		// window title
		stage.setTitle("Trust List Generator");
		stage.setScene(scene);
		stage.show();
		return provider;
	}
	

	
	private void addCertsToTree(List<CertificateBean> certs){
//		// Adds all certs to uploaded certs tree including child certs
//		TreeItem<CertificateBean> root = uploadedCertsTree.getRoot();
//		for(CertificateBean bean : certs){
//			TreeItem<CertificateBean> parent = new TreeItem<>(bean);
//			// add children certs
//			for(CertificateBean child : bean.getChildrenCerts()){
//				parent.getChildren().add(new TreeItem<CertificateBean>(child));
//			}
//			// add parent cert bean
//			root.getChildren().add(parent);
//		}
	}
	
	private boolean isRoot(TreeItem<ProviderAttribute> attr){
		return attr.getValue().getType() == ProviderAttributeType.ROOT;
	}
	
	
	
	private void initializeFirstTabTrees(){
		// interface for calling CertificateBean property "name". Maps to method name "nameProperty()"
		uploadedCertsTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<ProviderAttribute, ProviderAttribute>("name"));
		tlCertsTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<ProviderAttribute, ProviderAttribute>("name"));
		
		// set roots. superfluous. only there to add children.
		TreeItem<ProviderAttribute> rootUpload = new TreeItem<>(new Root("Uploaded Providers"));
		TreeItem<ProviderAttribute> rootTL = new TreeItem<>(new Root("TL Providers"));
		
		for(TSPType provider : um.getProviderList()){
			rootUpload.getChildren().add(tlFactory.buildProvider(provider));
		}
		
		// Set size of each container for cell to take shape
		uploadedCertsTree.setFixedCellSize(40);
//		uploadedCertsTree.getSelectionModel().setCellSelectionEnabled(true);
		
		// set custom cell factory for managing container displayed to user
		uploadedCertsTreeCol.setCellFactory(new TLTableCallbackHandler());
		tlCertsTreeCol.setCellFactory(new TLTableCallbackHandler());	
		
		uploadedCertsTree.setRoot(rootUpload);
		tlCertsTree.setRoot(rootTL);
		// remove root from user view
//		uploadedCertsTree.setShowRoot(false);
		tlCertsTree.setShowRoot(false);
		// allow multiple user selection
		uploadedCertsTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tlCertsTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// do not allow sorting. User will sort manually
		uploadedCertsTreeCol.setSortable(false);
		tlCertsTreeCol.setSortable(false);
	}
	
	/*****************************************************************************
	 * 																			 *
	 * 																			 *
	 * 						Second Tab Helper Methods							 *
	 * 																			 *
	 *																			 *
	 *****************************************************************************/
	
	/**
	 * Not yet implemented
	 */
	private void initializeSecondTabTrees(){
//		trustListsCol.setCellValueFactory(new TreeItemPropertyValueFactory<TrustListBean, CertificateBean>("name"));
//		listOfListsCol.setCellValueFactory(new TreeItemPropertyValueFactory<TrustListBean, CertificateBean>("name"));
//		
//		TrustListBean dummyRootCert = new TrustListBean("Uploaded Trust Lists");
//		TrustListBean dummyTLCertRoot = new TrustListBean("TL Certificates");
//		TreeItem<TrustListBean> rootUpload = new TreeItem<>(dummyRootCert);
//		TreeItem<TrustListBean> rootTL = new TreeItem<>(dummyTLCertRoot);
//		
//		trustLists.setRoot(rootUpload);
//		trustLists.setShowRoot(false);
//		trustLists.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//		listOfLists.setRoot(rootTL);
//		listOfLists.setShowRoot(false);
//		listOfLists.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//		trustListsCol.setSortable(false);
//		listOfListsCol.setSortable(false);
	}
	
	/*****************************************************************************
	 * 																			 *
	 * 																			 *
	 * 								Style Methods							 	 *
	 * 																			 *
	 *																			 *
	 *****************************************************************************/
	
	/**
	 * Method for consolidating table initialization
	 */
	private void initializeTreeTables(){
		initializeFirstTabTrees();
		initializeSecondTabTrees();
	}
	
	/**
	 * Convienience method for displaying errors to user
	 * @param title displayed at top of window
	 * @param header content displayed right next to symbol
	 * @param content content below symbol (usually null)
	 * @param exp Exception that caused the error
	 */
	private void displayErrorMessage(String title, String header, String content, Exception exp){
		Alert error = new Alert(AlertType.ERROR);
		error.setContentText(content);
		error.setHeaderText(header);
		error.setTitle(title);
		exp.printStackTrace();
		error.showAndWait();
	}
	
	/**
	 * Sets the background image from the image located in the resources folder
	 */
	private void setBackgroundImages() {
		Image logo = new Image(getClass().getResourceAsStream(
				"/resources/background.png"));
		
		BackgroundSize logoSize = new BackgroundSize(600, 400, false, false,
				true, true);
		
		BackgroundImage image = new BackgroundImage(logo,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, logoSize);
		Background background = new Background(image);
		tlGridPane.setBackground(background);
		
		Image logo1 = new Image(getClass().getResourceAsStream(
				"/resources/background2.png"));
		
		BackgroundImage image1 = new BackgroundImage(logo1,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, logoSize);
		
		Background background1 = new Background(image1);
		listOfListGridPane.setBackground(background1);
	}
	
	
	private void setTabPaneBackground(){
//		System.out.println(tabPane.getStyle());
//		tabPane.getStylesheets().add(getClass().getResource("/resources/removeGray.css").toString());
	}
	
	
	/*****************************************************************************
	 * 																			 *
	 * 																			 *
	 * 							Threads/Inner Classes 							 *
	 * 																			 *
	 *																			 *
	 *****************************************************************************/

	
	/**
	 * Listener for saving the preferences when the user closes the GUI using
	 * the mechanism provided by the underlying OS (red "X" at the top right
	 * corner for windows)
	 * 
	 * @author Karottop
	 *
	 */
	public static class OnCloseWindow implements EventHandler<WindowEvent> {

		@Override
		public void handle(WindowEvent event) {
			/**
			 *  will save preferences here when we get some. At the moment, no preference have been identified
			 */
			
			
			Platform.exit();
		}

	}
	
	
	public class OnSettingClose implements EventHandler<WindowEvent> {
		
		DefaultSettingsController controller;
		
		public OnSettingClose(DefaultSettingsController controller) {
			this.controller = controller;
		}

		@Override
		public void handle(WindowEvent event) {
//			XmlUtilities.exportObjectToXML(controller.getSettingsTL(), "defaultValues.xml");
			
		}

	}
	
//	public class OnAddProvider imp
	
	

}
