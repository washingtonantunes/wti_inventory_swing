package model.entities;

import java.beans.Beans;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Monitor extends Beans implements Serializable, Cloneable {

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
	
	@Override
	public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
