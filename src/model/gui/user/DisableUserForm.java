package model.gui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db.DBException;
import exception.ValidationException;
import model.entities.Option;
import model.entities.User;
import model.services.user.UserService;
import model.services.user.UserTableModel;
import model.util.MyButton;
import model.util.MyComboBox;
import model.util.MyLabel;

public class DisableUserForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private final int SIZE_LABELS = 1; 
	private final int SIZE_FIELDS_COMBOX = 2; 
	private final int SIZE_LABELS_ERROR = 4; 

	private final int SIZEBUTTONS = 1; 

	private final int COLOR_LABEL = 1; 
	private final int COLOR_LABEL_ERROR = 3; 

	private final int WIDTH_INTERNAL_PANEL = (100 + 150 + 250) + 40; 

	private final int HEIGHT_TOP_PANEL = 10; 
	private final int HEIGHT_FIELD_PANEL = 42 * 1; 
	private final int HEIGHT_BUTTON_PANEL = 50; 

	private final int WIDTH_MAIN_PANEL = WIDTH_INTERNAL_PANEL + 50; 
	private final int HEIGHT_MAIN_PANEL = HEIGHT_FIELD_PANEL + HEIGHT_BUTTON_PANEL + 64 + 20; 

	private UserTableModel model;
	private User user;
	private final int lineSelected;
	
	private JComboBox<Object> comboBox_Reason;

	private JLabel labelError_Reason;

	public DisableUserForm(UserTableModel model, User user, int lineSelected) {
		this.model = model;
		this.user = user;
		this.lineSelected = lineSelected;
		initComponents();
	}
	
	private void initComponents() {
		setTitle("Disable User");
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
	}
	
	private JPanel createTopPanel() {
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_TOP_PANEL));
		buttonPanel.setBackground(COLOR1);

		return buttonPanel;
	}

	private JPanel createFieldsPanel() {
		final JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		fieldsPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_FIELD_PANEL));

		final JLabel label_Registration = new MyLabel("Reason:", SIZE_LABELS, COLOR_LABEL);
		fieldsPanel.add(label_Registration);

		comboBox_Reason = new MyComboBox(UserList.options.stream().filter(o -> o.getType().equals("REASON-USER") && o.getStatus().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList()), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(comboBox_Reason);

		labelError_Reason = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR);
		fieldsPanel.add(labelError_Reason);	

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
				user = getFormData();
				UserService service = new UserService();
				service.disable(user);
				model.updateUser(lineSelected, user);
				dispose();
				JOptionPane.showMessageDialog(rootPane, "User successfully disabled", "Success disabling object", JOptionPane.INFORMATION_MESSAGE);
			} catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			} catch (DBException e) {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error disabling object", JOptionPane.ERROR_MESSAGE);
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
		User user = this.user;

		ValidationException exception = new ValidationException("Validation error");

		// Validation Reason
		if (comboBox_Reason.getSelectedIndex() < 0 || comboBox_Reason.getSelectedItem() == null) {
			exception.addError("reason", "It is necessary to select a reason!");
		} 
		else {
			user.setReason(comboBox_Reason.getSelectedItem().toString());
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		// Insert Status
		user.setStatus("DISABLED");

		return user;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_Reason.setText(fields.contains("reason") ? errors.get("reason") : "");
	}
}
