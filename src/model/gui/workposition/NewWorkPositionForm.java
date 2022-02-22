package model.gui.workposition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DBException;
import exception.ValidationException;
import model.entities.Equipment;
import model.entities.Monitor;
import model.entities.Option;
import model.entities.WorkPosition;
import model.gui.MainWindow;
import model.services.workposition.WorkPositionService;
import model.services.workposition.WorkPositionTableModel;
import model.util.JTextFieldFilter;
import model.util.Utils;

public class NewWorkPositionForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final int COLUMN1 = 20;
	private final int COLUMN2 = 110;
	private final int COLUMN3 = 320;

	private int line = 0;
	private int line_multiplier = 30;

	private final int WIDTH_LABEL = 80;
	private final int HEIGHT_LABEL = 25;

	private final int WIDTH_TEXTFIELD_COMBOBOX = 200;
	private final int HEIGHT_TEXTFIELD_COMBOBOX = 25;

	private final int WIDTH_LABEL_ERROR = 170;
	private final int HEIGHT_LABEL_ERROR = 25;
	
	private final int widthPanel = WIDTH_LABEL + WIDTH_TEXTFIELD_COMBOBOX + WIDTH_LABEL_ERROR + 50; //largura
	private final int heightPanel = (30 * 4) + 140; //altura

	private final Dimension DIMENSIONMAINPANEL = new Dimension(widthPanel, heightPanel);
	
	private final int positionButton = (widthPanel / 2) - 140;

	private final Color COLOR1 = new Color(0, 65, 83);

	private JTextField textField_WorkPoint;
	private JComboBox<String> comboBox_Location;
	private JComboBox<String> comboBox_Floor;
	private JTextField textField_NetPoint;

	private JLabel labelError_WorkPoint;
	private JLabel labelError_Location;
	private JLabel labelError_Floor;

	private WorkPositionTableModel model;
	private WorkPosition workPosition;
	private List<Option> options;

	public NewWorkPositionForm(WorkPositionTableModel model, List<Option> options) {
		this.model = model;
		this.options = options;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("New Work Position");
		setPreferredSize(DIMENSIONMAINPANEL);
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createPanelMain() {
		final JPanel panel = new JPanel(new FlowLayout());
		panel.setLayout(null);

		addLabels(panel);
		addTextFieldsAndComboBoxes(panel);
		addLabelsError(panel);
		addButtons(panel);

		return panel;
	}

	private void addLabels(JPanel panel) {
		final JLabel label_WorkPoint = new JLabel("Work Point:");
		label_WorkPoint.setForeground(COLOR1);
		label_WorkPoint.setBounds(COLUMN1, line = 30, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_WorkPoint);

		final JLabel label_Location = new JLabel("Location:");
		label_Location.setForeground(COLOR1);
		label_Location.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Location);

		final JLabel label_Floor = new JLabel("Floor:");
		label_Floor.setForeground(COLOR1);
		label_Floor.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Floor);

		final JLabel label_NetPoint = new JLabel("NetPoint:");
		label_NetPoint.setForeground(COLOR1);
		label_NetPoint.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_NetPoint);
	}

	private void addTextFieldsAndComboBoxes(JPanel panel) {
		textField_WorkPoint = new JTextField();
		textField_WorkPoint.setDocument(new JTextFieldFilter(JTextFieldFilter.SERIALNUMBER, 20));
		textField_WorkPoint.setBounds(COLUMN2, line = 30, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(textField_WorkPoint);

		comboBox_Location = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("LOCALIZATION") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Location.setSelectedIndex(-1);
		comboBox_Location.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(comboBox_Location);

		comboBox_Floor = new JComboBox<>(
				new Vector<>(options.stream().filter(o -> o.getType().equals("FLOOR") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Floor.setSelectedIndex(-1);
		comboBox_Floor.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(comboBox_Floor);

		textField_NetPoint = new JTextField();
		textField_NetPoint.setDocument(new JTextFieldFilter(JTextFieldFilter.DECIMAL, 4));
		textField_NetPoint.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
				HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(textField_NetPoint);
	}

	private void addLabelsError(JPanel panel) {
		labelError_WorkPoint = new JLabel();
		labelError_WorkPoint.setForeground(Color.RED);
		labelError_WorkPoint.setBounds(COLUMN3, line = 30, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_WorkPoint);

		labelError_Location = new JLabel();
		labelError_Location.setForeground(Color.RED);
		labelError_Location.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Location);

		labelError_Floor = new JLabel();
		labelError_Floor.setForeground(Color.RED);
		labelError_Floor.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Floor);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(positionButton, 180, 120, 30);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(positionButton + 160, 180, 120, 30);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonSaveListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				workPosition = getFormData();
				WorkPositionService service = new WorkPositionService();
				service.save(workPosition);
				model.addWorkPosition(workPosition);
				MainWindow.addWorkPosition(workPosition);
				dispose();
				JOptionPane.showMessageDialog(rootPane, "Work Position successfully added", "Success saving object",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			} catch (DBException e) {
				setErroMessagesDBException(e);
			}
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	private WorkPosition getFormData() {
		WorkPosition workPosition = new WorkPosition();

		ValidationException exception = new ValidationException("Validation error");

		// validation Work Position
		if (textField_WorkPoint.getText() == null || textField_WorkPoint.getText().trim().equals("")) {
			exception.addError("workPoint", "Field can't be empty");
		} else if (textField_WorkPoint.getText().length() < 6) {
			exception.addError("workPoint", "Invalid Work Point - Ex: > 6");
		} else {
			workPosition.setWorkPoint(textField_WorkPoint.getText().trim().toUpperCase());
		}

		// Validation Location
		if (comboBox_Location.getSelectedIndex() < 0 || comboBox_Location.getSelectedItem() == null) {
			exception.addError("location", "Field can't be empty");
		} else {
			workPosition.setLocation(comboBox_Location.getSelectedItem().toString());
		}

		// Validation Floor
		if (comboBox_Floor.getSelectedIndex() < 0 || comboBox_Floor.getSelectedItem() == null) {
			exception.addError("floor", "Field can't be empty");
		} else {
			workPosition.setFloor(comboBox_Floor.getSelectedItem().toString());
		}

		// validation Net Point
		workPosition.setNetPoint(Utils.tryParseToString(textField_NetPoint.getText().trim().toUpperCase()));

		// Insert Status
		workPosition.setStatus("FREE");

		// Insert DateEntry
		workPosition.setDateEntry(new Date());

		// Insert Equipment
		workPosition.setEquipment(new Equipment());

		// Insert Monitor 1
		workPosition.setMonitor1(new Monitor());

		// Insert Monitor 2
		workPosition.setMonitor2(new Monitor());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return workPosition;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_WorkPoint.setText(fields.contains("workPoint") ? errors.get("workPoint") : "");
		labelError_Location.setText(fields.contains("location") ? errors.get("location") : "");
		labelError_Floor.setText(fields.contains("floor") ? errors.get("floor") : "");
	}

	private void setErroMessagesDBException(DBException e) {
		if (e.getMessage().contains("Duplicate entry")) {
			if (e.getMessage().contains("work_positions.PRIMARY")) {
				JOptionPane.showMessageDialog(rootPane, "This work point already exists", "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
