package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Monitor implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serialNumberMonitor;
	private String modelMonitor;
	private String patrimonyNumberMonitor;
	private String statusMonitor;
	private Date dateEntry;
	private String reason;

	private List<Change> changes;

	public Monitor() {
	}
	
	public Monitor(String serialNumberMonitor, String modelMonitor, String patrimonyNumberMonitor, String statusMonitor) {
		this.serialNumberMonitor = serialNumberMonitor;
		this.modelMonitor = modelMonitor;
		this.patrimonyNumberMonitor = patrimonyNumberMonitor;
		this.statusMonitor = statusMonitor;
	}

	public Monitor(String serialNumberMonitor, String modelMonitor, String patrimonyNumberMonitor, String statusMonitor,
			Date dateEntry, String reason, List<entities.Change> changes) {
		this.serialNumberMonitor = serialNumberMonitor;
		this.modelMonitor = modelMonitor;
		this.patrimonyNumberMonitor = patrimonyNumberMonitor;
		this.statusMonitor = statusMonitor;
		this.dateEntry = dateEntry;
		this.reason = reason;
		this.changes = changes;
	}

	public String getSerialNumberMonitor() {
		return serialNumberMonitor;
	}

	public void setSerialNumberMonitor(String serialNumberMonitor) {
		this.serialNumberMonitor = serialNumberMonitor;
	}

	public String getModelMonitor() {
		return modelMonitor;
	}

	public void setModelMonitor(String modelMonitor) {
		this.modelMonitor = modelMonitor;
	}

	public String getPatrimonyNumberMonitor() {
		return patrimonyNumberMonitor;
	}

	public void setPatrimonyNumberMonitor(String patrimonyNumberMonitor) {
		this.patrimonyNumberMonitor = patrimonyNumberMonitor;
	}

	public String getStatusMonitor() {
		return statusMonitor;
	}

	public void setStatusMonitor(String statusMonitor) {
		this.statusMonitor = statusMonitor;
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
		return Objects.hash(serialNumberMonitor);
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
		return Objects.equals(serialNumberMonitor, other.serialNumberMonitor);
	}

	@Override
	public String toString() {
		return serialNumberMonitor;
	}
}
