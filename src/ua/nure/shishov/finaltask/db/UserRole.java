package ua.nure.shishov.finaltask.db;

import ua.nure.shishov.finaltask.db.entity.User;

/**
 * Role entity.
 * 
 * @author N.Shishov
 * 
 */
public enum UserRole {

	ADMIN, DISPATCHER, DRIVER;

	public static UserRole getRole(User user) {
		int roleId = user.getRoleId();
		return values()[roleId];
	}

	public String getName() {
		return name().toLowerCase();
	}
}
