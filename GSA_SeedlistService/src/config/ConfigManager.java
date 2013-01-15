package config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ConfigManager {
	
	private static ConfigManager _instance = null;
	private Configuration config;
	
	private ConfigManager(){
		try {
			setConfig(new PropertiesConfiguration("config/config.properties"));
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public synchronized static ConfigManager getInstance(){
		
		if (_instance==null)_instance = new ConfigManager();
		return _instance;
	}

	public String getString(String property){
		return config.getString(property);
	}
	
	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

}
