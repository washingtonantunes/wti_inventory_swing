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
import exception.ObjectException;
import exception.ValidationException;
import model.entities.Project;
import model.entities.Option;
import model.services.project.ProjectService;
import model.services.project.ProjectTableModel;
import model.util.JTextFieldFilter;

public class EditProjectForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private final int COLUMN1 = 20;
	private final int COLUMN2 = 120;
	private final int COLUMN3 = 330;

	private int line = 0;
	private int line_multiplier = 30;

	private final int WIDTH_LABEL = 70;
	private final int HEIGHT_LABEL = 25;

	private final int WIDTH_TEXTFIELD_COMBOBOX = 200;
	private final int HEIGHT_TEXTFIELD_COMBOBOX = 25;

	private final int WIDTH_LABEL_ERROR = 150;
	private final int HEIGHT_LABEL_ERROR = 25;
	
	private final int widthPanel = WIDTH_LABEL + WIDTH_TEXTFIELD_COMBOBOX + WIDTH_LABEL_ERROR + 50; //largura
	private final int heightPanel = (30 * 5) + 140; //altura

	private final Dimension DIMENSIONMAINPANEL = new Dimension(widthPanel, heightPanel);
	
	private final int positionButton = (widthPanel / 2) - 140;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private JTextField textField_Name;
	private JComboBox<String> comboBox_City;

	private JLabel labelError_Name;
	private JLabel labelError_City;

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
		final JLabel label_CostCenter = new JLabel("Cost Center:");
		label_CostCenter.setForeground(COLOR1);
		label_CostCenter.setBounds(COLUMN1, line = 30, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_CostCenter);

		final JLabel label_Name = new JLabel("Name:");
		label_Name.setForeground(COLOR1);
		label_Name.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Name);

		final JLabel label_City = new JLabel("City:");
		label_City.setForeground(COLOR1);
		label_City.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_City);

		JLabel label_Status = new JLabel("Status:");
		label_Status.setForeground(COLOR1);
		label_Status.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Status);

		JLabel label_DateEntry = new JLabel("DateEntry:");
		label_DateEntry.setForeground(COLOR1);
		label_DateEntry.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_DateEntry);
	}

	private void addTextFieldsAndComboBoxes(JPanel panel) {
		final JLabel label_Show__CostCenter = new JLabel(projectOld.getCostCenter());
		label_Show__CostCenter.setForeground(COLOR2);
		label_Show__CostCenter.setBounds(COLUMN2, line = 30, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show__CostCenter);

		textField_Name = new JTextField();
		textField_Name.setDocument(new JTextFieldFilter(JTextFieldFilter.PROJECT, 50));
		textField_Name.setText(projectOld.getName());
		textField_Name.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(textField_Name);

		comboBox_City = new JComboBox<>(
				new Vector<>(options.stream().filter(o -> o.getType().equals("CITY") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_City.setSelectedItem(projectOld.getCity());
		comboBox_City.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(comboBox_City);

		final JLabel label_Show_Status = new JLabel(projectOld.getStatus());
		label_Show_Status.setForeground(COLOR2);
		label_Show_Status.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(projectOld.getDateEntry()));
		label_Show_DateEntry.setForeground(COLOR2);
		label_Show_DateEntry.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
		panel.add(label_Show_DateEntry);
	}

	private void addLabelsError(JPanel panel) {
		labelError_Name = new JLabel();
		labelError_Name.setForeground(Color.RED);
		labelError_Name.setBounds(COLUMN3, line = 60, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Name);

		labelError_City = new JLabel();
		labelError_City.setForeground(Color.RED);
		labelError_City.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_City);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(positionButton, 210, 120, 30);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(positionButton + 160, 210, 120, 30);
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

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return project;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_Name.setText(fields.contains("name") ? errors.get("name") : "");
		labelError_City.setText(fields.contains("city") ? errors.get("city") : "");
	}

	private void setErroMessagesDBException(DBException e) {
		JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error update object", JOptionPane.ERROR_MESSAGE);
	}
}
