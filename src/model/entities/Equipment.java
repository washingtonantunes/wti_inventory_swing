package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Equipment implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serialNumber;
	private String hostName;
	private String addressMAC;
	private String type;
	private String patrimonyNumber;
	private String brand;
	private String model;
	private String memoryRam;
	private String hardDisk;
	private String costType;
	private Double value;
	private String status;
	private Date dateEntry;
	private String reason;

	private List<Change> changes;

	public Equipment() {
	}

	public Equipment(String serialNumber, String hostName, String addressMAC, String type,
			String patrimonyNumber, String brand, String model, String memoryRam,
			String hardDisk, String costType, Double value, String status, Date dateEntry) {
		this.serialNumber = serialNumber;
		this.hostName = hostName;
		this.addressMAC = addressMAC;
		this.type = type;
		this.patrimonyNumber = patrimonyNumber;
		this.brand = brand;
		this.model = model;
		this.memoryRam = memoryRam;
		this.hardDisk = hardDisk;
		this.costType = costType;
		this.value = value;
		this.status = status;
		this.dateEntry = dateEntry;
	}

	public Equipment(String serialNumber, String hostName, String addressMAC, String type,
			String patrimonyNumber, String brand, String model, String memoryRam,
			String hardDisk, String costType, Double value, String status, Date dateEntry,
			String reason, List<model.entities.Change> changes) {
		this.serialNumber = serialNumber;
		this.hostName = hostName;
		this.addressMAC = addressMAC;
		this.type = type;
		this.patrimonyNumber = patrimonyNumber;
		this.brand = brand;
		this.model = model;
		this.memoryRam = memoryRam;
		this.hardDisk = hardDisk;
		this.costType = costType;
		this.value = value;
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

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getAddressMAC() {
		return addressMAC;
	}

	public void setAddressMAC(String addressMAC) {
		this.addressMAC = addressMAC;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPatrimonyNumber() {
		return patrimonyNumber;
	}

	public void setPatrimonyNumber(String patrimonyNumber) {
		this.patrimonyNumber = patrimonyNumber;
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

	public String getMemoryRam() {
		return memoryRam;
	}

	public void setMemoryRam(String memoryRam) {
		this.memoryRam = memoryRam;
	}

	public String getHardDisk() {
		return hardDisk;
	}

	public void setHardDisk(String hardDisk) {
		this.hardDisk = hardDisk;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
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
		return Objects.hash(addressMAC, hostName, serialNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipment other = (Equipment) obj;
		return Objects.equals(addressMAC, other.addressMAC) && Objects.equals(hostName, other.hostName)
				&& Objects.equals(serialNumber, other.serialNumber);
	}

	@Override
	public String toString() {
		return serialNumber;
	}
}
