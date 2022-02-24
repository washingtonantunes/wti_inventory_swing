package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Peripheral implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Double value;
	private Integer quantity;
	private String status;

	private String reason;
	private String user;

	private List<Change> changes = new ArrayList<>();

	public Peripheral() {
	}
	
	public Peripheral(String name) {
		this.name = name;
	}

	public Peripheral(String name, Double value, Integer quantity, String status) {
		this.name = name;
		this.value = value;
		this.quantity = quantity;
		this.status = status;
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

	public String getStatus() {
		return status;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Change> getChanges() {
		return changes;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void addChange(Change change) {
		this.changes.add(change);
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Peripheral other = (Peripheral) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return name;
	}

}