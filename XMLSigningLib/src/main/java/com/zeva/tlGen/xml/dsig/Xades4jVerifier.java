package com.zeva.tlGen.xml.dsig;

import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xades4j.providers.CertificateValidationProvider;
import xades4j.providers.ValidationData;
import xades4j.verification.XAdESVerificationResult;
import xades4j.verification.XadesVerificationProfile;

public class Xades4jVerifier implements IXmlSignatureVerifier {

	private CertificateValidationProvider validationProvider;
	
	public Xades4jVerifier(CertificateValidationProvider certificateValidationProvider){
		validationProvider = certificateValidationProvider;
	}
	
	@Override
	public void Verify(Document signedXmlDocument) throws Exception {

		
		XadesVerificationProfile verificationProfile = new XadesVerificationProfile(
				validationProvider);
		
		Element signatureNode = getSigatureElement(signedXmlDocument);

		XAdESVerificationResult res = verificationProfile.newVerifier().verify(signatureNode, null);

		ValidationData xf = res.getValidationData();
	}
	
	private Element getSigatureElement(Document doc) throws Exception {
		return (Element) doc.getElementsByTagNameNS(Constants.SignatureSpecNS,
				Constants._TAG_SIGNATURE).item(0);
	}
}
