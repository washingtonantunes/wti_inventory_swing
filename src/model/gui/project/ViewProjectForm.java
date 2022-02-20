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

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private final int COLUMN1 = 40;
	private final int COLUMN2 = 150;

	private int line = 0;
	private int line_multiplier = 30;

	private final int WIDTH_LABEL = 80;
	private final int HEIGHT_LABEL = 30;

	private final int WIDTH_LABEL_SHOW = 200;
	private final int HEIGHT_LABEL_SHOW = 30;

	private final int widthPanel = WIDTH_LABEL + WIDTH_LABEL_SHOW + 100; // largura
	private final int heightPanel = (30 * 5) + 140; // altura

	private final Dimension DIMENSIONMAINPANEL = new Dimension(widthPanel, heightPanel);

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

	private void addLabelsShow(JPanel panel) {
		final JLabel label_CostCenter = new JLabel(project.getCostCenter());
		label_CostCenter.setForeground(COLOR2);
		label_CostCenter.setBounds(COLUMN2, line = 30, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_CostCenter);

		final JLabel label_Show_Name = new JLabel(project.getName());
		label_Show_Name.setForeground(COLOR2);
		label_Show_Name.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Name);

		final JLabel label_Show_City = new JLabel(project.getCity());
		label_Show_City.setForeground(COLOR2);
		label_Show_City.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_City);

		final JLabel label_Show_Status = new JLabel(project.getStatus());
		label_Show_Status.setForeground(COLOR2);
		label_Show_Status.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(project.getDateEntry()));
		label_Show_DateEntry.setForeground(COLOR2);
		label_Show_DateEntry.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_DateEntry);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonDashboard = new JButton("Dashboard");
		buttonDashboard.setBounds(15, 210, 100, 25);
		buttonDashboard.addActionListener(new buttonDashboardListener());
		panel.add(buttonDashboard);

		final JButton buttonChanges = new JButton("Changes");
		buttonChanges.setBounds(130, 210, 90, 25);
		buttonChanges.addActionListener(new buttonChangesListener());
		panel.add(buttonChanges);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(235, 210, 120, 25);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}
	
	private class buttonDashboardListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			System.out.println("buttonDashboardListener");
		}
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
