package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Monitor implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serialNumber;
	private String brand;
	private String model;
	private String patrimonyNumber;
	private String status;
	private Date dateEntry;
	private String reason;

	private List<Change> changes;

	public Monitor() {
	}

	public Monitor(String serialNumber, String model, String patrimonyNumber, String status,
			Date dateEntry) {
		this.serialNumber = serialNumber;
		this.model = model;
		this.patrimonyNumber = patrimonyNumber;
		this.status = status;
		this.dateEntry = dateEntry;
	}

	public Monitor(String serialNumber, String model, String patrimonyNumber, String status,
			Date dateEntry, String reason, List<model.entities.Change> changes) {
		this.serialNumber = serialNumber;
		this.model = model;
		this.patrimonyNumber = patrimonyNumber;
		this.status = status;
		this.dateEntry = dateEntry;
		this.reason = reason;
		this.changes = changes;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPatrimonyNumber() {
		return patrimonyNumber;
	}

	public void setPatrimonyNumber(String patrimonyNumber) {
		this.patrimonyNumber = patrimonyNumber;
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
		return Objects.hash(serialNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Monitor other = (Monitor) obj;
		return Objects.equals(serialNumber, other.serialNumber);
	}

	@Override
	public String toString() {
		return serialNumber;
	}
}
