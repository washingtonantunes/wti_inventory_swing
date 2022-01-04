package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String registration;
	private String nameUser;
	private String cpf;
	private String phone;
	private String email;
	private String project;
	private String department;
	private String status;
	private Date dateEntry;
	private String reason;

	private List<Change> changes;

	public User() {
	}

	public User(String registration, String nameUser, String cpf, String phone, String email, String project,
			String department, String status, Date dateEntry) {
		this.registration = registration;
		this.nameUser = nameUser;
		this.cpf = cpf;
		this.phone = phone;
		this.email = email;
		this.project = project;
		this.department = department;
		this.status = status;
		this.dateEntry = dateEntry;
	}

	public User(String registration, String nameUser, String cpf, String phone, String email, String project,
			String department, String status, String reason, Date dateEntry) {
		this.registration = registration;
		this.nameUser = nameUser;
		this.cpf = cpf;
		this.phone = phone;
		this.email = email;
		this.project = project;
		this.department = department;
		this.status = status;
		this.reason = reason;
		this.dateEntry = dateEntry;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getCPF() {
		return cpf;
	}

	public void setCPF(String cpf) {
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

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(Date dateEntry) {
		this.dateEntry = dateEntry;
	}

	public List<Change> getChanges() {
		return changes;
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
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
		return registration.toString();
	}
}
