package model.entities.utilitary;

import java.io.Serializable;
import java.util.Objects;

public class EquipmentWithUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String registration;
	private String serialNumber;
	
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	@Override
	public int hashCode() {
		return Objects.hash(registration, serialNumber);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipmentWithUser other = (EquipmentWithUser) obj;
		return Objects.equals(registration, other.registration) && Objects.equals(serialNumber, other.serialNumber);
	}
}
