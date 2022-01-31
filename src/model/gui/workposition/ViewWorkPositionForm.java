package model.gui.workposition;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.entities.WorkPosition;
import model.gui.change.ChangesPanel;

public class ViewWorkPositionForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 170;

	private static final int WIDTH = 150;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 390);

	private WorkPosition workPosition;

	public ViewWorkPositionForm(WorkPosition workPosition) {
		this.workPosition = workPosition;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("View WorkPosition");
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
		final JLabel label_WorkPoint = new JLabel("Work Point:");
		label_WorkPoint.setBounds(COLUMN1, 10, WIDTH, HEIGHT);
		panel.add(label_WorkPoint);

		final JLabel label_Location = new JLabel("Location:");
		label_Location.setBounds(COLUMN1, 50, WIDTH, HEIGHT);
		panel.add(label_Location);

		final JLabel label_Floor = new JLabel("Floor:");
		label_Floor.setBounds(COLUMN1, 90, WIDTH, HEIGHT);
		panel.add(label_Floor);

		final JLabel label_NetPoint = new JLabel("NetPoint:");
		label_NetPoint.setBounds(COLUMN1, 130, WIDTH, HEIGHT);
		panel.add(label_NetPoint);

		final JLabel label_Status = new JLabel("Status:");
		label_Status.setBounds(COLUMN1, 170, WIDTH, HEIGHT);
		panel.add(label_Status);

		final JLabel label_DateEntry = new JLabel("DateEntry:");
		label_DateEntry.setBounds(COLUMN1, 210, WIDTH, HEIGHT);
		panel.add(label_DateEntry);

		final JLabel label_Reason = new JLabel("Reason:");
		label_Reason.setBounds(COLUMN1, 250, WIDTH, HEIGHT);
		panel.add(label_Reason);
	}

	private void addLabelsShow(JPanel panel) {
		final JLabel label_WorkPoint = new JLabel(workPosition.getWorkPoint());
		label_WorkPoint.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(label_WorkPoint);

		final JLabel label_Show_Location = new JLabel(workPosition.getLocation());
		label_Show_Location.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(label_Show_Location);

		final JLabel label_Show_Floor = new JLabel(workPosition.getFloor());
		label_Show_Floor.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(label_Show_Floor);

		final JLabel label_Show_NetPoint = new JLabel(workPosition.getNetPoint());
		label_Show_NetPoint.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(label_Show_NetPoint);

		final JLabel label_Show_Status = new JLabel(workPosition.getStatus());
		label_Show_Status.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(workPosition.getDateEntry()));
		label_Show_DateEntry.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(label_Show_DateEntry);

		final JLabel label_Show_Reason = new JLabel(workPosition.getReason());
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
			new ChangesPanel(workPosition.getChanges()).setVisible(true);
			;
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
}
