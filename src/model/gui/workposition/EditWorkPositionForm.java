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
import exception.ObjectException;
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

	private final int COLUMN1 = 20;
	private final int COLUMN2 = 110;
	private final int COLUMN3 = 370;

	private int line = 0;
	private int line_multiplier = 30;

	private final int WIDTH_LABEL = 80;
	private final int HEIGHT_LABEL = 25;

	private final int WIDTH_TEXTFIELD_COMBOBOX = 200;
	private final int HEIGHT_TEXTFIELD_COMBOBOX = 25;

	private final int WIDTH_LABEL_ERROR = 170;
	private final int HEIGHT_LABEL_ERROR = 25;

	private final int widthPanel = WIDTH_LABEL + WIDTH_TEXTFIELD_COMBOBOX + WIDTH_LABEL_ERROR + 50; //largura
	private final int heightPanel = (30 * 9) + 140; //altura

	private final Dimension DIMENSIONMAINPANEL = new Dimension(widthPanel, heightPanel);
	
	private final int positionButton = (widthPanel / 2) - 140;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

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

		final JLabel label_Status = new JLabel("Status:");
		label_Status.setForeground(COLOR1);
		label_Status.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Status);

		final JLabel label_DateEntry = new JLabel("Date Entry:");
		label_DateEntry.setForeground(COLOR1);
		label_DateEntry.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_DateEntry);

		final JLabel label_Equipment = new JLabel("Equipment:");
		label_Equipment.setForeground(COLOR1);
		label_Equipment.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Equipment);

		final JLabel label_Monitor1 = new JLabel("Monitor 1:");
		label_Monitor1.setForeground(COLOR1);
		label_Monitor1.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Monitor1);
		
		final JLabel label_Monitor2 = new JLabel("Monitor 2:");
		label_Monitor2.setForeground(COLOR1);
		label_Monitor2.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Monitor2);
	}

	private void addTextFieldsAndComboBoxes(JPanel panel) {
		final JLabel label_Show_WorkPoint = new JLabel(workPositionOld.getWorkPoint());
		label_Show_WorkPoint.setForeground(COLOR2);
		label_Show_WorkPoint.setBounds(COLUMN2, line = 30, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_WorkPoint);

		comboBox_Location = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("LOCALIZATION") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Location.setSelectedItem(workPositionOld.getLocation());
		comboBox_Location.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(comboBox_Location);

		comboBox_Floor = new JComboBox<>(
				new Vector<>(options.stream().filter(o -> o.getType().equals("FLOOR") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Floor.setSelectedItem(workPositionOld.getFloor());
		comboBox_Floor.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(comboBox_Floor);

		textField_NetPoint = new JTextField();
		textField_NetPoint.setDocument(new JTextFieldFilter(JTextFieldFilter.DECIMAL, 4));
		textField_NetPoint.setText(workPositionOld.getNetPoint());
		textField_NetPoint.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(textField_NetPoint);

		final JLabel label_Show_Status = new JLabel(workPositionOld.getStatus());
		label_Show_Status.setForeground(COLOR2);
		label_Show_Status.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(workPositionOld.getDateEntry()));
		label_Show_DateEntry.setForeground(COLOR2);
		label_Show_DateEntry.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_DateEntry);

		final JLabel label_Show_Equipment = new JLabel(workPositionOld.getEquipment() != null ? workPositionOld.getEquipment().getSerialNumber() : "");
		label_Show_Equipment.setForeground(COLOR2);
		label_Show_Equipment.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_Equipment);

		final JLabel label_Show_Monitor1 = new JLabel(
				workPositionOld.getMonitor1() != null ? workPositionOld.getMonitor1().getSerialNumber() : "");
		label_Show_Monitor1.setForeground(COLOR2);
		label_Show_Monitor1.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_Monitor1);
		
		final JLabel label_Show_Monitor2 = new JLabel(
				workPositionOld.getMonitor2() != null ? workPositionOld.getMonitor2().getSerialNumber() : "");
		label_Show_Monitor2.setForeground(COLOR2);
		label_Show_Monitor2.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_Monitor2);
	}

	private void addLabelsError(JPanel panel) {
		labelError_Location = new JLabel();
		labelError_Location.setForeground(Color.RED);
		labelError_Location.setBounds(COLUMN3, line = 60, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Location);

		labelError_Floor = new JLabel();
		labelError_Floor.setForeground(Color.RED);
		labelError_Floor.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Floor);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(positionButton, 330, 120, 30);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(positionButton + 160, 330, 120, 30);
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
			catch (ObjectException e) {
				if (e.getMessage().contains("There is no change")) {
					JOptionPane.showMessageDialog(rootPane, "There is no change", "Error updating object",
							JOptionPane.INFORMATION_MESSAGE);
				}
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

		workPosition.setLocation(comboBox_Location.getSelectedItem().toString());

		workPosition.setFloor(comboBox_Floor.getSelectedItem().toString());

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
				JOptionPane.showMessageDialog(rootPane, "This work point already exists", "Error update object", JOptionPane.ERROR_MESSAGE);
			} 
			else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error update object", JOptionPane.ERROR_MESSAGE);
			}
		} 
		else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error update object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
