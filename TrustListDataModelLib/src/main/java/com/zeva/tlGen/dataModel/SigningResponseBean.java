package com.zeva.tlGen.dataModel;

import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;

public class SigningResponseBean {
	public enum RESPONSE_TYPE {CONTINUE_WITHOUT_SIG, CONTINUE_WITH_SIG, INVALID_SIG, DO_NOT_CONTINUE}
	public RESPONSE_TYPE response;
	public FileSystemKeyStoreKeyingDataProvider provider;

	public SigningResponseBean(FileSystemKeyStoreKeyingDataProvider provider){
		this.provider = provider;
	}
	
	public SigningResponseBean(FileSystemKeyStoreKeyingDataProvider provider, RESPONSE_TYPE response){
		this.response = response;
		this.provider = provider;
	}

	public RESPONSE_TYPE getResponse() {
		return response;
	}

	public void setResponse(RESPONSE_TYPE response) {
		this.response = response;
	}

	public FileSystemKeyStoreKeyingDataProvider getProvider() {
		return provider;
	}

	public void setProvider(FileSystemKeyStoreKeyingDataProvider provider) {
		this.provider = provider;
	}		
}
