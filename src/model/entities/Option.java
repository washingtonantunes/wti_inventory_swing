package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Option implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String option;
	private String type;
	private String status;
	private Date dateEntry;

	public Option() {
	}

	public Option(String option, String type, String status, Date dateEntry) {
		this.option = option;
		this.type = type;
		this.status = status;
		this.dateEntry = dateEntry;
	}

	public Option(Integer id, String option, String type, String status, Date dateEntry) {
		this.id = id;
		this.option = option;
		this.type = type;
		this.status = status;
		this.dateEntry = dateEntry;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(Date dateEntry) {
		this.dateEntry = dateEntry;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, option);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Option other = (Option) obj;
		return Objects.equals(id, other.id) && Objects.equals(option, other.option);
	}

	@Override
	public String toString() {
		return "Option [id=" + id + ", option=" + option + ", type=" + type + ", status=" + status + ", dateEntry=" + dateEntry + "]";
	}
		
}
