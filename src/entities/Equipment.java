package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Equipment implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serialNumber;
	private String hostName;
	private String addressMAC;
	private String typeEquipment;
	private String patrimonyNumberEquipment;
	private String brandEquipment;
	private String modelEquipment;
	private String memoryRam;
	private String hardDisk;
	private String costType;
	private Double valueEquipment;
	private String statusEquipment;
	private Date dateEntry;
	private String reason;

	private List<Change> changes;

	public Equipment() {
	}
	
	public Equipment(String serialNumber, String hostName, String addressMAC, String typeEquipment,
			String patrimonyNumberEquipment, String brandEquipment, String modelEquipment, String memoryRam,
			String hardDisk, String costType, Double valueEquipment, String statusEquipment, Date dateEntry) {
		this.serialNumber = serialNumber;
		this.hostName = hostName;
		this.addressMAC = addressMAC;
		this.typeEquipment = typeEquipment;
		this.patrimonyNumberEquipment = patrimonyNumberEquipment;
		this.brandEquipment = brandEquipment;
		this.modelEquipment = modelEquipment;
		this.memoryRam = memoryRam;
		this.hardDisk = hardDisk;
		this.costType = costType;
		this.valueEquipment = valueEquipment;
		this.statusEquipment = statusEquipment;
		this.dateEntry = dateEntry;
	}

	public Equipment(String serialNumber, String hostName, String addressMAC, String typeEquipment,
			String patrimonyNumberEquipment, String brandEquipment, String modelEquipment, String memoryRam,
			String hardDisk, String costType, Double valueEquipment, String statusEquipment, Date dateEntry,
			String reason, List<entities.Change> changes) {
		this.serialNumber = serialNumber;
		this.hostName = hostName;
		this.addressMAC = addressMAC;
		this.typeEquipment = typeEquipment;
		this.patrimonyNumberEquipment = patrimonyNumberEquipment;
		this.brandEquipment = brandEquipment;
		this.modelEquipment = modelEquipment;
		this.memoryRam = memoryRam;
		this.hardDisk = hardDisk;
		this.costType = costType;
		this.valueEquipment = valueEquipment;
		this.statusEquipment = statusEquipment;
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

	public String getTypeEquipment() {
		return typeEquipment;
	}

	public void setTypeEquipment(String typeEquipment) {
		this.typeEquipment = typeEquipment;
	}

	public String getPatrimonyNumberEquipment() {
		return patrimonyNumberEquipment;
	}

	public void setPatrimonyNumberEquipment(String patrimonyNumberEquipment) {
		this.patrimonyNumberEquipment = patrimonyNumberEquipment;
	}

	public String getBrandEquipment() {
		return brandEquipment;
	}

	public void setBrandEquipment(String brandEquipment) {
		this.brandEquipment = brandEquipment;
	}

	public String getModelEquipment() {
		return modelEquipment;
	}

	public void setModelEquipment(String modelEquipment) {
		this.modelEquipment = modelEquipment;
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

	public Double getValueEquipment() {
		return valueEquipment;
	}

	public void setValueEquipment(Double valueEquipment) {
		this.valueEquipment = valueEquipment;
	}

	public String getStatusEquipment() {
		return statusEquipment;
	}

	public void setStatusEquipment(String statusEquipment) {
		this.statusEquipment = statusEquipment;
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
