package model.gui.inventory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.entities.Inventory;

public class ViewInventoryForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);
	
	private Inventory inventory;

	public ViewInventoryForm(Inventory inventory) {
		this.inventory = inventory;
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("View Inventory");
		setPreferredSize(new Dimension(1280, 650));
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createPanelMain() {
		final JPanel panel = new JPanel();
		panel.setLayout(null);

		panel.add(createPanelWorkPosition());
		panel.add(createPanelProject());
		panel.add(createPanelUser());
		panel.add(createPanelEquipment());
		panel.add(createPanelMonitor1());
		panel.add(createPanelMonitor2());
		
		addButton(panel);

		return panel;
	}

	private JPanel createPanelWorkPosition() {
		final JPanel panel = new JPanel();
		
		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Work Position");
		panel.setBorder(title);
		panel.setBounds(10, 60, 250, 250);
		panel.setLayout(null);

		addLabelsWorkPosition(panel);
		addLabelsAndComboBoxesWorkPosition(panel);

		return panel;
	}
	
	private JPanel createPanelProject() {
		final JPanel panel = new JPanel(new FlowLayout());
		
		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Project");
		panel.setBorder(title);
		panel.setBounds(10, 320, 250, 210);
		panel.setLayout(null);

		addLabelsProject(panel);
		addLabelsAndComboBoxesProject(panel);

		return panel;
	}
	
	private JPanel createPanelUser() {
		final JPanel panel = new JPanel(new FlowLayout());
		
		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "User");
		panel.setBorder(title);
		panel.setBounds(270, 110, 330, 340);
		panel.setLayout(null);

		addLabelsUser(panel);
		addLabelsAndComboBoxesUser(panel);

		return panel;
	}
	
	private JPanel createPanelEquipment() {
		final JPanel panel = new JPanel(new FlowLayout());
		
		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Equipment");
		panel.setBorder(title);
		panel.setBounds(610, 10, 330, 530);
		panel.setLayout(null);

		addLabelsEquipment(panel);
		addLabelsAndComboBoxesEquipment(panel);

		return panel;
	}
	
	private JPanel createPanelMonitor1() {
		final JPanel panel = new JPanel(new FlowLayout());
		
		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Monitor 1");
		panel.setBorder(title);
		panel.setBounds(950, 10, 300, 260);
		panel.setLayout(null);

		addLabelsMonitor1(panel);
		addLabelsAndComboBoxesMonitor1(panel);

		return panel;
	}
	
	private JPanel createPanelMonitor2() {
		final JPanel panel = new JPanel(new FlowLayout());
		
		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Monitor 2");
		panel.setBorder(title);
		panel.setBounds(950, 280, 300, 260);
		panel.setLayout(null);

		addLabelsMonitor2(panel);
		addLabelsAndComboBoxesMonitor2(panel);

		return panel;
	}

	private void addLabelsWorkPosition(JPanel panel) {		
		final JLabel label_WorkPoint = new JLabel("Work Point:");
		label_WorkPoint.setForeground(COLOR1);
		label_WorkPoint.setBounds(20, 90, 80, 25);
		panel.add(label_WorkPoint);
		
		final JLabel label_Location = new JLabel("Location:");
		label_Location.setForeground(COLOR1);
		label_Location.setBounds(20, 130, 80, 25);
		panel.add(label_Location);
		
		final JLabel label_Floor = new JLabel("Floor:");
		label_Floor.setForeground(COLOR1);
		label_Floor.setBounds(20, 170, 80, 25);
		panel.add(label_Floor);
		
		final JLabel label_NetPoint = new JLabel("NetPoint:");
		label_NetPoint.setForeground(COLOR1);
		label_NetPoint.setBounds(20, 210, 80, 25);
		panel.add(label_NetPoint);
	}

	private void addLabelsAndComboBoxesWorkPosition(JPanel panel) {		
		final JLabel labelShow_WorkPoint = new JLabel(inventory.getWorkPosition().getWorkPoint());
		labelShow_WorkPoint.setForeground(COLOR2);
		labelShow_WorkPoint.setBounds(110, 90, 120, 25);
		panel.add(labelShow_WorkPoint);
		
		final JLabel labelShow_Location = new JLabel(inventory.getWorkPosition().getLocation());
		labelShow_Location.setForeground(COLOR2);
		labelShow_Location.setBounds(110, 130, 120, 25);
		panel.add(labelShow_Location);
		
		final JLabel labelShow_Floor = new JLabel(inventory.getWorkPosition().getFloor());
		labelShow_Floor.setForeground(COLOR2);
		labelShow_Floor.setBounds(110, 170, 120, 25);
		panel.add(labelShow_Floor);
		
		final JLabel labelShow_NetPoint = new JLabel(inventory.getWorkPosition().getNetPoint());
		labelShow_NetPoint.setForeground(COLOR2);
		labelShow_NetPoint.setBounds(110, 210, 120, 25);
		panel.add(labelShow_NetPoint);
	}
	
	private void addLabelsProject(JPanel panel) {		
		final JLabel label_Name = new JLabel("Name:");
		label_Name.setForeground(COLOR1);
		label_Name.setBounds(20, 90, 90, 25);
		panel.add(label_Name);
		
		final JLabel label_Locality = new JLabel("City:");
		label_Locality.setForeground(COLOR1);
		label_Locality.setBounds(20, 130, 90, 25);
		panel.add(label_Locality);
		
		final JLabel label_CostCenter = new JLabel("Cost Center:");
		label_CostCenter.setForeground(COLOR1);
		label_CostCenter.setBounds(20, 170, 90, 25);
		panel.add(label_CostCenter);
	}

	private void addLabelsAndComboBoxesProject(JPanel panel) {		
		final JLabel labelShow_NameProject = new JLabel(inventory.getProject().getName());
		labelShow_NameProject.setForeground(COLOR2);
		labelShow_NameProject.setBounds(110, 90, 120, 25);
		panel.add(labelShow_NameProject);
		
		final JLabel labelShow_CityProject = new JLabel(inventory.getProject().getCity());
		labelShow_CityProject.setForeground(COLOR2);
		labelShow_CityProject.setBounds(110, 130, 120, 25);
		panel.add(labelShow_CityProject);
		
		final JLabel labelShow_CostCenterProject = new JLabel(inventory.getProject().getCostCenter());
		labelShow_CostCenterProject.setForeground(COLOR2);
		labelShow_CostCenterProject.setBounds(110, 170, 120, 25);
		panel.add(labelShow_CostCenterProject);
	}
	
	private void addLabelsUser(JPanel panel) {		
		final JLabel label_Registration = new JLabel("Registration:");
		label_Registration.setForeground(COLOR1);
		label_Registration.setBounds(20, 90, 90, 25);
		panel.add(label_Registration);
		
		final JLabel label_NameUser = new JLabel("Name:");
		label_NameUser.setForeground(COLOR1);
		label_NameUser.setBounds(20, 130, 90, 25);
		panel.add(label_NameUser);
		
		final JLabel label_CPF = new JLabel("CPF:");
		label_CPF.setForeground(COLOR1);
		label_CPF.setBounds(20, 170, 90, 25);
		panel.add(label_CPF);
		
		final JLabel label_Phone = new JLabel("Phone:");
		label_Phone.setForeground(COLOR1);
		label_Phone.setBounds(20, 210, 90, 25);
		panel.add(label_Phone);
		
		final JLabel label_Email = new JLabel("Email:");
		label_Email.setForeground(COLOR1);
		label_Email.setBounds(20, 250, 90, 25);
		panel.add(label_Email);
		
		final JLabel label_Department = new JLabel("Department:");
		label_Department.setForeground(COLOR1);
		label_Department.setBounds(20, 290, 90, 25);
		panel.add(label_Department);
	}

	private void addLabelsAndComboBoxesUser(JPanel panel) {		
		final JLabel labelShow_RegistrationUser = new JLabel(inventory.getUser().getRegistration());
		labelShow_RegistrationUser.setForeground(COLOR2);
		labelShow_RegistrationUser.setBounds(110, 90, 200, 25);
		panel.add(labelShow_RegistrationUser);
		
		final JLabel labelShow_NameUser = new JLabel(inventory.getUser().getName());
		labelShow_NameUser.setForeground(COLOR2);
		labelShow_NameUser.setBounds(110, 130, 200, 25);
		panel.add(labelShow_NameUser);
		
		final JLabel labelShow_CPFUser = new JLabel(inventory.getUser().getCpf());
		labelShow_CPFUser.setForeground(COLOR2);
		labelShow_CPFUser.setBounds(110, 170, 200, 25);
		panel.add(labelShow_CPFUser);
		
		final JLabel labelShow_PhoneUser = new JLabel(inventory.getUser().getPhone());
		labelShow_PhoneUser.setForeground(COLOR2);
		labelShow_PhoneUser.setBounds(110, 210, 200, 25);
		panel.add(labelShow_PhoneUser);
		
		final JLabel labelShow_EmailUser = new JLabel(inventory.getUser().getEmail());
		labelShow_EmailUser.setForeground(COLOR2);
		labelShow_EmailUser.setBounds(110, 250, 200, 25);
		panel.add(labelShow_EmailUser);
		
		final JLabel labelShow_DepartmentUser = new JLabel(inventory.getUser().getDepartment());
		labelShow_DepartmentUser.setForeground(COLOR2);
		labelShow_DepartmentUser.setBounds(110, 290, 200, 25);
		panel.add(labelShow_DepartmentUser);
	}
	
	private void addLabelsEquipment(JPanel panel) {		
		final JLabel label_SerialNumber = new JLabel("Serial Number:");
		label_SerialNumber.setForeground(COLOR1);
		label_SerialNumber.setBounds(20, 90, 120, 25);
		panel.add(label_SerialNumber);
		
		final JLabel label_HostName = new JLabel("Host Name:");
		label_HostName.setForeground(COLOR1);
		label_HostName.setBounds(20, 130, 120, 25);
		panel.add(label_HostName);
		
		final JLabel label_AddressMAC = new JLabel("Address MAC:");
		label_AddressMAC.setForeground(COLOR1);
		label_AddressMAC.setBounds(20, 170, 120, 25);
		panel.add(label_AddressMAC);
		
		final JLabel label_Type = new JLabel("Type:");
		label_Type.setForeground(COLOR1);
		label_Type.setBounds(20, 210, 120, 25);
		panel.add(label_Type);
		
		final JLabel label_PatrimonyNumber = new JLabel("Patrimony Number:");
		label_PatrimonyNumber.setForeground(COLOR1);
		label_PatrimonyNumber.setBounds(20, 250, 120, 25);
		panel.add(label_PatrimonyNumber);
		
		final JLabel label_Brand = new JLabel("Brand:");
		label_Brand.setForeground(COLOR1);
		label_Brand.setBounds(20, 290, 120, 25);
		panel.add(label_Brand);
		
		final JLabel label_Model = new JLabel("Model:");
		label_Model.setForeground(COLOR1);
		label_Model.setBounds(20, 330, 120, 25);
		panel.add(label_Model);
		
		final JLabel label_MemoryRam = new JLabel("Memory Ram:");
		label_MemoryRam.setForeground(COLOR1);
		label_MemoryRam.setBounds(20, 370, 120, 25);
		panel.add(label_MemoryRam);
		
		final JLabel label_HardDisk = new JLabel("Hard Disk:");
		label_HardDisk.setForeground(COLOR1);
		label_HardDisk.setBounds(20, 410, 120, 25);
		panel.add(label_HardDisk);
		
		final JLabel label_CostType = new JLabel("Cost Type:");
		label_CostType.setForeground(COLOR1);
		label_CostType.setBounds(20, 450, 120, 25);
		panel.add(label_CostType);
		
		final JLabel label_Value = new JLabel("Value:");
		label_Value.setForeground(COLOR1);
		label_Value.setBounds(20, 490, 120, 25);
		panel.add(label_Value);
	}
	
	private void addLabelsAndComboBoxesEquipment(JPanel panel) {		
		final JLabel labelShow_SerialNumberEquipment = new JLabel(inventory.getEquipment().getSerialNumber());
		labelShow_SerialNumberEquipment.setForeground(COLOR2);
		labelShow_SerialNumberEquipment.setBounds(150, 90, 150, 25);
		panel.add(labelShow_SerialNumberEquipment);
		
		final JLabel labelShow_HostNameEquipment = new JLabel(inventory.getEquipment().getHostName());
		labelShow_HostNameEquipment.setForeground(COLOR2);
		labelShow_HostNameEquipment.setBounds(150, 130, 150, 25);
		panel.add(labelShow_HostNameEquipment);
		
		final JLabel labelShow_AddressMACEquipment = new JLabel(inventory.getEquipment().getAddressMAC());
		labelShow_AddressMACEquipment.setForeground(COLOR2);
		labelShow_AddressMACEquipment.setBounds(150, 170, 150, 25);
		panel.add(labelShow_AddressMACEquipment);
		
		final JLabel labelShow_TypeEquipment = new JLabel(inventory.getEquipment().getType());
		labelShow_TypeEquipment.setForeground(COLOR2);
		labelShow_TypeEquipment.setBounds(150, 210, 150, 25);
		panel.add(labelShow_TypeEquipment);
		
		final JLabel labelShow_PatrimonyNumberEquipment = new JLabel(inventory.getEquipment().getPatrimonyNumber());
		labelShow_PatrimonyNumberEquipment.setForeground(COLOR2);
		labelShow_PatrimonyNumberEquipment.setBounds(150, 250, 150, 25);
		panel.add(labelShow_PatrimonyNumberEquipment);
		
		final JLabel labelShow_BrandEquipment = new JLabel(inventory.getEquipment().getBrand());
		labelShow_BrandEquipment.setForeground(COLOR2);
		labelShow_BrandEquipment.setBounds(150, 290, 150, 25);
		panel.add(labelShow_BrandEquipment);
		
		final JLabel labelShow_ModelEquipment = new JLabel(inventory.getEquipment().getModel());
		labelShow_ModelEquipment.setForeground(COLOR2);
		labelShow_ModelEquipment.setBounds(150, 330, 150, 25);
		panel.add(labelShow_ModelEquipment);
		
		final JLabel labelShow_MemoryRamEquipment = new JLabel(inventory.getEquipment().getMemoryRam());
		labelShow_MemoryRamEquipment.setForeground(COLOR2);
		labelShow_MemoryRamEquipment.setBounds(150, 370, 150, 25);
		panel.add(labelShow_MemoryRamEquipment);
		
		final JLabel labelShow_HardDiskEquipment = new JLabel(inventory.getEquipment().getHardDisk());
		labelShow_HardDiskEquipment.setForeground(COLOR2);
		labelShow_HardDiskEquipment.setBounds(150, 410, 150, 25);
		panel.add(labelShow_HardDiskEquipment);
		
		final JLabel labelShow_CostTypeEquipment = new JLabel(inventory.getEquipment().getCostType());
		labelShow_CostTypeEquipment.setForeground(COLOR2);
		labelShow_CostTypeEquipment.setBounds(150, 450, 150, 25);
		panel.add(labelShow_CostTypeEquipment);
		
		final JLabel labelShow_ValueEquipment = new JLabel(String.valueOf(inventory.getEquipment().getValue()));
		labelShow_ValueEquipment.setForeground(COLOR2);
		labelShow_ValueEquipment.setBounds(150, 490, 150, 25);
		panel.add(labelShow_ValueEquipment);
	}
	
	private void addLabelsMonitor1(JPanel panel) {		
		final JLabel label_SerialNumber = new JLabel("Serial Number:");
		label_SerialNumber.setForeground(COLOR1);
		label_SerialNumber.setBounds(20, 90, 120, 25);
		panel.add(label_SerialNumber);
		
		final JLabel label_PatrimonyNumber = new JLabel("Patrimony Number:");
		label_PatrimonyNumber.setForeground(COLOR1);
		label_PatrimonyNumber.setBounds(20, 130, 120, 25);
		panel.add(label_PatrimonyNumber);
		
		final JLabel label_Brand = new JLabel("Brand:");
		label_Brand.setForeground(COLOR1);
		label_Brand.setBounds(20, 170, 120, 25);
		panel.add(label_Brand);
		
		final JLabel label_Model = new JLabel("Model:");
		label_Model.setForeground(COLOR1);
		label_Model.setBounds(20, 210, 120, 25);
		panel.add(label_Model);
	}

	private void addLabelsAndComboBoxesMonitor1(JPanel panel) {		
		final JLabel labelShow_SerialNumberMonitor1 = new JLabel(inventory.getMonitor1().getSerialNumber());
		labelShow_SerialNumberMonitor1.setForeground(COLOR2);
		labelShow_SerialNumberMonitor1.setBounds(150, 90, 120, 25);
		panel.add(labelShow_SerialNumberMonitor1);
		
		final JLabel labelShow_PatrimonyNumberMonitor1 = new JLabel(inventory.getMonitor1().getPatrimonyNumber());
		labelShow_PatrimonyNumberMonitor1.setForeground(COLOR2);
		labelShow_PatrimonyNumberMonitor1.setBounds(150, 130, 120, 25);
		panel.add(labelShow_PatrimonyNumberMonitor1);
		
		final JLabel labelShow_BrandMonitor1 = new JLabel(inventory.getMonitor1().getBrand());
		labelShow_BrandMonitor1.setForeground(COLOR2);
		labelShow_BrandMonitor1.setBounds(150, 170, 120, 25);
		panel.add(labelShow_BrandMonitor1);
		
		final JLabel labelShow_ModelMonitor1 = new JLabel(inventory.getMonitor1().getModel());
		labelShow_ModelMonitor1.setForeground(COLOR2);
		labelShow_ModelMonitor1.setBounds(150, 210, 120, 25);
		panel.add(labelShow_ModelMonitor1);
	}
	
	private void addLabelsMonitor2(JPanel panel) {		
		final JLabel label_SerialNumber = new JLabel("Serial Number:");
		label_SerialNumber.setForeground(COLOR1);
		label_SerialNumber.setBounds(20, 90, 120, 25);
		panel.add(label_SerialNumber);
		
		final JLabel label_PatrimonyNumber = new JLabel("Patrimony Number:");
		label_PatrimonyNumber.setForeground(COLOR1);
		label_PatrimonyNumber.setBounds(20, 130, 120, 25);
		panel.add(label_PatrimonyNumber);
		
		final JLabel label_Brand = new JLabel("Brand:");
		label_Brand.setForeground(COLOR1);
		label_Brand.setBounds(20, 170, 120, 25);
		panel.add(label_Brand);
		
		final JLabel label_Model = new JLabel("Model:");
		label_Model.setForeground(COLOR1);
		label_Model.setBounds(20, 210, 120, 25);
		panel.add(label_Model);
	}

	private void addLabelsAndComboBoxesMonitor2(JPanel panel) {		
		final JLabel labelShow_SerialNumberMonitor2 = new JLabel(inventory.getMonitor2().getSerialNumber());
		labelShow_SerialNumberMonitor2.setForeground(COLOR2);
		labelShow_SerialNumberMonitor2.setBounds(150, 90, 150, 25);
		panel.add(labelShow_SerialNumberMonitor2);
		
		final JLabel labelShow_PatrimonyNumberMonitor2 = new JLabel(inventory.getMonitor2().getPatrimonyNumber());
		labelShow_PatrimonyNumberMonitor2.setForeground(COLOR2);
		labelShow_PatrimonyNumberMonitor2.setBounds(150, 130, 130, 25);
		panel.add(labelShow_PatrimonyNumberMonitor2);
		
		final JLabel labelShow_BrandMonitor2 = new JLabel(inventory.getMonitor2().getBrand());
		labelShow_BrandMonitor2.setForeground(COLOR2);
		labelShow_BrandMonitor2.setBounds(150, 170, 130, 25);
		panel.add(labelShow_BrandMonitor2);
		
		final JLabel labelShow_ModelMonitor2 = new JLabel(inventory.getMonitor2().getModel());
		labelShow_ModelMonitor2.setForeground(COLOR2);
		labelShow_ModelMonitor2.setBounds(150, 210, 150, 25);
		panel.add(labelShow_ModelMonitor2);
	}

	private void addButton(JPanel panel) {
		final JButton buttonTerm = new JButton("Gerar Termo");
		buttonTerm.setBounds(500, 560, 120, 25);
		buttonTerm.addActionListener(new buttonTermListener());
		panel.add(buttonTerm);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(640, 560, 120, 25);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}
	
	private class buttonTermListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			
		}
	}

	private class buttonCloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
}
