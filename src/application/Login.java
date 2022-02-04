package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import exception.ValidationException;
import model.entities.Collaborator;
import model.gui.MainWindow;
import model.services.collaborator.CollaboratorService;

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Color COLOR1 = new Color(0, 65, 83);

	private JTextField textField_Login;
	private JPasswordField passwordField_Password;

	private JLabel labelError_Login;

	private Map<String, Collaborator> collaborators;
	private Collaborator adminRoot = new Collaborator("Administrador", "adminroot", "P@ssw0rd", 0, "Administrador",
			"ATIVO", null);
	private Collaborator collaboratorToValidate;
	private Collaborator collaboratorValidated;

	public Login() {
		collaborators = loadDataCollaboratos();
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("wTI Inventory");
		setPreferredSize(new Dimension(500, 460));
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createPanelMain() {
		final JPanel panel = new JPanel(new FlowLayout());
		panel.setLayout(null);

		addLabels(panel);
		addTextFields(panel);
		addLabelsError(panel);
		addButtons(panel);

		return panel;
	}

	private void addLabels(JPanel panel) {
		final JLabel label_Icon = new JLabel(new ImageIcon(getClass().getResource("/model/icon/iconMain.jpg")));
		label_Icon.setBounds(90, 40, 300, 180);
		panel.add(label_Icon);

		final JLabel label_Login = new JLabel("Login:");
		label_Login.setForeground(COLOR1);
		label_Login.setBounds(60, 250, 130, 30);
		panel.add(label_Login);

		final JLabel label_Password = new JLabel("Password:");
		label_Password.setForeground(COLOR1);
		label_Password.setBounds(300, 250, 130, 30);
		panel.add(label_Password);
	}

	private void addTextFields(JPanel panel) {
		textField_Login = new JTextField();
		textField_Login.setBounds(60, 280, 130, 30);
		panel.add(textField_Login);

		passwordField_Password = new JPasswordField();
		passwordField_Password.setBounds(300, 280, 130, 30);
		panel.add(passwordField_Password);
	}

	private void addLabelsError(JPanel panel) {
		labelError_Login = new JLabel();
		labelError_Login.setForeground(Color.RED);
		labelError_Login.setBounds(90, 320, 300, 30);
		panel.add(labelError_Login);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonEnter = new JButton("Enter");
		buttonEnter.setBounds(90, 360, 130, 30);
		buttonEnter.addActionListener(new buttonEnterListener());
		panel.add(buttonEnter);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(270, 360, 130, 30);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private Map<String, Collaborator> loadDataCollaboratos() {
		final CollaboratorService service = new CollaboratorService();
		Map<String, Collaborator> list = service.findAll();
		return list;
	}

	public Map<String, Collaborator> getCollaborators() {
		return collaborators;
	}

	private class buttonEnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				collaboratorToValidate = getFormData();
				Enter();
			} 
			catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			}
		}
	}

	private class buttonCloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			Runtime.getRuntime().exit(0);
		}
	}

	private Collaborator getFormData() {
		Collaborator collaborator = new Collaborator();

		ValidationException exception = new ValidationException("Validation error");

		// validation Login
		if (textField_Login.getText() == null || textField_Login.getText().trim().equals("")) {
			exception.addError("login", "Login and Password fields can't be empty");
		} 
		else if (textField_Login.getText().length() < 6) {
			exception.addError("login", "Invalid login - Ex: > 6");
		} 
		else {
			collaborator.setRegistration(textField_Login.getText().trim());
		}

		// Validation Password
		if (String.copyValueOf(passwordField_Password.getPassword()) == null
				|| String.copyValueOf(passwordField_Password.getPassword()).trim().equals("")) {
			exception.addError("login", "Login and Password fields can't be empty");
		} 
		else if (String.copyValueOf(passwordField_Password.getPassword()).length() < 4) {
			exception.addError("login", "Invalid Patrimony Number - Ex: > 4");
		} 
		else {
			collaborator.setPassword(String.copyValueOf(passwordField_Password.getPassword()));
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return collaborator;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_Login.setText(fields.contains("login") ? errors.get("login") : "");
	}

	private void Enter() {
		ValidationException exception = new ValidationException("Validation error");

		collaboratorValidated = collaborators.get(collaboratorToValidate.getRegistration());

		if (adminRoot.getRegistration().equals(collaboratorToValidate.getRegistration())) {
			if (adminRoot.getPassword().equals(collaboratorToValidate.getPassword())) {
				new MainWindow().setVisible(true);
				dispose();
			} 
			else {
				exception.addError("login", "Invalid password");
			}
		} 
		else {
			if (collaboratorValidated == null) {
				exception.addError("login", "Collaborator not registered");
			} 
			else if (collaboratorValidated.getStatus().equals("DISABLED")) {
				exception.addError("login", "Disabled collaborator");
			} 
			else if (collaboratorValidated.getPassword().equals(collaboratorToValidate.getPassword())) {
				new MainWindow().setVisible(true);
				dispose();

			} 
			else {
				exception.addError("login", "Invalid password");
			}
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
	}
}
