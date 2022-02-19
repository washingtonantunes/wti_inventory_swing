package model.gui.project;

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

import model.entities.Project;
import model.gui.change.ChangesPanel;

public class ViewProjectForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 170;

	private static final int WIDTH = 150;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 390);
	
	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private Project project;

	public ViewProjectForm(Project project) {
		this.project = project;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("View Project");
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

		final JLabel label_Status = new JLabel("Status:");
		label_Status.setForeground(COLOR1);
		label_Status.setBounds(COLUMN1, 170, WIDTH, HEIGHT);
		panel.add(label_Status);

		final JLabel label_DateEntry = new JLabel("DateEntry:");
		label_DateEntry.setForeground(COLOR1);
		label_DateEntry.setBounds(COLUMN1, 210, WIDTH, HEIGHT);
		panel.add(label_DateEntry);

		final JLabel label_Reason = new JLabel("Reason:");
		label_Reason.setForeground(COLOR1);
		label_Reason.setBounds(COLUMN1, 250, WIDTH, HEIGHT);
		panel.add(label_Reason);
	}

	private void addLabelsShow(JPanel panel) {
//		final JLabel label_Show_ID = new JLabel(project.getId().toString());
//		label_Show_ID.setForeground(COLOR2);
//		label_Show_ID.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
//		panel.add(label_Show_ID);

		final JLabel label_Show_Name = new JLabel(project.getName());
		label_Show_Name.setForeground(COLOR2);
		label_Show_Name.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(label_Show_Name);

		final JLabel label_Show_City = new JLabel(project.getCity());
		label_Show_City.setForeground(COLOR2);
		label_Show_City.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(label_Show_City);

		final JLabel label_CostCenter = new JLabel(project.getCostCenter());
		label_CostCenter.setForeground(COLOR2);
		label_CostCenter.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(label_CostCenter);

		final JLabel label_Show_Status = new JLabel(project.getStatus());
		label_Show_Status.setForeground(COLOR2);
		label_Show_Status.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(project.getDateEntry()));
		label_Show_DateEntry.setForeground(COLOR2);
		label_Show_DateEntry.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(label_Show_DateEntry);

		final JLabel label_Show_Reason = new JLabel(project.getReason());
		label_Show_Reason.setForeground(COLOR2);
		label_Show_Reason.setBounds(COLUMN2, 250, WIDTH, HEIGHT);
		panel.add(label_Show_Reason);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonChanges = new JButton("Changes");
		buttonChanges.setBounds(180, 310, WIDTH - 30, HEIGHT);
		buttonChanges.addActionListener(new buttonChangesListener());
		panel.add(buttonChanges);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(320, 310, WIDTH - 30, HEIGHT);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonChangesListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			new ChangesPanel(project.getChanges()).setVisible(true);
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
}
