package com.zeva.tlGen.dataModel.settings;

import com.zeva.temp.jaxb.datamodel.PostalAddressType;
import com.zeva.tlGen.dataModel.abstraction.EncapsulatedDataType;

public class SchemeOpPostalBean extends EncapsulatedDataType<PostalAddressType>{


	public SchemeOpPostalBean(PostalAddressType dataType) {
		super(dataType);
	}
	
	public void storeDataValue(String street, String city, String state, 
			String zip, String country, String lang){
		dataType.setStreetAddress(street);
		dataType.setLocality(city);
		dataType.setStateOrProvince(state);
		dataType.setPostalCode(zip);
		dataType.setCountryName(country);
		dataType.setLang(lang);		
		name.set(street);
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof SchemeOpPostalBean){
			
			SchemeOpPostalBean other = (SchemeOpPostalBean)o;
			if(other.getDataType().getLang() == getDataType().getLang()
					&& other.getStringName() == getStringName() &&
					other.getDataType().getPostalCode() == getDataType().getPostalCode()){
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}
	}

}
