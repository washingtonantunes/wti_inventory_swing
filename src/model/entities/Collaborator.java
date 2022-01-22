package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Collaborator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int ADMINISTRATOR = 0;
	public static final int ANALYST = 1;
	public static final int GUEST = 2;

	private String name;
	private String registration;
	private String password;
	private int privilege;
	private String office;
	private String status;
	private Date dateEntry;
	private String reason;

	public Collaborator() {
	}

	public Collaborator(String name, String registration, String password, int privilege, String office,
			String status, Date dateEntry) {
		this.name = name;
		this.registration = registration;
		this.password = password;
		this.privilege = privilege;
		this.office = office;
		this.status = status;
		this.dateEntry = dateEntry;
	}

	public Collaborator(String name, String registration, String password, int privilege, String office,
			String status, Date dateEntry, String reason) {
		this.name = name;
		this.registration = registration;
		this.password = password;
		this.privilege = privilege;
		this.office = office;
		this.status = status;
		this.dateEntry = dateEntry;
		this.reason = reason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getStatusCollaborator() {
		return status;
	}

	public void setStatusCollaborator(String status) {
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

	@Override
	public int hashCode() {
		return Objects.hash(registration);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collaborator other = (Collaborator) obj;
		return Objects.equals(registration, other.registration);
	}

	@Override
	public String toString() {
		return name;
	}
}
