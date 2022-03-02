package model.gui.equipment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import application.LoadData;
import db.DBException;
import exception.ValidationException;
import model.entities.Equipment;
import model.services.equipment.EquipmentService;
import model.services.equipment.EquipmentTableModel;
import model.util.JTextFieldFilter;
import model.util.MyButton;
import model.util.MyComboBox;
import model.util.MyLabel;
import model.util.MyTextField;
import model.util.Utils;

public class NewEquipmentForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private final int SIZE_LABELS = 2;
	private final int SIZE_FIELDS_COMBOX = 3;
	private final int SIZE_LABELS_ERROR = 6;

	private final int SIZEBUTTONS = 1;

	private final int COLOR_LABEL = 1;
	private final int COLOR_LABEL_ERROR = 3;
	
	private final int FONT = 1;

	private final int WIDTH_INTERNAL_PANEL = (150 + 200 + 300) + 40;

	private final int HEIGHT_TOP_PANEL = 10;
	private final int HEIGHT_FIELD_PANEL = 36 * 13;
	private final int HEIGHT_BUTTON_PANEL = 50;

	private final int WIDTH_MAIN_PANEL = WIDTH_INTERNAL_PANEL + 50;
	private final int HEIGHT_MAIN_PANEL = HEIGHT_FIELD_PANEL + HEIGHT_BUTTON_PANEL + 84;
	
	private final EquipmentTableModel model;

	private JTextField textField_SerialNumber;
	private JTextField textField_HostName;
	private JTextField textField_AddressMAC;
	private JComboBox<Object> comboBox_Type;
	private JTextField textField_PatrimonyNumber;
	private JComboBox<Object> comboBox_Brand;
	private JComboBox<Object> comboBox_Model;
	private JComboBox<Object> comboBox_MemoryRam;
	private JComboBox<Object> comboBox_HardDisk;
	private JComboBox<Object> comboBox_CostType;
	private JTextField textField_Value;
	private JTextField textField_NoteEntry;
	private JTextField textField_Note;

	private JLabel labelError_SerialNumber;
	private JLabel labelError_HostName;
	private JLabel labelError_AddressMAC;
	private JLabel labelError_Type;
	private JLabel labelError_PatrimonyNumber;
	private JLabel labelError_Brand;
	private JLabel labelError_Model;
	private JLabel labelError_MemoryRam;
	private JLabel labelError_HardDisk;
	private JLabel labelError_CostType;
	private JLabel labelError_NoteEntry;

	public NewEquipmentForm(EquipmentTableModel model) {
		this.model = model;
		initComponents();
	}

	private void initComponents() {
		setTitle("New Equipment");
		setModal(true);
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
		setPreferredSize(new Dimension(WIDTH_MAIN_PANEL, HEIGHT_MAIN_PANEL));
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		add(createTopPanel());
		add(createFieldsPanel());
		add(createButtonPanel());

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel createTopPanel() {
		final JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_TOP_PANEL));
		panel.setBackground(COLOR1);

		return panel;
	}
	
	private JPanel createFieldsPanel() {
		final JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		fieldsPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_FIELD_PANEL));
		
		final JLabel label_SerialNumber = new MyLabel("Serial Number:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_SerialNumber);

		textField_SerialNumber = new MyTextField("", SIZE_FIELDS_COMBOX);
		textField_SerialNumber.setDocument(new JTextFieldFilter(JTextFieldFilter.SERIAL_NUMBER, 12));
		fieldsPanel.add(textField_SerialNumber);

		labelError_SerialNumber = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_SerialNumber);
		
		final JLabel label_HostName = new MyLabel("Host Name:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_HostName);

		textField_HostName = new MyTextField("", SIZE_FIELDS_COMBOX);
		textField_HostName.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMBERS_AND_LETTERS_NO_SPACE, 11));
		fieldsPanel.add(textField_HostName);

		labelError_HostName = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_HostName);
		
		final JLabel label_AddressMAC = new MyLabel("Address MAC:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_AddressMAC);

		textField_AddressMAC = new MyTextField("", SIZE_FIELDS_COMBOX);
		textField_AddressMAC.setDocument(new JTextFieldFilter(JTextFieldFilter.ADDRESS_MAC, 17));
		fieldsPanel.add(textField_AddressMAC);

		labelError_AddressMAC = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_AddressMAC);
		
		final JLabel label_Type = new MyLabel("Type:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Type);

		comboBox_Type = new MyComboBox(LoadData.getOptionByType("TYPE-EQUIPMENT"), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(comboBox_Type);

		labelError_Type = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Type);
		
		final JLabel label_PatrimonyNumber = new MyLabel("Patrimony Number:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_PatrimonyNumber);

		textField_PatrimonyNumber = new MyTextField("", SIZE_FIELDS_COMBOX);
		textField_PatrimonyNumber.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC, 7));
		fieldsPanel.add(textField_PatrimonyNumber);

		labelError_PatrimonyNumber = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_PatrimonyNumber);
		
		final JLabel label_Brand = new MyLabel("Brand:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Brand);

		comboBox_Brand = new MyComboBox(LoadData.getOptionByType("BRAND-EQUIPMENT"), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(comboBox_Brand);

		labelError_Brand = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Brand);
		
		final JLabel label_Model = new MyLabel("Model:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Model);

		comboBox_Model = new MyComboBox(LoadData.getOptionByType("MODEL-EQUIPMENT"), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(comboBox_Model);

		labelError_Model = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Model);
		
		final JLabel label_MemoryRam = new MyLabel("MemoryRam:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_MemoryRam);

		comboBox_MemoryRam = new MyComboBox(LoadData.getOptionByType("MEMORY-RAM"), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(comboBox_MemoryRam);

		labelError_MemoryRam = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_MemoryRam);
		
		final JLabel label_HardDisk = new MyLabel("HardDisk:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_HardDisk);

		comboBox_HardDisk = new MyComboBox(LoadData.getOptionByType("HARD-DISK"), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(comboBox_HardDisk);

		labelError_HardDisk = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_HardDisk);
		
		final JLabel label_CostType = new MyLabel("CostType:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_CostType);

		comboBox_CostType = new MyComboBox(LoadData.getOptionByType("COST-TYPE"), SIZE_FIELDS_COMBOX);
		fieldsPanel.add(comboBox_CostType);

		labelError_CostType = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_CostType);
		
		final JLabel label_Value = new MyLabel("Value:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Value);

		textField_Value = new MyTextField("", SIZE_FIELDS_COMBOX);
		textField_Value.setDocument(new JTextFieldFilter(JTextFieldFilter.DECIMAL, 5));
		fieldsPanel.add(textField_Value);

		final JLabel labelError_Value_Empty = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Value_Empty);
		
		final JLabel label_NoteEntry = new MyLabel("Note Entry:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_NoteEntry);

		textField_NoteEntry = new MyTextField("", SIZE_FIELDS_COMBOX);
		textField_NoteEntry.setDocument(new JTextFieldFilter(JTextFieldFilter.ALPHA_NUMERIC, 20));
		fieldsPanel.add(textField_NoteEntry);

		labelError_NoteEntry = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_NoteEntry);
		
		final JLabel label_Note = new MyLabel("Note:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Note);

		textField_Note = new MyTextField("", SIZE_FIELDS_COMBOX);
		textField_Note.setDocument(new JTextFieldFilter());
		fieldsPanel.add(textField_Note);

		final JLabel labelError_Note_Empty = new MyLabel("", SIZE_LABELS_ERROR, COLOR_LABEL_ERROR, FONT);
		fieldsPanel.add(labelError_Note_Empty);
		
		return fieldsPanel;
	}
	
	private JPanel createButtonPanel() {
		final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		buttonPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_BUTTON_PANEL));
		buttonPanel.setBackground(COLOR2);

		final JButton buttonSave = new MyButton("Save", SIZEBUTTONS);
		buttonSave.addActionListener(new buttonSaveListener());
		buttonPanel.add(buttonSave);

		final JButton buttonClose = new MyButton("Close", SIZEBUTTONS);
		buttonClose.addActionListener(new buttonCloseListener());
		buttonPanel.add(buttonClose);

		return buttonPanel;
	}

	private class buttonSaveListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				final Equipment equipment = getFormData();
				
				EquipmentService service = new EquipmentService();
				service.save(equipment);
				
				model.addEquipment(equipment);
				
				dispose();
				JOptionPane.showMessageDialog(rootPane, "Equipment successfully added", "Success saving object", JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (ValidationException ve) {
				setErrorMessages(ve.getErrors());
			} 
			catch (DBException db) {
				setErrorMessagesDBException(db);
			}
			finally {
				
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

		ValidationException exception = new ValidationException("Validation error");
		boolean hostNameToCheck = false;
		boolean TypeToCheck = false;

		// validation Serial Number
		if (textField_SerialNumber.getText() == null || textField_SerialNumber.getText().trim().equals("")) {
			exception.addError("serialNumber", "Field can't be empty");
		} 
		else if (textField_SerialNumber.getText().length() < 6) {
			exception.addError("serialNumber", "Invalid Serial Number - Ex: > 6");
		} 
		else {
			equipment.setSerialNumber(textField_SerialNumber.getText().trim().toUpperCase());
		}

		// Validation Host Name
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
			hostNameToCheck = true;
		}

		// Validation Address Mac
		if (textField_AddressMAC.getText() == null || textField_AddressMAC.getText().trim().equals("")) {
			exception.addError("addressMAC", "Field can't be empty");
		} 
		else if (textField_AddressMAC.getText().length() < 16) {
			exception.addError("addressMAC", "Invalid Address MAC - Ex: > 16");
		} 
		else if (Utils.ToCheckAddressMAC(textField_AddressMAC.getText())) {
			exception.addError("addressMAC", "Invalid format - Ex: '##-##-##-##-##-##'");
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
			TypeToCheck = true;
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
		if (comboBox_Brand.getSelectedIndex() < 0 || comboBox_Brand.getSelectedItem() == null) {
			exception.addError("brand", "Field can't be empty");
		} 
		else {
			equipment.setBrand(comboBox_Brand.getSelectedItem().toString());
		}

		// Validation Model
		if (comboBox_Model.getSelectedIndex() < 0 || comboBox_Model.getSelectedItem() == null) {
			exception.addError("model", "Field can't be empty");
		} 
		else {
			equipment.setModel(comboBox_Model.getSelectedItem().toString());
		}

		// Validation Memory Ram
		if (comboBox_MemoryRam.getSelectedIndex() < 0 || comboBox_MemoryRam.getSelectedItem() == null) {
			exception.addError("memoryRam", "Field can't be empty");
		} 
		else {
			equipment.setMemoryRam(comboBox_MemoryRam.getSelectedItem().toString());
		}

		// Validation Hard Disk
		if (comboBox_HardDisk.getSelectedIndex() < 0 || comboBox_HardDisk.getSelectedItem() == null) {
			exception.addError("hardDisk", "Field can't be empty");
		} 
		else {
			equipment.setHardDisk(comboBox_HardDisk.getSelectedItem().toString());
		}

		// Validation CostType
		if (comboBox_CostType.getSelectedIndex() < 0 || comboBox_CostType.getSelectedItem() == null) {
			exception.addError("costType", "Field can't be empty");
		} 
		else {
			equipment.setCostType(comboBox_CostType.getSelectedItem().toString());
		}

		// Insert Value
		equipment.setValue(Utils.tryParseToDouble(textField_Value.getText()));

		// Insert Location
		equipment.setLocation(Utils.getLocationEquipment());

		// Validation Note Entry
		if (textField_NoteEntry.getText() == null || textField_NoteEntry.getText().trim().equals("")) {
			exception.addError("noteEntry", "Field can't be empty");
		} 
		else if (textField_NoteEntry.getText().length() < 4) {
			exception.addError("noteEntry", "Invalid Note Entry - Ex: > 4");
		} 
		else {
			equipment.setNoteEntry(textField_NoteEntry.getText().trim().toUpperCase());
		}
		
		// Insert Note
		equipment.setNote(textField_Note.getText().trim().toUpperCase());

		//Validation Host Name and Type
		if (hostNameToCheck && TypeToCheck) {
			if (Utils.ToCheckHostNameAndType(equipment.getHostName(), equipment.getType())) {
				exception.addError("hostName", "Field without pattern");
				exception.addError("type", "Field without pattern");
			}
		}

		// Insert Status
		equipment.setStatus("STAND BY");

		// Insert DateEntry
		equipment.setDateEntry(new Date());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return equipment;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_SerialNumber.setText(fields.contains("serialNumber") ? errors.get("serialNumber") : "");
		labelError_HostName.setText(fields.contains("hostName") ? errors.get("hostName") : "");
		labelError_AddressMAC.setText(fields.contains("addressMAC") ? errors.get("addressMAC") : "");
		labelError_Type.setText(fields.contains("type") ? errors.get("type") : "");
		labelError_PatrimonyNumber.setText(fields.contains("patrimonyNumber") ? errors.get("patrimonyNumber") : "");
		labelError_Brand.setText(fields.contains("brand") ? errors.get("brand") : "");
		labelError_Model.setText(fields.contains("model") ? errors.get("model") : "");
		labelError_MemoryRam.setText(fields.contains("memoryRam") ? errors.get("memoryRam") : "");
		labelError_HardDisk.setText(fields.contains("hardDisk") ? errors.get("hardDisk") : "");
		labelError_CostType.setText(fields.contains("costType") ? errors.get("costType") : "");
		labelError_NoteEntry.setText(fields.contains("noteEntry") ? errors.get("noteEntry") : "");
	}

	private void setErrorMessagesDBException(DBException e) {
		if (e.getMessage().contains("Duplicate entry")) {
			if (e.getMessage().contains("equipments.PRIMARY")) {
				JOptionPane.showMessageDialog(rootPane, "This serial number already exists", "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			} 
			else if (e.getMessage().contains("equipments.hostname_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This host name already exists", "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			} 
			else if (e.getMessage().contains("equipments.addressMAC_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This address MAC already exists", "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			} 
			else if (e.getMessage().contains("equipments.patrimonyNumber_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This patrimony number already exists", "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			} 
			else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object",
						JOptionPane.ERROR_MESSAGE);
			}
		} 
		else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
