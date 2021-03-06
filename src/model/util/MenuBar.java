package model.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import application.MainWindow;
import model.gui.equipment.EquipmentList;
import model.gui.monitor.MonitorList;
import model.gui.project.ProjectList;
import model.gui.user.UserList;
import model.gui.workposition.WorkPositionList;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	
	private JDesktopPane mainDesktopPane;

	private int privilege;

	public MenuBar(JDesktopPane mainDesktopPane) {
		this.mainDesktopPane = mainDesktopPane;
		initComponents();
	}

	private void initComponents() {
		add(createMenuFile());
		add(createMenuObjects());
		add(createMenuTools());
		add(createMenuWork());
		add(createMenuProperties());
	}

	private JMenu createMenuFile() {
		final JMenu menuFile = new JMenu("File");

		final JMenuItem menuItemHome = new JMenuItem("Home");
		menuItemHome.addActionListener(new MenuItemHomeListener());
		menuFile.add(menuItemHome);

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

		final JMenuItem menuItemCollaborators = new JMenuItem("Collaborators");
		menuItemCollaborators.addActionListener(new MenuItemCollaboratorsListener());
		menuTools.add(menuItemCollaborators);

		final JMenuItem menuItemOptions = new JMenuItem("Options");
		menuItemOptions.addActionListener(new MenuItemOptionsListener());
		menuTools.add(menuItemOptions);

		return menuTools;
	}
	
	private JMenu createMenuWork() {
		final JMenu menuWork = new JMenu("Work");
		
		final JMenuItem menuItemDashboard = new JMenuItem("Dashboard");
		menuItemDashboard.addActionListener(new MenuItemDashboardListener());
		menuWork.add(menuItemDashboard);
		
		final JMenuItem menuItemLayout = new JMenuItem("Layout");
		menuItemLayout.addActionListener(new MenuItemLayoutListener());
		menuWork.add(menuItemLayout);
		
		return menuWork;
	}

	private JMenu createMenuProperties() {
		final JMenu menuProperties = new JMenu("Properties");

		final JMenuItem menuItemProfile = new JMenuItem("Profile");
		menuItemProfile.addActionListener(new MenuItemProfileListener());
		menuProperties.add(menuItemProfile);

		menuProperties.add(createMenuSettings());
		
		menuProperties.add(createMenuDefault());

		return menuProperties;
	}

	private JMenu createMenuSettings() {
		final JMenu menuSettings = new JMenu("Settings");

		final JMenuItem menuItemDataBase = new JMenuItem("DataBase");
		menuItemDataBase.addActionListener(new MenuItemDataBaseListener());
		menuSettings.add(menuItemDataBase);
		
		return menuSettings;
	}
	
	private JMenu createMenuDefault() {
		final JMenu menuDefault = new JMenu("Default");
		
		final JMenuItem menuItemSortOptions = new JMenuItem("Sort Options");
		menuItemSortOptions.addActionListener(new menuItemSortOptionsListener());
		menuDefault.add(menuItemSortOptions);
		
		final JMenuItem menuItemSortChanges = new JMenuItem("Sort Changes");
		menuItemSortChanges.addActionListener(new menuItemSortChangesListener());
		menuDefault.add(menuItemSortChanges);
		
		final JMenuItem menuItemDefaultChanges = new JMenuItem("Default Changes");
		menuItemDefaultChanges.addActionListener(new menuItemDefaultChangesListener());
		menuDefault.add(menuItemDefaultChanges);
		
		return menuDefault;
	}

	private class MenuItemHomeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainDesktopPane.removeAll();
			final MainWindow mainWindow = new MainWindow();
			mainDesktopPane.add(mainWindow.createPanelMain());
			mainDesktopPane.revalidate();
		}
	}

	private class MenuItemCloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Runtime.getRuntime().exit(0);
		}
	}

	private class MenuItemEquipmentsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainDesktopPane.removeAll();
			final EquipmentList equipmentList = new EquipmentList();
			mainDesktopPane.add(equipmentList);
			mainDesktopPane.revalidate();
		}
	}

	private class MenuItemMonitorsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainDesktopPane.removeAll();
			final MonitorList monitorList = new MonitorList();
			mainDesktopPane.add(monitorList);
			mainDesktopPane.revalidate();
		}
	}

	private class MenuItemWorkPositionsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainDesktopPane.removeAll();
			final WorkPositionList workPositionList = new WorkPositionList();
			mainDesktopPane.add(workPositionList);
			mainDesktopPane.revalidate();
		}
	}

	private class MenuItemProjectsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainDesktopPane.removeAll();
			final ProjectList projectList = new ProjectList();
			mainDesktopPane.add(projectList);
			mainDesktopPane.revalidate();
		}
	}

	private class MenuItemUsersListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainDesktopPane.removeAll();
			final UserList userList = new UserList();
			mainDesktopPane.add(userList);
			mainDesktopPane.revalidate();
		}
	}

	private class MenuItemCollaboratorsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemCollaboratosListener");
		}
	}

	private class MenuItemOptionsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemOptionsListener");
		}
	}
	
	private class MenuItemDashboardListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemDashboardListener");
		}
	}
	
	private class MenuItemLayoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemLayoutListener");
		}
	}

	private class MenuItemProfileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("MenuItemProfileListener");
		}
	}

	private class MenuItemDataBaseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (privilege != 1) {
				JOptionPane.showMessageDialog(getComponentPopupMenu(), "Access denied");
			} else {
				System.out.println("MenuItemDataBaseListener");
			}
		}
	}
	
	private class menuItemSortOptionsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new SortListOption();
		}
	}
	
	private class menuItemSortChangesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new SortListChange();
		}
	}
	
	private class menuItemDefaultChangesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new DefaultChanges();
		}
	}
}
