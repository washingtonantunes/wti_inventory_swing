package model.gui.workposition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
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
import model.util.Utils;

public class EditWorkPositionForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 170;
	private static final int COLUMN3 = 330;

	private static final int WIDTH = 150;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 350);

	private JComboBox<String> comboBox_Location;
	private JComboBox<String> comboBox_Floor;
	private JTextField textField_NetPoint;

	private JLabel labelError_Location;
	private JLabel labelError_Floor;

	private WorkPositionTableModel model;
	private final WorkPosition workPositionOld;
	private List<Option> options;
	private int lineSelected;

	public EditWorkPositionForm(WorkPositionTableModel model, WorkPosition workPositionOld, List<Option> options,
			int lineSelected) {
		this.model = model;
		this.workPositionOld = workPositionOld;
		this.options = options;
		this.lineSelected = lineSelected;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Edit Work Position");
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
		
		final JLabel label_Status = new JLabel("Status:");
		label_Status.setBounds(COLUMN1, 170, WIDTH, HEIGHT);
		panel.add(label_Status);

		final JLabel label_DateEntry = new JLabel("DateEntry:");
		label_DateEntry.setBounds(COLUMN1, 210, WIDTH, HEIGHT);
		panel.add(label_DateEntry);
	}

	private void addTextFieldsAndComboBoxes(JPanel panel) {
		final JLabel label_Show_WorkPoint = new JLabel(workPositionOld.getWorkPoint());
		label_Show_WorkPoint.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(label_Show_WorkPoint);

		comboBox_Location = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("LOCATION") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Location.setSelectedItem(workPositionOld.getLocation());
		comboBox_Location.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(comboBox_Location);

		comboBox_Floor = new JComboBox<>(
				new Vector<>(options.stream().filter(o -> o.getType().equals("FLOOR") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Floor.setSelectedItem(workPositionOld.getFloor());
		comboBox_Floor.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(comboBox_Floor);

		textField_NetPoint = new JTextField();
		textField_NetPoint.setDocument(new JTextFieldFilter(JTextFieldFilter.DECIMAL, 4));
		textField_NetPoint.setText(workPositionOld.getNetPoint());
		textField_NetPoint.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(textField_NetPoint);

		final JLabel label_Show_Status = new JLabel(workPositionOld.getStatus());
		label_Show_Status.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(workPositionOld.getDateEntry()));
		label_Show_DateEntry.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(label_Show_DateEntry);
	}

	private void addLabelsError(JPanel panel) {
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
		buttonSave.setBounds(180, 270, WIDTH - 30, HEIGHT);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(320, 270, WIDTH - 30, HEIGHT);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonSaveListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				final WorkPosition workPositionNew = getFormData();
				WorkPositionService service = new WorkPositionService();
				service.update(workPositionOld, workPositionNew);
				model.updateWorkPosition(lineSelected, workPositionNew);
				dispose();
				JOptionPane.showMessageDialog(rootPane, "Work Position successfully updated", "Success updating object",
						JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			} 
			catch (DBException e) {
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
		workPosition = (WorkPosition) workPositionOld.clone();

		ValidationException exception = new ValidationException("Validation error");

		// Validation Location
		if (comboBox_Location.getSelectedIndex() < 0 || comboBox_Location.getSelectedItem() == null) {
			exception.addError("location", "Field can't be empty");
		} 
		else {
			workPosition.setLocation(comboBox_Location.getSelectedItem().toString());
		}

		// Validation Floor
		if (comboBox_Floor.getSelectedIndex() < 0 || comboBox_Floor.getSelectedItem() == null) {
			exception.addError("floor", "Field can't be empty");
		} 
		else {
			workPosition.setFloor(comboBox_Floor.getSelectedItem().toString());
		}

		// validation Net Point
				workPosition.setNetPoint(Utils.tryParseToString(textField_NetPoint.getText().trim().toUpperCase()));

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return workPosition;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

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
