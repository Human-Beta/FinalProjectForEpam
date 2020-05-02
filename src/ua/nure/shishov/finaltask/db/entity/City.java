package ua.nure.shishov.finaltask.db.entity;

public class City extends Entity {

	private static final long serialVersionUID = -6222927222744681496L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", getId()=" + getId() + "]";
	}

}
