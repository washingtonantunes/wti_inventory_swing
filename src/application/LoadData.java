package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import model.entities.Change;
import model.entities.Equipment;
import model.entities.License;
import model.entities.Monitor;
import model.entities.Option;
import model.entities.Peripheral;
import model.entities.Project;
import model.entities.User;
import model.entities.WorkPosition;
import model.entities.utilitary.EquipmentWithUser;
import model.entities.utilitary.LicenseWithUser;
import model.entities.utilitary.MonitorWithUser;
import model.entities.utilitary.PeripheralWithUser;
import model.services.ObjectWithUserService;
import model.services.OptionService;
import model.services.change.ChangeService;
import model.services.equipment.EquipmentService;
import model.services.license.LicenseService;
import model.services.monitor.MonitorService;
import model.services.peripheral.PeripheralService;
import model.services.project.ProjectService;
import model.services.user.UserService;
import model.services.workposition.WorkPositionService;

public class LoadData {

	public LoadData() {
		//new ProgressBar(1000).setVisible(true);

		MainWindow.changes = loadChanges();

		MainWindow.equipmentsWithUsers = loadEquipmentWithUser();
		MainWindow.monitorsWithUsers = loadMonitorWithUser();
		MainWindow.peripheralsWithUsers = loadPeripheralWithUser();
		MainWindow.licensesWithUsers = loadLicenseWithUser();

		MainWindow.workPositions = loadWorkPositions();
		MainWindow.projects = loadProjects();
		MainWindow.users = loadUsers();
		
		MainWindow.equipments = loadEquipments();
		MainWindow.monitors = loadMonitors();
		MainWindow.peripherals = loadPeripherals();
		MainWindow.licenses = loadLicenses();
		
		MainWindow.options = loadOption();
		
		loadObjetcWithUser();
	}

	private void loadObjetcWithUser() {
		final Map<String, User> users = MainWindow.users;

		for (String entry : users.keySet()) {

			users.get(entry).setEquipments(getEquipmentByUser(entry));
			users.get(entry).setMonitors(getMonitorByUser(entry));
			users.get(entry).setPeripherals(getPeripheralByUser(entry));
			users.get(entry).setLicenses(getLicenseByUser(entry));
		}
	}

	private List<Change> loadChanges() {
		final ChangeService service = new ChangeService();
		List<Change> list = service.findAll();
		return list;
	}

	private List<EquipmentWithUser> loadEquipmentWithUser() {
		final ObjectWithUserService service = new ObjectWithUserService();
		List<EquipmentWithUser> list = service.findAllEquipmentWithUser();
		return list;
	}

	private List<MonitorWithUser> loadMonitorWithUser() {
		final ObjectWithUserService service = new ObjectWithUserService();
		List<MonitorWithUser> list = service.findAllMonitorWithUser();
		return list;
	}

	private List<PeripheralWithUser> loadPeripheralWithUser() {
		final ObjectWithUserService service = new ObjectWithUserService();
		List<PeripheralWithUser> list = service.findAllPeripheralWithUser();
		return list;
	}

	private List<LicenseWithUser> loadLicenseWithUser() {
		final ObjectWithUserService service = new ObjectWithUserService();
		List<LicenseWithUser> list = service.findAllLicenseWithUser();
		return list;
	}

	private Map<String, WorkPosition> loadWorkPositions() {
		final WorkPositionService service = new WorkPositionService();
		Map<String, WorkPosition> map = service.findAll();
		return map;
	}

	private Map<String, Project> loadProjects() {
		final ProjectService service = new ProjectService();
		Map<String, Project> map = service.findAll();
		return map;
	}
	
	private Map<String, User> loadUsers() {
		final UserService service = new UserService();
		Map<String, User> map = service.findAll();
		return map;
	}

	private Map<String, Equipment> loadEquipments() {
		final EquipmentService service = new EquipmentService();
		Map<String, Equipment> map = service.findAll();
		return map;
	}

	private Map<String, Monitor> loadMonitors() {
		final MonitorService service = new MonitorService();
		Map<String, Monitor> map = service.findAll();
		return map;
	}

	private Map<String, Peripheral> loadPeripherals() {
		final PeripheralService service = new PeripheralService();
		Map<String, Peripheral> map = service.findAll();
		return map;
	}

	private Map<String, License> loadLicenses() {
		final LicenseService service = new LicenseService();
		Map<String, License> map = service.findAll();
		return map;
	}
	
