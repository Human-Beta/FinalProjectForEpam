package ua.nure.shishov.finaltask.db.entity;

public class CarModel extends Entity {

	private static final long serialVersionUID = -3377935904763479852L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Model [name=" + name + ", getId()=" + getId() + "]";
	}

}
