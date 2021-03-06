package com.zeva.tlGen.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.security.cert.X509Certificate;

import org.apache.xml.security.exceptions.Base64DecodingException;

import com.zeva.tlGen.dataModel.CertificateBean;

public class CertificateEncapsulater {
	
	static enum CERT_TYPES {CER, PEM, P7B, PFX};
	List<X509Certificate> certs;
	
	/**
	 * Uses the file extension to determine the file type. Reads in the entire file and 
	 * stores it as X509Certificates
	 * @param certFile
	 * @throws Exception 
	 */
	public CertificateEncapsulater(File certFile) throws Exception {
		certs = new ArrayList<>();
		if(certFile.getAbsolutePath().toLowerCase().endsWith("cer")){
			loadCERFile(certFile);
		}else if(certFile.getAbsolutePath().toLowerCase().endsWith("pem")){
			loadPEMFile(certFile);
		}else if(certFile.getAbsolutePath().toLowerCase().endsWith("p7b") ||
				certFile.getAbsolutePath().toLowerCase().endsWith("p7b")){
			loadP7BFile(certFile);
		}else if(certFile.getAbsolutePath().toLowerCase().endsWith("pfx")){
			loadPFXFile(certFile);
		}else{
			throw new Exception(certFile.getAbsolutePath() + "is not a supported file type");
		}
		
		
	}
	
	/**
	 * Reads in the certificate file and encapsulates the certificates using the method type specified.
	 * @param base64 base 64 string containing certificate information. Each certificate must be
	 * 		bounded at the beginning by -----BEGIN CERTIFICATE-----, and bounded at the end by -----END CERTIFICATE-----.
	 * @param type type of certificate file being passed in
	 * @throws CertificateException
	 * @throws IOException
	 * @throws Base64DecodingException
	 * @throws CMSException
	 */
	public CertificateEncapsulater(String base64, CERT_TYPES type) throws CertificateException, IOException, Base64DecodingException{
		certs = new ArrayList<>();
		if(type == CERT_TYPES.CER){
			loadCERFile(base64);
		}else if(type == CERT_TYPES.PEM){
			loadPEMFile(base64);
		}else if(type == CERT_TYPES.PFX){
			loadPFXFile(base64);
		}else {
			loadP7BFile(base64);
		}
	}
	
	/**
	 * Accepts a certificate file containing one or more certificates. Must specify the encoding type of the file. This
	 * constructor is useful if the certificate file is encoded differently then what the extension indicates
	 * @param certFile file containing one or more certificates
	 * @param ext type of certificate encoding
	 * @throws CertificateException
	 * @throws IOException
	 * @throws Base64DecodingException
	 * @throws CMSException
	 */
	public CertificateEncapsulater(File certFile, CERT_TYPES ext) throws CertificateException, IOException, Base64DecodingException{
		certs = new ArrayList<>();
		if(ext == CERT_TYPES.CER){
			loadCERFile(certFile);
		}else if(ext == CERT_TYPES.PEM){
			loadPEMFile(certFile);
		}else if(ext == CERT_TYPES.PFX){
			loadPFXFile(certFile);
		}else {
			loadP7BFile(certFile);
		}
	}
	
	/**
	 * Method for loading cer encoded certificate file
	 * @param certFile file containing certificate
	 * @throws CertificateException
	 * @throws IOException
	 */
	private void loadCERFile(File certFile) throws CertificateException, IOException{
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		Collection<? extends Certificate> col = 
				(Collection<? extends Certificate>)factory.generateCertificates(new FileInputStream(certFile));
		
		Iterator<? extends Certificate> itr = col.iterator();
		while(itr.hasNext()){
			certs.add((X509Certificate)itr.next());
		}
	}
	
	/**
	 * Method for loading PEM certificate file. Certificates must be bounded at the 
	 * beginning by -----BEGIN CERTIFICATE-----, and bounded at the end by -----END CERTIFICATE-----.
	 * @param certFile
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 */
	private void loadPEMFile(File certFile) throws CertificateException, FileNotFoundException{
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		Collection<? extends Certificate> col = 
				(Collection<? extends Certificate>)factory.generateCertificates(new FileInputStream(certFile));
		Iterator<? extends Certificate> itr = col.iterator();
		while(itr.hasNext()){
			certs.add((X509Certificate)itr.next());
		}
	}
	
