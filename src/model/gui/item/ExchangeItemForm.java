package model.gui.item;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import application.LoadData;
import db.DBException;
import exception.ObjectException;
import model.entities.Equipment;
import model.entities.License;
import model.entities.Monitor;
import model.entities.Peripheral;
import model.entities.User;
import model.entities.utilitary.Item;
import model.services.equipment.EquipmentService;
import model.services.itens.CreatePDFFileExchange;
import model.services.itens.ItemTableModel;
import model.services.license.LicenseService;
import model.services.monitor.MonitorService;
import model.services.peripheral.PeripheralService;
import model.util.MyButton;
import model.util.MyComboBox;
import model.util.MyLabel;

public class ExchangeItemForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);
	
	private final int SIZE_LABELS = 9;
	private final int SIZE_LABELS_SHOW = 3;

	private final int SIZEBUTTONS = 1;

	private final int COLOR_LABEL = 1;
	private final int COLOR_LABEL_SHOW = 3;
	
	private final int FONT = 1;

	private final int WIDTH_INTERNAL_PANEL = (320 + 320) + 20;

	private final int HEIGHT_TOP_PANEL = 10;
	private final int HEIGHT_FIELD_PANEL = 42 * 5;
	private final int HEIGHT_BUTTON_PANEL = 50;

	private final int WIDTH_MAIN_PANEL = WIDTH_INTERNAL_PANEL + 50;
	private final int HEIGHT_MAIN_PANEL = HEIGHT_FIELD_PANEL + HEIGHT_BUTTON_PANEL + 84;
	
	private JComboBox<Object> comboBox;
	
	private JLabel label_Show_Type;
	private JLabel label_Show_Code;
	private JLabel label_Show_Name;
	
	final User user;
	
	private Object selectedObject;
	
	private final ItemTableModel model;
	private final int lineSelected;
	private final Item itemOld;
	private Item itemNew;
	private final int type;

	public ExchangeItemForm(ItemTableModel model, int lineSelected, Item itemOld, User user, int type) {
		this.model = model;
		this.lineSelected = lineSelected;
		this.itemOld = itemOld;
		this.user = user;
		this.type = type;
		initComponents();
	}
	
	private void initComponents() {
		setTitle("Exchange Item");
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
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_FIELD_PANEL));
		
		panel.add(createPanelFieldOld());
		panel.add(createPanelFieldNew());
		
		return panel;
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
	
	private JPanel createPanelFieldOld() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panel.setPreferredSize(new Dimension(320, 200));

		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Equipment Old");
		panel.setBorder(title);
		
		{//Panel Internal Empty
			final JPanel panelInternal = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 5));
			panelInternal.setPreferredSize(new Dimension(250, 40));
			
			panel.add(panelInternal);
		}

		final JLabel label_Type = new MyLabel("Type:", SIZE_LABELS, COLOR_LABEL, FONT);
		panel.add(label_Type);

		final JLabel label_Show_Type = new MyLabel(itemOld.getType(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		panel.add(label_Show_Type);
		
		final JLabel label_Code = new MyLabel("Code:", SIZE_LABELS, COLOR_LABEL, FONT);
		panel.add(label_Code);

		final JLabel label_Show_Code = new MyLabel(itemOld.getCode(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		panel.add(label_Show_Code);
		
		final JLabel label_Name = new MyLabel("Name:", SIZE_LABELS, COLOR_LABEL, FONT);
		panel.add(label_Name);

		final JLabel label_Show_Name = new MyLabel(itemOld.getName(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		panel.add(label_Show_Name);

		return panel;
	}
	
	private JPanel createPanelFieldNew() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panel.setPreferredSize(new Dimension(320, 200));

		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Equipment New");
		panel.setBorder(title);
		
		
		{//Panel Internal for ComboBox
			final JPanel panelInternal = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 5));
			panelInternal.setPreferredSize(new Dimension(250, 40));

			comboBox = new MyComboBox(getObject(), 3);
			comboBox.addItemListener(new ItemChangeObjectListener());
			
			panelInternal.add(comboBox);
			
			panel.add(panelInternal);
		}
		
		final JLabel label_Type = new MyLabel("Type:", SIZE_LABELS, COLOR_LABEL, FONT);
		panel.add(label_Type);

		label_Show_Type = new MyLabel("", SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		panel.add(label_Show_Type);
		
		final JLabel label_Code = new MyLabel("Code:", SIZE_LABELS, COLOR_LABEL, FONT);
		panel.add(label_Code);

		label_Show_Code = new MyLabel("", SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		panel.add(label_Show_Code);
		
		final JLabel label_Name = new MyLabel("Name:", SIZE_LABELS, COLOR_LABEL, FONT);
		panel.add(label_Name);

		label_Show_Name = new MyLabel("", SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		panel.add(label_Show_Name);

		return panel;
	}

	//
	private void setObject() {
		// create Item
		final Item item = new Item();

		if (type == 1) {
			Equipment equipment = (Equipment) selectedObject;
			
			item.setIndex(itemOld.getIndex());
			item.setType("Equipment");
			item.setCode(equipment.getSerialNumber());
			item.setName(equipment.getType() + " " + equipment.getBrand());
			item.setPatrimonyNumber(equipment.getPatrimonyNumber());
			item.setValue(equipment.getValue());
			
		} 
		else if (type == 2) {
			Monitor monitor = (Monitor) selectedObject;
			
			item.setIndex(itemOld.getIndex());
			item.setType("Monitor");
			item.setCode(monitor.getSerialNumber());
			item.setName(monitor.getModel());
			item.setPatrimonyNumber(monitor.getPatrimonyNumber());
			item.setValue(monitor.getValue());
		} 
		else if (type == 3) {
			Peripheral peripheral = (Peripheral) selectedObject;
			
			item.setIndex(itemOld.getIndex());
			item.setType("Peripheral");
			item.setCode(peripheral.getCode());
			item.setName(peripheral.getName() + " " + peripheral.getBrand());
			item.setValue(peripheral.getValue());
		} 
		else if (type == 4) {
			License license = (License) selectedObject;
			
			item.setIndex(itemOld.getIndex());
			item.setType("License");
			item.setCode(license.getCode());
			item.setName(license.getName() + " " + license.getBrand());
			item.setValue(license.getValue());
		}
		
		itemNew = item;

		label_Show_Type.setText(item.getType());
		label_Show_Code.setText(item.getCode());
		label_Show_Name.setText(item.getName());
	}
	
	//Get list for ComboBox
	private List<Object> getObject() {
		if (type == 1) {
			List<Equipment> list = LoadData.getEquipmentsList();
			return list.stream().filter(p -> p.getStatus().equals("STAND BY")).collect(Collectors.toList());
		} 
		else if (type == 2) {
			List<Monitor> list = LoadData.getMonitorsList();
			return list.stream().filter(p -> p.getStatus().equals("STAND BY")).collect(Collectors.toList());
		} 
		else if (type == 3) {
			List<Peripheral> list = LoadData.getPeripheralsList();
			return list.stream().filter(p -> p.getStatus().equals("ACTIVE")).collect(Collectors.toList());
		} 
		else if (type == 4) {
			List<License> list = LoadData.getLicensesList();
			return list.stream().filter(p -> p.getStatus().equals("ACTIVE")).collect(Collectors.toList());
		}
		return null;
	}
	
	private class ItemChangeObjectListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				selectedObject = event.getItem();
				setObject();
			}
		}
	}
	
	private void uptdateItem() {
		if (itemOld != null && itemNew != null) {
			if (type == 1) {
				final EquipmentService service = new EquipmentService();
				
				final Equipment equipmentOld = LoadData.getEquipment(itemOld.getCode());
				final Equipment equipmentNew = LoadData.getEquipment(itemNew.getCode());
				
				service.exchangeForUser(equipmentOld, equipmentNew, user);
			} 
			else if (type == 2) {
				final MonitorService service = new MonitorService();
				
				final Monitor monitorOld = LoadData.getMonitor(itemOld.getCode());
				final Monitor monitorNew = LoadData.getMonitor(itemNew.getCode());
				
				service.removeForUser(monitorOld, user);
				service.addForUser(monitorNew, user);
			} 
			else if (type == 3) {
				final PeripheralService service = new PeripheralService();
				
				final Peripheral peripheralOld = LoadData.getPeripheral(itemOld.getCode());
				final Peripheral peripheralNew = LoadData.getPeripheral(itemNew.getCode());
				
				service.removeForUser(peripheralOld, user);
				service.addForUser(peripheralNew, user);
			} 
			else if (type == 4) {
				final LicenseService service = new LicenseService();
				
				License licenseOld = LoadData.getLicense(itemOld.getCode());
				License licenseNew = LoadData.getLicense(itemNew.getCode());

				service.removeForUser(licenseOld, user);
				service.addForUser(licenseNew, user);
			}
		} else {
			throw new ObjectException("There is no change");
		}
	}
	
	private void createTerm() {		
		new CreatePDFFileExchange(user, itemOld, itemNew);
	}
	
	private class buttonSaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				uptdateItem();

				createTerm();

				model.updateItem(lineSelected, itemNew);

				dispose();
				JOptionPane.showMessageDialog(rootPane, "Item successfully updated", "Success updating item", JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (ObjectException oe) {
				JOptionPane.showMessageDialog(null, "There is no object to exchange", "Unable to updating", JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (DBException db) {
				JOptionPane.showMessageDialog(rootPane, db.getMessage(), "Error updating item", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class buttonCloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
}
