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
import model.entities.Option;
import model.entities.WorkPosition;
import model.services.workposition.WorkPositionService;
import model.services.workposition.WorkPositionTableModel;
import model.util.JTextFieldFilter;

public class NewWorkPositionForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 170;
	private static final int COLUMN3 = 330;

	private static final int WIDTH = 150;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 270);

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
		label_WorkPoint.setBounds(COLUMN1, 10, WIDTH, HEIGHT);
		panel.add(label_WorkPoint);

		final JLabel label_Location = new JLabel("Location:");
		label_Location.setBounds(COLUMN1, 50, WIDTH, HEIGHT);
		panel.add(label_Location);

		final JLabel label_Floor = new JLabel("Floor:");
		label_Floor.setBounds(COLUMN1, 90, WIDTH, HEIGHT);
		panel.add(label_Floor);

		final JLabel label_NetPoint = new JLabel("NetPoint:");
		label_NetPoint.setBounds(COLUMN1, 130, WIDTH, HEIGHT);
		panel.add(label_NetPoint);
	}

	private void addTextFieldsAndComboBoxes(JPanel panel) {
		textField_WorkPoint = new JTextField();
		textField_WorkPoint.setDocument(new JTextFieldFilter(JTextFieldFilter.SERIALNUMBER, 12));
		textField_WorkPoint.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(textField_WorkPoint);

		comboBox_Location = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("LOCATION") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Location.setSelectedIndex(-1);
		comboBox_Location.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(comboBox_Location);

		comboBox_Floor = new JComboBox<>(
				new Vector<>(options.stream().filter(o -> o.getType().equals("FLOOR") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Floor.setSelectedIndex(-1);
		comboBox_Floor.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(comboBox_Floor);

		textField_NetPoint = new JTextField();
		textField_NetPoint.setDocument(new JTextFieldFilter(JTextFieldFilter.DECIMAL, 4));
		textField_NetPoint.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(textField_NetPoint);
	}

	private void addLabelsError(JPanel panel) {
		labelError_WorkPoint = new JLabel();
		labelError_WorkPoint.setForeground(Color.RED);
		labelError_WorkPoint.setBounds(COLUMN3, 10, WIDTH + 90, HEIGHT);
		panel.add(labelError_WorkPoint);

		labelError_Location = new JLabel();
		labelError_Location.setForeground(Color.RED);
		labelError_Location.setBounds(COLUMN3, 50, WIDTH + 90, HEIGHT);
		panel.add(labelError_Location);

		labelError_Floor = new JLabel();
		labelError_Floor.setForeground(Color.RED);
		labelError_Floor.setBounds(COLUMN3, 90, WIDTH + 90, HEIGHT);
		panel.add(labelError_Floor);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(180, 190, WIDTH - 30, HEIGHT);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(320, 190, WIDTH - 30, HEIGHT);
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
			exception.addError("workPoint", "Invalid work point - Ex: > 6");
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
		if (textField_NetPoint.getText() == null || textField_NetPoint.getText().trim().equals("")) {
			exception.addError("netPoint", "Field can't be empty");
		} 
		else {
			workPosition.setNetPoint(textField_NetPoint.getText().trim().toUpperCase());
		}

		// Insert Status
		workPosition.setStatus("FREE");

		// Insert DateEntry
		workPosition.setDateEntry(new Date());

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
			} 
			else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			}
		} 
		else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