	private List<Option> loadOption() {
		final OptionService service = new OptionService();
		List<Option> list = service.findAll();
		return list;
	}

	// Change Methods

	// Used to load Change after boot
	public static void setChanges(List<Change> changes_) {
		MainWindow.changes = changes_;
	}

	// Used to add a change during play
	public static void addChange(Change change_) {
		MainWindow.changes.add(change_);
	}

	// Used to get a list by object
	public static List<Change> getChangesByObject(String object_) {
		final List<Change> list = MainWindow.changes.stream().filter(c -> c.getObject().equals(object_))
				.collect(Collectors.toList());
		list.sort((c1, c2) -> c1.getDate().compareTo(c2.getDate()));
		return list;
	}
	
	public static List<Change> getChangesList() {
		return MainWindow.changes;
	}

	// EquipmentWithUser Methods

	// Used to load EquipmentWithUser after boot
	public static void setEquipmentWithUser(List<EquipmentWithUser> equipmentsWithUsers_) {
		MainWindow.equipmentsWithUsers = equipmentsWithUsers_;
	}

	// Used to add a EquipmentWithUser during play
	public static void addChange(EquipmentWithUser equipmentWithUser_) {
		MainWindow.equipmentsWithUsers.add(equipmentWithUser_);
	}

	// Used to obtain the serial numbers of equipment used by a user
	public static Set<String> getEquipmentWithUser(String registration) {
		final Set<String> set = MainWindow.equipmentsWithUsers.stream().filter(e -> e.getRegistration().equals(registration))
				.map(EquipmentWithUser::getSerialNumber).collect(Collectors.toSet());
		return set;
	}

	// MonitorWithUser Methods

	// Used to load MonitorWithUser after boot
	public static void setMonitorWithUser(List<MonitorWithUser> monitorsWithUsers_) {
		MainWindow.monitorsWithUsers = monitorsWithUsers_;
	}

	// Used to add a MonitorWithUser during play
	public static void addChange(MonitorWithUser monitorWithUser_) {
		MainWindow.monitorsWithUsers.add(monitorWithUser_);
	}

	// Used to obtain the serial numbers of monitor used by a user
	public static Set<String> getMonitorWithUser(String registration) {
		final Set<String> set = MainWindow.monitorsWithUsers.stream().filter(e -> e.getRegistration().equals(registration))
				.map(MonitorWithUser::getSerialNumber).collect(Collectors.toSet());
		return set;
	}

	// PeripheralWithUser Methods

	// Used to load PeripheralWithUser after boot
	public static void setPeripheralWithUser(List<PeripheralWithUser> peripheralsWithUsers_) {
		MainWindow.peripheralsWithUsers = peripheralsWithUsers_;
	}

	// Used to add a PeripheralWithUser during play
	public static void addChange(PeripheralWithUser peripheralWithUser_) {
		MainWindow.peripheralsWithUsers.add(peripheralWithUser_);
	}

	// Used to obtain the serial numbers of peripheral used by a user
	public static Set<String> getPeripheralWithUser(String registration) {
		final Set<String> set = MainWindow.peripheralsWithUsers.stream().filter(e -> e.getRegistration().equals(registration))
				.map(PeripheralWithUser::getCode).collect(Collectors.toSet());
		return set;
	}

	// LicenseWithUser Methods

	// Used to load LicenseWithUser after boot
	public static void setLicenseWithUser(List<LicenseWithUser> licensesWithUsers_) {
		MainWindow.licensesWithUsers = licensesWithUsers_;
	}

	// Used to add a LicenseWithUser during play
	public static void addChange(LicenseWithUser licenseWithUser_) {
		MainWindow.licensesWithUsers.add(licenseWithUser_);
	}

	// Used to obtain the serial numbers of license used by a user
	public static Set<String> getLicenseWithUser(String registration) {
		final Set<String> set = MainWindow.licensesWithUsers.stream().filter(e -> e.getRegistration().equals(registration))
				.map(LicenseWithUser::getCode).collect(Collectors.toSet());
		return set;
	}

	// Equipment Methods

	// Used to load Equipment after boot
	public static void setEquipments(Map<String, Equipment> equipments_) {
		MainWindow.equipments = equipments_;
	}

	// Used to add a equipment during play
	public static void addEquipment(Equipment equipment_) {
		MainWindow.equipments.put(equipment_.getSerialNumber(), equipment_);
	}

	//
	public static Equipment getEquipment(String serialNumber) {
		return MainWindow.equipments.get(serialNumber);
	}

