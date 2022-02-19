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
	private Equipment equipment;
	private Monitor monitor1;
	private Monitor monitor2;

	private List<Peripheral> peripherals;
	private List<License> licenses;

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

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Monitor getMonitor1() {
		return monitor1;
	}

	public void setMonitor1(Monitor monitor1) {
		this.monitor1 = monitor1;
	}

	public Monitor getMonitor2() {
		return monitor2;
	}

	public void setMonitor2(Monitor monitor2) {
		this.monitor2 = monitor2;
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

		// get value equipment
		if (equipment != null) {
			cost += equipment.getValue();
		}

		// get value monitor1
		if (monitor1 != null) {
			cost += monitor1.getValue();
		}

		// get value monitor2
		if (monitor2 != null) {
			cost += monitor1.getValue();
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
