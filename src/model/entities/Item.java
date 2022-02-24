package model.entities;

import java.util.Objects;

public class Item {

	private int index;
	private String type;
	private String name;
	private Double value;

	public Item() {
	}
	
	public Item(String name) {
		this.name = name;
	}

	public Item(int index, String type, String name, Double value) {
		this.index = index;
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	@Override
	public int hashCode() {
		return Objects.hash(name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(name, other.name) && Objects.equals(type, other.type);
	}
}
