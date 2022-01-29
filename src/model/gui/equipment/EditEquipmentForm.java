package model.gui.equipment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DBException;
import exception.ValidationException;
import model.entities.Equipment;
import model.entities.Option;
import model.services.equipment.EquipmentService;
import model.services.equipment.EquipmentTableModel;
import model.util.JTextFieldFilter;
import model.util.Utils;

public class EditEquipmentForm extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 170;
	private static final int COLUMN3 = 330;

	private static final int WIDTH = 150;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 630);

	private JTextField textField_HostName;
	private JTextField textField_AddressMAC;
	private JComboBox<String> comboBox_Type;
	private JTextField textField_PatrimonyNumber;
	private JComboBox<String> comboBox_Brand;
	private JComboBox<String> comboBox_Model;
	private JComboBox<String> comboBox_MemoryRam;
	private JComboBox<String> comboBox_HardDisk;
	private JComboBox<String> comboBox_CostType;
	private JTextField textField_Value;

	private JLabel labelError_HostName;
	private JLabel labelError_AddressMAC;
	private JLabel labelError_Type;
	private JLabel labelError_PatrimonyNumber;
	private JLabel labelError_Brand;
	private JLabel labelError_Model;
	private JLabel labelError_MemoryRam;
	private JLabel labelError_HardDisk;

	private EquipmentTableModel model;
	private final Equipment equipmentOld;
	private List<Option> options;
	private int lineSelected;

	public EditEquipmentForm(EquipmentTableModel model, Equipment equipmentOld, List<Option> options, int lineSelected) {
		this.model = model;
		this.equipmentOld = equipmentOld;
		this.options = options;
		this.lineSelected = lineSelected;
		initComponents();
	}
	
	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Edit Equipment");
		setPreferredSize(DIMENSIONMAINPANEL);
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}
	
	private JPanel createPanelMain() {
		final JPanel panel = new JPanel(new FlowLayout());
		panel.setLayout(null);

		addLabels(panel);
		addTextFieldsAndComboBoxes(panel);
		addLabelsError(panel);
		addButtons(panel);

		return panel;
	}
	
	private void addLabels(JPanel panel) {
		JLabel label_SerialNumber = new JLabel("Serial Number:");
		label_SerialNumber.setBounds(COLUMN1, 10, WIDTH, HEIGHT);
		panel.add(label_SerialNumber);

		JLabel label_HostName = new JLabel("Host Name:");
		label_HostName.setBounds(COLUMN1, 50, WIDTH, HEIGHT);
		panel.add(label_HostName);

		JLabel label_AddressMAC = new JLabel("Address MAC:");
		label_AddressMAC.setBounds(COLUMN1, 90, WIDTH, HEIGHT);
		panel.add(label_AddressMAC);

		JLabel label_Type = new JLabel("Type:");
		label_Type.setBounds(COLUMN1, 130, WIDTH, HEIGHT);
		panel.add(label_Type);
		
		JLabel label_PatrimonyNumber = new JLabel("PatrimonyNumber:");
		label_PatrimonyNumber.setBounds(COLUMN1, 170, WIDTH, HEIGHT);
		panel.add(label_PatrimonyNumber);
		
		JLabel label_Brand = new JLabel("Brand:");
		label_Brand.setBounds(COLUMN1, 210, WIDTH, HEIGHT);
		panel.add(label_Brand);
		
		JLabel label_Model = new JLabel("Model:");
		label_Model.setBounds(COLUMN1, 250, WIDTH, HEIGHT);
		panel.add(label_Model);
		
		JLabel label_MemoryRam = new JLabel("MemoryRam:");
		label_MemoryRam.setBounds(COLUMN1, 290, WIDTH, HEIGHT);
		panel.add(label_MemoryRam);
		
		JLabel label_HardDisk = new JLabel("HardDisk:");
		label_HardDisk.setBounds(COLUMN1, 330, WIDTH, HEIGHT);
		panel.add(label_HardDisk);
		
		JLabel label_CostType = new JLabel("CostType:");
		label_CostType.setBounds(COLUMN1, 370, WIDTH, HEIGHT);
		panel.add(label_CostType);
		
		JLabel label_Value = new JLabel("Value:");
		label_Value.setBounds(COLUMN1, 410, WIDTH, HEIGHT);
		panel.add(label_Value);
		
		JLabel label_Status = new JLabel("Status:");
		label_Status.setBounds(COLUMN1, 450, WIDTH, HEIGHT);
		panel.add(label_Status);
		
		JLabel label_DateEntry = new JLabel("DateEntry:");
		label_DateEntry.setBounds(COLUMN1, 490, WIDTH, HEIGHT);
		panel.add(label_DateEntry);
	}
	
	private void addTextFieldsAndComboBoxes(JPanel panel) {
		final JLabel label_Show_SerialNumber = new JLabel(equipmentOld.getSerialNumber());
		label_Show_SerialNumber.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(label_Show_SerialNumber);

		textField_HostName = new JTextField();
		textField_HostName.setDocument(new JTextFieldFilter(JTextFieldFilter.UPPERCASE_NUMERIC_NO_SPACE, 11));
		textField_HostName.setText(equipmentOld.getHostName());
		textField_HostName.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(textField_HostName);

		textField_AddressMAC = new JTextField();
		textField_AddressMAC.setDocument(new JTextFieldFilter(JTextFieldFilter.ADDRESS_MAC, 17));
		textField_AddressMAC.setText(equipmentOld.getAddressMAC());
		textField_AddressMAC.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(textField_AddressMAC);
		
		comboBox_Type = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("TYPE") && o.getStatus().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Type.setSelectedItem(equipmentOld.getType());
		comboBox_Type.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(comboBox_Type);
		
		textField_PatrimonyNumber = new JTextField();
		textField_PatrimonyNumber.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC, 6));
		textField_PatrimonyNumber.setText(equipmentOld.getPatrimonyNumber());
		textField_PatrimonyNumber.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(textField_PatrimonyNumber);
		
		comboBox_Brand = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("BRAND-EQUIPMENT") && o.getStatus().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Brand.setSelectedItem(equipmentOld.getBrand());
		comboBox_Brand.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(comboBox_Brand);
		
		comboBox_Model = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("MODEL-EQUIPMENT") && o.getStatus().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Model.setSelectedItem(equipmentOld.getModel());
		comboBox_Model.setBounds(COLUMN2, 250, WIDTH, HEIGHT);
		panel.add(comboBox_Model);
		
		comboBox_MemoryRam = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("MEMORY RAM") && o.getStatus().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_MemoryRam.setSelectedItem(equipmentOld.getMemoryRam());
		comboBox_MemoryRam.setBounds(COLUMN2, 290, WIDTH, HEIGHT);
		panel.add(comboBox_MemoryRam);
		
		comboBox_HardDisk = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("HARD DISK") && o.getStatus().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_HardDisk.setSelectedItem(equipmentOld.getHardDisk());
		comboBox_HardDisk.setBounds(COLUMN2, 330, WIDTH, HEIGHT);
		panel.add(comboBox_HardDisk);
		
		comboBox_CostType = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("COST TYPE") && o.getStatus().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_CostType.setSelectedItem(equipmentOld.getCostType());
		comboBox_CostType.setBounds(COLUMN2, 370, WIDTH, HEIGHT);
		panel.add(comboBox_CostType);
		
		textField_Value = new JTextField();
		textField_Value.setDocument(new JTextFieldFilter(JTextFieldFilter.DECIMAL, 6));
		textField_Value.setText(String.valueOf(equipmentOld.getValue()));
		textField_Value.setBounds(COLUMN2, 410, WIDTH, HEIGHT);
		panel.add(textField_Value);
		
		final JLabel label_Show_Status = new JLabel(equipmentOld.getStatus());
		label_Show_Status.setBounds(COLUMN2, 450, WIDTH, HEIGHT);
		panel.add(label_Show_Status);
		
		final JLabel label_Show_DateEntry = new JLabel(sdf.format(equipmentOld.getDateEntry()));
		label_Show_DateEntry.setBounds(COLUMN2, 490, WIDTH, HEIGHT);
		panel.add(label_Show_DateEntry);
	}
	
	private void addLabelsError(JPanel panel) {		
		labelError_HostName = new JLabel();
		labelError_HostName.setForeground(Color.RED);
		labelError_HostName.setBounds(COLUMN3, 50, WIDTH + 90, HEIGHT);
		panel.add(labelError_HostName);

		labelError_AddressMAC = new JLabel();
		labelError_AddressMAC.setForeground(Color.RED);
		labelError_AddressMAC.setBounds(COLUMN3, 90, WIDTH + 90, HEIGHT);
		panel.add(labelError_AddressMAC);
		
		labelError_Type = new JLabel();
		labelError_Type.setForeground(Color.RED);
		labelError_Type.setBounds(COLUMN3, 130, WIDTH + 90, HEIGHT);
		panel.add(labelError_Type);
		
		labelError_PatrimonyNumber = new JLabel();
		labelError_PatrimonyNumber.setForeground(Color.RED);
		labelError_PatrimonyNumber.setBounds(COLUMN3, 170, WIDTH + 90, HEIGHT);
		panel.add(labelError_PatrimonyNumber);
		
		labelError_Brand = new JLabel();
		labelError_Brand.setForeground(Color.RED);
		labelError_Brand.setBounds(COLUMN3, 210, WIDTH + 90, HEIGHT);
		panel.add(labelError_Brand);
		
		labelError_Model = new JLabel();
		labelError_Model.setForeground(Color.RED);
		labelError_Model.setBounds(COLUMN3, 250, WIDTH + 90, HEIGHT);
		panel.add(labelError_Model);
		
		labelError_MemoryRam = new JLabel();
		labelError_MemoryRam.setForeground(Color.RED);
		labelError_MemoryRam.setBounds(COLUMN3, 290, WIDTH + 90, HEIGHT);
		panel.add(labelError_MemoryRam);
		
		labelError_HardDisk = new JLabel();
		labelError_HardDisk.setForeground(Color.RED);
		labelError_HardDisk.setBounds(COLUMN3, 330, WIDTH + 90, HEIGHT);
		panel.add(labelError_HardDisk);
	}

	private void addButtons(JPanel panel) {
		final JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(180, 550, WIDTH - 30, HEIGHT);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(320, 550, WIDTH - 30, HEIGHT);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}
	
	private class buttonSaveListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				final Equipment equipmentNew = getFormData();
				EquipmentService service = new EquipmentService();
				service.update(equipmentOld, equipmentNew);
				model.updateEquipment(lineSelected, equipmentNew);
				dispose();
				JOptionPane.showMessageDialog(rootPane, "Equipment successfully updated", "Success updating object", JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			} 
			catch (DBException e) {
				setErroMessagesDBException(e);
			}
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
	
	private Equipment getFormData() {
		Equipment equipment = new Equipment();
		equipment = (Equipment) equipmentOld.clone();

		ValidationException exception = new ValidationException("Validation error");

		// Validation HostName
		if (textField_HostName.getText() == null || textField_HostName.getText().trim().equals("")) {
			exception.addError("hostName", "Field can't be empty");
		} 
		else if (textField_HostName.getText().length() < 10) {
			exception.addError("hostName", "Invalid HostName - Ex: > 10");
		} 
		else if (Utils.ToCheckHostName(textField_HostName.getText())) {
			exception.addError("hostName", "Invalid format - Ex: 'SPODSK' or 'SPONTB'");
		} 
		else {
			equipment.setHostName(textField_HostName.getText().trim().toUpperCase());
		}

		// Validation AddressMac
		if (textField_AddressMAC.getText() == null || textField_AddressMAC.getText().trim().equals("")) {
			exception.addError("addressMAC", "Field can't be empty");
		} 
		else if (textField_AddressMAC.getText().length() < 16) {
			exception.addError("addressMAC", "Invalid Address MAC - Ex: > 16");
		} 
		else if (Utils.ToCheckAddressMAC(textField_AddressMAC.getText())) {
			exception.addError("addressMAC", "Invalid format - Ex: '0A-00-27-00-00-0B'");
		} 
		else {
			equipment.setAddressMAC(textField_AddressMAC.getText().trim().toUpperCase());
		}

		// Validation Type
		if (comboBox_Type.getSelectedIndex() < 0 || comboBox_Type.getSelectedItem() == null) {
			exception.addError("type", "Field can't be empty");
		} 
		else {
			equipment.setType(comboBox_Type.getSelectedItem().toString());
		}

		// Validation Patrimony Number
		if (textField_PatrimonyNumber.getText() == null || textField_PatrimonyNumber.getText().trim().equals("")) {
			exception.addError("patrimonyNumber", "Field can't be empty");
		} 
		else if (textField_PatrimonyNumber.getText().length() < 4) {
			exception.addError("patrimonyNumber", "Invalid Patrimony Number - Ex: > 4");
		} 
		else {
			equipment.setPatrimonyNumber(textField_PatrimonyNumber.getText().trim().toUpperCase());
		}

		// Validation Brand
		if (comboBox_Brand.getSelectedIndex() < 0
				|| comboBox_Brand.getSelectedItem() == null) {
			exception.addError("brand", "Field can't be empty");
		} 
		else {
			equipment.setBrand(comboBox_Brand.getSelectedItem().toString());
		}

		// Validation Model
		if (comboBox_Model.getSelectedIndex() < 0
				|| comboBox_Model.getSelectedItem() == null) {
			exception.addError("model", "Field can't be empty");
		} 
		else {
			equipment.setModel(comboBox_Model.getSelectedItem().toString());
		}

		// Validation Memory Ram
		if (comboBox_MemoryRam.getSelectedIndex() < 0
				|| comboBox_MemoryRam.getSelectedItem() == null) {
			exception.addError("memoryRam", "Field can't be empty");
		} 
		else {
			equipment.setMemoryRam(comboBox_MemoryRam.getSelectedItem().toString());
		}

		// Validation Hard Disk
		if (comboBox_HardDisk.getSelectedIndex() < 0
				|| comboBox_HardDisk.getSelectedItem() == null) {
			exception.addError("hardDisk", "Field can't be empty");
		} 
		else {
			equipment.setHardDisk(comboBox_HardDisk.getSelectedItem().toString());
		}

		// Insert CostType
		equipment.setCostType(Utils.tryParseToString(comboBox_CostType));

		// Insert Value
		equipment.setValue(Utils.tryParseToDouble(textField_Value.getText()));

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return equipment;
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_HostName.setText(fields.contains("hostName") ? errors.get("hostName") : "");
		labelError_AddressMAC.setText(fields.contains("addressMAC") ? errors.get("addressMAC") : "");
		labelError_Type.setText(fields.contains("type") ? errors.get("type") : "");
		labelError_PatrimonyNumber.setText(fields.contains("patrimonyNumber") ? errors.get("patrimonyNumber") : "");
		labelError_Brand.setText(fields.contains("brand") ? errors.get("brand") : ""); 
		labelError_Model.setText(fields.contains("model") ? errors.get("model") : ""); 
		labelError_MemoryRam.setText(fields.contains("memoryRam") ? errors.get("memoryRam") : "");
		labelError_HardDisk.setText(fields.contains("hardDisk") ?errors.get("hardDisk") : "");
	}
	
	private void setErroMessagesDBException(DBException e) {
		if (e.getMessage().contains("Duplicate entry")) {
			if (e.getMessage().contains("equipments.hostname_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This host name already exists", "Error saving object", JOptionPane.ERROR_MESSAGE);
			} 
			else if (e.getMessage().contains("equipments.addressMAC_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This address MAC already exists", "Error saving object", JOptionPane.ERROR_MESSAGE);
			} 
			else if (e.getMessage().contains("equipments.patrimonyNumberEquipment_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This patrimony number already exists", "Error saving object", JOptionPane.ERROR_MESSAGE);
			} 
			else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
			}
		} 
		else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
