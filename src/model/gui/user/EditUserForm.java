package model.gui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import db.DBException;
import exception.ObjectException;
import exception.ValidationException;
import model.entities.User;
import model.gui.MainWindow;
import model.entities.Option;
import model.entities.Project;
import model.services.user.UserService;
import model.services.user.UserTableModel;
import model.util.JTextFieldFilter;
import model.util.Utils;

public class EditUserForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private final int COLUMN1 = 20;
	private final int COLUMN2 = 120;
	private final int COLUMN3 = 380;

	private int line = 0;
	private int line_multiplier = 30;

	private final int WIDTH_LABEL = 80;
	private final int HEIGHT_LABEL = 25;

	private final int WIDTH_TEXTFIELD_COMBOBOX = 250;
	private final int HEIGHT_TEXTFIELD_COMBOBOX = 25;

	private final int WIDTH_LABEL_ERROR = 230;
	private final int HEIGHT_LABEL_ERROR = 25;

	private final Dimension DIMENSIONMAINPANEL = new Dimension(600, 410);

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private JTextField textField_Name;
	private JTextField textField_CPF;
	private JTextField textField_Phone;
	private JTextField textField_Email;
	private JComboBox<String> comboBox_Department;
	private JComboBox<Project> comboBox_Project;

	private JLabel labelError_Name;
	private JLabel labelError_CPF;
	private JLabel labelError_Phone;
	private JLabel labelError_Email;
	private JLabel labelError_Department;
	private JLabel labelError_Project;

	private UserTableModel model;
	private User userOld;
	private List<Option> options;
	private int lineSelected;

	public EditUserForm(UserTableModel model, User userOld, List<Option> options, int lineSelected) {
		this.model = model;
		this.userOld = userOld;
		this.options = options;
		this.lineSelected = lineSelected;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Edit User");
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
		final JLabel label_Registration = new JLabel("Registration:");
		label_Registration.setForeground(COLOR1);
		label_Registration.setBounds(COLUMN1, line = 30, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Registration);

		final JLabel label_Name = new JLabel("Name:");
		label_Name.setForeground(COLOR1);
		label_Name.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Name);

		final JLabel label_CPF = new JLabel("CPF:");
		label_CPF.setForeground(COLOR1);
		label_CPF.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_CPF);

		final JLabel label_Phone = new JLabel("Phone:");
		label_Phone.setForeground(COLOR1);
		label_Phone.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Phone);

		final JLabel label_Email = new JLabel("Email:");
		label_Email.setForeground(COLOR1);
		label_Email.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Email);

		final JLabel label_Department = new JLabel("Department:");
		label_Department.setForeground(COLOR1);
		label_Department.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Department);

		final JLabel label_Project = new JLabel("Project:");
		label_Project.setForeground(COLOR1);
		label_Project.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Project);

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
		try {
			final JLabel label_Show_Registration = new JLabel(userOld.getRegistration());
			label_Show_Registration.setForeground(COLOR2);
			label_Show_Registration.setBounds(COLUMN2, line = 30, WIDTH_TEXTFIELD_COMBOBOX, HEIGHT_TEXTFIELD_COMBOBOX);
			panel.add(label_Show_Registration);

			textField_Name = new JTextField();
			textField_Name.setText(userOld.getName());
			textField_Name.setForeground(COLOR2);
			textField_Name.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
					HEIGHT_TEXTFIELD_COMBOBOX);
			panel.add(textField_Name);

			textField_CPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
			textField_CPF.setText(userOld.getCpf());
			textField_CPF.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
					HEIGHT_TEXTFIELD_COMBOBOX);
			panel.add(textField_CPF);

			textField_Phone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
			textField_Phone.setText(userOld.getPhone());
			textField_Phone.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
					HEIGHT_TEXTFIELD_COMBOBOX);
			panel.add(textField_Phone);

			textField_Email = new JTextField();
			textField_Email.setDocument(new JTextFieldFilter(JTextFieldFilter.MYEMAIL, 50));
			textField_Email.setText(userOld.getEmail());
			textField_Email.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
					HEIGHT_TEXTFIELD_COMBOBOX);
			panel.add(textField_Email);

			comboBox_Department = new JComboBox<>(new Vector<>(
					options.stream().filter(o -> o.getType().equals("DEPARTMENT") && o.getStatus().equals("ACTIVE"))
							.map(Option::getOption).collect(Collectors.toList())));
			comboBox_Department.setSelectedItem(userOld.getDepartment());
			comboBox_Department.setForeground(COLOR2);
			comboBox_Department.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
					HEIGHT_TEXTFIELD_COMBOBOX);
			panel.add(comboBox_Department);

			comboBox_Project = new JComboBox<>(new Vector<>(MainWindow.getProjectList().stream()
					.filter(p -> p.getStatus().equals("ACTIVE")).collect(Collectors.toList())));
			comboBox_Project.setSelectedItem(userOld.getProject());
			comboBox_Project.setForeground(COLOR2);
			comboBox_Project.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
					HEIGHT_TEXTFIELD_COMBOBOX);
			panel.add(comboBox_Project);

			final JLabel label_Show_Status = new JLabel(userOld.getStatus());
			label_Show_Status.setForeground(COLOR2);
			label_Show_Status.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
					HEIGHT_TEXTFIELD_COMBOBOX);
			panel.add(label_Show_Status);

			final JLabel label_Show_DateEntry = new JLabel(sdf.format(userOld.getDateEntry()));
			label_Show_DateEntry.setForeground(COLOR2);
			label_Show_DateEntry.setBounds(COLUMN2, line += line_multiplier, WIDTH_TEXTFIELD_COMBOBOX,
					HEIGHT_TEXTFIELD_COMBOBOX);
			panel.add(label_Show_DateEntry);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error input data",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void addLabelsError(JPanel panel) {
		labelError_Name = new JLabel();
		labelError_Name.setForeground(Color.RED);
		labelError_Name.setBounds(COLUMN3, line = 60, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Name);

		labelError_CPF = new JLabel();
		labelError_CPF.setForeground(Color.RED);
		labelError_CPF.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_CPF);

		labelError_Phone = new JLabel();
		labelError_Phone.setForeground(Color.RED);
		labelError_Phone.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Phone);

		labelError_Email = new JLabel();
		labelError_Email.setForeground(Color.RED);
		labelError_Email.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Email);

		labelError_Department = new JLabel();
		labelError_Department.setForeground(Color.RED);
		labelError_Department.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Department);

		labelError_Project = new JLabel();
		labelError_Project.setForeground(Color.RED);
		labelError_Project.setBounds(COLUMN3, line += line_multiplier, WIDTH_LABEL_ERROR, HEIGHT_LABEL_ERROR);
		panel.add(labelError_Project);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(165, 330, 120, 25);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(305, 330, 120, 25);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonSaveListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				final User userNew = getFormData();
				UserService service = new UserService();
				service.update(userOld, userNew);
				model.updateUser(lineSelected, userNew);
				dispose();
				JOptionPane.showMessageDialog(rootPane, "User successfully updated", "Success updating object",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			} catch (DBException e) {
				setErroMessagesDBException(e);
			} catch (ObjectException e) {
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

	private User getFormData() {
		User user = new User();
		user = (User) userOld.clone();

		ValidationException exception = new ValidationException("Validation error");

		// Validation Name
		if (textField_Name.getText() == null || textField_Name.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		} else if (textField_Name.getText().length() < 10) {
			exception.addError("name", "Invalid Name - Ex: > 10");
		} else {
			user.setName(textField_Name.getText().trim().toUpperCase());
		}

		// Validation CPF
		if (Utils.ToCheckCPFNull(textField_CPF.getText())) {
			exception.addError("CPF", "Field can't be empty");
		} else if (textField_CPF.getText().length() < 14) {
			exception.addError("CPF", "Invalid CPF - Ex: > 14");
		} else {
			user.setCpf(textField_CPF.getText().trim().toUpperCase());
		}

		// Validation Phone
		if (Utils.ToCheckPhoneNull(textField_Phone.getText())) {
			exception.addError("phone", "Field can't be empty");
		} else if (textField_Phone.getText().length() < 14) {
			exception.addError("phone", "Invalid phone - Ex: > 14");
		} else {
			user.setPhone(textField_Phone.getText().trim().toUpperCase());
		}

		// Validation Email
		if (textField_Email.getText() == null || textField_Email.getText().trim().equals("")) {
			exception.addError("email", "Field can't be empty");
		} else if (textField_Email.getText().length() < 11) {
			exception.addError("email", "Invalid Email - Ex: > 11");
		} else if (Utils.ToCheckEmailNull(textField_Email.getText())) {
			exception.addError("email", "Invalid Domain - Ex: @MINSAIT.COM");
		} else {
			user.setEmail(textField_Email.getText().trim().toUpperCase());
		}

		// Validation Department
		if (comboBox_Department.getSelectedIndex() < 0 || comboBox_Department.getSelectedItem() == null) {
			exception.addError("department", "Field can't be empty");
		} else {
			user.setDepartment(comboBox_Department.getSelectedItem().toString());
		}

		// Validation Project
		if (comboBox_Project.getSelectedIndex() < 0 || comboBox_Project.getSelectedItem() == null) {
			exception.addError("project", "Field can't be empty");
		} else {
			user.setProject((Project) comboBox_Project.getSelectedItem());
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return user;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_Name.setText(fields.contains("name") ? errors.get("name") : "");
		labelError_CPF.setText(fields.contains("CPF") ? errors.get("CPF") : "");
		labelError_Phone.setText(fields.contains("phone") ? errors.get("phone") : "");
		labelError_Email.setText(fields.contains("email") ? errors.get("email") : "");
		labelError_Department.setText(fields.contains("department") ? errors.get("department") : "");
		labelError_Project.setText(fields.contains("project") ? errors.get("project") : "");
	}

	private void setErroMessagesDBException(DBException e) {
		if (e.getMessage().contains("Duplicate entry")) {
			if (e.getMessage().contains("users.cpf_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This CPF already exists", "Error update object",
						JOptionPane.ERROR_MESSAGE);
			} else if (e.getMessage().contains("users.email_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This email already exists", "Error update object",
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error update object",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error update object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
