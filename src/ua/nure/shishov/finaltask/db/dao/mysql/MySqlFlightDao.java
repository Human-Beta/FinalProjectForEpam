package ua.nure.shishov.finaltask.db.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.db.DBManager;
import ua.nure.shishov.finaltask.db.dao.FlightDao;
import ua.nure.shishov.finaltask.db.entity.Flight;
import ua.nure.shishov.finaltask.exception.DBException;

public class MySqlFlightDao extends FlightDao {

	private static final Logger LOG = Logger.getLogger(MySqlFlightDao.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_ALL_FLIGHTS = "SELECT * FROM flights";
	private static final String SQL_FIND_FLIGHT_BY_ID = "SELECT * FROM flights WHERE id=?";
	private static final String SQL_UPDATE_FLIGHT = "UPDATE flights SET status_id=?, user_id=?, car_id=? WHERE id=?";
	private static final String SQL_UPDATE_FLIGHT_STATUS_OF_USER_BY_ID = "UPDATE flights SET status_id=? WHERE id=? AND user_id=?";
	private static final String SQL_UPDATE_FLIGHT_USER_AND_CAR_AND_STATUS = "UPDATE flights SET user_id=?, car_id=?, status_id=? WHERE id=?";
	private static final String SQL_INSERT_FLIGHT = "INSERT INTO flights VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)";

	/////////////////////////////////////////////////////////////

	@Override
	public List<Flight> getFlights() throws DBException {
		List<Flight> flights = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DBManager.getInstance().getConnection();

			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_FIND_ALL_FLIGHTS);
			while (resultSet.next()) {
				flights.add(extractFlight(resultSet));
			}

			if (!flights.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_FLIGHTS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return flights;
	}

	@Override
	public Flight getFlightById(long id) throws DBException {
		Flight flight = null;

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DBManager.getInstance().getConnection();

			statement = connection.prepareStatement(SQL_FIND_FLIGHT_BY_ID);
			statement.setLong(1, id);

			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				flight = extractFlight(resultSet);
				LOG.trace(Messages.TRACE_OBTAIN_FLIGHT_BY_ID + id);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_FLIGHT_BY_ID + id, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHT_BY_ID + id, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return flight;
	}

	@Override
	public boolean updateFlight(Flight flight) throws DBException {
		if (flight == null) {
			throw new DBException(Messages.ERR_CANNOT_UPDATE_FLIGHT + flight);
		}

		int result = 0;

		Connection connection = null;
		PreparedStatement statement = null;

		connection = DBManager.getInstance().getConnection();
		try {
			statement = connection.prepareStatement(SQL_UPDATE_FLIGHT);

			int k = 1;
			statement.setLong(k++, flight.getStatusId());
			statement.setLong(k++, flight.getUserId());
			statement.setLong(k++, flight.getCarId());
			statement.setLong(k++, flight.getId());
			statement.setLong(k++, flight.getUserId());
			statement.setLong(k++, flight.getId());

			result = statement.executeUpdate();
			if (result == 1) {
				LOG.trace(Messages.TRACE_UPDATE_FLIGHT + flight);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_FLIGHT + flight, e);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_FLIGHT + flight, e);
		} finally {
			DBManager.getInstance().close(statement);
			DBManager.getInstance().close(connection);
		}

		return result == 1;
	}

	@Override
	public boolean updateFlightUserAndCarAndStatusById(long flightId, long userId, long carId, int statusId) throws DBException {
		int result = 0;

		Connection connection = null;
		PreparedStatement statement = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_FLIGHT_USER_AND_CAR_AND_STATUS);

			int k = 1;
			
			if (userId == -1) {
				statement.setNull(k++, Types.INTEGER);
			} else {
				statement.setLong(k++, userId);
			}
			
			if (userId == -1) {
				statement.setNull(k++, Types.INTEGER);
			} else {
				statement.setLong(k++, carId);
			}
			statement.setInt(k++, statusId);
			statement.setLong(k++, flightId);

			result = statement.executeUpdate();
			if (result == 1) {
				LOG.trace(Messages.TRACE_UPDATE_FLIGHT_BY_ID + flightId);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_FLIGHT_BY_ID + flightId, e);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_FLIGHT_BY_ID + flightId, e);
		} finally {
			DBManager.getInstance().close(statement);
			DBManager.getInstance().close(connection);
		}

		return result == 1;
	}

	@Override
	public boolean updateFlightStatusByIdAndUser(long flightId, long userId, int statusId) throws DBException {
		int result = 0;

		Connection connection = null;
		PreparedStatement statement = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_UPDATE_FLIGHT_STATUS_OF_USER_BY_ID);
			int k = 1;
			statement.setInt(k++, statusId);
			statement.setLong(k++, flightId);
			statement.setLong(k++, userId);

			result = statement.executeUpdate();
			if (result == 1) {
				LOG.trace(Messages.TRACE_UPDATE_FLIGHT_BY_ID + flightId);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_FLIGHT_BY_ID + flightId, e);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_FLIGHT_BY_ID + flightId, e);
		} finally {
			DBManager.getInstance().close(statement);
			DBManager.getInstance().close(connection);
		}
		return result == 1;
	}

	@Override
	public Flight insertFlight(Flight flight) throws DBException {
		if (flight == null) {
			throw new DBException(Messages.ERR_CANNOT_INSERT_FLIGHT + flight);
		}

		Flight flightToReturn = copyFlight(flight);

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_INSERT_FLIGHT, Statement.RETURN_GENERATED_KEYS);

			int k = 1;
			statement.setLong(k++, flight.getFromCityId());
			statement.setLong(k++, flight.getToCityId());
			statement.setTimestamp(k++, flight.getDateOfCreation());
			statement.setInt(k++, flight.getStatusId());

			if (flight.getUserId() == 0) {
				statement.setObject(k++, null);
			} else {
				statement.setLong(k++, flight.getUserId());
			}

			if (flight.getUserId() == 0) {
				statement.setObject(k++, null);
			} else {
				statement.setLong(k++, flight.getCarId());
			}

			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				flightToReturn.setId(resultSet.getLong(1));
				LOG.trace(Messages.TRACE_INSERT_FLIGHT + flight);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_INSERT_FLIGHT + flight, e);
			throw new DBException(Messages.ERR_CANNOT_INSERT_FLIGHT + flight, e);
		} finally {
			DBManager.getInstance().close(resultSet);
			DBManager.getInstance().close(statement);
			DBManager.getInstance().close(connection);
		}

		return flightToReturn;

	}

}
