package model.entities;

import java.io.Serializable;
import java.util.Date;

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
	public String toString() {
		return "Change [id=" + id + ", object=" + object + ", type=" + type + ", changes=" + changes + ", date=" + date + ", author=" + author + "]";
	}
}
