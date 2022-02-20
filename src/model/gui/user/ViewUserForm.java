package model.gui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.entities.Item;
import model.entities.User;
import model.gui.change.ChangesPanel;
import model.gui.item.ItemList;

public class ViewUserForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private final int COLUMN1 = 20;
	private final int COLUMN2 = 120;

	private int line = 0;
	private int line_multiplier = 30;

	private final int WIDTH_LABEL = 80;
	private final int HEIGHT_LABEL = 30;

	private final int WIDTH_LABEL_SHOW = 300;
	private final int HEIGHT_LABEL_SHOW = 30;

	private final int widthPanel = WIDTH_LABEL + WIDTH_LABEL_SHOW + 50; // largura
	private final int heightPanel = (30 * 9) + 140; // altura

	private final Dimension DIMENSIONMAINPANEL = new Dimension(widthPanel, heightPanel);

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

	private void addLabelsShow(JPanel panel) {
		final JLabel label_Show_Registration = new JLabel(user.getRegistration());
		label_Show_Registration.setForeground(COLOR2);
		label_Show_Registration.setBounds(COLUMN2, line = 30, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Registration);

		final JLabel label_Show_Name = new JLabel(user.getName());
		label_Show_Name.setForeground(COLOR2);
		label_Show_Name.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Name);

		final JLabel label_Show_CPF = new JLabel(user.getCpf());
		label_Show_CPF.setForeground(COLOR2);
		label_Show_CPF.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_CPF);

		final JLabel label_Show_Phone = new JLabel(user.getPhone());
		label_Show_Phone.setForeground(COLOR2);
		label_Show_Phone.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Phone);

		final JLabel label_Show_Email = new JLabel(user.getEmail());
		label_Show_Email.setForeground(COLOR2);
		label_Show_Email.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Email);

		final JLabel label_Show_Department = new JLabel(user.getDepartment());
		label_Show_Department.setForeground(COLOR2);
		label_Show_Department.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Department);

		final JLabel label_Show_Project = new JLabel(user.getProject().getName());
		label_Show_Project.setForeground(COLOR2);
		label_Show_Project.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Project);
		
		final JLabel label_Show_Status = new JLabel(user.getStatus());
		label_Show_Status.setForeground(COLOR2);
		label_Show_Status.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(user.getDateEntry()));
		label_Show_DateEntry.setForeground(COLOR2);
		label_Show_DateEntry.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_DateEntry);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonItens = new JButton("Itens");
		buttonItens.setBounds(15, 330, 100, 25);
		buttonItens.addActionListener(new buttonItensListener());
		panel.add(buttonItens);

		final JButton buttonChanges = new JButton("Changes");
		buttonChanges.setBounds(130, 330, 90, 25);
		buttonChanges.addActionListener(new buttonChangesListener());
		panel.add(buttonChanges);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(235, 330, 120, 25);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonItensListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			final List<Item> itens = getItens();
			new ItemList(itens).setVisible(true);;
		}
	}

	private class buttonChangesListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			new ChangesPanel(user.getChanges());
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
	
	private List<Item> getItens() {
		final List<Item> itens = new ArrayList<Item>();
		
		return itens;
	}
}
