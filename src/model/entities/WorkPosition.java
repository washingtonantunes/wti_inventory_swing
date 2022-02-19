package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class WorkPosition implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String workPoint;
	private String location;
	private String floor;
	private String netPoint;
	private String status;
	private Date dateEntry;
	private String reason;
	
	private Equipment equipment;
	private Monitor monitor1;
	private Monitor monitor2;

	private List<Change> changes = new ArrayList<>();;

	public WorkPosition() {
	}

	public String getWorkPoint() {
		return workPoint;
	}

	public void setWorkPoint(String workPoint) {
		this.workPoint = workPoint;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getNetPoint() {
		return netPoint;
	}

	public void setNetPoint(String netPoint) {
		this.netPoint = netPoint;
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
		return Objects.hash(floor, location, workPoint);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkPosition other = (WorkPosition) obj;
		return Objects.equals(floor, other.floor) && Objects.equals(location, other.location)
				&& Objects.equals(workPoint, other.workPoint);
	}

	@Override
	public String toString() {
		return workPoint;
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
