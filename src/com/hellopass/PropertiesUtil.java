package com.hellopass;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesUtil {
	private static String FILENAME = "pass.properties";
	
	private PropertiesConfiguration config;
	
	public PropertiesConfiguration getConfig()
	{
		return config;
	}
	
	public PropertiesUtil()
	{
		try {
			config = new PropertiesConfiguration(FILENAME);
			config.setAutoSave(true);
			config.isThrowExceptionOnMissing();

			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
