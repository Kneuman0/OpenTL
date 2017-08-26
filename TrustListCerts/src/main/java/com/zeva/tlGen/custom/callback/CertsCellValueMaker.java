package com.zeva.tlGen.custom.callback;

import java.util.Collections;
import java.util.List;

import com.zeva.temp.jaxb.datamodel.DigitalIdentityType;
import com.zeva.tlGen.dataModel.ProviderAttribute;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

public class CertsCellValueMaker extends TreeTableCell<ProviderAttribute, ProviderAttribute>{

	ImageView up;
	ImageView down;
	GridPane grid;
	Label name;
	Label serialNumber;
	VBox innerVB;
	GridPane innerLowerGrid;
	ImageButton upButton;
	ImageButton downButton;
	TextFlow flow;
	CheckBox revoked;
	
	public CertsCellValueMaker(){
		up = new ImageView(new Image(getClass().getResourceAsStream(
				"/resources/up.png")));
		up.setFitHeight(15);
		up.setFitWidth(15);
		upButton = new ImageButton(up);
		
		down = new ImageView(new Image(getClass().getResourceAsStream(
				"/resources/down.png")));
		down.setFitHeight(15);
		down.setFitWidth(15);
		downButton = new ImageButton(down);
				
		grid = new GridPane();
		innerVB = new VBox();
		innerVB.setPadding(new Insets(0, 0, 0, 3));
		innerLowerGrid = new GridPane();
		
		serialNumber = new Label("");
		serialNumber.setFont(Font.font ("System", 10));
		innerLowerGrid.add(serialNumber, 1, 0);
		name = new Label();
		name.setPrefWidth(Region.USE_COMPUTED_SIZE);
		this.revoked = new UpdatedCheckBox("Revoked ");
		
		innerVB.getChildren().addAll(name, serialNumber, revoked);
		
		upButton.setOnMouseClicked(new OnMoveUp());
		downButton.setOnMouseClicked(new OnMoveDown());
		
		// set lower nested grid
		grid.add(upButton, 0, 0);
		grid.add(downButton, 1, 0);
		grid.add(innerVB, 2, 0);
		setGraphic(grid);
	}
	
	@Override
	public void updateItem(ProviderAttribute item, boolean empty){
		super.updateItem(item, empty);
		
		if(item == null || empty){
			setGraphic(null);		
		}else{
			DigitalIdentityType certBean = item.getEncapsulatedBean();
			setGraphic(grid);
			name.setText(item.getStringName());
			revoked.setSelected(item.<DigitalIdentityType>getEncapsulatedBean().isRevoked());
			if(certBean.getParentCert() != null){
				serialNumber.setText("SN: " + certBean.getParentCert().getSerialNumber().toString());
						
			}
			
		}
	}

	public class OnMoveUp implements EventHandler<MouseEvent>{
		 private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 3 1 1 3;";

		@SuppressWarnings("unchecked")
		@Override
		public void handle(MouseEvent event) {
			Button button = (Button)event.getSource();
			button.setStyle(STYLE_PRESSED);
			
			// get the table
			TreeTableView<ProviderAttribute> table = (TreeTableView<ProviderAttribute>)
					button.getParent().getParent().getParent()
					.getParent().getParent().getParent().getParent();
			
			// get the item
			TreeTableRow<ProviderAttribute> row = (TreeTableRow<ProviderAttribute>)
					button.getParent().getParent().getParent();
			ProviderAttribute itemValue  = row.getItem();
			TreeItem<ProviderAttribute> item = row.getTreeItem();
			
			ObservableList<TreeItem<ProviderAttribute>> list = item.getParent().getChildren();
			
			if(item == null || list == null || list.size() <= 1) return;
			
			// swap the current item with the one above it
			int index = getIndexOf(list, itemValue);
			if(index > 0){
				Collections.swap(list, index, index-1);
				table.getSelectionModel().clearAndSelect(index-1);
			}
		}
		
	}
	
	public class OnMoveDown implements EventHandler<MouseEvent>{
		 private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 3 1 1 3;";

		@SuppressWarnings("unchecked")
		@Override
		public void handle(MouseEvent event) {
			Button button = (Button)event.getSource();
			button.getGraphic().setOpacity(50);
			button.setStyle(STYLE_PRESSED);
			
			// get the table
			TreeTableView<ProviderAttribute> table = (TreeTableView<ProviderAttribute>)
					button.getParent().getParent().getParent()
					.getParent().getParent().getParent().getParent();
			
			// get the item
			TreeTableRow<ProviderAttribute> row = (TreeTableRow<ProviderAttribute>)
					button.getParent().getParent().getParent();
			ProviderAttribute itemValue  = row.getItem();
			TreeItem<ProviderAttribute> item = row.getTreeItem();
			
			ObservableList<TreeItem<ProviderAttribute>> list = item.getParent().getChildren();
			if(item == null || list == null || list.size() <= 1) return;
			
			int index = getIndexOf(list, itemValue);
			if(index < list.size() - 1){
				Collections.swap(list, index, index+1);
				table.getSelectionModel().clearAndSelect(index+1);
			}
		}
		
	}
	
	public int getIndexOf(List<TreeItem<ProviderAttribute>> items, ProviderAttribute bean){
		int index = -1;
		for(int i = 0; i < items.size(); i++){
			if(items.get(i).getValue().equals(bean))
				return i;
		}
		
		return index;
	}
	
	public class ImageButton extends Button {
		private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
    
	    public ImageButton(ImageView image) {
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
	
	public class UpdatedCheckBox extends CheckBox{
		
		public UpdatedCheckBox(String name){
			super(name);
			setOnAction(new RevokedSelected());
		}
	
		public class RevokedSelected implements EventHandler<ActionEvent>{
	
			@SuppressWarnings("unchecked")
			@Override
			public void handle(ActionEvent event) {
				CheckBox box = (CheckBox)event.getSource();
				TreeTableRow<ProviderAttribute> row = (TreeTableRow<ProviderAttribute>)
						box.getParent().getParent().getParent().getParent();
				row.getItem().<DigitalIdentityType>getEncapsulatedBean().setRevoked(isSelected());
						
			}
		}
		
	}
		
//		public class EventHandler
}