package com.zeva.tlGen.mainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.JAXBException;

import org.apache.xml.security.exceptions.Base64DecodingException;

import biz.ui.controller.utils.IPopupController;
import biz.ui.launchers.generic.PopupLauncher;

import com.zeva.tlGen.utils.CertificateEncapsulater;
import com.zeva.tlGen.utils.TrustListUtilFactory;
import com.zeva.tlGen.utils.xml.JAXBTrustListMarshallerV5;
import com.zeva.tlGen.utils.xml.JAXBTrustListUnmarshallerV5;
import com.zeva.tlGen.utils.xml.XMLTrustListMarshaller;
import com.zeva.tlGen.utils.xml.XMLTrustListUnmarshaller;
import com.zeva.temp.jaxb.datamodel.DigitalIdentityType;
import com.zeva.temp.jaxb.datamodel.OtherTSLPointerType;
import com.zeva.temp.jaxb.datamodel.TrustStatusListType;
import com.zeva.tlGen.controllers.TLGenSigningController;
import com.zeva.tlGen.controllers.settings.DefaultSettingsController;
import com.zeva.tlGen.custom.callback.CertsCallbackHandler;
import com.zeva.tlGen.custom.callback.PointerCallBackHandler;
import com.zeva.tlGen.dataModel.CertificateBean;
import com.zeva.tlGen.dataModel.ProviderAttribute;
import com.zeva.tlGen.dataModel.TLPointer;
import com.zeva.tlGen.dataModel.utils.DataModelHandler;
import com.zeva.trustlist.popup.PointerToOtherTSLAlert;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser.ExtensionFilter;

public class TLGenController extends TLGenSigningController implements IPopupController{
	
    @FXML
    private TreeTableView<ProviderAttribute> tlCertsTree, uploadedCertsTree;

    @FXML
    private TreeTableColumn<ProviderAttribute, ProviderAttribute> uploadedCertsTreeCol, tlCertsTreeCol;
    
    @FXML
    private TreeTableColumn<TLPointer, TLPointer> trustListsCol, listOfListsCol;
    
    @FXML
    private TreeTableView<TLPointer> trustLists, listOfLists;

    @FXML
    private TextArea textArea, textArea1;
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private GridPane listOfListGridPane, tlGridPane;	
    
    @SuppressWarnings("unused")
	private Stage stage;
    
    
    TrustListUtilFactory tlFactory;
    	
	public void initialize(){		
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
	}
	
	/*****************************************************************************
	 * 																			 *
	 * 																			 *
	 * 							First Tab Listeners								 *
	 * 																			 *
	 *																			 *
	 *****************************************************************************/
	
	public void exportCertsToPemListener(){
		// do nothing if there are no certs to export
		List<TreeItem<ProviderAttribute>> tlCerts = tlCertsTree.getRoot().getChildren();
		if(tlCerts == null || tlCerts.size() == 0) return;
		
		File certFile = requestSave("PEM File Location",
				"TestMe", new ExtensionFilter("Privacy Enhanced Mail", "*.pem"));

		// if null, user clicked cancel so do nothing
		if (certFile == null) {
			return;
		} else {
			try {
				tlFactory.exportNodesToPem(
						tlCertsTree.getRoot().getChildren(), certFile);
			} catch (IOException e) {
				String header = "An error occured while exporting the .pem file";
				displayErrorMessage("Export Error", header,	null, e);
			} catch (CertificateEncodingException e) {
				String header = "An error occured while exporting the .pem file";
				displayErrorMessage("Export Error", header,	null, e);
			}
		}
		
		
	}
	
