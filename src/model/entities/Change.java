package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Change implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idChange;
	private String object;
	private String typeChange;
	private String changes;
	private Date dateChange;
	private String author;

	public Change() {
	}
	
	public Change(String object, String typeChange, String changes, String author) {
		this.object = object;
		this.typeChange = typeChange;
		this.changes = changes;
		this.author = author;
	}

	public Change(Integer idChange, String object, String typeChange, String changes, Date dateChange, String author) {
		this.idChange = idChange;
		this.object = object;
		this.typeChange = typeChange;
		this.changes = changes;
		this.dateChange = dateChange;
		this.author = author;
	}

	public Integer getIdChange() {
		return idChange;
	}

	public void setIdChange(Integer idChange) {
		this.idChange = idChange;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getTypeChange() {
		return typeChange;
	}

	public void setTypeChange(String typeChange) {
		this.typeChange = typeChange;
	}

	public Date getDateChange() {
		return dateChange;
	}

	public void setDateChange(Date dateChange) {
		this.dateChange = dateChange;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idChange);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Change other = (Change) obj;
		return Objects.equals(idChange, other.idChange);
	}

	@Override
	public String toString() {
		return "Change [idChange=" + idChange + "]";
	}
}
