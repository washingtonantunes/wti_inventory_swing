package model.gui.monitor;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.entities.Monitor;
import model.gui.change.ChangesPanel;


public class ViewMonitorForm extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 170;

	private static final int WIDTH = 150;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 390);

	private Monitor monitor;

	public ViewMonitorForm(Monitor monitor) {
		this.monitor = monitor;
		initComponents();
	}
	
	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("View Monitor");
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
		final JLabel label_SerialNumber = new JLabel("Serial Number:");
		label_SerialNumber.setBounds(COLUMN1, 10, WIDTH, HEIGHT);
		panel.add(label_SerialNumber);
		
		final JLabel label_PatrimonyNumber = new JLabel("PatrimonyNumber:");
		label_PatrimonyNumber.setBounds(COLUMN1, 50, WIDTH, HEIGHT);
		panel.add(label_PatrimonyNumber);

		final JLabel label_Brand = new JLabel("Brand:");
		label_Brand.setBounds(COLUMN1, 90, WIDTH, HEIGHT);
		panel.add(label_Brand);
		
		final JLabel label_Model = new JLabel("Model:");
		label_Model.setBounds(COLUMN1, 130, WIDTH, HEIGHT);
		panel.add(label_Model);
		
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
		final JLabel label_Show_SerialNumber = new JLabel(monitor.getSerialNumber());
		label_Show_SerialNumber.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(label_Show_SerialNumber);

		final JLabel label_Show_PatrimonyNumber = new JLabel(monitor.getPatrimonyNumber());
		label_Show_PatrimonyNumber.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(label_Show_PatrimonyNumber);
		
		final JLabel label_Show_Brand = new JLabel(monitor.getBrand());
		label_Show_Brand.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(label_Show_Brand);
		
		final JLabel label_Show_Model = new JLabel(monitor.getModel());
		label_Show_Model.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(label_Show_Model);
		
		final JLabel label_Show_Status = new JLabel(monitor.getStatus());
		label_Show_Status.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(label_Show_Status);
		
		final JLabel label_Show_DateEntry = new JLabel(sdf.format(monitor.getDateEntry()));
		label_Show_DateEntry.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(label_Show_DateEntry);
		
		final JLabel label_Show_Reason = new JLabel(monitor.getReason());
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
			new ChangesPanel(monitor.getChanges()).setVisible(true);;
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
}

