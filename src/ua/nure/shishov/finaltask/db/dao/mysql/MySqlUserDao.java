package ua.nure.shishov.finaltask.db.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.db.DBManager;
import ua.nure.shishov.finaltask.db.dao.UserDao;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.DBException;

public class MySqlUserDao extends UserDao {

	private static final Logger LOG = Logger.getLogger(MySqlUserDao.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";
	
	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";
	
	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	private static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?)";

	/////////////////////////////////////////////////////////////

	@Override
	public List<User> getUsers() throws DBException {
		List<User> users = new ArrayList<>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.createStatement();

			resultSet = statement.executeQuery(SQL_FIND_ALL_USERS);

			while (resultSet.next()) {
				users.add(extractUser(resultSet));
			}

			if (!users.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_USERS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, e);
		}

		return users;
	}

	@Override
	public User getUserById(long id) throws DBException {
		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		connection = DBManager.getInstance().getConnection();
		try {
			statement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
			statement.setLong(1, id);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				user = extractUser(resultSet);
				LOG.error(Messages.TRACE_OBTAIN_USER_BY_ID + id);
			}
			
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID + id);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID + id, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return user;
	}

	@Override
	public User getUserByLogin(String login) throws DBException {
		if(login == null) {
			return null;
		}
		
		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		connection = DBManager.getInstance().getConnection();
		try {
			statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			statement.setString(1, login);

			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				user = extractUser(resultSet);
				LOG.trace(Messages.TRACE_OBTAIN_USER_BY_LOGIN + login);
			}

		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN + login, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN + login, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return user;
	}

	@Override
	public User insertUser(User user) throws DBException {
		User userToReturn = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;


		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);

			int k = 1;
			statement.setString(k++, user.getLogin());
			statement.setString(k++, user.getPassword());
			statement.setString(k++, user.getFirstName());
			statement.setString(k++, user.getLastName());
			statement.setInt(k++, user.getRoleId());

			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();

			if (resultSet.next()) {
				userToReturn = copyUser(user);
				userToReturn.setId(resultSet.getLong(1));
				LOG.trace(Messages.TRACE_INSERT_CAR + userToReturn);
			}

		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_INSERT_USER + user, e);
			throw new DBException(Messages.ERR_CANNOT_INSERT_CAR + user, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return userToReturn;
	}

}
