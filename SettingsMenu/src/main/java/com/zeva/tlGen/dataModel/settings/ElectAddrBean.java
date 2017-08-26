package com.zeva.tlGen.dataModel.settings;

import com.zeva.temp.jaxb.datamodel.ElectronicAddressType;
import com.zeva.temp.jaxb.datamodel.UriBeanType;
import com.zeva.tlGen.dataModel.abstraction.EncapsulatedDataType;

public class ElectAddrBean extends EncapsulatedDataType<UriBeanType>{

	public ElectAddrBean(UriBeanType dataType) {
		super(dataType);
	}
	
	public void storeDataValue(String uri, ElectronicAddressType addressType){
		dataType.setUri(uri);
		name.set(uri);
		addressType.unwrapUris();
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof ElectAddrBean){
			ElectAddrBean uri = (ElectAddrBean)o;
				return uri.getDataType().getUri().equals(this.dataType.getUri());
		}else{
			return false;
		}
	}

}
