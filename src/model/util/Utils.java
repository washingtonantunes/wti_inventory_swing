package model.util;

import javax.swing.JComboBox;

public class Utils {

	public static boolean ToCheckHostName(String hostNameToCheck) {
		String a = hostNameToCheck.toUpperCase().trim().substring(0, 6);

		if (a.equalsIgnoreCase("SPODSK") || a.equalsIgnoreCase("SPONTB")) {
			return false;
		}
		return true;
	}

	public static boolean ToCheckAddressMAC(String addressMACToCheck) {
		String a = addressMACToCheck.toUpperCase().trim().substring(2, 3);
		String b = addressMACToCheck.toUpperCase().trim().substring(5, 6);
		String c = addressMACToCheck.toUpperCase().trim().substring(8, 9);
		String d = addressMACToCheck.toUpperCase().trim().substring(11, 12);
		String e = addressMACToCheck.toUpperCase().trim().substring(14, 15);

		if (a.equals("-") && b.equals("-") && c.equals("-") && d.equals("-") && e.equals("-")) {
			return false;
		}
		return true;
	}
	
	public static Double tryParseToDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}

	public static String tryParseToString(JComboBox<String> str) {
		try {
			return str.getSelectedItem().toString();
		} catch (NullPointerException e) {
			return null;
		}
	}
}
