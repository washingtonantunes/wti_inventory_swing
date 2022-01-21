package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Change implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String object;
	private String type;
	private String changes;
	private Date date;
	private String author;

	public Change() {
	}

	public Change(Integer id, String object, String type, String changes, Date date, String author) {
		this.id = id;
		this.object = object;
		this.type = type;
		this.changes = changes;
		this.date = date;
		this.author = author;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Change [idChange=" + id + "]";
	}
}
