package model.util;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class MyButton extends JButton {

	private static final long serialVersionUID = 1L;

	private static final Dimension DIMENSION1 = new Dimension(100, 30);
	private static final Dimension DIMENSION2 = new Dimension(120, 30);
	private static final Dimension DIMENSION3 = new Dimension(140, 30);
	private static final Dimension DIMENSION4 = new Dimension(160, 30);

	private static final Font FONT = new Font(null, Font.BOLD, 15);

	private int size;

	public MyButton(String text, int size) {
		super(text);
		this.size = size;
		initObject();
	}

	private void initObject() {

		setPreferredSize(setDimension());
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
		return null;
	}
}
