package ua.nure.shishov.finaltask.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.exception.DBException;

/**
 * DB manager. Provides methods to interact with DB.
 * 
 * @author N.SHishov
 * 
 */
public class DBManager {
	private static final Logger LOG = Logger.getLogger(DBManager.class);

	private static DBManager instance;

	/**
	 * DBManager instance is provided by this method.
	 * 
	 * @return Instance of DBManager
	 * @throws DBException
	 */
	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DataSource ds;

	private DBManager() throws DBException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			// ST4DB - the name of data source

			ds = (DataSource) envContext.lookup("jdbc/ST4DB");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
		}
	}

	/**
	 * Returns a DB connection from the Pool Connections. Before using this method
	 * you must configure the Date Source and the Connections Pool in your
	 * WEB_APP_ROOT/META-INF/context.xml file.
	 * 
	 * @throws DBException
	 * @return DB connection.
	 */
	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
		}
		return con;
	}

	/**
	 * Closes a result set object.
	 * 
	 * @param resultSet Result set to be closed.
	 */
	public void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception e) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULT_SET, e);
			}
		}
	}

	/**
	 * Closes a statement object.
	 * 
	 * @param statement Statement to be closed.
	 */
	public void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, e);
			}
		}
	}

	/**
	 * Closes a connection object.
	 * 
	 * @param connection Connection to be closed.
	 */
	public void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, e);
			}
		}
	}

	/**
	 * Closes resources.
	 * 
	 * @param connection Connection to be closed.
	 * @param statement  Statement to be closed.
	 * @param resultSet  Result set to be closed.
	 */
	public void close(Connection connection, Statement statement, ResultSet resultSet) {
		close(resultSet);
		close(statement);
		close(connection);
	}
}