	public void importTrustListListener(){	
		/**
		 * Let user pick the file containing the trust list
		 */		
		List<File> trustLists = requestFiles("Trust List Location", null, 
				new ExtensionFilter("XML Document", "*.xml"));

		// if null, user clicked cancel so do nothing
		if (trustLists == null) {
			return;
		} else {
			for(File tl : trustLists){
				try {
					XMLTrustListUnmarshaller um = new JAXBTrustListUnmarshallerV5(tl);
//					CertificateValidationProvider provider = requestVerificationInfo();
//					IXmlSignatureVerifier verif = new Xades4jVerifier(provider);
//					verif.Verify(um.getDocument());
					System.out.println("Trust List: " + um.getTrustList().getSchemeInformation());
					addCertsToTreeProvider(new DataModelHandler(um.getTrustList()).getCerts());
					
				} catch(Exception error){
					String title = "Upload Error";
					String header = "An error occured while reading one or more files\n"
							+ "Error: " + error.getMessage();
					displayErrorMessage(title, header, null, error);
				}
				
			}
			
		}
	}
	
	public void addCertsToTrustListButton(){
		/**
		 * Copies certificate beans from Certificate list to
		 * trust list
		 */
		ObservableList<TreeItem<ProviderAttribute>> chosenCerts = 
		uploadedCertsTree.getSelectionModel().getSelectedItems();
		tlCertsTree.getRoot().getChildren().addAll(chosenCerts);
	}
	
