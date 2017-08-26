
package com.zeva.temp.dataModellib;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.ZonedDateTime;

import javafx.beans.property.SimpleObjectProperty;

import com.zeva.certs.utils.CertificateUtilities;
import com.zeva.tlGen.dataModel.CertificateBean;
import com.zeva.tlGen.dataModel.ProviderAttribute;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class ServiceBean extends ProviderAttribute{
	X509Certificate x509Certificate;
	String serviceTypeIdentifier;
	// ServiceName is the parent element, this field is for the child element Name
	String serviceName;
	String language;
	// Parent is DigitalID
	String base64Cert;
	// Parent is DigitalID
	String x509SKI;
	// Parent is DigitalID
	String x509SubjectName;
	// Parent is DigitalID
	String keyValue;
	String serviceStatus;
	ZonedDateTime statusStartingTime;
	// Parent is ServiceInformationExtensions
	String extension;
	
	private SimpleObjectProperty<ProviderAttribute> nameProp;
	
	public ServiceBean(){
		this.nameProp = new SimpleObjectProperty<ProviderAttribute>(this);
		// When should this be?
		this.statusStartingTime = ZonedDateTime.now();
		this.language = "en";
	}
	
	public ServiceBean(CertificateBean bean){
		this.nameProp = new SimpleObjectProperty<ProviderAttribute>(this);
		this.x509Certificate = bean.getParentCert();
		
		this.serviceName = bean.getParentCert().getIssuerX500Principal().getName();
		this.language = "en";
		this.base64Cert = getBase64EncodedCert();
		this.x509SKI = CertificateUtilities.generateX509SKI(bean.getParentCert());
		
		// which is the subject name and which is the service name?
		this.x509SubjectName = bean.getParentCert().getSubjectX500Principal().getName();
		
		// What is this if anything?
		this.keyValue = null;
		
		// What is this if anything?
		this.serviceStatus = null;
		
		// What is this if anything?
		this.extension = null;
		
		// When should this be?
		this.statusStartingTime = ZonedDateTime.now();
				
	}
	
	/**
	 * @return the x509Certificate
	 */
	public X509Certificate getX509Certificate() {
		return x509Certificate;
	}
	/**
	 * @return the base64Cert
	 */
	public String getBase64Cert() {
		return base64Cert;
	}
	/**
	 * @param base64Cert the base64Cert to set
	 */
	public void setBase64Cert(String base64Cert) {
		this.base64Cert = base64Cert;
	}
	/**
	 * @param x509Certificate the x509Certificate to set
	 * @throws CertificateException 
	 */
	public void setX509Certificate(byte[] x509Certificate) throws CertificateException {
		CertificateFactory factory = CertificateFactory.getInstance("X509");
		this.x509Certificate = (X509Certificate)factory
				.generateCertificate(new ByteArrayInputStream(x509Certificate));
	}
	/**
	 * @return the serviceTypeIdentifier
	 */
	public String getServiceTypeIdentifier() {
		return serviceTypeIdentifier;
	}
	/**
	 * @param serviceTypeIdentifier the serviceTypeIdentifier to set
	 */
	public void setServiceTypeIdentifier(String serviceTypeIdentifier) {
		this.serviceTypeIdentifier = serviceTypeIdentifier;
	}
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * @return the x509SKI
	 */
	public String getX509SKI() {
		return x509SKI;
	}
	/**
	 * @param x509ski the x509SKI to set
	 */
	public void setX509SKI(String x509ski) {
		x509SKI = x509ski;
	}
	/**
	 * @return the x509SubjectName
	 */
	public String getX509SubjectName() {
		return x509SubjectName;
	}
	/**
	 * @param x509SubjectName the x509SubjectName to set
	 */
	public void setX509SubjectName(String x509SubjectName) {
		this.x509SubjectName = x509SubjectName;
	}
	/**
	 * @return the keyValue
	 */
	public String getKeyValue() {
		return keyValue;
	}
	/**
	 * @param keyValue the keyValue to set
	 */
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	/**
	 * @return the serviceStatus
	 */
	public String getServiceStatus() {
		return serviceStatus;
	}
	/**
	 * @param serviceStatus the serviceStatus to set
	 */
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	/**
	 * @return the statusStartingTime
	 */
	public ZonedDateTime getStatusStartingTime() {
		return statusStartingTime;
	}
	/**
	 * @param statusStartingTime the statusStartingTime to set
	 */
	public void setStatusStartingTime(ZonedDateTime statusStartingTime) {
		this.statusStartingTime = statusStartingTime;
	}
	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getBase64EncodedCert(){
		byte[] binary = null;
		try {
			binary = x509Certificate.getEncoded();
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(binary).replaceAll("[ \n\t\r]", "");
				
	}

	@Override
	public SimpleObjectProperty<ProviderAttribute> nameProperty() {
		// TODO Auto-generated method stub
		return nameProp;
	}

	@Override
	public ProviderAttributeType getType() {
		// TODO Auto-generated method stub
		return ProviderAttributeType.SERVICE;
	}

	/**
	 * Use of this method assumed a check has been placed using the 
	 * getType() first to determine the Type of this subclass
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ServiceBean getEncapsulatedBean() {
		// TODO Auto-generated method stub
		return this;
	}


	@Override
	public String getStringName() {
		// TODO Auto-generated method stub
		return serviceName;
	}

	@Override
	public ProviderAttribute initialize() {
		// TODO Auto-generated method stub
		return this;
	}	
}
