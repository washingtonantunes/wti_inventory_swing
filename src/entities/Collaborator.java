package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Collaborator implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String registration;
	private String password;
	private String privilege;
	private String office;
	private String statusCollaborator;
	private Date dateEntry;

	public Collaborator() {
	}

	public Collaborator(String name, String registration, String password, String privilege, String office,
			String statusCollaborator, Date dateEntry) {
		super();
		this.name = name;
		this.registration = registration;
		this.password = password;
		this.privilege = privilege;
		this.office = office;
		this.statusCollaborator = statusCollaborator;
		this.dateEntry = dateEntry;
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

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getStatusCollaborator() {
		return statusCollaborator;
	}

	public void setStatusCollaborator(String statusCollaborator) {
		this.statusCollaborator = statusCollaborator;
	}

	public Date getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(Date dateEntry) {
		this.dateEntry = dateEntry;
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
