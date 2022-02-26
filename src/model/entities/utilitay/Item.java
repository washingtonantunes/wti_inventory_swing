package model.entities.utilitay;

import java.util.Objects;

public class Item {

	private int index;
	private String type;
	private String code;
	private String name;
	private String brand;
	private String patrimonyNumber;
	private Double value;

	public Item() {
	}
	
	public Item(String name) {
		this.name = name;
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
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getPatrimonyNumber() {
		return patrimonyNumber;
	}

	public void setPatrimonyNumber(String patrimonyNumber) {
		this.patrimonyNumber = patrimonyNumber;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brand, name, type);
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
		return Objects.equals(brand, other.brand) && Objects.equals(name, other.name)
				&& Objects.equals(type, other.type);
	}
}
