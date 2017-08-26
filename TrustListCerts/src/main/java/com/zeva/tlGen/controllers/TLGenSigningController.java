package com.zeva.tlGen.controllers;

import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;
import java.util.Optional;

import xades4j.providers.CertificateValidationProvider;
import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;

import com.zeva.tlGen.dataModel.SigningResponseBean;
import com.zeva.tlGen.dataModel.SigningResponseBean.RESPONSE_TYPE;
import com.zeva.tlGen.utils.xml.IPrinter;
import com.zeva.tlGen.utils.xml.TrustListXMLPrinter;
import com.zeva.tlGen.utils.xml.XMLTrustListMarshaller;
import com.zeva.tlGen.xml.dsig.CertificateValidationProviderFactory;
import com.zeva.tlGen.xml.dsig.FileSystemKeyStoreKeyingDataProviderFactory;
import com.zeva.tlGen.xml.dsig.IXmlSigner;
import com.zeva.tlGen.xml.dsig.Xades4jSigner;

import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import biz.ui.alert.custom.CustomSigningAlert;
import biz.ui.controller.utils.ControllerUtils;

public abstract class TLGenSigningController extends ControllerUtils {

	@Override
	public abstract Window getWindow();

	public SigningResponseBean requestSigningInfo() throws KeyStoreException {
		CustomSigningAlert credsWindow = new CustomSigningAlert();
		credsWindow.setTitle("Import Signing Certificate");
		credsWindow.setHeaderText("Please fill in the appropriate content");
		Optional<ButtonType> credButton = credsWindow.showAndWait();

		if (credButton.get() == ButtonType.CANCEL) {
			Optional<ButtonType> btn = displayQueryToContinuePrompt(
					"Continue Without Signature?",
					"Do you want to continue without a digital signature?",
					null);
			// if user does not want to contunue, terminate
			if (btn.get() == ButtonType.NO) {
				return new SigningResponseBean(null,
						RESPONSE_TYPE.DO_NOT_CONTINUE);

				// user wants to continue without signing, print document and
				// terminate
			} else {
				return new SigningResponseBean(null,
						RESPONSE_TYPE.CONTINUE_WITHOUT_SIG);
			}
		}

		FileSystemKeyStoreKeyingDataProvider provider = null;
		try {
			provider = FileSystemKeyStoreKeyingDataProviderFactory
					.createFileSystemKeyingDataProvider("PKCS12", credsWindow
							.getCredLoc().getAbsolutePath().replace("\\", "/"),
							credsWindow.getPassword(), false);
		} catch (KeyStoreException e) {
			displayErrorMessage("Export Error",
					"An error occured while exporting", null, e);
			return new SigningResponseBean(null, RESPONSE_TYPE.INVALID_SIG);
		}

		if (provider == null) {
			return new SigningResponseBean(null, RESPONSE_TYPE.INVALID_SIG);
		} else {
			return new SigningResponseBean(provider,
					RESPONSE_TYPE.CONTINUE_WITH_SIG);
		}

	}

	public CertificateValidationProvider requestVerificationInfo()
			throws Exception {
		CustomSigningAlert credsWindow = new CustomSigningAlert();
		credsWindow.setTitle("Import Signing Certificate");
		credsWindow.setHeaderText("Please fill in the appropriate content");
		Optional<ButtonType> btn = credsWindow.showAndWait();
		if (btn.get() == ButtonType.CANCEL) {
			return null;
		}
		return CertificateValidationProviderFactory.Create("PKCS12",
				credsWindow.getCredLoc().getAbsolutePath().replace("\\", "/"),
				credsWindow.getPassword());

	}

	public void signAndPrint(XMLTrustListMarshaller marshaller, File tlolLoc) {
		FileSystemKeyStoreKeyingDataProvider provider = null;
		try {
			SigningResponseBean response = requestSigningInfo();
			provider = response.getProvider();
			if (response.getResponse() == RESPONSE_TYPE.CONTINUE_WITH_SIG) {
				IXmlSigner signer = new Xades4jSigner(provider);
				signer.signDocument(marshaller.getMarshalledDocument());
				printTrustList(marshaller, tlolLoc);
				// if user does not want to contunue, terminate
			} else if (response.getResponse() == RESPONSE_TYPE.DO_NOT_CONTINUE) {
				return;
			} else if (response.getResponse() == RESPONSE_TYPE.INVALID_SIG) {
				return;
			} else {
				System.out.println("printing unsigned");
				printTrustList(marshaller, tlolLoc);

			}
		} catch (Exception e) {
			displayErrorMessage("Error", "An error occured while exporting",
					null, e);
			return;
		}

	}

	public void printTrustList(XMLTrustListMarshaller marshaller, File tlolLoc) {
		try {
			IPrinter printer = new TrustListXMLPrinter(
					marshaller.getMarshalledDocument());
			printer.print(tlolLoc);
			displaySuccessMessage("Export Successful", "XML exported successfully!", null);
		} catch (IOException e) {
			displayErrorMessage("Export Error",
					"An error occured during export", null, e);
		}
	}
}
