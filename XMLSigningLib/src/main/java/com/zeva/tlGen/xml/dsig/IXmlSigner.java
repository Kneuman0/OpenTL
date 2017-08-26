package com.zeva.tlGen.xml.dsig;

import org.w3c.dom.Document;



public interface IXmlSigner {
	
	Document signDocument(Document xmlDocument) throws Exception;
}
