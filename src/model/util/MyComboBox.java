package model.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JComboBox;

public class MyComboBox extends JComboBox<Object> {

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

	private List<Object> objects;
	private Object selectedObject;
	private int size;

	public MyComboBox(List<Object> objects, Object selectedObject, int size) {
		this.objects = objects;
		this.selectedObject = selectedObject;
		this.size = size;
		addObjects();
		initObject();
		selectObject();
	}
	
	public MyComboBox(List<Object> objects, int size) {
		this.objects = objects;
		this.size = size;
		addObjects();
		initObject();
	}

	private void initObject() {

		setPreferredSize(setDimension());
		setForeground(COLOR);
		setFont(FONT);
		setSelectedIndex(-1);
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
	
	private void addObjects() {
		
		for (Object object : objects) {
			addItem(object);
		}
	}
	
	private void selectObject() {
		setSelectedItem(selectedObject);
	}
}
