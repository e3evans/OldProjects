package com.cisco.swtg.bss.delegates;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is used to get details from properties file Available methods in
 * this class are : getPropertyMapFromCache(),setPropertyMapToCache(),
 * ApplicationProperties
 * (),createNewInstance(),getInstance(),getResourceBundle(),
 * validateFileName(),getPropertyMap
 * (),getPropertyMap(),createPropertiesMap(),isRequiredEnvironment()
 */
public class ApplicationProperties {

	// The Log
	protected static final Log log = LogFactory.getLog(ApplicationProperties.class.getName());

	// Hashmap to store the properties in ResourceBundle as map
	@SuppressWarnings("unchecked")
	private HashMap propertiesMap = new HashMap();

	// The singleton instance
	private static ApplicationProperties instance = null;

	// The current System environment loaded only once and stored
	@SuppressWarnings("unused")
	private static String environment = "";

	static {
		environment = System.getProperty("cisco.life");
	}

	/**
	 * Gets the PropertiesMap for the specified filename
	 * 
	 * @param fileName
	 * @return HashMap
	 */
	@SuppressWarnings("unchecked")
	private HashMap getPropertyMapFromCache(String fileName) {
		log.info("getPropertyMapFromCache invoked with fileName = " + fileName);
		HashMap propertiesMap = (HashMap) this.propertiesMap.get(fileName);
		return propertiesMap;
	}

	/**
	 * Stores the PropertiesMap to the internal Hashmap
	 * 
	 * @param fileName
	 * @param propertiesMap
	 */
	@SuppressWarnings("unchecked")
	private void setPropertyMapToCache(String fileName, HashMap propertiesMap) {
		this.propertiesMap.put(fileName, propertiesMap);
	}

	/**
	 * Private constructer to avoid instantiation of the class
	 */
	private ApplicationProperties() {

	}

	/**
	 * Create the single instance of this class in synchronized method
	 * 
	 * @return ApplicationProperties
	 */
	private static synchronized ApplicationProperties createNewInstance() {

		if (instance == null) {
			instance = new ApplicationProperties();
		}
		return instance;
	}

	/**
	 * Gets the instance of the ApplicationProperties class Ensures that only a
	 * single instance of this class is created at any time
	 * 
	 * @return ApplicationProperties
	 */
	public static ApplicationProperties getInstance() {

		if (instance == null) {
			instance = createNewInstance();
		}
		return instance;
	}

	/**
	 * Method to get the URL bundle for the input String
	 * 
	 * @param fileName
	 * @return ResourceBundle
	 */
	private ResourceBundle getResourceBundle(String fileName) {

		ResourceBundle bundle = null;
		try {
			String envFileName = fileName;
			// envFileName = (environment == null)
			// ? fileName
			// : environment + "." + fileName;

			bundle = ResourceBundle.getBundle(envFileName);
		} catch (Exception e) {
			// Logger Message implementation
			log.error(" [ERROR] Exception in Resource Bundle in getBundle(): {}", e);
		}
		return bundle;
	}

	/**
	 * This method is used to validate the file name
	 * 
	 * @param fileName
	 * @return String
	 */
	private String validateFileName(String fileName) {

		return (fileName == null || fileName.trim().length() == 0) ? "bssapp" : fileName;
	}

	/**
	 * Gets the keys and values of the default application resource bundle in
	 * HashMap form
	 * 
	 * @return HashMap
	 */
	@SuppressWarnings("unchecked")
	public HashMap getPropertyMap() {
		return getPropertyMap("bssapp");
	}

	/**
	 * Gets the keys and values of the specified resource bundle in HashMap form
	 * 
	 * @param fileName
	 * @return HashMap
	 */
	@SuppressWarnings("unchecked")
	public HashMap getPropertyMap(String fileName) {

		log.info("getPropertyMap invoked with fileName = " + fileName);
		HashMap propertiesMap = null;
		fileName = validateFileName(fileName);
		propertiesMap = getPropertyMapFromCache(fileName);
		log.info("getPropertyMap propertiesMap from getPropertyMapFromCache = " + propertiesMap);

		if (propertiesMap == null) {
			log.info("getPropertyMap propertiesMap is not present so creating one");
			propertiesMap = createPropertiesMap(fileName);
			log.info("getPropertyMap propertiesMap after creating one");

			if (propertiesMap != null) {
				setPropertyMapToCache(fileName, propertiesMap);
			}
		}
		log.info("getPropertyMap returning propertiesMap = " + propertiesMap);
		return propertiesMap;
	}

	/**
	 * This method is used to create properties map from the resource bundle
	 * 
	 * @param fileName
	 * @return HashMap
	 */
	@SuppressWarnings("unchecked")
	private HashMap createPropertiesMap(String fileName) {
		HashMap propertiesMap = null;
		ResourceBundle bundle = getResourceBundle(fileName);
		log.info("createPropertiesMap Resourcebundle = " + bundle);

		if (bundle != null) {
			propertiesMap = new HashMap();
			Enumeration keys = bundle.getKeys();

			for (; keys.hasMoreElements();) {
				String key = (String) keys.nextElement();
				String value = bundle.getString(key);
				propertiesMap.put(key, value);
			}
		}
		return propertiesMap;
	}

	/**
	 * This method is used to check whether the environment is required or not
	 * 
	 * @param key
	 * @param environment
	 * @return boolean
	 */
	@SuppressWarnings("unused")
	private boolean isRequiredEnvironment(String key, String environment) {
		boolean envSpecific = false;

		int strIndex = key.indexOf(environment);
		if (strIndex == 0) {
			envSpecific = true;
		}
		return envSpecific;
	}

}
