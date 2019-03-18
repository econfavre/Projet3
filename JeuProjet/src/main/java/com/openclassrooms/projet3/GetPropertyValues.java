package com.openclassrooms.projet3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class GetPropertyValues {

	private Integer size;
	private Integer tryMax;
	private Integer maxDigit;
	private Integer devMode;

	String result = "";
	InputStream inputStream;

	public void getPropValues() throws IOException {

		try {
			Properties prop = new Properties();
			String confFileName = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(confFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + confFileName + "' not found in the classpath");
			}

			Date time = new Date(System.currentTimeMillis());

			// get the property value and print it out
			size = Integer.valueOf(prop.getProperty("size"));
			maxDigit = Integer.valueOf(prop.getProperty("maxDigit"));
			tryMax = Integer.valueOf(prop.getProperty("tryMax"));
			devMode = Integer.valueOf(prop.getProperty("devMode"));

			System.out.println("\nProgram Ran on " + time);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}

	}

	public Integer getSize() {
		return size;
	}

	public Integer getTryMax() {
		return tryMax;
	}

	public Integer getMaxDigit() {
		return maxDigit;
	}

	public Integer getDevMode() {
		return devMode;
	}

}
