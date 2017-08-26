package com.zeva.tlGen.custom.callback;

import java.util.Collections;
import java.util.List;

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
import com.zeva.temp.jaxb.datamodel.OtherTSLPointerType;
import com.zeva.tlGen.dataModel.TLPointer;

public class PointerCellValueMaker extends TreeTableCell<TLPointer, TLPointer>{
	ImageView up;
	ImageView down;
	GridPane grid;
	Label name;
	Label type;
	Label serialNumber;
	VBox innerVB;
	GridPane innerLowerGrid;
	ImageButton upButton;
	ImageButton downButton;
	
	public PointerCellValueMaker() {
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
		
		innerVB.getChildren().addAll(name, serialNumber);
		
		upButton.setOnMouseClicked(new OnMoveUp());
		downButton.setOnMouseClicked(new OnMoveDown());
		
		// set lower nested grid
		grid.add(upButton, 0, 0);
		grid.add(downButton, 1, 0);
		grid.add(innerVB, 2, 0);
		setGraphic(grid);
	}
	
	@Override
	public void updateItem(TLPointer item, boolean empty){
		super.updateItem(item, empty);
		
		if(item == null || empty){
			setGraphic(null);		
		}else{
			OtherTSLPointerType pointer = item.getEncapsulatedBean();
			setGraphic(grid);
			name.setText(pointer.getSchemeOperatorName());
			if(pointer.getTSLType().equals("http://uri.etsi.org/TrstSvc/TrustedList/TSLType/EUlistofthelists")){
				serialNumber.setText("LoTL");
			}else{
				serialNumber.setText("TL");
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
			TreeTableView<Object> table = (TreeTableView<Object>)
					button.getParent().getParent().getParent()
					.getParent().getParent().getParent().getParent();
			
			// get the item
			TreeTableRow<Object> row = (TreeTableRow<Object>)
					button.getParent().getParent().getParent();
			Object itemValue  = row.getItem();
			TreeItem<Object> item = row.getTreeItem();
			
			ObservableList<TreeItem<Object>> list = item.getParent().getChildren();
			
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
			TreeTableView<Object> table = (TreeTableView<Object>)
					button.getParent().getParent().getParent()
					.getParent().getParent().getParent().getParent();
			
			// get the item
			TreeTableRow<Object> row = (TreeTableRow<Object>)
					button.getParent().getParent().getParent();
			Object itemValue  = row.getItem();
			TreeItem<Object> item = row.getTreeItem();
			
			ObservableList<TreeItem<Object>> list = item.getParent().getChildren();
			if(item == null || list == null || list.size() <= 1) return;
			
			int index = getIndexOf(list, itemValue);
			if(index < list.size() - 1){
				Collections.swap(list, index, index+1);
				table.getSelectionModel().clearAndSelect(index+1);
			}
		}
		
	}
	
	public int getIndexOf(List<TreeItem<Object>> items, Object bean){
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
			
//		public class EventHandler
}