	/**
	 * method for loading P7B file
	 * @param certFile file containing P7B or P7C bundle
	 * @throws CertificateException
	 * @throws IOException
	 * @throws Base64DecodingException
	 */
	private void loadP7BFile(File certFile) throws CertificateException, IOException, Base64DecodingException{
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		Collection<? extends Certificate> certs = factory.generateCertificates(new FileInputStream(certFile));
		Iterator<? extends Certificate> itr = certs.iterator();
		while(itr.hasNext()){
			this.certs.add((X509Certificate)itr.next());
		}
	}
	
	/**
	 * Method for loading PFX file
	 * @param certFile
	 * @throws CertificateException
	 * @throws IOException
	 * @throws Base64DecodingException
	 */
	private void loadPFXFile(File certFile) throws CertificateException, IOException, Base64DecodingException{
		CertificateFactory factory = CertificateFactory.getInstance("PFX");
		Collection<? extends Certificate> col = 
				(Collection<? extends Certificate>)factory.generateCertificates(new FileInputStream(certFile));
		Iterator<? extends Certificate> itr = col.iterator();
		while(itr.hasNext()){
			certs.add((X509Certificate)itr.next());
		}
	}
	
	/**
	 * Method for removing PKCS7 bounds
	 * @param certFile
	 * @return
	 * @throws IOException
	 */
	public static String removeBoundsP7B(File certFile) throws IOException{
		Scanner reader = new Scanner(new FileReader(certFile));
		StringBuilder cert = new StringBuilder();
		while(reader.hasNext()){
			String line = reader.nextLine();
		    if(!line.contains("PKCS7")){
		    	cert.append(line);
		    }
		}
		
		reader.close();
		
		return cert.toString();
	}

	/**
	 * @return the certs
	 */
	public List<X509Certificate> getCerts() {
		return certs;
	}

	/**
	 * @param certs the certs to set
	 */
	public void setCerts(List<X509Certificate> certs) {
		this.certs = certs;
	}
	
	public List<CertificateBean> getEncapulatedCerts(){
		List<CertificateBean> temp = new ArrayList<>();
		for(X509Certificate cert : certs)
			temp.add(new CertificateBean(cert));
		return temp;
	}
	
	private void loadCERFile(String certFile) throws CertificateException, IOException{
		CertificateFactory factory = CertificateFactory.getInstance("PFX");
		@SuppressWarnings("unchecked")
		Collection<? extends Certificate> col = 
				(Collection<? extends Certificate>)factory.generateCertificate(
						new ByteArrayInputStream(certFile.getBytes()));
		Iterator<? extends Certificate> itr = col.iterator();
		while(itr.hasNext()){
			certs.add((X509Certificate)itr.next());
		}
	}
	
	/**
	 * Method for loading base 64 pem string. Certificate/s must be bounded at the 
	 * beginning by -----BEGIN CERTIFICATE-----, and bounded at the end by -----END CERTIFICATE-----.
	 * @param certFile
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 */
	private void loadPEMFile(String certFile) throws CertificateException, FileNotFoundException{
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		X509Certificate cert = 
				(X509Certificate)factory.generateCertificate(
						new ByteArrayInputStream(certFile.getBytes()));
		
		certs.add(cert);
	}
	
	private void loadP7BFile(String certFile) throws CertificateException, IOException, Base64DecodingException{
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		@SuppressWarnings("unchecked")
		Collection<? extends Certificate> col = 
				(Collection<? extends Certificate>)factory.generateCertificate(
						new ByteArrayInputStream(certFile.getBytes()));
		Iterator<? extends Certificate> itr = col.iterator();
		while(itr.hasNext()){
			certs.add((X509Certificate)itr.next());
		}
	}
	
	private void loadPFXFile(String certFile) throws CertificateException, IOException, Base64DecodingException{
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		@SuppressWarnings("unchecked")
		Collection<? extends Certificate> col = 
				(Collection<? extends Certificate>)factory.generateCertificate(
						new ByteArrayInputStream(certFile.getBytes()));
		Iterator<? extends Certificate> itr = col.iterator();
		while(itr.hasNext()){
			certs.add((X509Certificate)itr.next());
		}
	}
	
	

}
