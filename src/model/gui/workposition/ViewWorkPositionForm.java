package model.gui.workposition;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.entities.WorkPosition;
import model.gui.change.ChangesPanel;
import model.gui.equipment.ViewEquipmentForm;
import model.gui.monitor.ViewMonitorForm;

public class ViewWorkPositionForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private final int COLUMN1 = 20;
	private final int COLUMN2 = 110;

	private int line = 0;
	private int line_multiplier = 30;

	private final int WIDTH_LABEL = 80;
	private final int HEIGHT_LABEL = 30;

	private final int WIDTH_LABEL_SHOW = 200;
	private final int HEIGHT_LABEL_SHOW = 30;

	private final int widthPanel = WIDTH_LABEL + WIDTH_LABEL_SHOW + 50; // largura
	private final int heightPanel = (30 * 9) + 140; // altura

	private final Dimension DIMENSIONMAINPANEL = new Dimension(widthPanel, heightPanel);

	private final int positionButton = (widthPanel / 2) - 140;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

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
		label_WorkPoint.setForeground(COLOR1);
		label_WorkPoint.setBounds(COLUMN1, line = 30, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_WorkPoint);

		final JLabel label_Location = new JLabel("Location:");
		label_Location.setForeground(COLOR1);
		label_Location.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Location);

		final JLabel label_Floor = new JLabel("Floor:");
		label_Floor.setForeground(COLOR1);
		label_Floor.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Floor);

		final JLabel label_NetPoint = new JLabel("NetPoint:");
		label_NetPoint.setForeground(COLOR1);
		label_NetPoint.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_NetPoint);

		final JLabel label_Status = new JLabel("Status:");
		label_Status.setForeground(COLOR1);
		label_Status.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Status);

		final JLabel label_DateEntry = new JLabel("Date Entry:");
		label_DateEntry.setForeground(COLOR1);
		label_DateEntry.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_DateEntry);

		final JLabel label_Equipment = new JLabel("Equipment:");
		label_Equipment.setForeground(COLOR1);
		label_Equipment.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Equipment);

		final JLabel label_Monitor1 = new JLabel("Monitor 1:");
		label_Monitor1.setForeground(COLOR1);
		label_Monitor1.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Monitor1);

		final JLabel label_Monitor2 = new JLabel("Monitor 2:");
		label_Monitor2.setForeground(COLOR1);
		label_Monitor2.setBounds(COLUMN1, line += line_multiplier, WIDTH_LABEL, HEIGHT_LABEL);
		panel.add(label_Monitor2);
	}

	private void addLabelsShow(JPanel panel) {
		final JLabel label_Show_WorkPoint = new JLabel(workPosition.getWorkPoint());
		label_Show_WorkPoint.setForeground(COLOR2);
		label_Show_WorkPoint.setBounds(COLUMN2, line = 30, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_WorkPoint);

		final JLabel label_Show_Location = new JLabel(workPosition.getLocation());
		label_Show_Location.setForeground(COLOR2);
		label_Show_Location.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Location);

		final JLabel label_Show_Floor = new JLabel(workPosition.getFloor());
		label_Show_Floor.setForeground(COLOR2);
		label_Show_Floor.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Floor);

		final JLabel label_Show_NetPoint = new JLabel(workPosition.getNetPoint());
		label_Show_NetPoint.setForeground(COLOR2);
		label_Show_NetPoint.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_NetPoint);

		final JLabel label_Show_Status = new JLabel(workPosition.getStatus());
		label_Show_Status.setForeground(COLOR2);
		label_Show_Status.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(workPosition.getDateEntry()));
		label_Show_DateEntry.setForeground(COLOR2);
		label_Show_DateEntry.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_DateEntry);

		final JLabel label_Show_Equipment = new JLabel(
				workPosition.getEquipment() != null ? workPosition.getEquipment().getSerialNumber() : "");
		label_Show_Equipment.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_Show_Equipment.setForeground(COLOR2);
		label_Show_Equipment.addMouseListener(new MouseListenerEquipment());
		label_Show_Equipment.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Equipment);

		final JLabel label_Show_Monitor1 = new JLabel(
				workPosition.getMonitor1() != null ? workPosition.getMonitor1().getSerialNumber() : "");
		label_Show_Monitor1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_Show_Monitor1.setForeground(COLOR2);
		label_Show_Monitor1.addMouseListener(new MouseListenerMonitor1());
		label_Show_Monitor1.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Monitor1);

		final JLabel label_Show_Monitor2 = new JLabel(
				workPosition.getMonitor2() != null ? workPosition.getMonitor2().getSerialNumber() : "");
		label_Show_Monitor2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_Show_Monitor2.setForeground(COLOR2);
		label_Show_Monitor2.addMouseListener(new MouseListenerMonitor2());
		label_Show_Monitor2.setBounds(COLUMN2, line += line_multiplier, WIDTH_LABEL_SHOW, HEIGHT_LABEL_SHOW);
		panel.add(label_Show_Monitor2);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonChanges = new JButton("Changes");
		buttonChanges.setBounds(positionButton, 330, 120, 30);
		buttonChanges.addActionListener(new buttonChangesListener());
		panel.add(buttonChanges);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(positionButton + 160, 330, 120, 30);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonChangesListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			new ChangesPanel(workPosition.getChanges());
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	private class MouseListenerEquipment extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 1) {
				String equipment = workPosition.getEquipment().toString();
				if (equipment != null) {
					new ViewEquipmentForm(workPosition.getEquipment()).setVisible(true);
				}
			}
		}
	}

	private class MouseListenerMonitor1 extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 1) {
				String monitor1 = workPosition.getMonitor1().toString();
				if (monitor1 != null) {
					new ViewMonitorForm(workPosition.getMonitor1()).setVisible(true);
				}
			}
		}
	}

	private class MouseListenerMonitor2 extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 1) {
				String monitor2 = workPosition.getMonitor2().toString();
				if (monitor2 != null) {
					new ViewMonitorForm(workPosition.getMonitor2()).setVisible(true);
				}
			}
		}
	}
}
