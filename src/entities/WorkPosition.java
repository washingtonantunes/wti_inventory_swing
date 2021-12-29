package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class WorkPosition implements Serializable {

	private static final long serialVersionUID = 1L;

	private String workPoint;
	private String location;
	private String floors;
	private String netPoint;
	private String statusWorkPoint;
	private Date dateEntry;
	private String reason;

	private List<Change> changes;

	public WorkPosition() {
	}

	public WorkPosition(String workPoint, String location, String floors, String netPoint, String statusWorkPoint,
			Date dateEntry, String reason, List<Change> changes) {
		this.workPoint = workPoint;
		this.location = location;
		this.floors = floors;
		this.netPoint = netPoint;
		this.statusWorkPoint = statusWorkPoint;
		this.dateEntry = dateEntry;
		this.reason = reason;
		this.changes = changes;
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

	public String getFloors() {
		return floors;
	}

	public void setFloors(String floors) {
		this.floors = floors;
	}

	public String getNetPoint() {
		return netPoint;
	}

	public void setNetPoint(String netPoint) {
		this.netPoint = netPoint;
	}

	public String getStatusWorkPoint() {
		return statusWorkPoint;
	}

	public void setStatusWorkPoint(String statusWorkPoint) {
		this.statusWorkPoint = statusWorkPoint;
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
		return Objects.hash(floors, location, workPoint);
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
		return Objects.equals(floors, other.floors) && Objects.equals(location, other.location)
				&& Objects.equals(workPoint, other.workPoint);
	}

	@Override
	public String toString() {
		return workPoint;
	}
}
