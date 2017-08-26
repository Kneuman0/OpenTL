package com.zeva.tlGen.controllers.CallBackHandlers;

import java.util.Collections;
import java.util.List;

import com.zeva.temp.jaxb.datamodel.DigitalIdentityType;
import com.zeva.temp.jaxb.datamodel.TSPServiceType;
import com.zeva.temp.jaxb.datamodel.TSPType;
import com.zeva.tlGen.dataModel.ProviderAttribute;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TLTableCellValueMaker extends TreeTableCell<ProviderAttribute, ProviderAttribute>{

	ImageView up;
	ImageView down;
	GridPane grid;
	Text cellName;
	Text cellDesc;
	Label serialNumber;
	VBox innerVB;
	GridPane innerLowerGrid;
	ImageButton upButton;
	ImageButton downButton;
	TextFlow flow;
	
	public TLTableCellValueMaker(){
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
		cellName = new Text("");
		cellDesc = new Text("");
		cellDesc.setStyle("-fx-font-weight: bold");
		flow = new TextFlow();
		flow.setPrefWidth(Region.USE_COMPUTED_SIZE);
		flow.getChildren().addAll(cellDesc, cellName);
		
		
		
		innerVB.getChildren().addAll(flow, serialNumber);
		
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
		grid.getChildren().clear();
		if(item == null || empty){
			setGraphic(null);		
			setText("");
			setStyle(null);
			cellName.setText("");
			cellDesc.setText("");
		}else{
			if(item instanceof DigitalIdentityType){
				DigitalIdentityType certBean = item.getEncapsulatedBean();
				grid.addRow(0, upButton, downButton, innerVB);
				setGraphic(grid);
				cellName.setText(item.getStringName());
				cellDesc.setText("Cert: ");
				setStyle("-fx-background-color: darkkhaki;");
				if(certBean.getParentCert() != null){
					serialNumber.setText("SN: " + certBean.getParentCert().getSerialNumber().toString());
				}	
				
//				if(((TreeTableRow<ProviderAttribute>)getParent()).isFocused()){
//					setStyle("-fx-background-color: blue;");
//				}
			}else if(item instanceof TSPServiceType){
				
				grid.addRow(0, upButton, downButton, innerVB);
				setGraphic(grid);
				cellName.setText(item.getStringName());
				serialNumber.setText("");
				cellDesc.setText("Service: ");
				setStyle("-fx-background-color: lightblue;");
//				if(((TreeTableRow<ProviderAttribute>)getParent()).isFocused()){
//					setStyle("-fx-background-color: blue;");
//				}
			}else if(item instanceof TSPType){
				grid.addRow(0, upButton, downButton, innerVB);
				setGraphic(grid);
				cellName.setText(item.getStringName());
				serialNumber.setText("");
				cellDesc.setText("TSP: ");
				setStyle("-fx-background-color: bisque;");
//				if(((TreeTableRow<ProviderAttribute>)getParent()).isSelected()){
//					setStyle("-fx-background-color: blue;");
//				}
			}else{
				grid.addRow(0, innerVB);
				setGraphic(grid);
				cellDesc.setText("");
				cellName.setText(item.getStringName());
				serialNumber.setText("");
				setStyle("-fx-background-color: white;");
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
				table.getSelectionModel().clearSelection();
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
				table.getSelectionModel().clearSelection();
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
//               setOpacity(0);
            }            
        });
    }
    
}
	
//	public class EventHandler
}
