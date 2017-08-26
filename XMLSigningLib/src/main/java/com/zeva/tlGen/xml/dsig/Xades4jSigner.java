package com.zeva.tlGen.xml.dsig;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xades4j.production.Enveloped;
import xades4j.production.XadesBesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.providers.KeyingDataProvider;

public class Xades4jSigner implements IXmlSigner {


	
	private KeyingDataProvider keyingDataProvider;
	
	public Xades4jSigner(KeyingDataProvider keyingDataProvider){
		this.keyingDataProvider = keyingDataProvider;
	}
	
	@Override
	public Document signDocument(Document xmlDocument) throws Exception {
		
		Element elemToSign = xmlDocument.getDocumentElement();
		
		// TODO: inject signing profile type
		XadesSigner signer = (XadesSigner)new XadesBesSigningProfile(this.keyingDataProvider ).newSigner();
        
		new Enveloped(signer).sign(elemToSign);
		
        return xmlDocument;
	}
	
}
