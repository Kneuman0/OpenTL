package com.zeva.temp.dataModellib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleObjectProperty;

import com.zeva.temp.dataModellib.AddressBean;
import com.zeva.tlGen.dataModel.ProviderAttribute;

public class ProviderList extends ProviderAttribute{
	String name;
	String language;
	Map<String, String> tspInformationURI;
	AddressBean address;
	List<ServiceBean> tspServices;
	private SimpleObjectProperty<ProviderAttribute> nameProp;
	
	public ProviderList(){
		tspInformationURI = new HashMap<>();
		this.tspServices = new ArrayList<>();
		this.nameProp = new SimpleObjectProperty<ProviderAttribute>(this);
	}
	
	/**
	 * @return the tspInformationURI
	 */
	public Map<String, String> getTspInformationURI() {
		return tspInformationURI;
	}
	/**
	 * @param tspInformationURI the tspInformationURI to set
	 */
	public void setTspInformationURI(Map<String, String> tspInformationURI) {
		this.tspInformationURI = tspInformationURI;
	}
	
	public void addTspInformationURI(String uri, String lang){
		this.tspInformationURI.put(uri, lang);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the address
	 */
	public AddressBean getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(AddressBean address) {
		this.address = address;
	}
	/**
	 * @return the tspServices
	 */
	public List<ServiceBean> getTspServices() {
		return tspServices;
	}
	/**
	 * @param tspServices the tspServices to set
	 */
	public void setTspServices(List<ServiceBean> tspServices) {
		this.tspServices = tspServices;
	}

	@Override
	public SimpleObjectProperty<ProviderAttribute> nameProperty() {
		// TODO Auto-generated method stub
		return nameProp;
	}

	@Override
	public ProviderAttributeType getType() {
		// TODO Auto-generated method stub
		return ProviderAttributeType.PROVIDER;
	}

	/**
	 * Use of this method assumed a check has been placed using the 
	 * getType() first to determine the Type of this subclass
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ProviderList getEncapsulatedBean() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getStringName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public ProviderAttribute initialize() {
		// TODO Auto-generated method stub
		return this;
	}
	
	
}
