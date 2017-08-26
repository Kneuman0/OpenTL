package com.zeva.tlGen.controllers.settings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import tornadofx.control.DateTimePicker;

import com.zeva.temp.jaxb.datamodel.TSLSchemeInformationType;

public class ListIssueDateController extends SingleValueController<TSLSchemeInformationType>{
	@FXML
	private VBox contentVbox;
	
	private DateTimePicker dateAndTime;
	
	/**
	 * A ZonedDateTime standard format used across the project. This pattern was
	 * chosen specifically because it can be used to parse a String
	 * representation of java.util.Date
	 */
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE_TIME;
	
	@Override
	public void initialize() {
		dateAndTime = new DateTimePicker();		
	}
	
	@Override
	public void setDataModelItem(TSLSchemeInformationType modelItem) {
		this.type = modelItem;
		dateAndTime.setDateTimeValue(LocalDateTime.parse(type.getListIssueDateTime().toXMLFormat(), dateFormatter));
		this.contentVbox.getChildren().add(dateAndTime);
	}

	@Override
	public void save() {
		String isoTime = dateFormatter.format(dateAndTime.getDateTimeValue());
		try {
			type.setListIssueDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(isoTime));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected TSLSchemeInformationType getDataTypeFromModel(
			TSLSchemeInformationType modelItem) {
		// Leave blank
		return null;
	}

}
