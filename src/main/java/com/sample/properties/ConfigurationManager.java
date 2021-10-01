package com.sample.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigurationManager {
	
	
  private ConfigurationManager() {
	  
  }
  
  private static ConfigurationManager singleton = new ConfigurationManager();
  
	private Log log = LogFactory.getLog( ConfigurationManager.class );

	
/*
 * return the Configuration Manager
 */
	public static ConfigurationManager instance()
	{
		return singleton;
	}
	
    private  File configFolder;

	
	private HashMap<String,String> properties = new HashMap<>();
	
	

	public HashMap<String, String> getProperties() {
		return properties;
	}


	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}
	
	
	public boolean  readFile(File ConfigFile) {
		try {
		if(ConfigFile.exists()) {
			return readFileDeatils(new FileInputStream(ConfigFile));
		}else {
			log.error("unable to read the config  file "+ConfigFile.getName()+" make sure it is valid");
			return false;
		}
		}
		catch(Exception c) {
		log.info("Error while reading the config file"+c.getMessage());
		return false;
		}
		
	}
	
	private boolean readFileDeatils(InputStream stream) {
        Properties cP = new Properties();
        try {
        cP.load(stream);
        for(Object key:cP.keySet()) {
        	properties.put((String)key, (String)cP.get(key));
        }
        log.info("Successfully loaded config properties into singleton class");
        return true;
        }
        catch(Exception c) {
            log.info("error reading the set up configuration properties file "+c.toString());

        	return false;
        }

	}
	
	
	
	
}
