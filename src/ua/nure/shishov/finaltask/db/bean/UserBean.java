package ua.nure.shishov.finaltask.db.bean;

import ua.nure.shishov.finaltask.db.entity.Entity;

public class UserBean extends Entity {

	private static final long serialVersionUID = -2084917695917641376L;

	private String login;
	private String firstName;
	private String lastName;
	private String roleName;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "UserBean [login=" + login + ", firstName=" + firstName + ", lastName=" + lastName + ", roleName="
				+ roleName + ", getId()=" + getId() + "]";
	}

}
