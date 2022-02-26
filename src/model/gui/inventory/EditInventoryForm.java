package model.gui.inventory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import db.DBException;
import exception.ValidationException;
import model.entities.Equipment;
import model.entities.Inventory;
import model.entities.Monitor;
import model.entities.Project;
import model.entities.User;
import model.entities.WorkPosition;
import model.services.inventory.InventoryService;
import model.services.inventory.InventoryTableModel;

public class EditInventoryForm extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);
	
	private JComboBox<WorkPosition> comboBox_WorkPosition;
	private JLabel labelShow_WorkPoint;
	private JLabel labelShow_Location;
	private JLabel labelShow_Floor;
	private JLabel labelShow_NetPoint;
	
	private JComboBox<Project> comboBox_Project;
	private JLabel labelShow_NameProject;
	private JLabel labelShow_CityProject;
	private JLabel labelShow_CostCenterProject;
		
	private JComboBox<User> comboBox_User;
	private JLabel labelShow_RegistrationUser;
	private JLabel labelShow_NameUser;
	private JLabel labelShow_CPFUser;
	private JLabel labelShow_PhoneUser;
	private JLabel labelShow_EmailUser;
	private JLabel labelShow_DepartmentUser;
	
	private JComboBox<Equipment> comboBox_Equipment;
	private JLabel labelShow_SerialNumberEquipment;
	private JLabel labelShow_HostNameEquipment;
	private JLabel labelShow_AddressMACEquipment;
	private JLabel labelShow_TypeEquipment;
	private JLabel labelShow_PatrimonyNumberEquipment;
	private JLabel labelShow_BrandEquipment;
	private JLabel labelShow_ModelEquipment;
	private JLabel labelShow_MemoryRamEquipment;
	private JLabel labelShow_HardDiskEquipment;
	private JLabel labelShow_CostTypeEquipment;
	private JLabel labelShow_ValueEquipment;
	
	private JComboBox<Monitor> comboBox_Monitor1;
	private JLabel labelShow_SerialNumberMonitor1;
	private JLabel labelShow_PatrimonyNumberMonitor1;
	private JLabel labelShow_BrandMonitor1;
	private JLabel labelShow_ModelMonitor1;
	
	private JComboBox<Monitor> comboBox_Monitor2;
	private JLabel labelShow_SerialNumberMonitor2;
	private JLabel labelShow_PatrimonyNumberMonitor2;
	private JLabel labelShow_BrandMonitor2;
	private JLabel labelShow_ModelMonitor2;

	private JLabel labelError_WorkPosition;
	private JLabel labelError_Equipment;
	private JLabel labelError_Monitor1;
	private JLabel labelError_Monitor2;
	
	private List<WorkPosition> workPositions;
	private List<Project> projects;
	private List<User> users;
	private List<Equipment> equipments;
	private List<Monitor> monitors;

	private InventoryTableModel model;
	private final Inventory inventoryOld;
	private int lineSelected;

	public EditInventoryForm(InventoryTableModel model, Inventory inventoryOld, int lineSelected, List<WorkPosition> workPositions, List<Project> projects, List<User> users, List<Equipment> equipments, List<Monitor> monitors) {
		this.workPositions = workPositions;
		this.projects = projects;
		this.users = users;
		this.equipments = equipments;
		this.monitors = monitors;
		this.model = model;
		this.inventoryOld = inventoryOld;
		this.lineSelected = lineSelected;
		initComponents();
		load();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Edit Inventory");
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
		
		addButtons(panel);

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
		labelError_WorkPosition = new JLabel();
		labelError_WorkPosition.setForeground(Color.RED);
		labelError_WorkPosition.setHorizontalAlignment(SwingConstants.CENTER);
		labelError_WorkPosition.setBounds(20, 50, 210, 25);
		panel.add(labelError_WorkPosition);
		
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
		comboBox_WorkPosition = new JComboBox<WorkPosition>(new Vector<>(workPositions));
		AutoCompleteDecorator.decorate(comboBox_WorkPosition);
		comboBox_WorkPosition.addItemListener(new ItemChangeWorkPositionListener());
		comboBox_WorkPosition.setSelectedIndex(-1);
		comboBox_WorkPosition.setBounds(50, 20, 150, 25);
		panel.add(comboBox_WorkPosition);
		
		labelShow_WorkPoint = new JLabel();
		labelShow_WorkPoint.setForeground(COLOR2);
		labelShow_WorkPoint.setBounds(110, 90, 120, 25);
		panel.add(labelShow_WorkPoint);
		
		labelShow_Location = new JLabel();
		labelShow_Location.setForeground(COLOR2);
		labelShow_Location.setBounds(110, 130, 120, 25);
		panel.add(labelShow_Location);
		
		labelShow_Floor = new JLabel();
		labelShow_Floor.setForeground(COLOR2);
		labelShow_Floor.setBounds(110, 170, 120, 25);
		panel.add(labelShow_Floor);
		
		labelShow_NetPoint = new JLabel();
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
		comboBox_Project = new JComboBox<>(new Vector<>(projects));
		AutoCompleteDecorator.decorate(comboBox_Project);
		comboBox_Project.addItemListener(new ItemChangeProjectListener());
		comboBox_Project.setEditable(false);
		comboBox_Project.setBounds(50, 20, 150, 25);
		panel.add(comboBox_Project);
		
		labelShow_NameProject = new JLabel();
		labelShow_NameProject.setForeground(COLOR2);
		labelShow_NameProject.setBounds(110, 90, 120, 25);
		panel.add(labelShow_NameProject);
		
		labelShow_CityProject = new JLabel();
		labelShow_CityProject.setForeground(COLOR2);
		labelShow_CityProject.setBounds(110, 130, 120, 25);
		panel.add(labelShow_CityProject);
		
		labelShow_CostCenterProject = new JLabel();
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
		comboBox_User = new JComboBox<>(new Vector<>(users));
		AutoCompleteDecorator.decorate(comboBox_User);
		comboBox_User.addItemListener(new ItemChangeUserListener());
		comboBox_User.setSelectedIndex(-1);
		comboBox_User.setBounds(90, 20, 150, 25);
		panel.add(comboBox_User);
		
		labelShow_RegistrationUser = new JLabel();
		labelShow_RegistrationUser.setForeground(COLOR2);
		labelShow_RegistrationUser.setBounds(110, 90, 200, 25);
		panel.add(labelShow_RegistrationUser);
		
		labelShow_NameUser = new JLabel();
		labelShow_NameUser.setForeground(COLOR2);
		labelShow_NameUser.setBounds(110, 130, 200, 25);
		panel.add(labelShow_NameUser);
		
		labelShow_CPFUser = new JLabel();
		labelShow_CPFUser.setForeground(COLOR2);
		labelShow_CPFUser.setBounds(110, 170, 200, 25);
		panel.add(labelShow_CPFUser);
		
		labelShow_PhoneUser = new JLabel();
		labelShow_PhoneUser.setForeground(COLOR2);
		labelShow_PhoneUser.setBounds(110, 210, 200, 25);
		panel.add(labelShow_PhoneUser);
		
		labelShow_EmailUser = new JLabel();
		labelShow_EmailUser.setForeground(COLOR2);
		labelShow_EmailUser.setBounds(110, 250, 200, 25);
		panel.add(labelShow_EmailUser);
		
		labelShow_DepartmentUser = new JLabel();
		labelShow_DepartmentUser.setForeground(COLOR2);
		labelShow_DepartmentUser.setBounds(110, 290, 200, 25);
		panel.add(labelShow_DepartmentUser);
	}
	
	private void addLabelsEquipment(JPanel panel) {
		labelError_Equipment = new JLabel();
		labelError_Equipment.setForeground(Color.RED);
		labelError_Equipment.setHorizontalAlignment(SwingConstants.CENTER);
		labelError_Equipment.setBounds(20, 50, 290, 25);
		panel.add(labelError_Equipment);
		
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
		comboBox_Equipment = new JComboBox<>(new Vector<>(equipments));
		AutoCompleteDecorator.decorate(comboBox_Equipment);
		comboBox_Equipment.addItemListener(new ItemChangeEquipmentListener());
		comboBox_Equipment.setSelectedIndex(-1);
		comboBox_Equipment.setBounds(90, 20, 150, 25);
		panel.add(comboBox_Equipment);
		
		labelShow_SerialNumberEquipment = new JLabel();
		labelShow_SerialNumberEquipment.setForeground(COLOR2);
		labelShow_SerialNumberEquipment.setBounds(150, 90, 150, 25);
		panel.add(labelShow_SerialNumberEquipment);
		
		labelShow_HostNameEquipment = new JLabel();
		labelShow_HostNameEquipment.setForeground(COLOR2);
		labelShow_HostNameEquipment.setBounds(150, 130, 150, 25);
		panel.add(labelShow_HostNameEquipment);
		
		labelShow_AddressMACEquipment = new JLabel();
		labelShow_AddressMACEquipment.setForeground(COLOR2);
		labelShow_AddressMACEquipment.setBounds(150, 170, 150, 25);
		panel.add(labelShow_AddressMACEquipment);
		
		labelShow_TypeEquipment = new JLabel();
		labelShow_TypeEquipment.setForeground(COLOR2);
		labelShow_TypeEquipment.setBounds(150, 210, 150, 25);
		panel.add(labelShow_TypeEquipment);
		
		labelShow_PatrimonyNumberEquipment = new JLabel();
		labelShow_PatrimonyNumberEquipment.setForeground(COLOR2);
		labelShow_PatrimonyNumberEquipment.setBounds(150, 250, 150, 25);
		panel.add(labelShow_PatrimonyNumberEquipment);
		
		labelShow_BrandEquipment = new JLabel();
		labelShow_BrandEquipment.setForeground(COLOR2);
		labelShow_BrandEquipment.setBounds(150, 290, 150, 25);
		panel.add(labelShow_BrandEquipment);
		
		labelShow_ModelEquipment = new JLabel();
		labelShow_ModelEquipment.setForeground(COLOR2);
		labelShow_ModelEquipment.setBounds(150, 330, 150, 25);
		panel.add(labelShow_ModelEquipment);
		
		labelShow_MemoryRamEquipment = new JLabel();
		labelShow_MemoryRamEquipment.setForeground(COLOR2);
		labelShow_MemoryRamEquipment.setBounds(150, 370, 150, 25);
		panel.add(labelShow_MemoryRamEquipment);
		
		labelShow_HardDiskEquipment = new JLabel();
		labelShow_HardDiskEquipment.setForeground(COLOR2);
		labelShow_HardDiskEquipment.setBounds(150, 410, 150, 25);
		panel.add(labelShow_HardDiskEquipment);
		
		labelShow_CostTypeEquipment = new JLabel();
		labelShow_CostTypeEquipment.setForeground(COLOR2);
		labelShow_CostTypeEquipment.setBounds(150, 450, 150, 25);
		panel.add(labelShow_CostTypeEquipment);
		
		labelShow_ValueEquipment = new JLabel();
		labelShow_ValueEquipment.setForeground(COLOR2);
		labelShow_ValueEquipment.setBounds(150, 490, 150, 25);
		panel.add(labelShow_ValueEquipment);
	}
	
	private void addLabelsMonitor1(JPanel panel) {
		labelError_Monitor1 = new JLabel();
		labelError_Monitor1.setForeground(Color.RED);
		labelError_Monitor1.setHorizontalAlignment(SwingConstants.CENTER);
		labelError_Monitor1.setBounds(20, 50, 260, 25);
		panel.add(labelError_Monitor1);
		
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
		comboBox_Monitor1 = new JComboBox<>(new Vector<>(monitors));
		AutoCompleteDecorator.decorate(comboBox_Monitor1);
		comboBox_Monitor1.addItemListener(new ItemChangeMonitor1Listener());
		comboBox_Monitor1.setSelectedIndex(-1);
		comboBox_Monitor1.setBounds(80, 20, 150, 25);
		panel.add(comboBox_Monitor1);
		
		labelShow_SerialNumberMonitor1 = new JLabel();
		labelShow_SerialNumberMonitor1.setForeground(COLOR2);
		labelShow_SerialNumberMonitor1.setBounds(150, 90, 120, 25);
		panel.add(labelShow_SerialNumberMonitor1);
		
		labelShow_PatrimonyNumberMonitor1 = new JLabel();
		labelShow_PatrimonyNumberMonitor1.setForeground(COLOR2);
		labelShow_PatrimonyNumberMonitor1.setBounds(150, 130, 120, 25);
		panel.add(labelShow_PatrimonyNumberMonitor1);
		
		labelShow_BrandMonitor1 = new JLabel();
		labelShow_BrandMonitor1.setForeground(COLOR2);
		labelShow_BrandMonitor1.setBounds(150, 170, 120, 25);
		panel.add(labelShow_BrandMonitor1);
		
		labelShow_ModelMonitor1 = new JLabel();
		labelShow_ModelMonitor1.setForeground(COLOR2);
		labelShow_ModelMonitor1.setBounds(150, 210, 120, 25);
		panel.add(labelShow_ModelMonitor1);
	}
	
	private void addLabelsMonitor2(JPanel panel) {
		labelError_Monitor2 = new JLabel();
		labelError_Monitor2.setForeground(Color.RED);
		labelError_Monitor2.setHorizontalAlignment(SwingConstants.CENTER);
		labelError_Monitor2.setBounds(20, 50, 260, 25);
		panel.add(labelError_Monitor2);
		
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
		comboBox_Monitor2 = new JComboBox<>(new Vector<>(monitors));
		AutoCompleteDecorator.decorate(comboBox_Monitor2);
		comboBox_Monitor2.addItemListener(new ItemChangeMonitor2Listener());
		comboBox_Monitor2.setSelectedIndex(-1);
		comboBox_Monitor2.setBounds(80, 20, 150, 25);
		panel.add(comboBox_Monitor2);
		
		labelShow_SerialNumberMonitor2 = new JLabel();
		labelShow_SerialNumberMonitor2.setForeground(COLOR2);
		labelShow_SerialNumberMonitor2.setBounds(150, 90, 150, 25);
		panel.add(labelShow_SerialNumberMonitor2);
		
		labelShow_PatrimonyNumberMonitor2 = new JLabel();
		labelShow_PatrimonyNumberMonitor2.setForeground(COLOR2);
		labelShow_PatrimonyNumberMonitor2.setBounds(150, 130, 130, 25);
		panel.add(labelShow_PatrimonyNumberMonitor2);
		
		labelShow_BrandMonitor2 = new JLabel();
		labelShow_BrandMonitor2.setForeground(COLOR2);
		labelShow_BrandMonitor2.setBounds(150, 170, 130, 25);
		panel.add(labelShow_BrandMonitor2);
		
		labelShow_ModelMonitor2 = new JLabel();
		labelShow_ModelMonitor2.setForeground(COLOR2);
		labelShow_ModelMonitor2.setBounds(150, 210, 150, 25);
		panel.add(labelShow_ModelMonitor2);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(450, 560, 120, 25);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(600, 560, 120, 25);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}
	
	private void load() {
		comboBox_WorkPosition.setSelectedItem(inventoryOld.getWorkPosition());
		comboBox_Project.setSelectedItem(inventoryOld.getProject());
		comboBox_Project.setEnabled(false);
		comboBox_User.setSelectedItem(inventoryOld.getUser());
		comboBox_User.setEnabled(false);
		comboBox_Equipment.setSelectedItem(inventoryOld.getEquipment());
		comboBox_Monitor1.setSelectedItem(inventoryOld.getMonitor1());
		comboBox_Monitor2.setSelectedItem(inventoryOld.getMonitor2());
	}

	private class buttonSaveListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				final Inventory inventoryNew = getFormData();
				InventoryService service = new InventoryService();
				//service.update(inventoryOld, inventoryNew);
				model.updateInventory(lineSelected, inventoryNew);
				dispose();
				JOptionPane.showMessageDialog(rootPane, "Inventory successfully updated", "Success updating object",
						JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			} 
			catch (DBException e) {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	private Inventory getFormData() {
		Inventory inventory = new Inventory();
		inventory = (Inventory) inventoryOld.clone();

		ValidationException exception = new ValidationException("Validation error");

		// validation Work Position
		if (comboBox_WorkPosition.getSelectedIndex() < 0 || comboBox_WorkPosition.getSelectedItem() == null) {
			exception.addError("workPosition", "Field work position can't be empty");
		} 
		else {
			inventory.setWorkPosition(workPositions.get(workPositions.indexOf(comboBox_WorkPosition.getSelectedItem())));
		}

		// validation Equipment
		if (comboBox_Equipment.getSelectedIndex() < 0 || comboBox_Equipment.getSelectedItem() == null) {
			exception.addError("equipment", "Field equipment can't be empty");
		} 
		else {
			inventory.setEquipment(equipments.get(equipments.indexOf(comboBox_Equipment.getSelectedItem())));
		}

		inventory.setMonitor1(comboBox_Monitor1.getSelectedIndex() < 0? null: monitors.get(monitors.indexOf(comboBox_Monitor1.getSelectedItem())));
		inventory.setMonitor2(comboBox_Monitor2.getSelectedIndex() < 0? null: monitors.get(monitors.indexOf(comboBox_Monitor2.getSelectedItem())));

		// validation Monitor 1
		if (inventory.getEquipment() != null && inventory.getEquipment().getType().equals("DESKTOP") && inventory.getMonitor1() == null) {
			exception.addError("monitor1", "Field monitor 1 can't be empty");
		}
		else if (inventory.getMonitor1() != null && inventory.getMonitor2() != null && inventory.getMonitor1().equals(inventory.getMonitor2())) {
			exception.addError("monitor1", "The two monitors selected are the same");
			exception.addError("monitor2", "The two monitors selected are the same");
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return inventory;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_WorkPosition.setText(fields.contains("workPosition") ? errors.get("workPosition") : "");
		labelError_Equipment.setText(fields.contains("equipment") ? errors.get("equipment") : "");
		labelError_Monitor1.setText(fields.contains("monitor1") ? errors.get("monitor1") : "");
		labelError_Monitor2.setText(fields.contains("monitor2") ? errors.get("monitor2") : "");
	}

private class ItemChangeWorkPositionListener implements ItemListener {
		
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				WorkPosition workPosition = (WorkPosition) event.getItem();

				labelShow_WorkPoint.setText(workPosition.getWorkPoint());
				labelShow_Location.setText(workPosition.getLocation());
				labelShow_Floor.setText(workPosition.getFloor());
				labelShow_NetPoint.setText(workPosition.getNetPoint());
			}
		}
	}

	private class ItemChangeProjectListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Project project = (Project) event.getItem();
				
				labelShow_NameProject.setText(project.getName());
				labelShow_CityProject.setText(project.getCity());
				labelShow_CostCenterProject.setText(project.getCostCenter());				
			}
		}
	}

	private class ItemChangeUserListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				User user = (User) event.getItem();

				labelShow_RegistrationUser.setText(user.getRegistration());
				labelShow_NameUser.setText(user.getName());
				labelShow_CPFUser.setText(user.getCpf());
				labelShow_PhoneUser.setText(user.getPhone());
				labelShow_EmailUser.setText(user.getEmail());
				labelShow_DepartmentUser.setText(user.getDepartment());
			}
		}
	}

	private class ItemChangeEquipmentListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Equipment equipment = (Equipment) event.getItem();

				labelShow_SerialNumberEquipment.setText(equipment.getSerialNumber());
				labelShow_HostNameEquipment.setText(equipment.getHostName());
				labelShow_AddressMACEquipment.setText(equipment.getAddressMAC());
				labelShow_TypeEquipment.setText(equipment.getType());
				labelShow_PatrimonyNumberEquipment.setText(equipment.getPatrimonyNumber());
				labelShow_BrandEquipment.setText(equipment.getBrand());
				labelShow_ModelEquipment.setText(equipment.getModel());
				labelShow_MemoryRamEquipment.setText(equipment.getMemoryRam());
				labelShow_HardDiskEquipment.setText(equipment.getHardDisk());
				labelShow_CostTypeEquipment.setText(equipment.getCostType());
				labelShow_ValueEquipment.setText(String.valueOf(equipment.getValue()));
			}
		}
	}

	private class ItemChangeMonitor1Listener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Monitor monitor1 = (Monitor) event.getItem();
				
				labelShow_SerialNumberMonitor1.setText(monitor1.getSerialNumber());
				labelShow_PatrimonyNumberMonitor1.setText(monitor1.getPatrimonyNumber());
				labelShow_BrandMonitor1.setText(monitor1.getBrand());
				labelShow_ModelMonitor1.setText(monitor1.getModel());
			}
		}
	}

	private class ItemChangeMonitor2Listener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Monitor monitor2 = (Monitor) event.getItem();

				labelShow_SerialNumberMonitor2.setText(monitor2.getSerialNumber());
				labelShow_PatrimonyNumberMonitor2.setText(monitor2.getPatrimonyNumber());
				labelShow_BrandMonitor2.setText(monitor2.getBrand());
				labelShow_ModelMonitor2.setText(monitor2.getModel());
			}
		}
	}
}
