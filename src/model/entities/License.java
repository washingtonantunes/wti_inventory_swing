package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class License implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String name;
	private Double value;

	private User user;

	public License() {
	}

	public License(String code, String name, Double value) {
		this.code = code;
		this.name = name;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		License other = (License) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return name;
	}
}
