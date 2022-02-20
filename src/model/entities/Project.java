package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
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

	private Integer quantityDesktops;
	private Integer quantityNotebooks;

	private Double costTotal;

	private List<Change> changes = new ArrayList<>();

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
		return quantityDesktops;
	}

	public void setQuantityDesktops(Integer quantityDesktops) {
		this.quantityDesktops = quantityDesktops;
	}

	public Integer getQuantityNotebooks() {
		return quantityNotebooks;
	}

	public void setQuantityNotebooks(Integer quantityNotebooks) {
		this.quantityNotebooks = quantityNotebooks;
	}

	public Double getCostTotals() {
		return costTotal;
	}

	public void setCostTotal(Double costTotal) {
		this.costTotal = costTotal;
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
