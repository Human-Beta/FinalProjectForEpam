package ua.nure.shishov.finaltask.db.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.db.DBManager;
import ua.nure.shishov.finaltask.db.bean.UserBean;
import ua.nure.shishov.finaltask.db.dao.UserBeanDao;
import ua.nure.shishov.finaltask.exception.DBException;

public class MySqlUserBeanDao extends UserBeanDao {

	private static final Logger LOG = Logger.getLogger(MySqlUserBeanDao.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_ALL_USER_BEANS = "SELECT u.id, u.login, u.first_name, u.last_name, r.name AS role_name "
			+ "FROM users AS u " + "INNER JOIN roles AS r ON u.role_id=r.id";

	/////////////////////////////////////////////////////////////

	@Override
	public List<UserBean> getUserBeans() throws DBException {
		List<UserBean> userBeans = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.createStatement();

			resultSet = statement.executeQuery(SQL_FIND_ALL_USER_BEANS);

			while (resultSet.next()) {
				userBeans.add(extractUserBean(resultSet));
			}
			
			if (!userBeans.isEmpty()) {
				LOG.error(Messages.TRACE_OBTAIN_USER_BEANS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BEANS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BEANS, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return userBeans;
	}

}
