package com.zeva.tlGen.dataModel.utils;

import java.util.ArrayList;
import java.util.List;

import com.zeva.temp.jaxb.datamodel.AddressType;
import com.zeva.temp.jaxb.datamodel.ElectronicAddressType;
import com.zeva.temp.jaxb.datamodel.PostalAddressListType;
import com.zeva.temp.jaxb.datamodel.PostalAddressType;
import com.zeva.temp.jaxb.datamodel.TSPServiceType;
import com.zeva.temp.jaxb.datamodel.TSPType;
import com.zeva.temp.jaxb.datamodel.TrustStatusListType;
import com.zeva.tlGen.dataModel.ProviderAttribute;

public class DataModelHandler {
	
	private TrustStatusListType model;
	
	public DataModelHandler(TrustStatusListType tl){
		
		this.model = tl;
	}
	
	public List<ProviderAttribute> getCerts(){
		List<ProviderAttribute> certs = new ArrayList<ProviderAttribute>();
		for(TSPType tsp : model.getTrustServiceProviderList().getTrustServiceProvider()){
			System.out.println(tsp.getServices().size());
			for(TSPServiceType service : tsp.getServices()){
				certs.addAll(service.getCerts());
				System.out.println("cert");
			}
		}
		
		return certs;
	}
	
	public static AddressType buildAddressType(){
		AddressType allAddr = new AddressType();
		allAddr.setPostalAddresses(new PostalAddressListType());
		allAddr.setElectronicAddress(new ElectronicAddressType());
		
		return allAddr;
	}

}
