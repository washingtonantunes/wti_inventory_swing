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
import exception.ValidationException;
import model.entities.User;
import model.entities.Option;
import model.services.user.UserService;
import model.services.user.UserTableModel;
import model.util.JTextFieldFilter;
import model.util.Utils;

public class EditUserForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 120;
	private static final int COLUMN3 = 330;

	private static final int WIDTH = 200;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 470);
	
	private final Color COLOR1 = new Color(0, 65, 83);

	private JTextField textField_Name;
	private JTextField textField_CPF;
	private JTextField textField_Phone;
	private JComboBox<String> comboBox_Project;
	private JTextField textField_Email;
	private JComboBox<String> comboBox_Department;

	private JLabel labelError_Name;
	private JLabel labelError_CPF;
	private JLabel labelError_Phone;
	private JLabel labelError_Project;
	private JLabel labelError_Email;
	private JLabel labelError_Department;

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
		label_Registration.setBounds(COLUMN1, 10, WIDTH, HEIGHT);
		panel.add(label_Registration);

		final JLabel label_Name = new JLabel("Name:");
		label_Name.setForeground(COLOR1);
		label_Name.setBounds(COLUMN1, 50, WIDTH, HEIGHT);
		panel.add(label_Name);
		
		final JLabel label_CPF = new JLabel("CPF:");
		label_CPF.setForeground(COLOR1);
		label_CPF.setBounds(COLUMN1, 90, WIDTH, HEIGHT);
		panel.add(label_CPF);
		
		final JLabel label_Phone = new JLabel("Phone:");
		label_Phone.setForeground(COLOR1);
		label_Phone.setBounds(COLUMN1, 130, WIDTH, HEIGHT);
		panel.add(label_Phone);

		final JLabel label_Project = new JLabel("Project:");
		label_Project.setForeground(COLOR1);
		label_Project.setBounds(COLUMN1, 170, WIDTH, HEIGHT);
		panel.add(label_Project);
		
		final JLabel label_Email = new JLabel("Email:");
		label_Email.setForeground(COLOR1);
		label_Email.setBounds(COLUMN1, 210, WIDTH, HEIGHT);
		panel.add(label_Email);

		final JLabel label_Department = new JLabel("Department:");
		label_Department.setForeground(COLOR1);
		label_Department.setBounds(COLUMN1, 250, WIDTH, HEIGHT);
		panel.add(label_Department);

		JLabel label_Status = new JLabel("Status:");
		label_Status.setForeground(COLOR1);
		label_Status.setBounds(COLUMN1, 290, WIDTH, HEIGHT);
		panel.add(label_Status);

		JLabel label_DateEntry = new JLabel("DateEntry:");
		label_DateEntry.setForeground(COLOR1);
		label_DateEntry.setBounds(COLUMN1, 330, WIDTH, HEIGHT);
		panel.add(label_DateEntry);
	}

	private void addTextFieldsAndComboBoxes(JPanel panel) {
		try {
		final JLabel label_Show_Registration = new JLabel(userOld.getRegistration());
		label_Show_Registration.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(label_Show_Registration);

		textField_Name = new JTextField();
		textField_Name.setDocument(new JTextFieldFilter(JTextFieldFilter.NAME, 50));
		textField_Name.setText(userOld.getName());
		textField_Name.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(textField_Name);
		
		textField_CPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		textField_CPF.setText(userOld.getCPF());
		textField_CPF.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(textField_CPF);
		
		textField_Phone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
		textField_Phone.setText(userOld.getPhone());
		textField_Phone.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(textField_Phone);

		comboBox_Project = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Project.addItem("TECNOLOGIA");
		comboBox_Project.setSelectedItem(userOld.getProject());
		comboBox_Project.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(comboBox_Project);
		
		textField_Email = new JTextField();
		textField_Email.setDocument(new JTextFieldFilter(JTextFieldFilter.MYEMAIL, 50));
		textField_Email.setText(userOld.getEmail());
		textField_Email.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(textField_Email);

		comboBox_Department = new JComboBox<>(new Vector<>(
				options.stream().filter(o -> o.getType().equals("DEPARTMENT") && o.getStatus().equals("ACTIVE"))
						.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Department.setSelectedItem(userOld.getDepartment());;
		comboBox_Department.setBounds(COLUMN2, 250, WIDTH, HEIGHT);
		panel.add(comboBox_Department);

		final JLabel label_Show_Status = new JLabel(userOld.getStatus());
		label_Show_Status.setBounds(COLUMN2, 290, WIDTH, HEIGHT);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(userOld.getDateEntry()));
		label_Show_DateEntry.setBounds(COLUMN2, 330, WIDTH, HEIGHT);
		panel.add(label_Show_DateEntry);
		}
		catch(ParseException e) {
			
		}
	}

	private void addLabelsError(JPanel panel) {
		labelError_Name = new JLabel();
		labelError_Name.setForeground(Color.RED);
		labelError_Name.setBounds(COLUMN3, 50, WIDTH + 90, HEIGHT);
		panel.add(labelError_Name);

		labelError_CPF = new JLabel();
		labelError_CPF.setForeground(Color.RED);
		labelError_CPF.setBounds(COLUMN3, 90, WIDTH + 90, HEIGHT);
		panel.add(labelError_CPF);
		
		labelError_Phone = new JLabel();
		labelError_Phone.setForeground(Color.RED);
		labelError_Phone.setBounds(COLUMN3, 130, WIDTH + 90, HEIGHT);
		panel.add(labelError_Phone);

		labelError_Project = new JLabel();
		labelError_Project.setForeground(Color.RED);
		labelError_Project.setBounds(COLUMN3, 170, WIDTH + 90, HEIGHT);
		panel.add(labelError_Project);

		labelError_Email = new JLabel();
		labelError_Email.setForeground(Color.RED);
		labelError_Email.setBounds(COLUMN3, 210, WIDTH + 90, HEIGHT);
		panel.add(labelError_Email);

		labelError_Department = new JLabel();
		labelError_Department.setForeground(Color.RED);
		labelError_Department.setBounds(COLUMN3, 250, WIDTH + 90, HEIGHT);
		panel.add(labelError_Department);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(180, 390, 120, 25);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(320, 390, 120, 25);
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
				JOptionPane.showMessageDialog(rootPane, "User successfully updated", "Success updating object", JOptionPane.INFORMATION_MESSAGE);
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

	private User getFormData() {
		User user = new User();
		user = (User) userOld.clone();

		ValidationException exception = new ValidationException("Validation error");

		// Validation Name
		if (textField_Name.getText() == null || textField_Name.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		} 
		else if (textField_Name.getText().length() < 10) {
			exception.addError("name", "Invalid Name - Ex: > 10");
		} 
		else {
			user.setName(textField_Name.getText().trim().toUpperCase());
		}

		// Validation CPF
		if (Utils.ToCheckCPF(textField_CPF.getText())) {
			exception.addError("CPF", "Field can't be empty");
		} 
		else if (textField_CPF.getText().length() < 14) {
			exception.addError("CPF", "Invalid CPF - Ex: > 14");
		} 
		else {
			user.setCPF(textField_CPF.getText().trim().toUpperCase());
		}

		// Validation Phone
		if (Utils.ToCheckPhone(textField_Phone.getText())) {
			exception.addError("phone", "Field can't be empty");
		} 
		else if (textField_Phone.getText().length() < 14) {
			exception.addError("phone", "Invalid phone - Ex: > 14");
		} 
		else {
			user.setPhone(textField_Phone.getText().trim().toUpperCase());
		}

		// Validation Project
		if (comboBox_Project.getSelectedIndex() < 0 || comboBox_Project.getSelectedItem() == null) {
			exception.addError("project", "Field can't be empty");
		} 
		else {
			user.setProject(comboBox_Project.getSelectedItem().toString());
		}

		// Validation Email
		if (textField_Email.getText() == null || textField_Email.getText().trim().equals("")) {
			exception.addError("email", "Field can't be empty");
		} 
		else if (textField_Email.getText().length() < 11) {
			exception.addError("email", "Invalid Email - Ex: > 11");
		} 
		else if (Utils.ToCheckEmail(textField_Email.getText())) {
			exception.addError("email", "Invalid Domain - Ex: @MINSAIT.COM");
		} 
		else {
			user.setEmail(textField_Email.getText().trim().toUpperCase());
		}

		// Validation Department
		if (comboBox_Department.getSelectedIndex() < 0 || comboBox_Department.getSelectedItem() == null) {
			exception.addError("department", "Field can't be empty");
		} 
		else {
			user.setDepartment(comboBox_Department.getSelectedItem().toString());
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
		labelError_Project.setText(fields.contains("project") ? errors.get("project") : "");
		labelError_Email.setText(fields.contains("email") ? errors.get("email") : "");
		labelError_Department.setText(fields.contains("department") ? errors.get("department") : "");
	}

	private void setErroMessagesDBException(DBException e) {
		if (e.getMessage().contains("Duplicate entry")) {
			if (e.getMessage().contains("users.cpf_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This CPF already exists", "Error saving object", JOptionPane.ERROR_MESSAGE);
			} 
			else if (e.getMessage().contains("users.email_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This email already exists", "Error saving object", JOptionPane.ERROR_MESSAGE);
			} 
			else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
			}
		} 
		else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
