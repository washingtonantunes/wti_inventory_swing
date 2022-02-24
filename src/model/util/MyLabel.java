package model.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class MyLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	private static final Dimension DIMENSION1 = new Dimension(100, 30);
	private static final Dimension DIMENSION2 = new Dimension(150, 30);
	private static final Dimension DIMENSION3 = new Dimension(200, 30);
	private static final Dimension DIMENSION4 = new Dimension(250, 30);
	private static final Dimension DIMENSION5 = new Dimension(275, 30);
	private static final Dimension DIMENSION6 = new Dimension(300, 30);
	private static final Dimension DIMENSION7 = new Dimension(400, 30);
	private static final Dimension DIMENSION8 = new Dimension(30, 30);
	private static final Dimension DIMENSION9 = new Dimension(80, 30);

	private static final Color COLOR1 = new Color(0, 65, 83);
	private static final Color COLOR2 = new Color(2, 101, 124);
	private static final Color COLOR3 = new Color(237, 28, 36);
	private static final Color COLOR4 = new Color(255, 255, 255); // White

	private static final Font FONT1 = new Font(null, Font.BOLD, 15);
	private static final Font FONT2 = new Font(null, Font.BOLD, 20);

	private int size;
	private int color;
	private int font;

	public MyLabel(String text, int size, int color, int font) {
		super(text);
		this.size = size;
		this.color = color;
		this.font = font;
		initObject();
	}

	private void initObject() {

		setPreferredSize(setDimension());
		setForeground(setColor());
		setFont(setFont());
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
		if (size == 8) {
			return DIMENSION8;
		}
		if (size == 9) {
			return DIMENSION9;
		}
		return null;
	}

	private Color setColor() {
		if (color == 1) {
			return COLOR1;
		}
		if (color == 2) {
			return COLOR2;
		}
		if (color == 3) {
			return COLOR3;
		}
		if (color == 4) {
			return COLOR4;
		}
		return null;
	}
	
	private Font setFont() {
		if (font == 1) {
			return FONT1;
		}
		if (font == 2) {
			return FONT2;
		}
		return null;
	}
}
