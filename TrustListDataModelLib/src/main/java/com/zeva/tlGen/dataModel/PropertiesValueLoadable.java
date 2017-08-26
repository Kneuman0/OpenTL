package com.zeva.tlGen.dataModel;

import java.util.List;

public interface PropertiesValueLoadable {
	
	/**
	 * This method will parse a string from the properties file. 
	 * The uniform syntax for parsing will be as follows:
	 * 
	 * ";;" will be used to tokenize individual children in an element
	 * ",," will be used to tokenize parent elements
	 * 
	 * All values initialized with this method will be save in with an English (en)
	 * local. All of other locals will be generated at time of export.
	 * @param propValue
	 */
	List<String[]> parse(String propValue);

}
