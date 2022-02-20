package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import db.DBException;

public class WTIProperties {

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("wti_inventory.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static String getCompany() {
		String companyName;
		Properties props = loadProperties();
		companyName = props.getProperty("Company_Name");
		return companyName;
	}

}
