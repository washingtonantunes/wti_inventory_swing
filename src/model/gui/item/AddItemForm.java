package model.gui.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import db.DBException;
import exception.ObjectException;
import model.entities.Equipment;
import model.entities.License;
import model.entities.Monitor;
import model.entities.Peripheral;
import model.entities.User;
import model.entities.utilitay.Item;
import model.entities.utilitay.ItemDelivery;
import model.gui.MainWindow;
import model.services.equipment.EquipmentService;
import model.services.itens.CreatePDFFileDelivery;
import model.services.itens.DeliveryTableModel;
import model.services.itens.ItemTableModel;
import model.services.itens.TableItem;
import model.services.license.LicenseService;
import model.services.monitor.MonitorService;
import model.services.peripheral.PeripheralService;
import model.services.user.UserService;
import model.util.MyButton;
import model.util.MyComboBox;

public class AddItemForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private JScrollPane scrollPane;
	private TableItem table;
	private DeliveryTableModel modelDelivery;
	
	private User user;

	private JComboBox<Object> comboBox_Equipment;
	private JComboBox<Object> comboBox_Monitor;
	private JComboBox<Object> comboBox_Peripheral;
	private JComboBox<Object> comboBox_License;
	
	private JButton buttonOKEquipment;
	private JButton buttonOKMonitor;
	private JButton buttonOKPeripheral;
	private JButton buttonOKLicense;

	private final List<Equipment> equipments;
	private final List<Monitor> monitors;
	private final List<Peripheral> peripherals;
	private final List<License> licenses;
	private final List<Item> itens;

	private Equipment selectedEquipment;
	private Monitor selectedMonitor;
	private Peripheral selectedPeripheral;
	private License selectedLicense;

	public AddItemForm(ItemTableModel modelOld, User user, List<Item> itens) {
		this.user = user;
		this.equipments = loadDataEquipments();
		this.monitors = loadDataMonitors();
		this.peripherals = loadDataPeripherals();
		this.licenses = loadDataLicenses();
		this.itens = itens;
		initComponents();
	}

	private void initComponents() {
		setTitle("Add Item");
		setModal(true);
		setPreferredSize(new Dimension(900, 600));
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);	
		
		add(createPanelWest(), BorderLayout.WEST);
		add(createTable(), BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel createPanelWest() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.setPreferredSize(new Dimension(320, 600));

		panel.add(createPanelComboBox());
		panel.add(createPanelButton());

		return panel;
	}

	private JPanel createPanelComboBox() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
		panel.setPreferredSize(new Dimension(320, 430));
		panel.setBackground(COLOR1);

		panel.add(createPanelEquipment());
		panel.add(createPanelMonitor());
		panel.add(createPanelPeripheral());
		panel.add(createPanelLicense());

		return panel;
	}

	private JPanel createPanelButton() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
		panel.setPreferredSize(new Dimension(320, 150));
		panel.setBackground(COLOR2);

		final JButton buttonSave = new MyButton("Save", 3);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonRemove = new MyButton("Remove", 3);
		buttonRemove.addActionListener(new buttonRemoveListener());
		panel.add(buttonRemove);

		final JButton buttonClose = new MyButton("Close", 3);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);

		return panel;
	}

	private JScrollPane createTable() {
		modelDelivery = new DeliveryTableModel();

		table = new TableItem(modelDelivery);

		scrollPane = new JScrollPane(table);
		return scrollPane;
	}

	private JPanel createPanelEquipment() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Equipment");
		panel.setBorder(title);

		comboBox_Equipment = new MyComboBox(
				equipments.stream().filter(p -> p.getStatus().equals("STAND BY")).collect(Collectors.toList()), 3);
		comboBox_Equipment.addItemListener(new ItemChangeEquipmentListener());
		panel.add(comboBox_Equipment);
		
		buttonOKEquipment = new MyButton("OK", 5);
		buttonOKEquipment.addActionListener(new buttonOKEquipmentListener());
		panel.add(buttonOKEquipment);
		
		getRootPane().setDefaultButton(buttonOKEquipment);
		
		if (checkContains(1, true, false)) {
			comboBox_Equipment.setEnabled(false);
			buttonOKEquipment.setEnabled(false);
		}

		return panel;
	}

	private JPanel createPanelMonitor() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Monitor");
		panel.setBorder(title);

		comboBox_Monitor = new MyComboBox(monitors.stream().filter(p -> p.getStatus().equals("STAND BY")).collect(Collectors.toList()), 3);
		comboBox_Monitor.addItemListener(new ItemChangeMonitorListener());
		panel.add(comboBox_Monitor);
		
		buttonOKMonitor = new MyButton("OK", 5);
		buttonOKMonitor.addActionListener(new buttonOKMonitorListener());
		panel.add(buttonOKMonitor);
		
		getRootPane().setDefaultButton(buttonOKMonitor);
		
		if (checkContains(2, true, false)) {
			comboBox_Monitor.setEnabled(false);
			buttonOKMonitor.setEnabled(false);
		}

		return panel;
	}

	private JPanel createPanelPeripheral() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Peripheral");
		panel.setBorder(title);

		comboBox_Peripheral = new MyComboBox(peripherals.stream().filter(p -> p.getStatus().equals("ACTIVE")).collect(Collectors.toList()), 3);
		comboBox_Peripheral.addItemListener(new ItemChangePeripheralListener());
		panel.add(comboBox_Peripheral);
		
		buttonOKPeripheral = new MyButton("OK", 5);
		buttonOKPeripheral.addActionListener(new buttonOKPeripheralListener());
		panel.add(buttonOKPeripheral);
		
		getRootPane().setDefaultButton(buttonOKPeripheral);
		
		if (checkContains(3, true, false)) {
			comboBox_Peripheral.setEnabled(false);
			buttonOKPeripheral.setEnabled(false);
		}

		return panel;
	}

	private JPanel createPanelLicense() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "License");
		panel.setBorder(title);

		comboBox_License = new MyComboBox(licenses.stream().filter(p -> p.getStatus().equals("ACTIVE")).collect(Collectors.toList()), 3);
		comboBox_License.addItemListener(new ItemChangeLicenseListener());
		panel.add(comboBox_License);
		
		buttonOKLicense = new MyButton("OK", 5);
		buttonOKLicense.addActionListener(new buttonOKLicenseListener());
		panel.add(buttonOKLicense);
		
		getRootPane().setDefaultButton(buttonOKLicense);
		
		if (checkContains(4, true, false)) {
			comboBox_License.setEnabled(false);
			buttonOKLicense.setEnabled(false);
		}

		return panel;
	}

	private List<Equipment> loadDataEquipments() {
		Map<String, Equipment> equipments = MainWindow.getEquipments();
		List<Equipment> list = new ArrayList<Equipment>();

		for (String entry : equipments.keySet()) {
			list.add(equipments.get(entry));
		}

		list.sort((e1, p2) -> e1.getSerialNumber().compareTo(p2.getSerialNumber()));
		return list;
	}

	private List<Monitor> loadDataMonitors() {
		Map<String, Monitor> monitors = MainWindow.getMonitors();
		List<Monitor> list = new ArrayList<Monitor>();

		for (String entry : monitors.keySet()) {
			list.add(monitors.get(entry));
		}

		list.sort((m1, m2) -> m1.getSerialNumber().compareTo(m2.getSerialNumber()));
		return list;
	}

	private List<Peripheral> loadDataPeripherals() {
		Map<String, Peripheral> peripherals = MainWindow.getPeripherals();
		List<Peripheral> list = new ArrayList<Peripheral>();

		for (String entry : peripherals.keySet()) {
			list.add(peripherals.get(entry));
		}

		list.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
		return list;
	}

	private List<License> loadDataLicenses() {
		Map<String, License> licenses = MainWindow.getLicenses();
		List<License> list = new ArrayList<License>();

		for (String entry : licenses.keySet()) {
			list.add(licenses.get(entry));
		}

		list.sort((l1, l2) -> l1.getName().compareTo(l2.getName()));
		return list;
	}
	
	public List<Item> getItens() {
		return itens;
	}
	
	private void addItem() {		
		if (checkContains(1, false, true)) {
			EquipmentService service = new EquipmentService();
			List<ItemDelivery> list = modelDelivery.getItemDelivery("Equipment");
			
			for (ItemDelivery itemDelivery : list) {
				Equipment equipment = MainWindow.getEquipment(itemDelivery.getName());
				
				final Item item = new Item();
				item.setIndex(itens.size() + 1);
				item.setType("Equipment");
				item.setCode(equipment.getSerialNumber());
				item.setName(equipment.getSerialNumber());
				item.setBrand(equipment.getBrand());
				item.setValue(equipment.getValue());
				itens.add(item);
				
				equipment.setStatus("IN USE");
				equipment.setUser(user);
				service.updateStatusForUser(equipment);
				
				user.setEquipment(equipment);
			}
		}
		
		if (checkContains(2, false, true)) {
			MonitorService service = new MonitorService();
			List<ItemDelivery> list = modelDelivery.getItemDelivery("Monitor");
			
			for (ItemDelivery itemDelivery : list) {
				Monitor monitor = MainWindow.getMonitor(itemDelivery.getName());
				
				final Item item = new Item();
				item.setIndex(itens.size() + 1);
				item.setType("Monitor");
				item.setCode(monitor.getSerialNumber());
				item.setName(monitor.getSerialNumber());
				item.setBrand(monitor.getBrand());
				item.setValue(monitor.getValue());
				itens.add(item);
				
				monitor.setStatus("IN USE");
				monitor.setUser(user);
				service.updateStatusForUser(monitor);
				
				user.setMonitor1(monitor);
			}
		}
		
		if (checkContains(3, false, true)) {
			PeripheralService service = new PeripheralService();
			List<ItemDelivery> listItemDelivery = modelDelivery.getItemDelivery("Peripheral");
			
			for (ItemDelivery itemDelivery : listItemDelivery) {
				Peripheral peripheral = MainWindow.getPeripheral(itemDelivery.getCode());
				
				final Item item = new Item();
				item.setIndex(itens.size() + 1);
				item.setType("Peripheral");
				item.setCode(peripheral.getCode());
				item.setName(peripheral.getName());
				item.setBrand(peripheral.getBrand());
				item.setValue(peripheral.getValue());
				itens.add(item);
				
				peripheral.setQuantity(peripheral.getQuantity() - 1);
				peripheral.setUser(user.getName());
				service.updateQuantity(peripheral, user, 1);
				
				user.addPeripheral(peripheral);
			}
			
		}
		
		if (checkContains(4, false, true)) {
			LicenseService service = new LicenseService();
			List<ItemDelivery> list = modelDelivery.getItemDelivery("License");
			
			for (ItemDelivery itemDelivery : list) {
				License license = MainWindow.getLicense(itemDelivery.getCode());
				
				final Item item = new Item();
				item.setIndex(itens.size() + 1);
				item.setType("License");
				item.setCode(license.getCode());
				item.setName(license.getName());
				item.setBrand(license.getBrand());
				item.setValue(license.getValue());
				itens.add(item);
				
				license.setQuantity(license.getQuantity() - 1);
				license.setUser(user.getName());
				service.updateQuantity(license, user, 1);
			}
		}
		
	}
	
	private void createTerm() {
		new CreatePDFFileDelivery(user, itens);
		
	}

	//Returns the Item of the comboBox according to the maximum amount allowed.
	private void returnItemComboBox(ItemDelivery item) {
		final String code = item.getCode();
		final String type = item.getType();

		if (type.equals("Equipment")) {			
			Equipment equipment = MainWindow.getEquipment(code);

			comboBox_Equipment.addItem(equipment);
			
			if (!checkContains(1, true, true)) {
				comboBox_Equipment.setEnabled(true);
				buttonOKEquipment.setEnabled(true);
			}
		} 
		else if (type.equals("Monitor")) {
			Monitor monitor = MainWindow.getMonitor(code);

			comboBox_Monitor.addItem(monitor);
			
			if (!checkContains(2, true, true)) {
				comboBox_Monitor.setEnabled(true);
				buttonOKMonitor.setEnabled(true);
			}
		}
		else if (type.equals("Peripheral")) {
			Peripheral peripheral = MainWindow.getPeripheral(code);

			comboBox_Peripheral.addItem(peripheral);
			
			if (!checkContains(3, true, true)) {
				comboBox_Peripheral.setEnabled(true);
				buttonOKPeripheral.setEnabled(true);
			}
		}
		else if (type.equals("License")) {
			License license = MainWindow.getLicense(code);

			comboBox_License.addItem(license);
			
			if (!checkContains(4, true, true)) {
				comboBox_License.setEnabled(true);
				buttonOKLicense.setEnabled(true);
			}
		}
	}

	//Removes the Item from the comboBox according to the maximum amount allowed.
	private void removeItemComboBox(int type) {
		if (type == 1) {
			
			final ItemDelivery itemDelivery = new ItemDelivery();
			itemDelivery.setType("Equipment");
			itemDelivery.setCode(selectedEquipment.getSerialNumber());
			itemDelivery.setName(selectedEquipment.getType());
			
			comboBox_Equipment.setSelectedIndex(-1);
			comboBox_Equipment.removeItem(selectedEquipment);
			
			modelDelivery.addItem(itemDelivery);
			
			if (checkContains(1, true, true)) {
				comboBox_Equipment.setEnabled(false);
				buttonOKEquipment.setEnabled(false);
			}
			
		} 
		else if (type == 2) {
		
			final ItemDelivery itemDelivery = new ItemDelivery();
			itemDelivery.setType("Monitor");
			itemDelivery.setCode(selectedMonitor.getSerialNumber());
			itemDelivery.setName(selectedMonitor.getModel());
			
			comboBox_Monitor.setSelectedIndex(-1);
			comboBox_Monitor.removeItem(selectedMonitor);
			
			modelDelivery.addItem(itemDelivery);
			
			if (checkContains(2, true, true)) {
				comboBox_Monitor.setEnabled(false);
				buttonOKMonitor.setEnabled(false);
			}
			
		} 
		else if (type == 3) {
			
			final ItemDelivery itemDelivery = new ItemDelivery();
			itemDelivery.setType("Peripheral");
			itemDelivery.setCode(selectedPeripheral.getCode());
			itemDelivery.setName(selectedPeripheral.getName() + " " + selectedPeripheral.getBrand());
			
			comboBox_Peripheral.setSelectedIndex(-1);
			comboBox_Peripheral.removeItem(selectedPeripheral);
			
			modelDelivery.addItem(itemDelivery);
			
			if (checkContains(3, true, true)) {
				comboBox_Peripheral.setEnabled(false);
				buttonOKPeripheral.setEnabled(false);
			}
			
		} 
		else if (type == 4) {
			
			final ItemDelivery itemDelivery = new ItemDelivery();
			itemDelivery.setType("License");
			itemDelivery.setCode(selectedLicense.getCode());
			itemDelivery.setName(selectedLicense.getName() + " " + selectedLicense.getBrand());
			
			comboBox_License.setSelectedIndex(-1);
			comboBox_License.removeItem(selectedLicense);
			
			modelDelivery.addItem(itemDelivery);
			
			if (checkContains(4, true, true)) {
				comboBox_License.setEnabled(false);
				buttonOKLicense.setEnabled(false);
			}
		}
	}
	
	//Check the quantity of each object type
	private boolean checkContains(int type, boolean checkItem, boolean checkItemDelivery) {
		int quantityEquipment = 0;
		int quantityMonitor = 0;
		int quantityPeripheral = 0;
		int quantityLicense = 0;
		
		if (checkItem) {
			for (Item item : itens) {
				String typeItem = item.getType(); 
				
				if (typeItem.equals("Equipment")) {
					++quantityEquipment;
				} else if (typeItem.equals("Monitor")) {
					++quantityMonitor;
				} else if (typeItem.equals("Peripheral")) {
					++quantityPeripheral;
				} else if (typeItem.equals("License")) {
					++quantityLicense;
				} 
			}
		}
		
		if (checkItemDelivery) {
			for (ItemDelivery itemDelivery : modelDelivery.getItems()) {
				String typeItem = itemDelivery.getType();

				if (typeItem.equals("Equipment")) {
					++quantityEquipment;
				} else if (typeItem.equals("Monitor")) {
					++quantityMonitor;
				} else if (typeItem.equals("Peripheral")) {
					++quantityPeripheral;
				} else if (typeItem.equals("License")) {
					++quantityLicense;
				}
			}
		}
		
		if (checkItem || checkItem && checkItemDelivery) {
			if (type == 1) {
				if (quantityEquipment >= 1) {
					return true;
				} else { 
					return false;
				}
			} else if (type == 2) {
				if (quantityMonitor >= 2) {
					return true;
				} else { 
					return false;
				}
			} else if (type == 3) {
				if (quantityPeripheral >= 5) {
					return true;
				} else { 
					return false;
				}
			} else if (type == 4) {
				if (quantityLicense >= 1) {
					return true;
				} else { 
					return false;
				}
			}
		}
		
		if (checkItemDelivery) {
			if (type == 1) {
				if (quantityEquipment >= 1) {
					return true;
				} else { 
					return false;
				}
			} else if (type == 2) {
				if (quantityMonitor >= 1) {
					return true;
				} else { 
					return false;
				}
			} else if (type == 3) {
				if (quantityPeripheral >= 1) {
					return true;
				} else { 
					return false;
				}
			} else if (type == 4) {
				if (quantityLicense >= 1) {
					return true;
				} else { 
					return false;
				}
			}
		}
		return false;
	}
	
	private class buttonSaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (modelDelivery.getRowCount() != 0) {
				try {
					addItem();
					createTerm();
					dispose();
				} catch (DBException db) {
					JOptionPane.showMessageDialog(rootPane, db.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
				} catch (ObjectException oe) {
					JOptionPane.showMessageDialog(rootPane, oe.getMessage(), "Error saving document", JOptionPane.ERROR_MESSAGE);
				}
			} 
			else {
				JOptionPane.showMessageDialog(null, "There is no data to save", "Unable to Save", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private class buttonRemoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int lineSelected = -1;
			lineSelected = table.getSelectedRow();
			if (lineSelected < 0) {
				JOptionPane.showMessageDialog(null, "It is necessary to select a line", "No lines selected",
						JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				ItemDelivery ItemDelivery = modelDelivery.getItemDelivery(lineSelected);
				modelDelivery.removeItem(lineSelected);
				returnItemComboBox(ItemDelivery);
			}
		}
	}
	
	private class buttonCloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	private class buttonOKEquipmentListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (selectedEquipment != null) {
				removeItemComboBox(1);		
				selectedEquipment = null;
			}
		}
	}

	private class buttonOKMonitorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (selectedMonitor != null) {
				removeItemComboBox(2);	
				selectedMonitor = null;
			}
		}
	}

	private class buttonOKPeripheralListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (selectedPeripheral != null) {
				removeItemComboBox(3);			
				selectedPeripheral = null;
			}
		}
	}

	private class buttonOKLicenseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (selectedLicense != null) {
				removeItemComboBox(4);	
				selectedLicense = null;
			}
		}
	}

	private class ItemChangeEquipmentListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Equipment equipment = (Equipment) event.getItem();
				if (selectedEquipment == null || !equipment.equals(selectedEquipment)) {
					selectedEquipment = equipment;
//					type = 1;
				}
			}
		}
	}

	private class ItemChangeMonitorListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Monitor monitor = (Monitor) event.getItem();
				if (selectedMonitor == null || !monitor.equals(selectedMonitor)) {
					selectedMonitor = monitor;
//					type = 2;
				}
			}
		}
	}

	private class ItemChangePeripheralListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Peripheral peripheral = (Peripheral) event.getItem();
				if (selectedPeripheral == null || !peripheral.equals(selectedPeripheral)) {
					selectedPeripheral = peripheral;
//					type = 3;
				}
			}
		}
	}

	private class ItemChangeLicenseListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				License license = (License) event.getItem();
				if (selectedLicense == null || !license.equals(selectedLicense)) {
					selectedLicense = license;
				}
			}
		}
	}
}
