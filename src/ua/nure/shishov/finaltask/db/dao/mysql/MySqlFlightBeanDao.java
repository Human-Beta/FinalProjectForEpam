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
import ua.nure.shishov.finaltask.db.FlightStatus;
import ua.nure.shishov.finaltask.db.bean.FlightBean;
import ua.nure.shishov.finaltask.db.dao.FlightBeanDao;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.DBException;

public class MySqlFlightBeanDao extends FlightBeanDao {

	private static final Logger LOG = Logger.getLogger(MySqlFlightBeanDao.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

//	private static final String SQL_FIND_ALL_FLIGHT_BEANS = "SELECT f.id, c1.name AS from_city_name, c2.name AS to_city_name, f.date_of_creation, s.name AS status_name, "
//			+ "u.id AS user_id, u.login AS user_login_name, u.first_name AS user_first_name, u.last_name AS user_last_name, "
//			+ "m.name AS car_model, c.id AS car_id, c.engine_power AS car_engine_power, c.length AS car_length, c.width AS car_width, c.height AS car_height, st.name AS car_state "
//			+ "FROM flights AS f " + "LEFT JOIN cities AS c1 ON f.to_city_id=c1.id "
//			+ "LEFT JOIN cities AS c2 ON f.from_city_id=c2.id "
//			+ "LEFT JOIN statuses AS s ON f.status_id=s.id "
//			+ "LEFT JOIN users AS u ON f.user_id=u.id "
//			+ "LEFT JOIN cars AS c ON f.car_id=c.id "
//			+ "LEFT JOIN car_models AS m ON c.model_id=m.id "
//			+ "LEFT JOIN car_states AS st ON c.state_id=st.id";
	private static final String SQL_FIND_ALL_FLIGHT_BEANS = "SELECT f.id, CONCAT(c1.name, '-', c2.name) AS direction, f.date_of_creation, s.name AS status_name, "
			+ "u.id AS user_id, u.login AS user_login_name, "
			+ "m.name AS car_model, c.id AS car_id "
			+ "FROM flights AS f "
			+ "INNER JOIN cities AS c1 ON f.from_city_id=c1.id "
			+ "INNER JOIN cities AS c2 ON f.to_city_id=c2.id "
			+ "INNER JOIN statuses AS s ON f.status_id=s.id "
			+ "LEFT JOIN users AS u ON f.user_id=u.id "
			+ "LEFT JOIN cars AS c ON f.car_id=c.id "
			+ "LEFT JOIN car_models AS m ON c.model_id=m.id";
//			+ "LEFT JOIN car_states AS st ON c.state_id=st.id";

	private static final String SQL_FIND_FLIGHT_BEANS_BY_STATUS = "SELECT f.id, CONCAT(c1.name, '-', c2.name) AS direction, f.date_of_creation, s.name AS status_name, "
			+ "u.id AS user_id, u.login AS user_login_name, "
			+ "m.name AS car_model, c.id AS car_id "
			+ "FROM flights AS f "
			+ "INNER JOIN cities AS c1 ON f.from_city_id=c1.id "
			+ "INNER JOIN cities AS c2 ON f.to_city_id=c2.id "
			+ "INNER JOIN statuses AS s ON f.status_id=s.id AND f.status_id=? "
			+ "LEFT JOIN users AS u ON f.user_id=u.id "
			+ "LEFT JOIN cars AS c ON f.car_id=c.id "
			+ "LEFT JOIN car_models AS m ON c.model_id=m.id";
//			+ "LEFT JOIN car_states AS st ON c.state_id=st.id";

	private static final String SQL_FIND_FLIGHT_BEANS_BY_USER = "SELECT f.id, CONCAT(c1.name, '-', c2.name) AS direction, f.date_of_creation, s.name AS status_name, "
			+ "u.id AS user_id, u.login AS user_login_name, "
			+ "m.name AS car_model, c.id AS car_id "
			+ "FROM flights AS f "
			+ "INNER JOIN cities AS c1 ON f.from_city_id=c1.id "
			+ "INNER JOIN cities AS c2 ON f.to_city_id=c2.id "
			+ "INNER JOIN statuses AS s ON f.status_id=s.id "
			+ "INNER JOIN users AS u ON f.user_id=u.id AND f.user_id=? "
			+ "LEFT JOIN cars AS c ON f.car_id=c.id "
			+ "LEFT JOIN car_models AS m ON c.model_id=m.id";
//			+ "LEFT JOIN car_states AS st ON c.state_id=st.id";
	/////////////////////////////////////////////////////////////

	@Override
	public List<FlightBean> getFlightBeans() throws DBException {
		List<FlightBean> flightBeans = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DBManager.getInstance().getConnection();

			statement = connection.createStatement();

			resultSet = statement.executeQuery(SQL_FIND_ALL_FLIGHT_BEANS);

			while (resultSet.next()) {
				flightBeans.add(extractFlightBean(resultSet));
			}

			if(!flightBeans.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_FLIGHT_BEANS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHT_BEANS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHT_BEANS, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return flightBeans;
	}

	@Override
	public List<FlightBean> getFlightBeansByStatus(FlightStatus flightStatus) throws DBException {
		List<FlightBean> flightBeans = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DBManager.getInstance().getConnection();

			statement = connection.prepareStatement(SQL_FIND_FLIGHT_BEANS_BY_STATUS);
			statement.setInt(1, flightStatus.ordinal());
			
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				flightBeans.add(extractFlightBean(resultSet));
			}

			if(!flightBeans.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_FLIGHT_BEANS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHT_BEANS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHT_BEANS, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return flightBeans;
	}

	@Override
	public List<FlightBean> getFlightBeansByUser(User user) throws DBException {
		List<FlightBean> flightBeans = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DBManager.getInstance().getConnection();

			statement = connection.prepareStatement(SQL_FIND_FLIGHT_BEANS_BY_USER);
			statement.setLong(1, user.getId());
			
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				flightBeans.add(extractFlightBean(resultSet));
			}

			if(!flightBeans.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_FLIGHT_BEANS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHT_BEANS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHT_BEANS, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return flightBeans;
	}

}
