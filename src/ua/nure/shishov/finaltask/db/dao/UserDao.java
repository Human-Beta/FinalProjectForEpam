package ua.nure.shishov.finaltask.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.nure.shishov.finaltask.db.Fields;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.DBException;

public abstract class UserDao {

	public abstract List<User> getUsers() throws DBException;

	public abstract User getUserById(long id) throws DBException;

	public abstract User getUserByLogin(String login) throws DBException;

	public abstract User insertUser(User user) throws DBException;
	
	// ////////////////////////////////////
	// Util methods
	///////////////////////////////////////

	/**
	 * Extracts a user entity from the result set.
	 * 
	 * @param rs Result set from which a user entity will be extracted.
	 * @return User entity
	 * @throws SQLException
	 */
	public User extractUser(ResultSet rs) throws SQLException {
		User user = new User();

		user.setId(rs.getLong(Fields.ENTITY_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setLastName(rs.getString(Fields.USER_LAST_NAME));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));

		return user;
	}
	
	/**
	 * Copy a user entity.
	 * 
	 * @param user The original object to be copied.
	 * @return Copy of {@link User} object.
	 */
	protected User copyUser(User user) {
		User copy = new User();
		
		copy.setId(user.getId());
		copy.setLogin(user.getLogin());
		copy.setPassword(user.getPassword());
		copy.setFirstName(user.getFirstName());
		copy.setLastName(user.getLastName());
		copy.setRoleId(user.getRoleId());
		
		return copy;
	}


}
