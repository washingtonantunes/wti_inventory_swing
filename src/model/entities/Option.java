package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Option implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idOption;
	private String option;
	private String type;
	private String statusOption;
	private Date dateEntry;

	public Option() {
	}
	
	public Option(String option, String type, String statusOption, Date dateEntry) {
		this.option = option;
		this.type = type;
		this.statusOption = statusOption;
		this.dateEntry = dateEntry;
	}

	public Option(Integer idOption, String option, String type, String statusOption, Date dateEntry) {
		this.idOption = idOption;
		this.option = option;
		this.type = type;
		this.statusOption = statusOption;
		this.dateEntry = dateEntry;
	}

	public Integer getIdOption() {
		return idOption;
	}

	public void setIdOption(Integer idOption) {
		this.idOption = idOption;
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

	public String getStatusOption() {
		return statusOption;
	}

	public void setStatusOption(String statusOption) {
		this.statusOption = statusOption;
	}

	public Date getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(Date dateEntry) {
		this.dateEntry = dateEntry;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idOption, option);
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
		return Objects.equals(idOption, other.idOption) && Objects.equals(option, other.option);
	}

	@Override
	public String toString() {
		return option;
	}
}
