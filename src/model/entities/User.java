package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String registration;
	private String name;
	private String cpf;
	private String phone;
	private String email;
	private String department;
	private String status;
	private Date dateEntry;
	private String reason;

	private Project project;

	private List<Equipment> equipments = new ArrayList<Equipment>();
	private List<Monitor> monitors = new ArrayList<Monitor>();
	private List<Peripheral> peripherals = new ArrayList<Peripheral>();
	private List<License> licenses = new ArrayList<License>();

	private List<Change> changes = new ArrayList<>();

	public User() {
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(Date dateEntry) {
		this.dateEntry = dateEntry;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void addEquipment(Equipment equipment) {
		this.equipments.add(equipment);
	}
	
	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}
	
	public List<Monitor> getMonitors() {
		return monitors;
	}

	public void addMonitor(Monitor monitor) {
		this.monitors.add(monitor);
	}
	
	public void setMonitors(List<Monitor> monitors) {
		this.monitors = monitors;
	}

	public List<Peripheral> getPeripherals() {
		return peripherals;
	}

	public void addPeripheral(Peripheral peripheral) {
		this.peripherals.add(peripheral);
	}
	
	public void setPeripherals(List<Peripheral> peripherals) {
		this.peripherals = peripherals;
	}

	public List<License> getLicenses() {
		return licenses;
	}

	public void addLicense(License license) {
		this.licenses.add(license);
	}
	
	public void setLicenses(List<License> licenses) {
		this.licenses = licenses;
	}

	public List<Change> getChanges() {
		return changes;
	}
	
	public void addChange(Change change) {
		this.changes.add(change);
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}
	
	public Double getUserCosts() {
		double cost = 0.0;

		// get value equipments
		if (equipments != null) {

			for (Equipment equipment : equipments) {
				cost += equipment.getValue();
			}
		}

		// get value monitors
		if (monitors != null) {

			for (Monitor monitor : monitors) {
				cost += monitor.getValue();
			}
		}

		// get value peripherals
		if (peripherals != null) {

			for (Peripheral peripheral : peripherals) {
				cost += peripheral.getValue();
			}
		}

		// get value licenses
		if (licenses != null) {

			for (License license : licenses) {
				cost += license.getValue();
			}
		}

		return cost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, email, registration);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(email, other.email)
				&& Objects.equals(registration, other.registration);
	}

	@Override
	public String toString() {
		return registration;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
