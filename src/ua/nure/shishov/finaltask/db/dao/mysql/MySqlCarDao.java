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
import ua.nure.shishov.finaltask.db.dao.CarDao;
import ua.nure.shishov.finaltask.db.entity.Car;
import ua.nure.shishov.finaltask.exception.DBException;

public class MySqlCarDao extends CarDao {

	public static final Logger LOG = Logger.getLogger(MySqlCarDao.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_CARS = "SELECT * FROM cars";

	private static final String SQL_INSERT_CAR = "INSERT INTO cars VALUES(DEFAULT, ?, ?, ?, ?, ?)";

	private static final String SQL_DELETE_CAR_BY_ID = "DELETE FROM cars WHERE id=?";

	private static final String SQL_UPDATE_CAR = "UPDATE cars SET model_id=?, engine_power=?, num_of_seats=?, engine_type_id=?, state=? WHERE id=?";

	private static final String SQL_UPDATE_CAR_STATE_BY_ID = "UPDATE cars SET state_id=? WHERE id=?";

	// //////////////////////////////////////////////////////////

	@Override
	public List<Car> getCars() throws DBException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Car> cars = new ArrayList<>();

		connection = DBManager.getInstance().getConnection();
		try {
			statement = connection.createStatement();

			resultSet = statement.executeQuery(SQL_FIND_CARS);

			while (resultSet.next()) {
				cars.add(extractCar(resultSet));
			}

			if (!cars.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_CAR_BEANS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CAR_BEANS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_BEANS, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return cars;
	}

	@Override
	public Car insertCar(Car car) throws DBException {
		if (car == null) {
			return null;
		}

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		Car carToReturn = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_INSERT_CAR, Statement.RETURN_GENERATED_KEYS);

			int k = 1;
			statement.setInt(k++, car.getModelId());
			statement.setInt(k++, car.getEnginePower());
			statement.setInt(k++, car.getNumOfSeats());
			statement.setInt(k++, car.getEngineTypeId());
			statement.setInt(k++, car.getStateId());

			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();

			if (resultSet.next()) {
				carToReturn = copyCar(car);
				carToReturn.setId(resultSet.getLong(1));
				LOG.trace(Messages.TRACE_INSERT_CAR + carToReturn);
			}

		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_INSERT_CAR + car, e);
			throw new DBException(Messages.ERR_CANNOT_INSERT_CAR + car, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return carToReturn;
	}

	@Override
	public boolean updateCar(Car car) throws DBException {
		if (car == null) {
			return false;
		}

		int result = 0;

		Connection connection = null;
		PreparedStatement statement = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_CAR);

			int k = 1;
			statement.setInt(k++, car.getModelId());
			statement.setInt(k++, car.getEnginePower());
			statement.setInt(k++, car.getNumOfSeats());
			statement.setInt(k++, car.getEngineTypeId());
			statement.setInt(k++, car.getStateId());
			statement.setLong(k++, car.getId());

			result = statement.executeUpdate();

			if (result == 1) {
				LOG.error(Messages.TRACE_UPDATE_CAR + car);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_CAR + car, e);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_CAR + car, e);
		} finally {
			DBManager.getInstance().close(statement);
			DBManager.getInstance().close(connection);
		}

		return result == 1;
	}

	@Override
	public boolean updateCarStateById(long carId, int carStateId) throws DBException {
		int result = 0;

		Connection connection = null;
		PreparedStatement statement = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_CAR_STATE_BY_ID);
			int k = 1;
			statement.setInt(k++, carStateId);
			statement.setLong(k++, carId);

			result = statement.executeUpdate();
			
			if (result == 1) {
				LOG.trace(Messages.TRACE_UPDATE_CAR_STATE_BY_ID + carId);
			}

		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_CAR_STATE_BY_ID + carId, e);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_CAR_STATE_BY_ID + carId, e);
		} finally {
			DBManager.getInstance().close(statement);
			DBManager.getInstance().close(connection);
		}
		return result == 1;
	}

	@Override
	public boolean deleteCarById(long carId) throws DBException {
		int result = 0;

		Connection connection = null;
		PreparedStatement statement = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_DELETE_CAR_BY_ID);
			statement.setLong(1, carId);

			result = statement.executeUpdate();

			if (result == 1) {
				LOG.trace(Messages.TRACE_DELETE_CAR_BY_ID);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_DELETE_CAR_BY_ID + carId, e);
			throw new DBException(Messages.ERR_CANNOT_DELETE_CAR_BY_ID + carId, e);
		} finally {
			DBManager.getInstance().close(statement);
			DBManager.getInstance().close(connection);
		}

		return result == 1;
	}

}
