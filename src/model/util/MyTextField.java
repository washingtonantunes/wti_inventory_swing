package model.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;

public class MyTextField extends JTextField {

	private static final long serialVersionUID = 1L;

	private static final Dimension DIMENSION1 = new Dimension(100, 30);
	private static final Dimension DIMENSION2 = new Dimension(150, 30);
	private static final Dimension DIMENSION3 = new Dimension(200, 30);
	private static final Dimension DIMENSION4 = new Dimension(250, 30);
	private static final Dimension DIMENSION5 = new Dimension(300, 30);
	private static final Dimension DIMENSION6 = new Dimension(350, 30);
	private static final Dimension DIMENSION7 = new Dimension(400, 30);

	private static final Color COLOR = new Color(2, 101, 124);

	private static final Font FONT = new Font(null, Font.BOLD, 15);

	private int size;

	public MyTextField(String text, int size) {
		super(text);
		this.size = size;
		initObject();
	}

	private void initObject() {

		setPreferredSize(setDimension());
		setForeground(COLOR);
		setFont(FONT);
	}

	private Dimension setDimension() {
		if (size == 1) {
			return DIMENSION1;
		}
		if (size == 2) {
			return DIMENSION2;
		}
		if (size == 3) {
			return DIMENSION3;
		}
		if (size == 4) {
			return DIMENSION4;
		}
		if (size == 5) {
			return DIMENSION5;
		}
		if (size == 6) {
			return DIMENSION6;
		}
		if (size == 7) {
			return DIMENSION7;
		}
		return null;
	}
}
