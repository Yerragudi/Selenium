package com.dxc.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileHandler {

	static public String getProperty(String propertyName_caseSensitive) {
		Properties p = new Properties();
		String configFileLocation = System.getProperty("user.dir") + "/config.properties";
		try {
			InputStream inputStream = new FileInputStream(configFileLocation);
			p.load(inputStream);
			return p.getProperty(propertyName_caseSensitive).trim();
		} catch (Exception e) {
			System.out.println(e);
			throw new Error(e);
		}
	}
}
