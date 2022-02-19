package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Project implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String costCenter;
	private String name;
	private String city;
	private String status;
	private Date dateEntry;
	private String reason;

	private Integer quantityDesktop;
	private Integer quantityNotebook;

	private Double valueTotal;

	private List<Change> changes;

	public Project() {
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public Integer getQuantityDesktop() {
		return quantityDesktop;
	}

	public void setQuantityDesktop(Integer quantityDesktop) {
		this.quantityDesktop = quantityDesktop;
	}

	public Integer getQuantityNotebook() {
		return quantityNotebook;
	}

	public void setQuantityNotebook(Integer quantityNotebook) {
		this.quantityNotebook = quantityNotebook;
	}

	public Double getValueTotal() {
		return valueTotal;
	}

	public void setValueTotal(Double valueTotal) {
		this.valueTotal = valueTotal;
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

	@Override
	public int hashCode() {
		return Objects.hash(costCenter);
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
		return Objects.equals(costCenter, other.costCenter);
	}

	@Override
	public String toString() {
		return name;
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
