package com.zeva.tlGen.xml.dsig;

import org.w3c.dom.Document;

public interface IXmlSignatureVerifier {

	public void Verify(Document signedXmlDocument) throws Exception;
}
