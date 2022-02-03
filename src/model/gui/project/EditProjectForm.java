package model.gui.project;

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
import model.entities.Project;
import model.entities.Option;
import model.services.project.ProjectService;
import model.services.project.ProjectTableModel;
import model.util.JTextFieldFilter;

public class EditProjectForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 170;
	private static final int COLUMN3 = 330;

	private static final int WIDTH = 150;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 350);

	private final Color COLOR1 = new Color(0, 65, 83);

	private JTextField textField_Name;
	private JComboBox<String> comboBox_City;
	private JTextField textField_CostCenter;

	private JLabel labelError_Name;
	private JLabel labelError_City;
	private JLabel labelError_CostCenter;

	private ProjectTableModel model;
	private Project projectOld;
	private List<Option> options;
	private int lineSelected;

	public EditProjectForm(ProjectTableModel model, Project projectOld, List<Option> options, int lineSelected) {
		this.model = model;
		this.projectOld = projectOld;
		this.options = options;
		this.lineSelected = lineSelected;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Edit Project");
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
		final JLabel label_ID = new JLabel("ID:");
		label_ID.setForeground(COLOR1);
		label_ID.setBounds(COLUMN1, 10, WIDTH, HEIGHT);
		panel.add(label_ID);

		final JLabel label_Name = new JLabel("Name:");
		label_Name.setForeground(COLOR1);
		label_Name.setBounds(COLUMN1, 50, WIDTH, HEIGHT);
		panel.add(label_Name);

		final JLabel label_City = new JLabel("City:");
		label_City.setForeground(COLOR1);
		label_City.setBounds(COLUMN1, 90, WIDTH, HEIGHT);
		panel.add(label_City);

		final JLabel label_CostCenter = new JLabel("Cost Center:");
		label_CostCenter.setForeground(COLOR1);
		label_CostCenter.setBounds(COLUMN1, 130, WIDTH, HEIGHT);
		panel.add(label_CostCenter);

		JLabel label_Status = new JLabel("Status:");
		label_Status.setForeground(COLOR1);
		label_Status.setBounds(COLUMN1, 170, WIDTH, HEIGHT);
		panel.add(label_Status);

		JLabel label_DateEntry = new JLabel("DateEntry:");
		label_DateEntry.setForeground(COLOR1);
		label_DateEntry.setBounds(COLUMN1, 210, WIDTH, HEIGHT);
		panel.add(label_DateEntry);
	}

	private void addTextFieldsAndComboBoxes(JPanel panel) {
		final JLabel label_Show_ID = new JLabel(projectOld.getId().toString());
		label_Show_ID.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(label_Show_ID);

		textField_Name = new JTextField();
		textField_Name.setDocument(new JTextFieldFilter(JTextFieldFilter.PROJECT, 50));
		textField_Name.setText(projectOld.getName());
		textField_Name.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(textField_Name);

		comboBox_City = new JComboBox<>(
				new Vector<>(options.stream().filter(o -> o.getType().equals("CITY") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_City.setSelectedItem(projectOld.getCity());
		comboBox_City.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(comboBox_City);

		textField_CostCenter = new JTextField();
		textField_CostCenter.setDocument(new JTextFieldFilter(JTextFieldFilter.SERIALNUMBER, 15));
		textField_CostCenter.setText(projectOld.getCostCenter());
		textField_CostCenter.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(textField_CostCenter);

		final JLabel label_Show_Status = new JLabel(projectOld.getStatus());
		label_Show_Status.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(projectOld.getDateEntry()));
		label_Show_DateEntry.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(label_Show_DateEntry);
	}

	private void addLabelsError(JPanel panel) {
		labelError_Name = new JLabel();
		labelError_Name.setForeground(Color.RED);
		labelError_Name.setBounds(COLUMN3, 50, WIDTH + 90, HEIGHT);
		panel.add(labelError_Name);

		labelError_City = new JLabel();
		labelError_City.setForeground(Color.RED);
		labelError_City.setBounds(COLUMN3, 90, WIDTH + 90, HEIGHT);
		panel.add(labelError_City);

		labelError_CostCenter = new JLabel();
		labelError_CostCenter.setForeground(Color.RED);
		labelError_CostCenter.setBounds(COLUMN3, 130, WIDTH + 90, HEIGHT);
		panel.add(labelError_CostCenter);
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
				final Project projectNew = getFormData();
				ProjectService service = new ProjectService();
				service.update(projectOld, projectNew);
				model.updateProject(lineSelected, projectNew);
				dispose();
				JOptionPane.showMessageDialog(rootPane, "Project successfully updated", "Success updating object", JOptionPane.INFORMATION_MESSAGE);
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

	private Project getFormData() {
		Project project = new Project();
		project = (Project) projectOld.clone();

		ValidationException exception = new ValidationException("Validation error");

		// validation Name
		if (textField_Name.getText() == null || textField_Name.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		} 
		else {
			project.setName(textField_Name.getText().trim().toUpperCase());
		}

		// Validation City
		if (comboBox_City.getSelectedIndex() < 0 || comboBox_City.getSelectedItem() == null) {
			exception.addError("city", "Field can't be empty");
		} 
		else {
			project.setCity(comboBox_City.getSelectedItem().toString());
		}

		// validation Cost Center
		if (textField_CostCenter.getText() == null || textField_CostCenter.getText().trim().equals("")) {
			exception.addError("costCenter", "Field can't be empty");
		} 
		else {
			project.setCostCenter(textField_CostCenter.getText().trim().toUpperCase());
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return project;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_Name.setText(fields.contains("name") ? errors.get("name") : "");
		labelError_City.setText(fields.contains("city") ? errors.get("city") : "");
		labelError_CostCenter.setText(fields.contains("costCenter") ? errors.get("costCenter") : "");
	}

	private void setErroMessagesDBException(DBException e) {
		if (e.getMessage().contains("Duplicate entry")) {
			if (e.getMessage().contains("projects.name_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This patrimony number already exists", "Error saving object", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
