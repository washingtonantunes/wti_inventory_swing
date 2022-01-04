package model.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private int privilege;

	public MenuBar(int privilege) {
		this.privilege = privilege;
		initComponents();
	}

	private void initComponents() {
		add(createMenuFile());
		add(createMenuObjects());
		add(createMenuTools());
		add(createMenuProperties());
	}

	private JMenu createMenuFile() {
		final JMenu menuFile = new JMenu("File");

		final JMenuItem menuItemHome = new JMenuItem("Home");
		menuItemHome.addActionListener(new MenuItemHomeListener());
		menuFile.add(menuItemHome);

		final JMenuItem menuItemLogoff = new JMenuItem("Logoff");
		menuItemLogoff.addActionListener(new MenuItemLogoffListener());
		menuFile.add(menuItemLogoff);

		final JMenuItem menuItemClose = new JMenuItem("Close");
		menuItemClose.addActionListener(new MenuItemCloseListener());
		menuFile.add(menuItemClose);

		return menuFile;
	}

	private JMenu createMenuObjects() {
		final JMenu menuObjects = new JMenu("Objects");

		final JMenuItem menuItemEquipments = new JMenuItem("Equipments");
		menuItemEquipments.addActionListener(new MenuItemEquipmentsListener());
		menuObjects.add(menuItemEquipments);

		final JMenuItem menuItemMonitors = new JMenuItem("Monitors");
		menuItemMonitors.addActionListener(new MenuItemMonitorsListener());
		menuObjects.add(menuItemMonitors);

		final JMenuItem menuItemWorkPositions = new JMenuItem("Work Positions");
		menuItemWorkPositions.addActionListener(new MenuItemWorkPositionsListener());
		menuObjects.add(menuItemWorkPositions);

		final JMenuItem menuItemProjects = new JMenuItem("Projects");
		menuItemProjects.addActionListener(new MenuItemProjectsListener());
		menuObjects.add(menuItemProjects);

		final JMenuItem menuItemUsers = new JMenuItem("Users");
		menuItemUsers.addActionListener(new MenuItemUsersListener());
		menuObjects.add(menuItemUsers);

		return menuObjects;
	}

	private JMenu createMenuTools() {
		final JMenu menuTools = new JMenu("Tools");

		final JMenuItem menuItemCollaboratos = new JMenuItem("Collaboratos");
		menuItemCollaboratos.addActionListener(new MenuItemCollaboratosListener());
		menuTools.add(menuItemCollaboratos);

		final JMenuItem menuItemOptions = new JMenuItem("Options");
		menuItemOptions.addActionListener(new MenuItemOptionsListener());
		menuTools.add(menuItemOptions);

		return menuTools;
	}

	private JMenu createMenuProperties() {
		final JMenu menuProperties = new JMenu("Properties");

		final JMenuItem menuItemProfile = new JMenuItem("Profile");
		menuItemProfile.addActionListener(new MenuItemProfileListener());
		menuProperties.add(menuItemProfile);

		menuProperties.add(createMenuSettings());

		return menuProperties;
	}

	private JMenu createMenuSettings() {
		final JMenu menuSettings = new JMenu("Settings");

		final JMenuItem menuItemDataBase = new JMenuItem("DataBase");
		menuItemDataBase.addActionListener(new MenuItemDataBaseListener());
		menuSettings.add(menuItemDataBase);

		return menuSettings;
	}

	private class MenuItemHomeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemHomeListener");
		}
	}

	private class MenuItemLogoffListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemLogoffListener");
		}
	}

	private class MenuItemCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

		}
	}

	private class MenuItemEquipmentsListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

		}
	}

	private class MenuItemMonitorsListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemMonitorsListener");
		}
	}

	private class MenuItemWorkPositionsListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemWorkPositionsListener");
		}
	}

	private class MenuItemProjectsListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemProjectsListener");
		}
	}

	private class MenuItemUsersListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemUsersListener");
		}
	}

	private class MenuItemCollaboratosListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemCollaboratosListener");
		}
	}

	private class MenuItemOptionsListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemOptionsListener");
		}
	}

	private class MenuItemProfileListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemProfileListener");
		}
	}

	private class MenuItemDataBaseListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (privilege != 1) {
				JOptionPane.showMessageDialog(getComponentPopupMenu(), "Access denied");
			} else {
				System.out.println("MenuItemDataBaseListener");
			}
		}
	}
}
