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
import java.util.Vector;
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
import model.services.monitor.MonitorService;

public class AddItemForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Dimension DIMENSIONBUTTON = new Dimension(150, 30);

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private JScrollPane scrollPane;
	private TableItem table;
	private ItemTableModel modelOld;
	private DeliveryTableModel modelDelivery;
	
	private User user;

	private JComboBox<Equipment> comboBox_Equipment;
	private JComboBox<Monitor> comboBox_Monitor;
	private JComboBox<Peripheral> comboBox_Peripheral;
	private JComboBox<License> comboBox_License;

	private List<Equipment> equipments;
	private List<Monitor> monitors;
	private List<Peripheral> peripherals;
	private List<License> licenses;
	private List<Item> itens;

	private Map<String, Equipment> equipmentsAdd = new HashMap<String, Equipment>();
	private Map<String, Monitor> monitorsAdd = new HashMap<String, Monitor>();
	private Map<String, Peripheral> peripheralsAdd = new HashMap<String, Peripheral>();
	private Map<String, License> licensesAdd = new HashMap<String, License>();

	private Object selectedObject;
	private int type = 0;

	public AddItemForm(ItemTableModel modelOld, User user) {
		this.user = user;
		this.modelOld = modelOld;
		this.equipments = loadDataEquipments();
		this.monitors = loadDataMonitors();
		this.peripherals = loadDataPeripherals();
		this.licenses = loadDataLicenses();
		this.itens = new ArrayList<Item>();
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelWest(), BorderLayout.WEST);
		add(createTable(), BorderLayout.EAST);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Itens");
		setPreferredSize(new Dimension(720, 600));
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel createPanelWest() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.setPreferredSize(new Dimension(250, 600));

		panel.add(createPanelComboBox());
		panel.add(createPanelButton());

		return panel;
	}

	private JPanel createPanelComboBox() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
		panel.setPreferredSize(new Dimension(250, 430));
		panel.setBackground(COLOR1);

		panel.add(createPanelEquipment());
		panel.add(createPanelMonitor());
		panel.add(createPanelPeripheral());
		panel.add(createPanelLicense());

		return panel;
	}

	private JPanel createPanelButton() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		panel.setPreferredSize(new Dimension(250, 200));
		panel.setBackground(COLOR2);

		final JButton buttonSave = new JButton("Save");
		buttonSave.setPreferredSize(DIMENSIONBUTTON);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		final JButton buttonRemove = new JButton("Remove");
		buttonRemove.setPreferredSize(DIMENSIONBUTTON);
		buttonRemove.addActionListener(new buttonRemoveListener());
		panel.add(buttonRemove);

		final JButton buttonClose = new JButton("Close");
		buttonClose.setPreferredSize(DIMENSIONBUTTON);
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
		final JPanel panel = new JPanel();

		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Equipment");
		panel.setBorder(title);

		comboBox_Equipment = new JComboBox<Equipment>(new Vector<>(equipments));
		//addAllEquipments(equipments);
		comboBox_Equipment.addItemListener(new ItemChangeEquipmentListener());
		comboBox_Equipment.setPreferredSize(DIMENSIONBUTTON);
		comboBox_Equipment.setSelectedIndex(-1);
		comboBox_Equipment.addKeyListener(new KeyObjectListener());
		panel.add(comboBox_Equipment);

		return panel;
	}

	private JPanel createPanelMonitor() {
		final JPanel panel = new JPanel();

		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Monitor");
		panel.setBorder(title);

		comboBox_Monitor = new JComboBox<>(new Vector<>(monitors));
		comboBox_Monitor.addItemListener(new ItemChangeMonitorListener());
		comboBox_Monitor.setPreferredSize(DIMENSIONBUTTON);
		comboBox_Monitor.setSelectedIndex(-1);
		comboBox_Monitor.addKeyListener(new KeyObjectListener());
		panel.add(comboBox_Monitor);

		return panel;
	}

	private JPanel createPanelPeripheral() {
		final JPanel panel = new JPanel();

		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Peripheral");
		panel.setBorder(title);

		comboBox_Peripheral = new JComboBox<>(new Vector<>(peripherals));
		comboBox_Peripheral.addItemListener(new ItemChangePeripheralListener());
		comboBox_Peripheral.setPreferredSize(DIMENSIONBUTTON);
		comboBox_Peripheral.setSelectedIndex(-1);
		comboBox_Peripheral.addKeyListener(new KeyObjectListener());
		panel.add(comboBox_Peripheral);

		return panel;
	}

	private JPanel createPanelLicense() {
		final JPanel panel = new JPanel();

		Border lineBorder = BorderFactory.createLineBorder(COLOR1);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "License");
		panel.setBorder(title);

		comboBox_License = new JComboBox<>(new Vector<>(licenses));
		comboBox_License.addItemListener(new ItemChangeLicenseListener());
		comboBox_License.setPreferredSize(DIMENSIONBUTTON);
		comboBox_License.setSelectedIndex(-1);
		comboBox_License.addKeyListener(new KeyObjectListener());
		panel.add(comboBox_License);

		return panel;
	}

	private List<Equipment> loadDataEquipments() {
		Map<String, Equipment> equipments = MainWindow.getEquipments();
		List<Equipment> list = new ArrayList<Equipment>();

		for (String entry : equipments.keySet()) {
			list.add(equipments.get(entry));
		}

		list = list.stream().filter(e -> e.getStatus().equals("STAND BY")).collect(Collectors.toList());
		list.sort((e1, p2) -> e1.getSerialNumber().compareTo(p2.getSerialNumber()));
		return list;
	}

	private List<Monitor> loadDataMonitors() {
		Map<String, Monitor> monitors = MainWindow.getMonitors();
		List<Monitor> list = new ArrayList<Monitor>();

		for (String entry : monitors.keySet()) {
			list.add(monitors.get(entry));
		}

		list = list.stream().filter(m -> m.getStatus().equals("STAND BY")).collect(Collectors.toList());
		list.sort((m1, m2) -> m1.getSerialNumber().compareTo(m2.getSerialNumber()));
		return list;
	}

	private List<Peripheral> loadDataPeripherals() {
//		Map<String, Peripheral> monitors = MainWindow.getMonitors();
		List<Peripheral> list = new ArrayList<Peripheral>();

		list.add(new Peripheral("MOUSE", 20.0));

//		for (String entry : monitors.keySet()) {
//			list.add(monitors.get(entry));
//		}

		list.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
		return list;
	}

	private List<License> loadDataLicenses() {
		// Map<String, License> monitors = MainWindow.getPeripherals();
		List<License> list = new ArrayList<License>();

		list.add(new License("5df4", "MICROSOFT OFFICE", 40.00));

//		for (String entry : monitors.keySet()) {
//			list.add(monitors.get(entry));
//		}

		list.sort((l1, l2) -> l1.getName().compareTo(l2.getName()));
		return list;
	}
	
	public List<Item> getItens() {
		return itens;
	}

	private class buttonSaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (modelDelivery.getRowCount() != 0) {
				try {
					addItem();
					updateStatusItem();
					updateStatusUser();
					
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
	
	private void addItem() {
		if (equipmentsAdd.keySet().size() > 1) {
			throw new ObjectException("more equipment");
		}
		
		if (monitorsAdd.keySet().size() > 2) {
			throw new ObjectException("more monitors");
		}
		
		int index = 0;
		index = modelOld.getRowCount();
		
		for (String equipmentadd : equipmentsAdd.keySet()) {
			Equipment equipment = equipmentsAdd.get(equipmentadd);
			itens.add(new Item(++index, "Equipment", equipment.getSerialNumber(), equipment.getValue()));
		}
		
		for (String monitoradd : monitorsAdd.keySet()) {
			Monitor monitor = monitorsAdd.get(monitoradd);
			itens.add(new Item(++index, "Monitor", monitor.getSerialNumber(), monitor.getValue()));
		}
		
	}
	
	private void updateStatusItem() {
		if (equipmentsAdd.keySet().size() > 0) {
			EquipmentService service = new EquipmentService();
			for (String equipmentadd : equipmentsAdd.keySet()) {
				Equipment equipment = equipmentsAdd.get(equipmentadd);
				equipment.setStatus("IN USE");
				equipment.setUser(user);
				System.out.println(equipment);
				//service.updateStatusForUser(equipment);
			}
		}
		if (monitorsAdd.keySet().size() > 0) {
			MonitorService service = new MonitorService();
			for (String monitoradd : monitorsAdd.keySet()) {
				Monitor monitor = monitorsAdd.get(monitoradd);
				monitor.setStatus("IN USE");
				monitor.setUser(user);
				System.out.println(monitor);
				//service.updateStatusForUser(monitor);				
			}
		}
		if (peripheralsAdd.keySet().size() > 0) {

		}
		if (licensesAdd.keySet().size() > 0) {

		}
	}
	
	private void updateStatusUser() {
		
	}

	private void returnItemComboBox(ItemDelivery item) {
		final String name = item.getName();
		final String type = item.getType();

		if (type.equals("Equipment")) {
			Equipment equipment = equipmentsAdd.get(name);

			comboBox_Equipment.addItem(equipment);
			equipmentsAdd.remove(equipment.getSerialNumber());
		} 
		else if (type.equals("Monitor")) {
			Monitor monitor = monitorsAdd.get(name);

			comboBox_Monitor.addItem(monitor);
			monitorsAdd.remove(monitor.getSerialNumber());
		}
		else if (type.equals("Peripheral")) {
			Peripheral peripheral = peripheralsAdd.get(name);

			comboBox_Peripheral.addItem(peripheral);
			peripheralsAdd.remove(peripheral.getName());
		}
		else if (type.equals("License")) {
			License license = licensesAdd.get(name);

			comboBox_License.addItem(license);
			licensesAdd.remove(license.getName());
		}
	}

	private void removeItemComboBox(Object obj, int type) {
		if (type == 1) {
			Equipment equipment = (Equipment) obj;
			if (!equipmentsAdd.containsKey(equipment.getSerialNumber())) {
				ItemDelivery itemDelivery = new ItemDelivery("Equipment", equipment.getSerialNumber());

				comboBox_Equipment.setSelectedIndex(-1);
				comboBox_Equipment.removeItem(obj);

				modelDelivery.addItem(itemDelivery);

				equipmentsAdd.put(equipment.getSerialNumber(), equipment);
			}
			
		} 
		else if (type == 2) {
			Monitor monitor = (Monitor) obj;
			if (!monitorsAdd.containsKey(monitor.getSerialNumber())) {
				ItemDelivery itemDelivery = new ItemDelivery("Monitor", monitor.getSerialNumber());

				comboBox_Monitor.setSelectedIndex(-1);
				comboBox_Monitor.removeItem(obj);

				modelDelivery.addItem(itemDelivery);

				monitorsAdd.put(monitor.getSerialNumber(), monitor);	
			}
			
		} 
		else if (type == 3) {
			Peripheral peripheral = (Peripheral) obj;
			if (!peripheralsAdd.containsKey(peripheral.getName())) {
				ItemDelivery itemDelivery = new ItemDelivery("Peripheral", peripheral.getName());

				comboBox_Peripheral.setSelectedIndex(-1);
				comboBox_Peripheral.removeItem(obj);

				modelDelivery.addItem(itemDelivery);

				peripheralsAdd.put(peripheral.getName(), peripheral);
			}
			
		} 
		else if (type == 4) {
			License license = (License) obj;
			if (!licensesAdd.containsKey(license.getName())) {
				ItemDelivery itemDelivery = new ItemDelivery("License", license.getName());

				comboBox_License.setSelectedIndex(-1);
				comboBox_License.removeItem(obj);

				modelDelivery.addItem(itemDelivery);

				licensesAdd.put(license.getName(), license);
			}
		}
	}

	private class KeyObjectListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (selectedObject != null && type != 0) {
					removeItemComboBox(selectedObject, type);					
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
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
