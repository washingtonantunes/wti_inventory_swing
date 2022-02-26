package model.entities.utilitary;

import java.io.Serializable;
import java.util.Objects;

public class PeripheralWithUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String registration;
	private String code;
	
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public int hashCode() {
		return Objects.hash(registration, code);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PeripheralWithUser other = (PeripheralWithUser) obj;
		return Objects.equals(registration, other.registration) && Objects.equals(code, other.code);
	}
}