	public void onExportTl(){
		TrustStatusListType tl = null;
		XMLTrustListMarshaller v5 = null;
		/**
		 * Let user pick location to save TL file
		 */		
		File certFile = requestSave("XML Trust List File Location", "TestMe", 
				new ExtensionFilter("XML Document", "*.xml"));

		// if null, user clicked cancel so do nothing
		if (certFile == null) {
			return;
		} else {
			// make list of ProviderAttributes from items in TL table
			List<ProviderAttribute> beans = extractProviderAttributes(tlCertsTree.getRoot().getChildren());
			
			try {
				XMLTrustListUnmarshaller um = new JAXBTrustListUnmarshallerV5(new File("defaultXML.xml"));
				tl = tlFactory.buildTrustList(beans, um.getTrustList());
				// instantial marshaller
				v5 = new JAXBTrustListMarshallerV5(tl);
				v5.marshalTrustList();
			} catch (FileNotFoundException e) {
				String title = "Load Error";
				String header = "Default trust list values could not be found";
				displayErrorMessage(title, header, null, e);
				return;
			} catch (JAXBException e) {
				displayErrorMessage("XML Error", "An error occured while forming the XML document", null, e);
				return;
			} catch (XMLParseException e) {
				displayErrorMessage("XML Error", "An error occured while forming the XML document", null, e);
				return;
			}
			
			signAndPrint(v5, certFile);
			
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
		//Let user specify file location/s
		List<File> certFiles = requestFiles("Import Certificates", null, tlFactory.getSupportedExtFilters());

		// if null, user clicked cancel so do nothing
		if (certFiles == null) {
			return;
		} else {
			// for each file, read in and add to list
			for(File certFile : certFiles){
				try {

					CertificateEncapsulater encap = new CertificateEncapsulater(certFile);
					addCertsToTree(encap.getEncapulatedCerts());
					
				} catch (CertificateException | Base64DecodingException
						| IOException e) {
					String title = "Upload Error";
					String header = "An error occured while reading one or more files\n"
							+ "Error: " + e.getMessage();
					displayErrorMessage(title, header, null, e);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					displayErrorMessage("Upload Error", "An error occured during upload", null, e);
				}
				
			}
			
		}
	}
	
	public void onSettingMenuSelected() throws MalformedURLException{
		//Launch settings menu from FXML file
		
		PopupLauncher<DefaultSettingsController> settings = 
				new PopupLauncher<DefaultSettingsController>
		("Default Trust List Settings", DefaultSettingsController.class.getResource(
				"/resources/SettingsGUI.fxml"));
//		PopupLauncher<DefaultSettingsController> settings = 
//				new PopupLauncher<DefaultSettingsController>
//		("Default Trust List Settings", new File("C:/Users/Karottop/workspace-tlgen/SettingsMenu/src/main/java/resources/SettingsGUI.fxml").toURL());
//		C:/Users/Karottop/workspace-tlgen/SettingsMenu/src/main/java/resources/SettingsGUI.fxml
		settings.getStage().getScene().getWindow().setOnCloseRequest(
				settings.getController().getOnCloseRequest());
		settings.getStage().initStyle(StageStyle.UTILITY);	
		settings.show();
	}
	
	@SuppressWarnings("unchecked")
	public void onSelectionChange(MouseEvent event){
		// if tableview, display the overriden certificate bean string
		if(event.getSource() instanceof TreeTableView ){
			try {
				TreeTableView<Object> tree = (TreeTableView<Object>)event.getSource();
				Object item = tree.getSelectionModel().getSelectedItem().getValue();
				if(item instanceof ProviderAttribute){
					textArea.setText(item.toString());
				}else{
					textArea1.setText(item.toString());
				}
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void onUploadTrustLists(){
		// Let user pick trust list of lists file location/s		
		List<File> trustListFiles = requestFiles("Trust List of Lists Location",
				null, new ExtensionFilter("XML Document", "*.xml"));

		// if null, user clicked cancel so do nothing
		if (trustListFiles == null) {
			return;
		} else {
			// for each TL, read in service providers and add to trust list
			for(File file : trustListFiles){
				try {
					
					// need to make class that determines the version of the tl
					XMLTrustListUnmarshaller um = new JAXBTrustListUnmarshallerV5(file);
//					CertificateValidationProvider provider = requestVerificationInfo();
//					IXmlSignatureVerifier verif = new Xades4jVerifier(provider);
//					verif.Verify(um.getDocument());
					List<ProviderAttribute> certs = tlFactory.extractCertificates(um.getTrustList());
					addCertsToTreeProvider(certs);
					
//					trustLists.getRoot().getChildren().add(new TreeItem<TLPointer>(tl));
				} catch (Exception e) {
					String header = "An error occured while uploading trust list:\n"
							+ e.getMessage();
					displayErrorMessage("Upload Error", header, null, e);
				} 
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
		
	public void onAddButtonForTrustListOfLists(){
		// get all selected items from Certificates table
		List<TreeItem<TLPointer>> list = trustLists.getSelectionModel().getSelectedItems();
		// if no certs were selected, do nothing
		if(list == null || list.size() == 0) return;
		
		// add all certs
		listOfLists.getRoot().getChildren().addAll(list);
	}
	
	public void onAddPointer(){
		PointerToOtherTSLAlert alert = new PointerToOtherTSLAlert();
		Optional<ButtonType> btn = alert.showAndWait();
		if(btn.get() == ButtonType.OK){
			TLPointer pointer = alert.getPointer();
			trustLists.getRoot().getChildren().add(new TreeItem<TLPointer>(pointer.initialize()));
		}else{
			return;
		}
		
		
	}
	
	public void exportTrustListofLists(){
		TrustStatusListType tl = null;
		XMLTrustListMarshaller marshaller = null;
		try {
			XMLTrustListUnmarshaller um = new JAXBTrustListUnmarshallerV5(new File("defaultXML.xml"));
			tl = um.getTrustList();
			tlFactory.buildTrustListofLists(listOfLists.getRoot().getChildren(), tl);
			marshaller = new JAXBTrustListMarshallerV5(tl);
			marshaller.marshalTrustList();
		} catch (JAXBException e) {
			displayErrorMessage("XML Error", "An error occured while forming the XML document", null, e);
		} catch (XMLParseException e) {
			displayErrorMessage("XML Error", "An error occured while forming the XML document", null, e);
			return;
		}
		
		File tlolLoc = requestSave("Save Trust List Of Lists", 
				new Date().toString().replace(":", ""), new ExtensionFilter("XML Document", "*.xml"));
		if(tlolLoc == null){
			return;
		}else{
			signAndPrint(marshaller, tlolLoc);
		}
	}
	
	
	
	
	/*****************************************************************************
	 * 																			 *
	 * 																			 *
	 * 						First Tab Helper Methods							 *
	 * 																			 *
	 *																			 *
	 *****************************************************************************/
	
	private void addCertsToTree(List<CertificateBean> certs){
		// Adds all certs to uploaded certs tree including child certs
		TreeItem<ProviderAttribute> root = uploadedCertsTree.getRoot();
		for(CertificateBean bean : certs){
			TreeItem<ProviderAttribute> parent = new TreeItem<>(new DigitalIdentityType(bean));
			
			// add parent cert bean
			root.getChildren().add(parent);
		}
	}
	
	private void addCertsToTreeProvider(List<ProviderAttribute> certs){
		// Adds all certs to uploaded certs tree including child certs
		TreeItem<ProviderAttribute> root = uploadedCertsTree.getRoot();
		for(ProviderAttribute bean : certs){
			TreeItem<ProviderAttribute> parent = new TreeItem<>(bean);
			
			// add parent cert bean
			root.getChildren().add(parent);
		}
	}
	
	private void initializeFirstTabTrees(){
		// interface for calling ProviderAttribute property "name". Maps to method name "nameProperty()"
		uploadedCertsTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<ProviderAttribute, ProviderAttribute>("name"));
		tlCertsTreeCol.setCellValueFactory(new TreeItemPropertyValueFactory<ProviderAttribute, ProviderAttribute>("name"));
		
		// set roots. superfluous. only there to add children. Never displayed to user
		ProviderAttribute dummyRootCert = new DigitalIdentityType("Uploaded Certificates");
		ProviderAttribute dummyTLCertRoot = new DigitalIdentityType("TL Certificates");
		TreeItem<ProviderAttribute> rootUpload = new TreeItem<>(dummyRootCert);
		TreeItem<ProviderAttribute> rootTL = new TreeItem<>(dummyTLCertRoot);
		
		// Set size of each container for cell to take shape
		uploadedCertsTree.setFixedCellSize(60);
		
		// set custom cell factory for managing container displayed to user
		uploadedCertsTreeCol.setCellFactory(new CertsCallbackHandler());
		tlCertsTreeCol.setCellFactory(new CertsCallbackHandler());	
		
		uploadedCertsTree.setRoot(rootUpload);
		tlCertsTree.setRoot(rootTL);
		// remove root from user view
		uploadedCertsTree.setShowRoot(false);
		tlCertsTree.setShowRoot(false);
		// allow multiple user selection
		uploadedCertsTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tlCertsTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// do not allow sorting. User will sort manually
		uploadedCertsTreeCol.setSortable(false);
		tlCertsTreeCol.setSortable(false);
	}
	
	private List<ProviderAttribute> extractProviderAttributes(List<TreeItem<ProviderAttribute>> nodes){
		List<ProviderAttribute> beans = new ArrayList<ProviderAttribute>();
		if(nodes == null) return null;
		for(TreeItem<ProviderAttribute> bean : nodes){
			beans.add(bean.getValue());
		}
		return beans;
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
		trustListsCol.setCellValueFactory(new TreeItemPropertyValueFactory<TLPointer, TLPointer>("name"));
		listOfListsCol.setCellValueFactory(new TreeItemPropertyValueFactory<TLPointer, TLPointer>("name"));
		
		TLPointer dummyRootCert = new OtherTSLPointerType("Uploaded Pointers");
		TLPointer dummyTLCertRoot = new OtherTSLPointerType("LoTL Pointers");
		TreeItem<TLPointer> rootUpload = new TreeItem<>(dummyRootCert);
		TreeItem<TLPointer> rootTL = new TreeItem<>(dummyTLCertRoot);
		
		trustListsCol.setCellFactory(new PointerCallBackHandler());
		listOfListsCol.setCellFactory(new PointerCallBackHandler());
		
		trustLists.setRoot(rootUpload);
		trustLists.setShowRoot(false);
		trustLists.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listOfLists.setRoot(rootTL);
		listOfLists.setShowRoot(false);
		listOfLists.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		trustListsCol.setSortable(false);
		listOfListsCol.setSortable(false);
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
	

	@Override
	public Window getWindow() {
		// TODO Auto-generated method stub
		return tlCertsTree.getScene().getWindow();
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	

	
	

}
