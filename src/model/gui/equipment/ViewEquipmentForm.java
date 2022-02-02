package model.gui.equipment;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.entities.Equipment;
import model.gui.change.ChangesPanel;

public class ViewEquipmentForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 170;

	private static final int WIDTH = 150;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 670);

	private Equipment equipment;

	public ViewEquipmentForm(Equipment equipment) {
		this.equipment = equipment;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("View Equipment");
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

		final JLabel label_HostName = new JLabel("Host Name:");
		label_HostName.setBounds(COLUMN1, 50, WIDTH, HEIGHT);
		panel.add(label_HostName);

		final JLabel label_AddressMAC = new JLabel("Address MAC:");
		label_AddressMAC.setBounds(COLUMN1, 90, WIDTH, HEIGHT);
		panel.add(label_AddressMAC);

		final JLabel label_Type = new JLabel("Type:");
		label_Type.setBounds(COLUMN1, 130, WIDTH, HEIGHT);
		panel.add(label_Type);

		final JLabel label_PatrimonyNumber = new JLabel("PatrimonyNumber:");
		label_PatrimonyNumber.setBounds(COLUMN1, 170, WIDTH, HEIGHT);
		panel.add(label_PatrimonyNumber);

		final JLabel label_Brand = new JLabel("Brand:");
		label_Brand.setBounds(COLUMN1, 210, WIDTH, HEIGHT);
		panel.add(label_Brand);

		final JLabel label_Model = new JLabel("Model:");
		label_Model.setBounds(COLUMN1, 250, WIDTH, HEIGHT);
		panel.add(label_Model);

		final JLabel label_MemoryRam = new JLabel("MemoryRam:");
		label_MemoryRam.setBounds(COLUMN1, 290, WIDTH, HEIGHT);
		panel.add(label_MemoryRam);

		final JLabel label_HardDisk = new JLabel("HardDisk:");
		label_HardDisk.setBounds(COLUMN1, 330, WIDTH, HEIGHT);
		panel.add(label_HardDisk);

		final JLabel label_CostType = new JLabel("CostType:");
		label_CostType.setBounds(COLUMN1, 370, WIDTH, HEIGHT);
		panel.add(label_CostType);

		final JLabel label_Value = new JLabel("Value:");
		label_Value.setBounds(COLUMN1, 410, WIDTH, HEIGHT);
		panel.add(label_Value);

		final JLabel label_Status = new JLabel("Status:");
		label_Status.setBounds(COLUMN1, 450, WIDTH, HEIGHT);
		panel.add(label_Status);

		final JLabel label_DateEntry = new JLabel("DateEntry:");
		label_DateEntry.setBounds(COLUMN1, 490, WIDTH, HEIGHT);
		panel.add(label_DateEntry);

		final JLabel label_Reason = new JLabel("Reason:");
		label_Reason.setBounds(COLUMN1, 530, WIDTH, HEIGHT);
		panel.add(label_Reason);
	}

	private void addLabelsShow(JPanel panel) {
		final JLabel label_Show_SerialNumber = new JLabel(equipment.getSerialNumber());
		label_Show_SerialNumber.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(label_Show_SerialNumber);

		final JLabel label_Show_HostName = new JLabel(equipment.getHostName());
		label_Show_HostName.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(label_Show_HostName);

		final JLabel label_Show_AddressMAC = new JLabel(equipment.getAddressMAC());
		label_Show_AddressMAC.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(label_Show_AddressMAC);

		final JLabel label_Show_Type = new JLabel(equipment.getType());
		label_Show_Type.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(label_Show_Type);

		final JLabel label_Show_PatrimonyNumber = new JLabel(equipment.getPatrimonyNumber());
		label_Show_PatrimonyNumber.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(label_Show_PatrimonyNumber);

		final JLabel label_Show_Brand = new JLabel(equipment.getBrand());
		label_Show_Brand.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(label_Show_Brand);

		final JLabel label_Show_Model = new JLabel(equipment.getModel());
		label_Show_Model.setBounds(COLUMN2, 250, WIDTH, HEIGHT);
		panel.add(label_Show_Model);

		final JLabel comboBox_MemoryRam = new JLabel(equipment.getMemoryRam());
		comboBox_MemoryRam.setBounds(COLUMN2, 290, WIDTH, HEIGHT);
		panel.add(comboBox_MemoryRam);

		final JLabel comboBox_HardDisk = new JLabel(equipment.getHardDisk());
		comboBox_HardDisk.setBounds(COLUMN2, 330, WIDTH, HEIGHT);
		panel.add(comboBox_HardDisk);

		final JLabel comboBox_CostType = new JLabel(equipment.getCostType());
		comboBox_CostType.setBounds(COLUMN2, 370, WIDTH, HEIGHT);
		panel.add(comboBox_CostType);

		final JLabel textField_Value = new JLabel(String.valueOf(equipment.getValue()));
		textField_Value.setBounds(COLUMN2, 410, WIDTH, HEIGHT);
		panel.add(textField_Value);

		final JLabel label_Show_Status = new JLabel(equipment.getStatus());
		label_Show_Status.setBounds(COLUMN2, 450, WIDTH, HEIGHT);
		panel.add(label_Show_Status);

		final JLabel label_Show_DateEntry = new JLabel(sdf.format(equipment.getDateEntry()));
		label_Show_DateEntry.setBounds(COLUMN2, 490, WIDTH, HEIGHT);
		panel.add(label_Show_DateEntry);

		final JLabel label_Show_Reason = new JLabel(equipment.getReason());
		label_Show_Reason.setBounds(COLUMN2, 530, WIDTH, HEIGHT);
		panel.add(label_Show_Reason);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonChanges = new JButton("Changes");
		buttonChanges.setBounds(180, 590, WIDTH - 30, HEIGHT);
		buttonChanges.addActionListener(new buttonChangesListener());
		panel.add(buttonChanges);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(320, 590, WIDTH - 30, HEIGHT);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}

	private class buttonChangesListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			new ChangesPanel(equipment.getChanges()).setVisible(true);
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
}
