package com.zeva.tlGen.controllers;

import java.util.ArrayList;
import java.util.List;

import com.zeva.temp.dataModellib.PhysicalAddressBean;
import com.zeva.tlGen.dataModel.settings.SettingBean;
import com.zeva.tlGen.dataModel.settings.SettingBean.SETTING_TYPE;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SettingsChangeWindow extends Alert {
	
	
	private SETTING_TYPE type = null;
	
	private IndexedGridPane parentGrid;
	private SettingBean bean;
	private DialogPane parentPane;
	
	
	public SettingsChangeWindow(SettingBean bean) {
		super(AlertType.CONFIRMATION);
//		this.bean = bean;
//		this.type = bean.getType();
//		ScrollPane scroll = new ScrollPane();
//		parentGrid = new IndexedGridPane();
//		scroll.setContent(parentGrid);
//		this.parentPane = getDialogPane();
//		setResizable(true);
//		
//		if(type == SETTING_TYPE.SINGLE){
//			FriendlyVBox vbox = new FriendlyVBox();
//			setTitle("Change " + bean.getName());
//			setHeaderText("Change the " + bean.getName() + " value by changing the value in the box");
//			parentGrid.setPadding(new Insets(20, 150, 0, 10));
//			vbox.getChildren().addAll(new Label(bean.getName()), new AutoTextBox(bean.getValue()));
//			parentGrid.add(vbox, 0, 0);
//		}else{
//			setTitle("Change " + bean.getName());
//			String header = "Change the " + bean.getName() + " value by changing the value in the box\n";
//			header += "You may add and delete value sets";
//			setHeaderText(header);
//			parentGrid.add(new AddButton(), 1, 0);
//			parentGrid.add(new RemoveButton(), 2, 0);
//			addMultipleValues();
//		}
//		
//		this.parentPane.setContent(scroll);
		
//		parentPane.setMinHeight(GridPane.USE_PREF_SIZE);
//		parentPane.setMinWidth(GridPane.USE_PREF_SIZE);
//		getDialogPane().getChildren().stream().forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
				
	}
	
	public SettingBean getValue(){
		return bean;
	}
	
	@SuppressWarnings("unchecked")
	private void addMultipleValues(){
//		List<Object> values = bean.getChildren();
//		
//		if(bean.getType() == SETTING_TYPE.MULTIPLE_ADDRESS){
//			for(Object addr : values){
//				PhysicalAddressBean address = (PhysicalAddressBean)addr;
//				addPhysicalAddress(address);
//			}			
//		}else{
//			for(Object uri : values){
//				addURI((String)uri);
//			}
//		}
//		
//		
	}
	
	public void saveValue(){
//		if(type == SETTING_TYPE.SINGLE){
//			List<Node> children = parentGrid.getChildren();
//			for(Node child : children){
//				if(child instanceof FriendlyVBox){
//					// cast to FriendlyVBox
//					String value = ((FriendlyVBox)child).getTextField().getText();
//					this.bean.setValue(value);
//				}
//			}
//		}else{
//			saveMultipleValues();
//		}
	}
	
	/**
	 * For values in XML that can have multiple child nodes
	 */
	private void saveMultipleValues(){
//		if(bean.getType() == SETTING_TYPE.MULTIPLE_ADDRESS){
//			savePostalAddress();
//		}else{
//			saveURI();
//		}
	}
	
	/**
	 * If the setting bean is encapsulating a list of physical address
	 * (when the name is: PostalAddress) populate via predefined structure
	 */
	private void savePostalAddress(){
//		List<Object> addresses = new ArrayList<>();
//		List<Node> children = parentGrid.getChildren();
//		for(Node child : children){
//			if(child instanceof IndexedGridPane){
//				IndexedGridPane pane = (IndexedGridPane) child;
//				PhysicalAddressBean add = new PhysicalAddressBean();
//				
//				// each address attribute in the order listed in Trust List XML
//				add.setStreetAddress(((FriendlyVBox)pane.get(0, 1)).getTextField().getText());
//				add.setLocality(((FriendlyVBox)pane.get(0, 2)).getTextField().getText());
//				add.setPostalCode(((FriendlyVBox)pane.get(0, 3)).getTextField().getText());
//				add.setCountryName(((FriendlyVBox)pane.get(0, 4)).getTextField().getText());
//				
//				// add address bean to list
//				addresses.add(add);
//			}
//		}
//		
//		bean.setChildren(addresses);
	}
	
	/**
	 * used to store any values in the XML that can have multiple child URI values
	 */
	private void saveURI(){
//		List<Object> uris = new ArrayList<>();
//		List<Node> children = parentGrid.getChildren();
//		for(Node child : children){
//			if(child instanceof FriendlyVBox){
//				FriendlyVBox vBox = (FriendlyVBox) child;
//				uris.add(vBox.getTextField().getText());
//			}
//		}
//		
//		bean.setChildren(uris);
	}
	
	private void addURI(String uri){
		int newSlot = parentGrid.getRowCount();
		FriendlyVBox vBox = new FriendlyVBox();
		vBox.getChildren().addAll(new Label("\n" + bean.getName()), new AutoTextBox(""));
		parentGrid.add(vBox, 0, newSlot);
	}
	
	private void addPhysicalAddress(){
		int newSlot = parentGrid.getRowCount();
		IndexedGridPane pane = new IndexedGridPane();
		Label label = new Label("\nPostal Address");
		pane.add(label, 0, 0);
		label.setFont(Font.font("system", FontWeight.BOLD, 12));
		
		FriendlyVBox postal = new FriendlyVBox();
		postal.getChildren().addAll(new Label("Street Address"), new AutoTextBox(""));
		FriendlyVBox local = new FriendlyVBox();
		local.getChildren().addAll(new Label("Locale"), new AutoTextBox(""));
		FriendlyVBox postalCode = new FriendlyVBox();
		postalCode.getChildren().addAll(new Label("Postal Code"), new AutoTextBox(""));
		FriendlyVBox country = new FriendlyVBox();
		country.getChildren().addAll(new Label("Country Name"), new AutoTextBox(""));
		
		
		pane.add(postal, 0, 1);
		pane.add(local, 0, 2);
		pane.add(postalCode, 0, 3);
		pane.add(country, 0, 4);
		
		parentGrid.add(pane, 0, newSlot);
		
		
	}
	
	private void addPhysicalAddress(PhysicalAddressBean address){
		int newSlot = parentGrid.getRowCount();
		IndexedGridPane pane = new IndexedGridPane();
		Label label = new Label("\nPostal Address");
		pane.add(label, 0, 0);
		label.setFont(Font.font("system", FontWeight.BOLD, 12));
		FriendlyVBox street = new FriendlyVBox();
		street.getChildren().addAll(new Label("Street Address"), new AutoTextBox(address.getStreetAddress()));
		FriendlyVBox local = new FriendlyVBox();
		local.getChildren().addAll(new Label("Locale"), new AutoTextBox(address.getLocality()));
		FriendlyVBox postalCode = new FriendlyVBox();
		postalCode.getChildren().addAll(new Label("Postal Code"), new AutoTextBox(address.getPostalCode()));
		FriendlyVBox country = new FriendlyVBox();
		country.getChildren().addAll(new Label("Country Name"), new AutoTextBox(address.getCountryName()));
		
		
		pane.add(street, 0, 1);
		pane.add(local, 0, 2);
		pane.add(postalCode, 0, 3);
		pane.add(country, 0, 4);
		
		parentGrid.add(pane, 0, newSlot);
		
	}
	
	
	
	private class IndexedGridPane extends GridPane{
		
		
		
		public  Node get(final int column, final int row) {
		    Node result = null;
		    ObservableList<Node> childrens = getChildren();

		    for (Node node : childrens) {
		        if(getColumnIndex(node) == column && getRowIndex(node) == row) {
		            result = node;
		            break;
		        }
		    }

		    return result;
		}
		
		public int getRowCount() {
	        int numRows = getRowConstraints().size();
	        for (int i = 0; i < getChildren().size(); i++) {
	            Node child = getChildren().get(i);
	            if (child.isManaged()) {
	                Integer rowIndex = GridPane.getRowIndex(child);
	                if(rowIndex != null){
	                    numRows = Math.max(numRows,rowIndex+1);
	                }
	            }
	        }
	        return numRows;
	    }
	}
	
	private class AutoTextBox extends TextField{
		
		public AutoTextBox(String contents){
			setMinWidth(Region.USE_PREF_SIZE);
			setMaxWidth(Region.USE_PREF_SIZE);
			textProperty().addListener(new AutoAdjustText());
			setText(contents);
		}

		private class AutoAdjustText implements ChangeListener<String>{

			@Override
			public void changed(ObservableValue<? extends String> ov,
					String prevText, String currText) {
				Platform.runLater(() -> {
			        Text text = new Text(currText);
			        text.setFont(getFont()); // Set the same font, so the size is the same
			        double width = text.getLayoutBounds().getWidth() // This big is the Text in the TextField
			                + getPadding().getLeft() + getPadding().getRight() // Add the padding of the TextField
			                + 2d; // Add some spacing
			        setPrefWidth(width); // Set the width
			        positionCaret(getCaretPosition()); // If you remove this line, it flashes a little bit
			    });
				
			}
			
		}
	}
	
	private class FriendlyVBox extends VBox{
		
		public TextField getTextField(){
			List<Node> children = getChildren();
			for(Node child : children){
				if(child instanceof TextField){
					return (TextField)child;
				}
			}
			
			return null;
		}
	}
	
	private class AddButton extends Button{
		public AddButton(){
			setText("Add+");
			onActionProperty().set(new AddValue());
		}
		
		private class AddValue implements EventHandler<ActionEvent>{

			@Override
			public void handle(ActionEvent event) {
//				if(bean.getType() == SETTING_TYPE.MULTIPLE_ADDRESS){
//					addPhysicalAddress();
//				}else{
//					addURI("Add URI here");
//					parentPane.getScene().getWindow().sizeToScene();
//				}
				
			}
		}
	}
	
	private class RemoveButton extends Button{
		public RemoveButton(){
			setText("Remove");
			onActionProperty().set(new RemoveValue());
		}
		
		private class RemoveValue implements EventHandler<ActionEvent>{

			@Override
			public void handle(ActionEvent event) {
				int rowCount = parentGrid.getRowCount();
				parentGrid.getChildren().remove(rowCount);
			}
		}
	}
	
		
	

}
