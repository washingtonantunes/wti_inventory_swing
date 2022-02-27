package model.gui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
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
import model.entities.Project;
import model.entities.User;
import model.services.user.UserService;
import model.services.user.UserTableModel;
import model.util.MyButton;
import model.util.MyComboBox;
import model.util.MyLabel;
import model.util.MyTextField;
import model.util.Utils;

public class EditUserForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private final int SIZE_LABELS = 1;
	private final int SIZE_LABELS_SHOW = 3;
	private final int SIZE_FIELDS_COMBOX = 3;
	private final int SIZE_LABELS_ERROR = 5;

	private final int SIZEBUTTONS = 1;

	private final int COLOR_LABEL = 1;
	private final int COLOR_LABEL_SHOW = 2;
	private final int COLOR_LABEL_ERROR = 3;
	
	private final int FONT = 1;

	private final int WIDTH_INTERNAL_PANEL = (100 + 200 + 275) + 40;

	private final int HEIGHT_TOP_PANEL = 10;
	private final int HEIGHT_FIELD_PANEL = 42 * 9;
	private final int HEIGHT_BUTTON_PANEL = 50;

	private final int WIDTH_MAIN_PANEL = WIDTH_INTERNAL_PANEL + 50;
	private final int HEIGHT_MAIN_PANEL = HEIGHT_FIELD_PANEL + HEIGHT_BUTTON_PANEL + 84;

	private UserTableModel model;
	private final User userOld;
	private final int lineSelected;

	private JTextField textField_Name;
	private JTextField textField_CPF;
	private JTextField textField_Phone;
	private JTextField textField_Email;
	private JComboBox<Object> comboBox_Department;
	private JComboBox<Object> comboBox_Project;

	private JLabel labelError_Name;
	private JLabel labelError_CPF;
	private JLabel labelError_Phone;
	private JLabel labelError_Email;
	private JLabel labelError_Department;
	private JLabel labelError_Project;

	public EditUserForm(UserTableModel model, User userOld, int lineSelected) {
		this.model = model;
		this.userOld = userOld;
		this.lineSelected = lineSelected;
		initComponents();
	}

	private void initComponents() {
		setTitle("Edit User");
		setModal(true);
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
		setPreferredSize(new Dimension(WIDTH_MAIN_PANEL, HEIGHT_MAIN_PANEL));
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		add(createTopPanel());
		add(createFieldsPanel());
		add(createButtonPanel());

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel createTopPanel() {
		final JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_TOP_PANEL));
		panel.setBackground(COLOR1);

		return panel;
	}

	private JPanel createFieldsPanel() {
		final JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		fieldsPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_FIELD_PANEL));

		final JLabel label_Registration = new MyLabel("Registration:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Registration);

		final JLabel label_Show_Registration = new MyLabel(userOld.getRegistration(), SIZE_LABELS_SHOW,
				COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Registration);

		final JLabel labelError_Registration_Empty = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Registration_Empty);

		final JLabel label_Name = new MyLabel("Name:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Name);

		textField_Name = new MyTextField(userOld.getName(), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(textField_Name);

		labelError_Name = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Name);

		final JLabel label_CPF = new MyLabel("CPF:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_CPF);

		textField_CPF = new MyTextField(userOld.getCpf(), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(textField_CPF);

		labelError_CPF = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_CPF);

		final JLabel label_Phone = new MyLabel("Phone:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Phone);

		textField_Phone = new MyTextField(userOld.getPhone(), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(textField_Phone);

		labelError_Phone = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Phone);

		final JLabel label_Email = new MyLabel("Email:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Email);

		textField_Email = new MyTextField(userOld.getEmail(), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(textField_Email);

		labelError_Email = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Email);

		final JLabel label_Department = new MyLabel("Department:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Department);

		comboBox_Department = new MyComboBox(
				UserList.options.stream()
						.filter(o -> o.getType().equals("DEPARTMENT") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList()),
				userOld.getDepartment(), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(comboBox_Department);

		labelError_Department = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Department);

		final JLabel label_Project = new MyLabel("Project:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Project);

		comboBox_Project = new MyComboBox(
				UserList.projects.stream().filter(p -> p.getStatus().equals("ACTIVE")).collect(Collectors.toList()),
				userOld.getProject(), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(comboBox_Project);

		labelError_Project = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Project);

		JLabel label_Status = new MyLabel("Status:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Status);

		final JLabel label_Show_Status = new MyLabel(userOld.getStatus(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Status);

		final JLabel labelError_Status_Empty = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Status_Empty);

		JLabel label_DateEntry = new MyLabel("DateEntry:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_DateEntry);

		final JLabel label_Show_DateEntry = new MyLabel(sdf.format(userOld.getDateEntry()), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_DateEntry);

		final JLabel labelError_DateEntry_Empty = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_DateEntry_Empty);

		return fieldsPanel;
	}

	private JPanel createButtonPanel() {
		final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		buttonPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_BUTTON_PANEL));
		buttonPanel.setBackground(COLOR2);

		final JButton buttonSave = new MyButton("Save", SIZEBUTTONS);
		buttonSave.addActionListener(new buttonSaveListener());
		buttonPanel.add(buttonSave);

		final JButton buttonClose = new MyButton("Close", SIZEBUTTONS);
		buttonClose.addActionListener(new buttonCloseListener());
		buttonPanel.add(buttonClose);

		return buttonPanel;
	}

	private class buttonSaveListener implements ActionListener {

		@Override
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
				setErrorMessagesDBException(e);
			} catch (ObjectException e) {
				if (e.getMessage().contains("There is no change")) {
					JOptionPane.showMessageDialog(rootPane, "There is no change", "Error updating object",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private class buttonCloseListener implements ActionListener {

		@Override
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

		// Insert Status
		user.setStatus("ACTIVE");

		// Insert DateEntry
		user.setDateEntry(new Date());

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

	private void setErrorMessagesDBException(DBException e) {
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
