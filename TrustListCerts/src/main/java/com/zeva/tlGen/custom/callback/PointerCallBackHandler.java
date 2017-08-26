package com.zeva.tlGen.custom.callback;

import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import com.zeva.tlGen.dataModel.TLPointer;

public class PointerCallBackHandler implements Callback<TreeTableColumn<TLPointer, TLPointer>,
			TreeTableCell<TLPointer, TLPointer>>{

	@Override
	public TreeTableCell<TLPointer, TLPointer> call(
			TreeTableColumn<TLPointer, TLPointer> param) {
		
			return new PointerCellValueMaker();
	}

}