package com.zeva.certs.utils;

import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;

public class CertificateUtilities {

	public static String generateX509SKI(X509Certificate cert) {
		JcaX509ExtensionUtils util = null;
		try {
			util = new JcaX509ExtensionUtils();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] array = util.createSubjectKeyIdentifier(cert.getPublicKey()).getKeyIdentifier();
		
		return DatatypeConverter.printHexBinary(array);
	}
}
