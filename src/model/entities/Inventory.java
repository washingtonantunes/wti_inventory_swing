package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Inventory implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private WorkPosition workPosition;
	private Project project;
	private User user;
	private Equipment equipment;
	private Monitor monitor1;
	private Monitor monitor2;

	public Inventory() {
	}

	public Inventory(Integer id, WorkPosition workPosition, Project project, User user, Equipment equipment,
			Monitor monitor1, Monitor monitor2) {
		super();
		this.id = id;
		this.workPosition = workPosition;
		this.project = project;
		this.user = user;
		this.equipment = equipment;
		this.monitor1 = monitor1;
		this.monitor2 = monitor2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WorkPosition getWorkPosition() {
		return workPosition;
	}

	public void setWorkPosition(WorkPosition workPosition) {
		this.workPosition = workPosition;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Monitor getMonitor1() {
		return monitor1;
	}

	public void setMonitor1(Monitor monitor1) {
		this.monitor1 = monitor1;
	}

	public Monitor getMonitor2() {
		return monitor2;
	}

	public void setMonitor2(Monitor monitor2) {
		this.monitor2 = monitor2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(equipment, id, monitor1, monitor2, project);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		return Objects.equals(equipment, other.equipment) && Objects.equals(id, other.id)
				&& Objects.equals(monitor1, other.monitor1) && Objects.equals(monitor2, other.monitor2)
				&& Objects.equals(project, other.project);
	}
	
	@Override
	public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
