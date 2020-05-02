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
import ua.nure.shishov.finaltask.db.bean.RequestBean;
import ua.nure.shishov.finaltask.db.dao.RequestBeanDao;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.DBException;

public class MySqlRequestBeanDao extends RequestBeanDao {
	
	private static final Logger LOG = Logger.getLogger(MySqlRequestBeanDao.class);


	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_ALL_REQUEST_BEANS = "SELECT r.id, r.user_id, u.login AS user_login,"
			+ "r.flight_id, CONCAT(c1.name, '-', c2.name) AS flight_direction,"
			+ "cm.name AS car_model_name, r.car_engine_power, r.car_num_of_seats, ct.name AS car_engine_type "
			+ "FROM requests AS r "
			+ "INNER JOIN users AS u ON r.user_id=u.id "
			+ "INNER JOIN flights AS f ON r.flight_id=f.id "
			+ "INNER JOIN car_models AS cm ON r.car_model_id=cm.id "
			+ "INNER JOIN car_engine_types AS ct ON r.car_engine_type_id=ct.id "
			+ "INNER JOIN cities AS c1 ON f.from_city_id=c1.id "
			+ "INNER JOIN cities AS c2 ON f.to_city_id=c2.id";
	
	private static final String SQL_FIND_REQUEST_BEANS_BY_USER = "SELECT r.id, r.user_id, u.login AS user_login,"
			+ "r.flight_id, CONCAT(c1.name, '-', c2.name) AS flight_direction,"
			+ "cm.name AS car_model_name, r.car_engine_power, r.car_num_of_seats, ct.name AS car_engine_type "
			+ "FROM requests AS r "
			+ "INNER JOIN users AS u ON r.user_id=u.id AND r.user_id=? "
			+ "INNER JOIN flights AS f ON r.flight_id=f.id "
			+ "INNER JOIN car_models AS cm ON r.car_model_id=cm.id "
			+ "INNER JOIN car_engine_types AS ct ON r.car_engine_type_id=ct.id "
			+ "INNER JOIN cities AS c1 ON f.from_city_id=c1.id "
			+ "INNER JOIN cities AS c2 ON f.to_city_id=c2.id";

	/////////////////////////////////////////////////////////////

	@Override
	public List<RequestBean> getRequestBeans() throws DBException {
		List<RequestBean> requestBeans = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DBManager.getInstance().getConnection();

			statement = connection.createStatement();

			resultSet = statement.executeQuery(SQL_FIND_ALL_REQUEST_BEANS);

			while (resultSet.next()) {
				requestBeans.add(extractRequestBean(resultSet));
			}

			if(!requestBeans.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_REQUEST_BEANS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_REQUEST_BEANS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_REQUEST_BEANS, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return requestBeans;
	}

	@Override
	public List<RequestBean> getRequestBeansOfUser(User user) throws DBException {
		List<RequestBean> requestBeans = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DBManager.getInstance().getConnection();

			statement = connection.prepareStatement(SQL_FIND_REQUEST_BEANS_BY_USER);
			statement.setLong(1, user.getId());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				requestBeans.add(extractRequestBean(resultSet));
			}

			if(!requestBeans.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_REQUEST_BEANS_OF_USER + user);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_REQUEST_BEANS_OF_USER + user, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_REQUEST_BEANS_OF_USER + user, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return requestBeans;
	}

}