	// Used to get a list of equipments by user
	public static List<Equipment> getEquipmentByUser(String registration) {
		Set<String> set = getEquipmentWithUser(registration);
		List<Equipment> list = new ArrayList<Equipment>();
		for (String equipment : set) {
			list.add(getEquipment(equipment));
		}
		return list;
	}

	// Used to get equipment on Map
	public static Map<String, Equipment> getEquipmentsMap() {
		return MainWindow.equipments;
	}

	// Used to get equipment in List
	public static List<Equipment> getEquipmentsList() {
		List<Equipment> list = new ArrayList<Equipment>();

		for (String entry : MainWindow.equipments.keySet()) {
			list.add(MainWindow.equipments.get(entry));
		}

		list.sort((e1, p2) -> e1.getSerialNumber().compareTo(p2.getSerialNumber()));
		return list;
	}
	
	// Used to get equipment in List
	public static List<Equipment> getEquipmentsListStatus(String status) {
		List<Equipment> list = new ArrayList<Equipment>();

		for (String entry : MainWindow.equipments.keySet()) {
			list.add(MainWindow.equipments.get(entry));
		}

		list = list.stream().filter(e -> e.getStatus().equals(status)).collect(Collectors.toList());
		list.sort((e1, p2) -> e1.getSerialNumber().compareTo(p2.getSerialNumber()));
		return list;
	}

	// Monitor Methods

	// Used to load Monitor after boot
	public static void setMonitors(Map<String, Monitor> monitors_) {
		MainWindow.monitors = monitors_;
	}

	// Used to add a monitor during play
	public static void addMonitor(Monitor monitor_) {
		MainWindow.monitors.put(monitor_.getSerialNumber(), monitor_);
	}

	//
	public static Monitor getMonitor(String serialNumber) {
		return MainWindow.monitors.get(serialNumber);
	}

	// Used to get a list of monitors by user
	public static List<Monitor> getMonitorByUser(String registration) {
		Set<String> set = getMonitorWithUser(registration);
		List<Monitor> list = new ArrayList<Monitor>();
		for (String monitor : set) {
			list.add(getMonitor(monitor));
		}
		return list;
	}

	// Used to get monitor on Map
	public static Map<String, Monitor> getMonitorsMap() {
		return MainWindow.monitors;
	}

	// Used to get monitor in List
	public static List<Monitor> getMonitorsList() {
		List<Monitor> list = new ArrayList<Monitor>();

		for (String entry : MainWindow.monitors.keySet()) {
			list.add(MainWindow.monitors.get(entry));
		}

		list.sort((e1, p2) -> e1.getSerialNumber().compareTo(p2.getSerialNumber()));
		return list;
	}

	// Peripheral Methods

	// Used to load Peripheral after boot
	public static void setPeripherals(Map<String, Peripheral> peripherals_) {
		MainWindow.peripherals = peripherals_;
	}

	// Used to add a peripheral during play
	public static void addPeripheral(Peripheral peripheral_) {
		MainWindow.peripherals.put(peripheral_.getCode(), peripheral_);
	}

	//
	public static Peripheral getPeripheral(String code) {
		return MainWindow.peripherals.get(code);
	}

	// Used to get a list of peripherals by user
	public static List<Peripheral> getPeripheralByUser(String registration) {
		Set<String> set = getPeripheralWithUser(registration);
		List<Peripheral> list = new ArrayList<Peripheral>();
		for (String peripheral : set) {
			list.add(getPeripheral(peripheral));
		}
		return list;
	}

	// Used to get peripheral on Map
	public static Map<String, Peripheral> getPeripheralsMap() {
		return MainWindow.peripherals;
	}

	// Used to get peripheral in List
	public static List<Peripheral> getPeripheralsList() {
		List<Peripheral> list = new ArrayList<Peripheral>();

		for (String entry : MainWindow.peripherals.keySet()) {
			list.add(MainWindow.peripherals.get(entry));
		}

		list.sort((e1, p2) -> e1.getName().compareTo(p2.getName()));
		return list;
	}

	// License Methods

	// Used to load License after boot
	public static void setLicenses(Map<String, License> licenses_) {
		MainWindow.licenses = licenses_;
	}

	// Used to add a license during play
	public static void addLicense(License license_) {
		MainWindow.licenses.put(license_.getCode(), license_);
	}

	//
	public static List<License> getLicenseByUser(String registration) {
		Set<String> set = getLicenseWithUser(registration);
		List<License> list = new ArrayList<License>();
		for (String license : set) {
			list.add(getLicense(license));
		}
		return list;
	}

