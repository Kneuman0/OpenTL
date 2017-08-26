package com.zeva.temp.dataModellib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddressBean {
	List<PhysicalAddressBean> physAddr;
	
	Map<String, String> elecAddr;
	
	public AddressBean(){
		physAddr = new ArrayList<>();
		elecAddr = new HashMap<>();
	}
	
	public AddressBean(String physAddresses, String elecAddresses){
		physAddr = new ArrayList<>();
		elecAddr = new HashMap<>();
		
		String[] pAddresses = physAddresses.split(",");
		String[] eAddresses = elecAddresses.split(",");
		for(String single : pAddresses)
			physAddr.add(new PhysicalAddressBean(single));
		
		for(String uri : eAddresses)
			elecAddr.put(uri, "en");
	}
	
	public AddressBean(List<PhysicalAddressBean> physAddresses, Map<String, String> elecAddresses){
		this.physAddr = physAddresses;
		this.elecAddr = elecAddresses;
	}
	
	public void addPhysAddress(PhysicalAddressBean addr){
		physAddr.add(addr);
	}
	
	public void addElecAddress(String electronicAddr, String lang){
		elecAddr.put(electronicAddr, lang);
	}

	/**
	 * @return the physAddr
	 */
	public List<PhysicalAddressBean> getPhysAddr() {
		return physAddr;
	}

	/**
	 * @param physAddr the physAddr to set
	 */
	public void setPhysAddr(List<PhysicalAddressBean> physAddr) {
		this.physAddr = physAddr;
	}

	/**
	 * @return the elecAddr
	 */
	public Map<String, String> getElecAddr() {
		return elecAddr;
	}

	/**
	 * @param elecAddr the elecAddr to set
	 */
	public void setElecAddr(Map<String, String> elecAddr) {
		this.elecAddr = elecAddr;
	}
	
	/**
	 * String representation
	 */
	@Override
	public String toString(){
		String toString = "";
		Iterator<String> itr = elecAddr.keySet().iterator();
		while(itr.hasNext()){
			toString += itr.next().toString() + "\n";
		}
		
		for(PhysicalAddressBean addr : physAddr){
			toString += addr.toString();
		}
		
		return toString;
	}
	
	
	
	
}
