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
import ua.nure.shishov.finaltask.db.CarState;
import ua.nure.shishov.finaltask.db.DBManager;
import ua.nure.shishov.finaltask.db.bean.CarBean;
import ua.nure.shishov.finaltask.db.dao.CarBeanDao;
import ua.nure.shishov.finaltask.exception.DBException;

public class MySqlCarBeanDao extends CarBeanDao {
	public static final Logger LOG = Logger.getLogger(MySqlCarBeanDao.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_CAR_BEANS = "SELECT c.id, m.name AS model_name, c.engine_power, c.num_of_seats, t.name AS engine_type, s.name AS state_name "
			+ "FROM cars AS c "
			+ "INNER JOIN car_models AS m ON m.id=c.model_id "
			+ "INNER JOIN car_engine_types AS t ON t.id=engine_type_id "
			+ "INNER JOIN car_states AS s ON s.id=c.state_id";

	private static final String SQL_FIND_CAR_BEANS_BY_STATE = "SELECT c.id, m.name AS model_name, c.engine_power, c.num_of_seats, t.name AS engine_type, s.name AS state_name "
			+ "FROM cars AS c " 
			+ "INNER JOIN car_models AS m ON m.id=c.model_id "
			+ "INNER JOIN car_engine_types AS t ON t.id=engine_type_id "
			+ "INNER JOIN car_states AS s ON s.id=c.state_id AND c.state_id=?";

	private static final String SQL_FIND_UNUSED_CAR_BEANS_BY_STATE = "SELECT c.id, m.name AS model_name, c.engine_power, c.num_of_seats, t.name AS engine_type, s.name AS state_name "
			+ "FROM cars AS c "
			+ "INNER JOIN car_models AS m ON m.id=c.model_id "
			+ "INNER JOIN car_engine_types AS t ON t.id=engine_type_id "
			+ "INNER JOIN car_states AS s ON s.id=c.state_id AND c.state_id=? "
			+ "WHERE c.id NOT IN (SELECT car_id FROM flights WHERE car_id IS NOT NULL AND status_id=1)";
	
	/////////////////////////////////////////////////////////////

	@Override
	public List<CarBean> getCarBeans() throws DBException {
		List<CarBean> carBeans = new ArrayList<>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		connection = DBManager.getInstance().getConnection();
		try {
			statement = connection.createStatement();

			resultSet = statement.executeQuery(SQL_FIND_CAR_BEANS);

			while (resultSet.next()) {
				carBeans.add(extractCarBean(resultSet));
			}
			
			if(!carBeans.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_CAR_BEANS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CAR_BEANS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_BEANS, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return carBeans;
	}

	@Override
	public List<CarBean> getCarBeansByState(CarState carState) throws DBException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<CarBean> carBeans = new ArrayList<>();

		connection = DBManager.getInstance().getConnection();
		try {
			statement = connection.prepareStatement(SQL_FIND_CAR_BEANS_BY_STATE);
			statement.setInt(1, carState.ordinal());
			
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				carBeans.add(extractCarBean(resultSet));
			}
			
			if(!carBeans.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_CAR_BEANS_BY_STATE);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CAR_BEANS_BY_STATE, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_BEANS_BY_STATE, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return carBeans;
	}
	
	@Override
	public List<CarBean> getUnusedCarBeansByState(CarState carState) throws DBException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<CarBean> carBeans = new ArrayList<>();

		connection = DBManager.getInstance().getConnection();
		try {
			statement = connection.prepareStatement(SQL_FIND_UNUSED_CAR_BEANS_BY_STATE);
			statement.setInt(1, carState.ordinal());
			
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				carBeans.add(extractCarBean(resultSet));
			}
			
			if(!carBeans.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_CAR_BEANS_BY_STATE);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CAR_BEANS_BY_STATE, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_BEANS_BY_STATE, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return carBeans;
	}

}
