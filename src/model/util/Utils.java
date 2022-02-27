package model.util;

public class Utils {

	public static boolean ToCheckHostName(String hostNameToCheck) {
		String a = hostNameToCheck.toUpperCase().trim().substring(0, 6);

		if (a.equalsIgnoreCase("SPODSK") || a.equalsIgnoreCase("SPONTB")) {
			return false;
		}
		return true;
	}
	
	public static boolean ToCheckHostNameAndType(String hostNameToCheck, String typeToCheck) {
		String hostName = hostNameToCheck.substring(3, 6);
		String type = typeToCheck;
		
		if (hostName.equals("DSK") && type.equals("DESKTOP")) {
			return false;
		}
		
		if (hostName.equals("NTB") && type.equals("NOTEBOOK")) {
			return false;
		}
		
		return true;
	}

	//Used to check the format of the Adress MAC
	public static boolean ToCheckAddressMAC(String addressMACToCheck) {
		String a = addressMACToCheck.toUpperCase().trim().substring(2, 3); // -
		String b = addressMACToCheck.toUpperCase().trim().substring(5, 6); // -
		String c = addressMACToCheck.toUpperCase().trim().substring(8, 9); // -
		String d = addressMACToCheck.toUpperCase().trim().substring(11, 12); //-
		String e = addressMACToCheck.toUpperCase().trim().substring(14, 15); //-

		if (a.equals("-") && b.equals("-") && c.equals("-") && d.equals("-") && e.equals("-")) {
			return false;
		}
		return true;
	}
	
	// Used to check the format of the CPF
	public static boolean ToCheckCPF(String CPFToCheck) {
		String a = CPFToCheck.toUpperCase().trim().substring(3, 4); // .
		String b = CPFToCheck.toUpperCase().trim().substring(7, 8); // .
		String c = CPFToCheck.toUpperCase().trim().substring(11, 12); // -

		if (a.equals(".") && b.equals(".") && c.equals("-")) {
			return false;
		}
		return true;
	}
	
	// Used to check the format of the Phone
	public static boolean ToCheckPhone(String PhoneToCheck) {
		String a = PhoneToCheck.toUpperCase().trim().substring(0, 1); // (
		String b = PhoneToCheck.toUpperCase().trim().substring(3, 4); // )
		String c = PhoneToCheck.toUpperCase().trim().substring(4, 5); // ' '
		String d = PhoneToCheck.toUpperCase().trim().substring(10, 11); // -

		if (a.equals("(") && b.equals(")") && c.equals(" ") && d.equals("-")) {
			return false;
		}
		return true;
	}

	public static Double tryParseToDouble(String str) {
		try {
			return Double.parseDouble(str);
		} 
		catch (NumberFormatException e) {
			return 0.0;
		}
	}
	
	public static String tryParseToString(String strToCheck) {
		try {
			return strToCheck;
		} 
		catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static boolean ToCheckEmailContain(String emailToCheck) {
		String a = emailToCheck.toUpperCase();
		
		if (a.contains("@MINSAIT.COM")) {
			return false;
		}
		return true;
	}	
	
	public static String getLocationEquipment() {
		String location = "WITH TI BPO";
		
		return location;
	}
}
