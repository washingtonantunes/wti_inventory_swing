package model.gui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.entities.User;
import model.gui.change.ChangesPanel;

public class ViewUserForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 120;

	private static final int WIDTH = 200;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 510);
	
	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private User user;

	public ViewUserForm(User user) {
		this.user = user;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("View User");
		setPreferredSize(DIMENSIONMAINPANEL);
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createPanelMain() {
		final JPanel panel = new JPanel(new FlowLayout());
		panel.setLayout(null);

		addLabels(panel);
		addLabelsShow(panel);
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

		final JLabel label_Reason = new JLabel("Reason:");
		label_Reason.setForeground(COLOR1);
		label_Reason.setBounds(COLUMN1, 370, WIDTH, HEIGHT);
		panel.add(label_Reason);
	}

	private void addLabelsShow(JPanel panel) {
		final JLabel label_Show_Registration = new JLabel(user.getRegistration());
		label_Show_Registration.setForeground(COLOR2);
		label_Show_Registration.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(label_Show_Registration);

		final JLabel label_Show_Name = new JLabel(user.getName());
		label_Show_Name.setForeground(COLOR2);
		label_Show_Name.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(label_Show_Name);
		
		final JLabel label_Show_Phone = new JLabel(user.getPhone());
		label_Show_Phone.setForeground(COLOR2);
		label_Show_Phone.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(label_Show_Phone);

		final JLabel label_Show_CPF = new JLabel(user.getCPF());
		label_Show_CPF.setForeground(COLOR2);
		label_Show_CPF.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(label_Show_CPF);

		final JLabel label_Show_Project = new JLabel(user.getProject());
		label_Show_Project.setForeground(COLOR2);
		label_Show_Project.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(label_Show_Project);
		
		final JLabel label_Show_Email = new JLabel(user.getEmail());
		label_Show_Email.setForeground(COLOR2);
		label_Show_Email.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(label_Show_Email);
		
		final JLabel label_Show_Department = new JLabel(user.getDepartment());
		label_Show_Department.setForeground(COLOR2);
		label_Show_Department.setBounds(COLUMN2, 250, WIDTH, HEIGHT);
		panel.add(label_Show_Department);

		final JLabel label_Show_Status = new JLabel(user.getStatus());
		label_Show_Status.setForeground(COLOR2);
		label_Show_Status.setBounds(COLUMN2, 290, WIDTH, HEIGHT);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(user.getDateEntry()));
		label_Show_DateEntry.setForeground(COLOR2);
		label_Show_DateEntry.setBounds(COLUMN2, 330, WIDTH, HEIGHT);
		panel.add(label_Show_DateEntry);

		final JLabel label_Show_Reason = new JLabel(user.getReason());
		label_Show_Reason.setForeground(COLOR2);
		label_Show_Reason.setBounds(COLUMN2, 370, WIDTH, HEIGHT);
		panel.add(label_Show_Reason);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonChanges = new JButton("Changes");
		buttonChanges.setBounds(180, 430, 120, 25);
		buttonChanges.addActionListener(new buttonChangesListener());
		panel.add(buttonChanges);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(320, 430, 120, 25);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonChangesListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			new ChangesPanel(user.getChanges()).setVisible(true);
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
}
