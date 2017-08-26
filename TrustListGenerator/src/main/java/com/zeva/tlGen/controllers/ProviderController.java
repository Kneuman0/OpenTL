package com.zeva.tlGen.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.zeva.temp.jaxb.datamodel.MultiLangNormStringType;
import com.zeva.temp.jaxb.datamodel.NonEmptyMultiLangURIType;
import com.zeva.temp.jaxb.datamodel.PostalAddressType;
import com.zeva.temp.jaxb.datamodel.TSPType;
import com.zeva.tlGen.alert.RemoveAddress;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ProviderController {
	
    @FXML
    private ComboBox<String> 
    		tspElecAddrCombo,
    		addrLangCombo,
    		tspInfoCombo,
    		courntyNameCombo,
    		tspTrdNameeCombo,
    		tspInfoExtCombo,
    		tspNameCombo;


    @FXML
    private Label userWarningLabel;
    
    @FXML
    private Button cancelButton;
    
    @FXML CheckBox criticalCheckBox;

    @FXML
    private TextArea 
    		tspElecAddrText,
    		tspTradeText,
    		tspInfoURIText,
    		tspInfoExtText,
    		tspNameText;


    @FXML
    private TextField 
    		streetText,
    		localityText,
    		postalCodeText,
    		stateText;

    @FXML
    private HBox 
    		tspNamehb,
    		tspTrdNamehb,
    		tspInfoExthb,
    		tspPostalhb,
    		tspInfoURIhb,
    		tspElecAddrhb;
    
    private TSPType provider;
    private Stage stage;
	
	public void initialize(){
		initializeButtons();
		initializeLangs();
	}
	
	private void initializeButtons(){
		tspPostalhb.getChildren().addAll(new ImageButtonAddAddress(), new ImageButtonDeleteAddress(), new ImageButtonSearchAddress());
		tspNamehb.getChildren().addAll(new ImageButtonAddName(), new ImageButtonDeleteName(), new ImageButtonSearchName());
		tspTrdNamehb.getChildren().addAll(new ImageButtonAddTradeName(), new ImageButtonDeleteTradeName(), new ImageButtonSearchTradeName());
		tspInfoExthb.getChildren().addAll(new ImageButtonAddInfoExt(), new ImageButtonDeleteInfoExt(), new ImageButtonSearchInfoExt());
		tspInfoURIhb.getChildren().addAll(new ImageButtonAddInfoURI(), new ImageButtonDeleteInfoURI(), new ImageButtonSearchInfoURI());
		tspElecAddrhb.getChildren().addAll(new ImageButtonAddElecAddr(), new ImageButtonDeleteElecAddr(), new ImageButtonSearchElecAddr());
	}
	
	public void setProvider(TSPType provider){
		this.provider = provider;
		setFields();
	}
	
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
	private void setFields(){
		setTspNames();
		setTspTradeNames();
		setTspPostal();
		setTspElecAddr();
		setInfoURIs();
		setInfoExts();
	}
	
	private void initializeLangs(){
		tspElecAddrCombo.setItems(getlangs());
		addrLangCombo.setItems(getlangs());
		tspInfoCombo.setItems(getlangs());
		tspTrdNameeCombo.setItems(getlangs());
		tspInfoExtCombo.setItems(getlangs());
		tspNameCombo.setItems(getlangs());
		
		// must set this with different values
		courntyNameCombo.setItems(getlangs());
		
	}
	
	private ObservableList<String> getlangs(){
		String[] langs = {"en", "bd", "bg-Latn", "ca", "cs"
				, "da", "de", "el", "el-Latn", "es", "et", "eu",
				"fi", "fr", "ga", "gl", "hr", "hu", "is",
				"it", "lv", "mt", "nb", "nl", "nn", "no",
				"pl", "pt", "ro", "sk", "sl", "sv", "tr"};
		return FXCollections.observableArrayList(Arrays.asList(langs));
	}
	
	protected ObservableList<String> setTspNames(){
		ObservableList<String> list = FXCollections.<String>observableArrayList();
		provider.getTSPInformation().getTSPName().getName();
		for(MultiLangNormStringType name : provider.getTSPInformation().getTSPName().getName()){
			list.add(name.getLang());
		}	
		
		return list;
	}
	
	protected ObservableList<String> setTspTradeNames(){
		ObservableList<String> list = FXCollections.<String>observableArrayList();
		for(MultiLangNormStringType tradenm : provider.getTSPInformation().getTSPTradeName().getName()){
			list.add(tradenm.getLang());
		}	
		
		return list;
	}
	
	protected ObservableList<String> setTspPostal(){
		ObservableList<String> list = FXCollections.<String>observableArrayList();
		for(PostalAddressType addr: provider.getTSPInformation().getTSPAddress().getPostalAddresses().getPostalAddress()){
			list.add(addr.getStreetAddress());
		}
		return list;
	}
	
	protected ObservableList<String> setTspElecAddr(){
		ObservableList<String> list = FXCollections.<String>observableArrayList();
		for(String uri : provider.getTSPInformation().getTSPAddress().getElectronicAddress().getURI()){
			list.add(uri);
		}
	return list;
	}
	
	protected ObservableList<String> setInfoURIs(){
		ObservableList<String> list = FXCollections.<String>observableArrayList();
		for(NonEmptyMultiLangURIType uri : provider.getTSPInformation().getTSPInformationURI().getURI()){
			list.add(uri.getLang());
		}
		return list;
	}
	
	protected void setInfoExts(){
//		ObservableList<String> list = FXCollections.<String>observableArrayList();
//		for(ExtensionType ext : provider.getTSPInformation().getTSPInformationExtensions().getExtension()){
//			list.add(ext.getContent().)
//		}
	}
	
	private int indexOf(List<PostalAddressType> objects, String o){
		for(int i = 0; i < objects.size(); i++){
			if(objects.get(i).getStreetAddress().equals(o)) return i ;
		}
        return -1;
	}
	
	private boolean requiredFieldsFilled(){
		return true;
	}
	
	@SuppressWarnings("static-access")
	public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
	    Node result = null;
	    ObservableList<Node> childrens = gridPane.getChildren();

	    for (Node node : childrens) {
	        if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
	            result = node;
	            break;
	        }
	    }

	    return result;
	}
	
	public class ImageButtonAddInfoExt extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonAddInfoExt() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/add.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonDeleteInfoExt extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonDeleteInfoExt() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/delete.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonSearchInfoExt extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonSearchInfoExt() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/search.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	
	
	public class ImageButtonAddInfoURI extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonAddInfoURI() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/add.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonDeleteInfoURI extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonDeleteInfoURI() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/delete.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonSearchInfoURI extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonSearchInfoURI() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/search.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	
	
	public class ImageButtonAddElecAddr extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonAddElecAddr() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/add.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonDeleteElecAddr extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonDeleteElecAddr() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/delete.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonSearchElecAddr extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonSearchElecAddr() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/search.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonAddTradeName extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonAddTradeName() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/add.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonDeleteTradeName extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonDeleteTradeName() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/delete.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonSearchTradeName extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonSearchTradeName() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/search.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	
	public class ImageButtonAddName extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonAddName() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/add.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonDeleteName extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonDeleteName() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/delete.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonSearchName extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonSearchName() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/search.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
