package model.util;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldFilter extends PlainDocument {

	private static final long serialVersionUID = 7849760310991654101L;

	/* TIPOS GEN…RICOS */	
	public static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz ";
	public static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
	public static final String UPPERCASE_NO_SPACE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    
	public static final String LOWERCASE_SPECIAL = "·‚ÈÌı˙Á„‡ÍÛÙ¸";
	public static final String UPPERCASE_SPECIAL = "¡¬…Õ’⁄«√¿ ”‘‹";

	public static final String ALPHA = LOWERCASE + UPPERCASE; 
	public static final String NUMERIC = "0123456789";
	public static final String DECIMAL = NUMERIC + ".";

	public static final String PONTO = ".";
	public static final String UPPERCASE_NUMERIC_PONTO = UPPERCASE + NUMERIC + PONTO;

	public static final String UPPERCASE_NUMERIC = UPPERCASE + NUMERIC;
	public static final String UPPERCASE_NUMERIC_NO_SPACE = UPPERCASE_NO_SPACE + NUMERIC;
	public static final String LOWERCASE_NUMERIC = LOWERCASE + NUMERIC;

	public static final String ALPHA_NUMERIC = ALPHA + NUMERIC;
	public static final String ALPHA_NUMERIC_UPPERCASE = UPPERCASE + NUMERIC;

	private static final String SPECIAL = "∫™";

	public static final String ENDERECO = UPPERCASE.trim() + DECIMAL + LOWERCASE;

	public static String FULL = ALPHA_NUMERIC + LOWERCASE_SPECIAL + UPPERCASE_SPECIAL + SPECIAL;

	/* TIPOS PR… DEFINIDOS */
	public static final String DATE = NUMERIC + "/";
	public static final String CEP = NUMERIC + "-";
	public static final String EMAIL = "abcdefghijklmnopqrstuvwxyz" +  NUMERIC + "_@-.+";
	
	/* MEUS TIPOS  */
	public static final String SERIALNUMBER = LOWERCASE.trim() + UPPERCASE.trim() + NUMERIC;
	public static final String ADDRESS_MAC = SERIALNUMBER + NUMERIC + "-";

	protected String acceptedChars = null;
	protected boolean negativeAccepted = false;
	protected int maxLength = 100;

	public JTextFieldFilter() {
	this(ALPHA_NUMERIC);
	}

	public JTextFieldFilter(String acceptedchars) {
	this.acceptedChars = acceptedchars;
	}

	public JTextFieldFilter(String acceptedchars, int maxLength) {
	this.acceptedChars = acceptedchars;
	this.maxLength = maxLength;
	}

	public void setNegativeAccepted(boolean negativeaccepted) {
	if (acceptedChars.equals(NUMERIC) || acceptedChars.equals(DECIMAL)
		|| acceptedChars.equals(ALPHA_NUMERIC) || acceptedChars.equals(DATE)
		|| acceptedChars.equals(CEP)) {
	    negativeAccepted = negativeaccepted;
	    acceptedChars += "-";
	}
	}

	@SuppressWarnings("static-access")
	public void insertString(int offset, String str, AttributeSet attr)
	throws BadLocationException {
	if (str == null)
	    return;

	// estudar sobre campos que aceitam apenas numeros..

	if (acceptedChars.equals(UPPERCASE) ||acceptedChars.equals(UPPERCASE_NUMERIC) || 
		acceptedChars.equals(UPPERCASE_NUMERIC+DECIMAL) || acceptedChars.equals(UPPERCASE_NUMERIC+UPPERCASE_SPECIAL) ||
		acceptedChars.equals(UPPERCASE_NO_SPACE) || acceptedChars.equals(UPPERCASE_NUMERIC_NO_SPACE) || 
		acceptedChars.equals(ALPHA_NUMERIC_UPPERCASE))
	    str = str.toUpperCase();
	else if (acceptedChars.equals(LOWERCASE) || acceptedChars.equals(LOWERCASE_NUMERIC) || acceptedChars.equals(LOWERCASE_SPECIAL) ||
			acceptedChars.equals(EMAIL))
	    str = str.toLowerCase();

	for (int i = 0; i < str.length(); i++) {
	    if (acceptedChars.indexOf(str.valueOf(str.charAt(i))) == -1)
		return;
	}

	if (acceptedChars.equals(DECIMAL)
		|| (acceptedChars.equals(DECIMAL + "-") && negativeAccepted)) {
	    if (str.indexOf(".") != -1) {
		if (getText(0, getLength()).indexOf(".") != -1) {
		    return;
		}
	    }
	}

	if (negativeAccepted && str.indexOf("-") != -1) {
	    if (str.indexOf("-") != 0 || offset != 0) {
		return;
	    }
	}

	int strLen = str.length();
	if (strLen == 0) {
	    return;
	}
	int len = getLength();
	if (strLen + len > maxLength) {
	    Toolkit.getDefaultToolkit().beep();
	    str = str.substring(0, maxLength - len);
	}

	//codigo para tirar espaÁo ‡ esquerda
	if (str.indexOf(" ")==0 && len==0){
		return;
	}

	super.insertString(offset, str, attr);
	}

	/**
	 * Metodo maxLength
	 * @return maxLength
	 */
	public int maxLength() {
	return maxLength;
	}
}
