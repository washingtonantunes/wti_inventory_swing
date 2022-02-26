package model.entities.utilitay;

import java.util.Objects;

public class User_License {
	
	private String registration;
	private String code;
	
	public User_License() {
	}
	
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
		return Objects.hash(code, registration);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User_License other = (User_License) obj;
		return Objects.equals(code, other.code) && Objects.equals(registration, other.registration);
	}
}
