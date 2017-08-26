package com.zeva.tlGen.xml.dsig;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

import xades4j.providers.CertificateValidationProvider;
import xades4j.providers.impl.PKIXCertificateValidationProvider;

public class CertificateValidationProviderFactory {


	public static CertificateValidationProvider Create(String keystoreType, String keystoreFilepath, String keystorePassword) 
			throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException{
		
		FileInputStream stream = new FileInputStream(keystoreFilepath);
		
		KeyStore store = KeyStore.getInstance(keystoreType, "BC");
		
		store.load(stream, keystorePassword.toCharArray());
		
		return new PKIXCertificateValidationProvider(store,
				false);
	}
	
}
