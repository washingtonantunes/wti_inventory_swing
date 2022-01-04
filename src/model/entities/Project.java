package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String locality;
	private String costCenter;
	private String status;
	private Date dateEntry;
	private String reason;

	private List<Change> changes;

	public Project() {
	}

	public Project(String name, String locality, String costCenter, String status, Date dateEntry) {
		this.name = name;
		this.locality = locality;
		this.costCenter = costCenter;
		this.status = status;
		this.dateEntry = dateEntry;
	}

	public Project(Integer id, String name, String locality, String costCenter, String status,
			Date dateEntry, String reason, List<Change> changes) {
		this.id = id;
		this.name = name;
		this.locality = locality;
		this.costCenter = costCenter;
		this.status = status;
		this.dateEntry = dateEntry;
		this.reason = reason;
		this.changes = changes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Change> getChanges() {
		return changes;
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
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
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return name;
	}
}
