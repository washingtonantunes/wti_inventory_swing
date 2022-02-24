package model.gui.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
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
import model.entities.Item;
import model.entities.ItemDelivery;
import model.entities.License;
import model.entities.Monitor;
import model.entities.Peripheral;
import model.entities.User;
import model.gui.MainWindow;
import model.services.equipment.EquipmentService;
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
	private ItemTableModel modelOld;
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

	private Map<String, Equipment> equipmentsAdd = new HashMap<String, Equipment>();
	private Map<String, Monitor> monitorsAdd = new HashMap<String, Monitor>();
	private Map<String, Peripheral> peripheralsAdd = new HashMap<String, Peripheral>();
	private Map<String, License> licensesAdd = new HashMap<String, License>();

	private Object selectedObject;
	private int type = 0;

	public AddItemForm(ItemTableModel modelOld, User user, List<Item> itens) {
		this.user = user;
		this.modelOld = modelOld;
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
		setPreferredSize(new Dimension(770, 600));
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);	
		
		add(createPanelWest(), BorderLayout.WEST);
		add(createTable(), BorderLayout.EAST);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel createPanelWest() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.setPreferredSize(new Dimension(300, 600));

		panel.add(createPanelComboBox());
		panel.add(createPanelButton());

		return panel;
	}

	private JPanel createPanelComboBox() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
		panel.setPreferredSize(new Dimension(300, 430));
		panel.setBackground(COLOR1);

		panel.add(createPanelEquipment());
		panel.add(createPanelMonitor());
		panel.add(createPanelPeripheral());
		panel.add(createPanelLicense());

		return panel;
	}

	private JPanel createPanelButton() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
		panel.setPreferredSize(new Dimension(300, 150));
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
				equipments.stream().filter(p -> p.getStatus().equals("STAND BY")).collect(Collectors.toList()), 2);
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

		comboBox_Monitor = new MyComboBox(monitors.stream().filter(p -> p.getStatus().equals("STAND BY")).collect(Collectors.toList()), 2);
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

		comboBox_Peripheral = new MyComboBox(peripherals.stream().filter(p -> p.getStatus().equals("ACTIVE")).collect(Collectors.toList()), 2);
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

		comboBox_License = new MyComboBox(licenses.stream().filter(p -> p.getStatus().equals("ACTIVE")).collect(Collectors.toList()), 2);
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

		//list = list.stream().filter(e -> e.getStatus().equals("ACTIVE")).collect(Collectors.toList());
		list.sort((e1, p2) -> e1.getSerialNumber().compareTo(p2.getSerialNumber()));
		return list;
	}

	private List<Monitor> loadDataMonitors() {
		Map<String, Monitor> monitors = MainWindow.getMonitors();
		List<Monitor> list = new ArrayList<Monitor>();

		for (String entry : monitors.keySet()) {
			list.add(monitors.get(entry));
		}

		//list = list.stream().filter(m -> m.getStatus().equals("ACTIVE")).collect(Collectors.toList());
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
//		if (equipmentsAdd.keySet().size() > 1) {
//			throw new ObjectException("more equipment");
//		}
//		
//		if (monitorsAdd.keySet().size() > 2) {
//			throw new ObjectException("more monitors");
//		}
		
		if (checkContains(1, false, true)) {
			List<ItemDelivery> list = modelDelivery.getItemDelivery("Equipment");
			
			for (ItemDelivery itemDelivery : list) {
				Equipment equipment = MainWindow.getEquipment(itemDelivery.getName());
				itens.add(new Item(itens.size() + 1, "Equipment", equipment.getSerialNumber(), equipment.getValue()));
			}
		}
		
		if (checkContains(2, false, true)) {
			List<ItemDelivery> list = modelDelivery.getItemDelivery("Monitor");
			
			for (ItemDelivery itemDelivery : list) {
				Monitor monitor = MainWindow.getMonitor(itemDelivery.getName());
				itens.add(new Item(itens.size() + 1, "Monitor", monitor.getSerialNumber(), monitor.getValue()));
			}
		}
		
		
		if (checkContains(3, false, true)) {
			List<ItemDelivery> list = modelDelivery.getItemDelivery("Peripheral");
			
			for (ItemDelivery itemDelivery : list) {
				Peripheral peripheral = MainWindow.getPeripheral(itemDelivery.getName());
				itens.add(new Item(itens.size() + 1, "Peripheral", peripheral.getName(), peripheral.getValue()));
			}
		}
		
		if (checkContains(4, false, true)) {
			List<ItemDelivery> list = modelDelivery.getItemDelivery("License");
			
			for (ItemDelivery itemDelivery : list) {
				License license = MainWindow.getLicense(itemDelivery.getName());
				itens.add(new Item(itens.size() + 1, "License", license.getName(), license.getValue()));
			}
		}
		
//		int index = itens.size();
//		
//		for (String equipmentadd : equipmentsAdd.keySet()) {
//			Equipment equipment = equipmentsAdd.get(equipmentadd);
//			itens.add(new Item(++index, "Equipment", equipment.getSerialNumber(), equipment.getValue()));
//		}
//		
//		for (String monitoradd : monitorsAdd.keySet()) {
//			Monitor monitor = monitorsAdd.get(monitoradd);
//			itens.add(new Item(++index, "Monitor", monitor.getSerialNumber(), monitor.getValue()));
//		}
//		
//		for (String peripheraladd : peripheralsAdd.keySet()) {
//			Peripheral peripheral = peripheralsAdd.get(peripheraladd);
//			itens.add(new Item(++index, "Peripheral", peripheral.getName(), peripheral.getValue()));
//		}
//		
//		for (String licenseadd : licensesAdd.keySet()) {
//			License license = licensesAdd.get(licenseadd);
//			itens.add(new Item(++index, "License", license.getName(), license.getValue()));
//		}
		
	}
	
	private void updateStatusItem() {
		if (equipmentsAdd.keySet().size() > 0) {
			EquipmentService service = new EquipmentService();
			for (String equipmentadd : equipmentsAdd.keySet()) {
				Equipment equipment = equipmentsAdd.get(equipmentadd);
				equipment.setStatus("IN USE");
				equipment.setUser(user);
				service.updateStatusForUser(equipment);
			}
		}
		if (monitorsAdd.keySet().size() > 0) {
			MonitorService service = new MonitorService();
			for (String monitoradd : monitorsAdd.keySet()) {
				Monitor monitor = monitorsAdd.get(monitoradd);
				monitor.setStatus("IN USE");
				monitor.setUser(user);
				service.updateStatusForUser(monitor);				
			}
		}
		if (peripheralsAdd.keySet().size() > 0) {
			PeripheralService service = new PeripheralService();
			for (String peripheraladd : peripheralsAdd.keySet()) {
				Peripheral peripheral = peripheralsAdd.get(peripheraladd);
				peripheral.setQuantity(peripheral.getQuantity() - 1);
				peripheral.setUser(user.getName());
				service.updateQuantity(peripheral);
			}
		}
		if (licensesAdd.keySet().size() > 0) {
			LicenseService service = new LicenseService();
			for (String licenseadd : licensesAdd.keySet()) {
				License license = licensesAdd.get(licenseadd);
				license.setQuantity(license.getQuantity() - 1);
				license.setUser(user.getName());
				service.updateQuantity(license);
			}
		}
	}
	
	private void updateIemUser() {
		UserService service = new UserService();
		service.updateItem(user);		
	}

	//Returns the Item of the comboBox according to the maximum amount allowed.
	private void returnItemComboBox(ItemDelivery item) {
		final String name = item.getName();
		final String type = item.getType();

		if (type.equals("Equipment")) {			
			Equipment equipment = MainWindow.getEquipment(name);

			comboBox_Equipment.addItem(equipment);
			
			if (!checkContains(1, true, true)) {
				comboBox_Equipment.setEnabled(true);
				buttonOKEquipment.setEnabled(true);
			}
			//comboBox_Equipment.setEnabled(true);
			//equipmentsAdd.remove(equipment.getSerialNumber());
		} 
		else if (type.equals("Monitor")) {
			Monitor monitor = MainWindow.getMonitor(name);

			comboBox_Monitor.addItem(monitor);
			
			if (!checkContains(2, true, true)) {
				comboBox_Monitor.setEnabled(true);
				buttonOKMonitor.setEnabled(true);
			}
			
//			Monitor monitor = monitorsAdd.get(name);
//
//			comboBox_Monitor.addItem(monitor);
//			
//			monitorsAdd.remove(monitor.getSerialNumber());
//			
//			if (monitorsAdd.size() < 2) {
//				comboBox_Monitor.setEnabled(true);
//			}
		}
		else if (type.equals("Peripheral")) {
			Peripheral peripheral = MainWindow.getPeripheral(name);

			comboBox_Peripheral.addItem(peripheral);
			
			if (!checkContains(3, true, true)) {
				comboBox_Peripheral.setEnabled(true);
				buttonOKPeripheral.setEnabled(true);
			}
			
//			Peripheral peripheral = peripheralsAdd.get(name);
//
//			comboBox_Peripheral.addItem(peripheral);
//			peripheralsAdd.remove(peripheral.getName());
//			
//			if (peripheralsAdd.size() < 5) {
//				comboBox_Peripheral.setEnabled(true);
//			}
		}
		else if (type.equals("License")) {
			License license = MainWindow.getLicense(name);

			comboBox_License.addItem(license);
			
			if (!checkContains(4, true, true)) {
				comboBox_License.setEnabled(true);
				buttonOKLicense.setEnabled(true);
			}
			
//			License license = licensesAdd.get(name);
//
//			comboBox_License.addItem(license);
//			licensesAdd.remove(license.getName());
//			
//			if (licensesAdd.size() < 2) {
//				comboBox_License.setEnabled(true);
//			}
		}
	}

	//Removes the Item from the comboBox according to the maximum amount allowed.
	private void removeItemComboBox(Object obj, int type) {
		if (type == 1) {
			Equipment equipment = (Equipment) obj;
			ItemDelivery itemDelivery = new ItemDelivery("Equipment", equipment.getSerialNumber());
			
			comboBox_Equipment.setSelectedIndex(-1);
			comboBox_Equipment.removeItem(obj);
			
			modelDelivery.addItem(itemDelivery);
			
			if (checkContains(1, true, true)) {
				comboBox_Equipment.setEnabled(false);
				buttonOKEquipment.setEnabled(false);
			}
			
			
//			Equipment equipment = (Equipment) obj;
//			if (!equipmentsAdd.containsKey(equipment.getSerialNumber())) {
//				ItemDelivery itemDelivery = new ItemDelivery("Equipment", equipment.getSerialNumber());
//
//				comboBox_Equipment.setSelectedIndex(-1);
//				comboBox_Equipment.removeItem(obj);
//				
//				modelDelivery.addItem(itemDelivery);
//
//				equipmentsAdd.put(equipment.getSerialNumber(), equipment);
//				
//				comboBox_Equipment.setEnabled(false);
//			}
			
		} 
		else if (type == 2) {
			Monitor monitor = (Monitor) obj;
			ItemDelivery itemDelivery = new ItemDelivery("Monitor", monitor.getSerialNumber());
			
			comboBox_Monitor.setSelectedIndex(-1);
			comboBox_Monitor.removeItem(obj);
			
			modelDelivery.addItem(itemDelivery);
			
			if (checkContains(2, true, true)) {
				comboBox_Monitor.setEnabled(false);
				buttonOKMonitor.setEnabled(false);
			}
			
			
//			Monitor monitor = (Monitor) obj;
//			if (!monitorsAdd.containsKey(monitor.getSerialNumber())) {
//				ItemDelivery itemDelivery = new ItemDelivery("Monitor", monitor.getSerialNumber());
//
//				comboBox_Monitor.setSelectedIndex(-1);
//				comboBox_Monitor.removeItem(obj);
//				
//				modelDelivery.addItem(itemDelivery);
//
//				monitorsAdd.put(monitor.getSerialNumber(), monitor);
//				
//				if (monitorsAdd.size() == 2) {
//					comboBox_Monitor.setEnabled(false);
//				}
//			}
			
		} 
		else if (type == 3) {
			Peripheral monitor = (Peripheral) obj;
			ItemDelivery itemDelivery = new ItemDelivery("Peripheral", monitor.getName());
			
			comboBox_Peripheral.setSelectedIndex(-1);
			comboBox_Peripheral.removeItem(obj);
			
			modelDelivery.addItem(itemDelivery);
			
			if (checkContains(3, true, true)) {
				comboBox_Peripheral.setEnabled(false);
				buttonOKPeripheral.setEnabled(false);
			}
			
//			Peripheral peripheral = (Peripheral) obj;
//			if (!peripheralsAdd.containsKey(peripheral.getName())) {
//				ItemDelivery itemDelivery = new ItemDelivery("Peripheral", peripheral.getName());
//
//				comboBox_Peripheral.setSelectedIndex(-1);
//				comboBox_Peripheral.removeItem(obj);
//				
//				modelDelivery.addItem(itemDelivery);
//
//				peripheralsAdd.put(peripheral.getName(), peripheral);
//				
//				if (peripheralsAdd.size() == 5) {
//					comboBox_Peripheral.setEnabled(false);
//				}
//			}
			
		} 
		else if (type == 4) {
			License license = (License) obj;
			ItemDelivery itemDelivery = new ItemDelivery("License", license.getName());
			
			comboBox_License.setSelectedIndex(-1);
			comboBox_License.removeItem(obj);
			
			modelDelivery.addItem(itemDelivery);
			
			if (checkContains(4, true, true)) {
				comboBox_License.setEnabled(false);
				buttonOKLicense.setEnabled(false);
			}
			
//			License license = (License) obj;
//			if (!licensesAdd.containsKey(license.getName())) {
//				ItemDelivery itemDelivery = new ItemDelivery("License", license.getName());
//
//				comboBox_License.setSelectedIndex(-1);
//				comboBox_License.removeItem(obj);
//
//				modelDelivery.addItem(itemDelivery);
//
//				licensesAdd.put(license.getName(), license);
//				
//				if (licensesAdd.size() == 2) {
//					comboBox_License.setEnabled(false);
//				}
//			}
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
					updateStatusItem();
					updateIemUser();
					
					dispose();
				} catch (ObjectException oe) {
					if (oe.getMessage().contains("more equipment")) {
						JOptionPane.showMessageDialog(rootPane, "More equipment than allowed!", "Unable to save", JOptionPane.INFORMATION_MESSAGE);
					} else if (oe.getMessage().contains("more monitor")) {
						JOptionPane.showMessageDialog(rootPane, "More monitors than allowed!", "Unable to save", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (DBException db) {
					JOptionPane.showMessageDialog(rootPane, db.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
				}
			} 
			else {
				JOptionPane.showMessageDialog(null, "There is no data to save", "Unable to Save",
						JOptionPane.INFORMATION_MESSAGE);
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
			if (selectedObject != null && type != 0) {
				removeItemComboBox(selectedObject, type);					
			}
		}
	}

	private class buttonOKMonitorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (selectedObject != null && type != 0) {
				removeItemComboBox(selectedObject, type);					
			}
		}
	}

	private class buttonOKPeripheralListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (selectedObject != null && type != 0) {
				removeItemComboBox(selectedObject, type);					
			}
		}
	}

	private class buttonOKLicenseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (selectedObject != null && type != 0) {
				removeItemComboBox(selectedObject, type);					
			}
		}
	}

	private class ItemChangeEquipmentListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Equipment equipment = (Equipment) event.getItem();
				if (selectedObject == null || !equipment.equals(selectedObject)) {
					selectedObject = event.getItem();
					type = 1;
				}
			}
		}
	}

	private class ItemChangeMonitorListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Monitor monitor = (Monitor) event.getItem();
				if (selectedObject == null || !monitor.equals(selectedObject)) {
					selectedObject = event.getItem();
					type = 2;
				}
			}
		}
	}

	private class ItemChangePeripheralListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				if (event.getItem() != null) {
					selectedObject = event.getItem();
					type = 3;
				}
			}
		}
	}

	private class ItemChangeLicenseListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				if (event.getItem() != null) {
					selectedObject = event.getItem();
					type = 4;
				}
			}
		}
	}
}
