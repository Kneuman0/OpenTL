package com.zeva.tlGen.utils.xml;

import org.w3c.dom.Document;


public interface XMLTrustListMarshaller {
	
	/**
	 * This method need will be used for marshaling an entire TrustList to
	 * XML. It is implementation specific depending on the TSLVersionIdentifier
	 * 
	 */
	void marshalTrustList();
	
	/**
	 * This document will be empty if called before the trust list has been built. Be sure
	 * to call the marshalTrustList(TrustList) method before calling this method.
	 * @return
	 */
	Document getMarshalledDocument();
	
}