//	               setOpacity(0);
	            }            
	        });
	    }
	    
	}
	
	
	public class ImageButtonAddAddress extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonAddAddress() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/add.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
	               if(!streetText.getText().isEmpty()){
	            	   PostalAddressType addr = new PostalAddressType();
	            	   addr.setCountryName(courntyNameCombo.getSelectionModel().getSelectedItem());
	            	   addr.setLang(addrLangCombo.getSelectionModel().getSelectedItem());
	            	   addr.setLocality(localityText.getText());
	            	   addr.setPostalCode(postalCodeText.getText());
	            	   addr.setStateOrProvince(stateText.getText());
	            	   addr.setStreetAddress(streetText.getText());
	            	   provider.getTSPInformation().getTSPAddress().getPostalAddresses().getPostalAddress().add(addr);
	            	   
	            	   // clear everything
	            	   addrLangCombo.getSelectionModel().clearSelection();
	            	   courntyNameCombo.getSelectionModel().clearSelection();
	            	   
	            	   streetText.clear();
		       			localityText.clear();
		       			postalCodeText.clear();
		       			stateText.clear();
	               }
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonDeleteAddress extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonDeleteAddress() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/delete.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
	               RemoveAddress alert = new RemoveAddress(setTspPostal());
	               Optional<ButtonType> button = alert.showAndWait();
	               if(button.get() == ButtonType.OK){
	            	   
	            	   // delete selected address
	            	   int index = indexOf(provider.getTSPInformation().getTSPAddress()
	            			   .getPostalAddresses().getPostalAddress(), alert.getChoice());
	            	   if(index != -1){
	            		  PostalAddressType addr =  provider.getTSPInformation().getTSPAddress()
            			   .getPostalAddresses().getPostalAddress().remove(index);
	            		  
	            		  // clear lang from combo box
	            		  int indexLang = addrLangCombo.getItems().indexOf(addr.getLang());
	            		  if( indexLang != -1) addrLangCombo.getItems().remove(indexLang);
	            		  
	            		  // clear country name from combo box
	            		  int indexCount = courntyNameCombo.getItems().indexOf(addr.getCountryName());
	            		  if(indexCount != -1) courntyNameCombo.getItems().remove(indexCount);
	            		  
	            	   }
	               }
	               
	               	streetText.clear();
	       			localityText.clear();
	       			postalCodeText.clear();
	       			stateText.clear();
	            }            
	        });
	    }
	    
	}
	
	public class ImageButtonSearchAddress extends Button {
	    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
	    
	    public ImageButtonSearchAddress() {
	    	ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/search.png")));
	    	image.setFitHeight(15);
	    	image.setFitWidth(15);
	        setGraphic(image);
	        setStyle(STYLE_NORMAL);
	        
	                
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
	               RemoveAddress alert = new RemoveAddress(setTspPostal());
	               Optional<ButtonType> button = alert.showAndWait();
	               if(button.get() == ButtonType.OK){
	            	   
	            	   // delete selected address
	            	   int index = indexOf(
	            			   provider.getTSPInformation().getTSPAddress()
	            			   .getPostalAddresses().getPostalAddress(), alert.getChoice());
	            	   PostalAddressType value = provider.getTSPInformation().getTSPAddress()
	            			   .getPostalAddresses().getPostalAddress().get(0);
	            	   System.out.println(index + ": " + alert.getChoice() + " : " + value + value.equals(alert.getChoice()));
	            	   if(index != -1){
	            		  PostalAddressType addr =  provider.getTSPInformation().getTSPAddress()
            			   .getPostalAddresses().getPostalAddress().get(index);
	            		  
	            		  // clear lang from combo box
	            		  int indexLang = addrLangCombo.getItems().indexOf(addr.getLang());
	            		  if( indexLang != -1) addrLangCombo.getSelectionModel().clearAndSelect(indexLang);
	            		  
	            		  // clear country name from combo box
	            		  int indexCount = courntyNameCombo.getItems().indexOf(addr.getCountryName());
	            		  if(indexCount != -1) courntyNameCombo.getSelectionModel().clearAndSelect(indexCount);
	            		  
	            		streetText.setText(addr.getStreetAddress());
	  	       			localityText.setText(addr.getLocality());
	  	       			postalCodeText.setText(addr.getPostalCode());
	  	       			stateText.setText(addr.getStateOrProvince());
	            	   }
	               }
	               
	               	
	            }            
	        });
	    }
	    
	}
	
	public class OnCloseWindowRequest implements EventHandler<WindowEvent>{

		@Override
		public void handle(WindowEvent event) {
			if(!requiredFieldsFilled()){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				String header = "Necessary information incomplete. If you close the window,\n"
						+ "you're information will not be saved.";
				String content = "Do you want to continue?";
				String title = "Information Incomplete";
				alert.setTitle(title);
				alert.setHeaderText(header);
				alert.setContentText(content);
				
				Optional<ButtonType> type = alert.showAndWait();
				if(type.get() == ButtonType.OK){
					provider = null;
					stage.close();
				}
			}
		}
		
	}
	
}
