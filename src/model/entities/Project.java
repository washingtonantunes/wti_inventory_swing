package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idProject;
	private String nameProject;
	private String locality;
	private String costCenter;
	private String statusProject;
	private Date dateEntry;
	private String reason;

	private List<Change> changes;

	public Project() {
	}
	
	public Project(String nameProject, String locality, String costCenter,
			String statusProject, Date dateEntry) {
		this.nameProject = nameProject;
		this.locality = locality;
		this.costCenter = costCenter;
		this.statusProject = statusProject;
		this.dateEntry = dateEntry;
	}

	public Project(Integer idProject, String nameProject, String locality, String costCenter,
			String statusProject, Date dateEntry, String reason, List<Change> changes) {
		this.idProject = idProject;
		this.nameProject = nameProject;
		this.locality = locality;
		this.costCenter = costCenter;
		this.statusProject = statusProject;
		this.dateEntry = dateEntry;
		this.reason = reason;
		this.changes = changes;
	}

	public Integer getIdProject() {
		return idProject;
	}

	public void setIdProject(Integer idProject) {
		this.idProject = idProject;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getStatusProject() {
		return statusProject;
	}

	public void setStatusProject(String statusProject) {
		this.statusProject = statusProject;
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

	public List<Change> getChanges() {
		return changes;
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idProject, nameProject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		return Objects.equals(idProject, other.idProject) && Objects.equals(nameProject, other.nameProject);
	}

	@Override
	public String toString() {
		return nameProject;
	}
}
