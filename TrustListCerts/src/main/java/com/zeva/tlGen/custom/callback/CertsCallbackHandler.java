package com.zeva.tlGen.custom.callback;

import com.zeva.tlGen.dataModel.ProviderAttribute;

import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class CertsCallbackHandler implements Callback<TreeTableColumn<ProviderAttribute, ProviderAttribute>,
													TreeTableCell<ProviderAttribute, ProviderAttribute>>{

	@Override
	public TreeTableCell<ProviderAttribute, ProviderAttribute> call(
			TreeTableColumn<ProviderAttribute, ProviderAttribute> param) {
		return new CertsCellValueMaker();
	}

}