	//
	public static License getLicense(String code) {
		return MainWindow.licenses.get(code);
	}

	// Used to get license on Map
	public static Map<String, License> getLicensesMap() {
		return MainWindow.licenses;
	}

	// Used to get license in List
	public static List<License> getLicensesList() {
		List<License> list = new ArrayList<License>();

		for (String entry : MainWindow.licenses.keySet()) {
			list.add(MainWindow.licenses.get(entry));
		}

		list.sort((e1, p2) -> e1.getCode().compareTo(p2.getCode()));
		return list;
	}

	// WorkPosition Methods

	// Used to load WorkPosition after boot
	public static void setWorkPositions(Map<String, WorkPosition> workPositions_) {
		MainWindow.workPositions = workPositions_;
	}

	// Used to add a workPosition during play
	public static void addWorkPosition(WorkPosition workPosition_) {
		MainWindow.workPositions.put(workPosition_.getWorkPoint(), workPosition_);
	}

	//
	public static WorkPosition getWorkPosition(String serialNumber) {
		return MainWindow.workPositions.get(serialNumber);
	}

	// Used to get workPosition on Map
	public static Map<String, WorkPosition> getWorkPositionsMap() {
		return MainWindow.workPositions;
	}

	// Used to get workPosition in List
	public static List<WorkPosition> getWorkPositionsList() {
		List<WorkPosition> list = new ArrayList<WorkPosition>();

		for (String entry : MainWindow.workPositions.keySet()) {
			list.add(MainWindow.workPositions.get(entry));
		}

		list.sort((e1, p2) -> e1.getWorkPoint().compareTo(p2.getWorkPoint()));
		return list;
	}

	// Project Methods

	// Used to load Project after boot
	public static void setProjects(Map<String, Project> projects_) {
		MainWindow.projects = projects_;
	}

	// Used to add a project during play
	public static void addProject(Project project_) {
		MainWindow.projects.put(project_.getCostCenter(), project_);
	}

	//
	public static Project getProject(String costCenter) {
		return MainWindow.projects.get(costCenter);
	}

	// Used to get project on Map
	public static Map<String, Project> getProjectsMap() {
		return MainWindow.projects;
	}

	// Used to get project in List
	public static List<Project> getProjectsList() {
		List<Project> list = new ArrayList<Project>();

		for (String entry : MainWindow.projects.keySet()) {
			list.add(MainWindow.projects.get(entry));
		}

		list.sort((e1, p2) -> e1.getName().compareTo(p2.getName()));
		return list;
	}
	
	// Used to get project in List By Status
	public static List<Object> getProjectsListByStatus(String status) {
		List<Project> list = new ArrayList<Project>();

		for (String entry : MainWindow.projects.keySet()) {
			list.add(MainWindow.projects.get(entry));
		}

		list.sort((e1, p2) -> e1.getName().compareTo(p2.getName()));
		return list.stream().filter(p -> p.getStatus().equalsIgnoreCase("ACTIVE")).collect(Collectors.toList());
	}

	// User Methods

	// Used to load User after boot
	public static void setUsers(Map<String, User> users_) {
		MainWindow.users = users_;
	}

	// Used to add a user during play
	public static void addUser(User user_) {
		MainWindow.users.put(user_.getRegistration(), user_);
	}

	//
	public static User getUserByRegistration(String registration) {
		return MainWindow.users.get(registration);
	}
	
	//
	public static User getUserByName(String name) {
		List<User> list = getUsersList();
		
		return list.stream().filter(u -> u.getName().equals(name)).findAny().get();
	}

	// Used to get user on Map
	public static Map<String, User> getUsersMap() {
		return MainWindow.users;
	}

	// Used to get user in List
	public static List<User> getUsersList() {
		List<User> list = new ArrayList<User>();

		for (String entry : MainWindow.users.keySet()) {
			list.add(MainWindow.users.get(entry));
		}

		list.sort((e1, p2) -> e1.getName().compareTo(p2.getName()));
		return list;
	}
	
	// Option Methods
	
	//
	public static List<Option> getOptionsList() {
		return MainWindow.options;
	}
	
	//	
	public static List<Object> getOptionByType(String type) {
		return MainWindow.options.stream().filter(o -> o.getType().equals(type) && o.getStatus().equals("ACTIVE")).map(Option::getOption).distinct().collect(Collectors.toList());
	}
}