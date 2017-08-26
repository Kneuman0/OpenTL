package com.zeva.temp.dataModellib;

import java.util.ArrayList;

import com.zeva.temp.dataModellib.TrustList;

public class TrustListofLists {
	
	private ArrayList<TrustList> trustLists;
	
	public TrustListofLists(){
		trustLists = new ArrayList<>();
	}

	/**
	 * @return the trustLists
	 */
	public ArrayList<TrustList> getTrustLists() {
		return trustLists;
	}

	/**
	 * @param trustLists the trustLists to set
	 */
	public void setTrustLists(ArrayList<TrustList> trustLists) {
		this.trustLists = trustLists;
	}
	
	
}
