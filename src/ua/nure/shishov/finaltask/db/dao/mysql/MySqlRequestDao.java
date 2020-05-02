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
import ua.nure.shishov.finaltask.db.dao.RequestDao;
import ua.nure.shishov.finaltask.db.entity.Request;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.DBException;


public class MySqlRequestDao extends RequestDao {

	private static final Logger LOG = Logger.getLogger(MySqlRequestDao.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_ALL_REQUESTS = "SELECT * FROM requests";
	private static final String SQL_FIND_REQUESTS_OF_USER = "SELECT * FROM requests WHERE user_id=?";
	private static final String SQL_INSERT_REQUEST = "INSERT INTO requests VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_REQUEST_BY_ID = "DELETE FROM requests WHERE id=?";
	private static final String SQL_DELETE_REQUEST_BY_FLIGHT_ID = "DELETE FROM requests WHERE flight_id=?";

	/////////////////////////////////////////////////////////////

	@Override
	public List<Request> getRequests() throws DBException {
		List<Request> requests = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DBManager.getInstance().getConnection();
			statement = connection.createStatement();

			resultSet = statement.executeQuery(SQL_FIND_ALL_REQUESTS);
			while (resultSet.next()) {
				requests.add(extractRequest(resultSet));
			}

			if (!requests.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_REQUESTS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_REQUESTS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_REQUESTS, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return requests;
	}

	@Override
	public List<Request> getRequestsOfUser(User user) throws DBException {
		List<Request> requests = new ArrayList<>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DBManager.getInstance().getConnection();
			statement = connection.prepareStatement(SQL_FIND_REQUESTS_OF_USER);

			statement.setLong(1, user.getId());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				requests.add(extractRequest(resultSet));
			}

			if (!requests.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_REQUESTS_OF_USER + user);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_REQUESTS_OF_USER + user, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_REQUESTS_OF_USER + user, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return requests;
	}

	@Override
	public Request insertRequest(Request request) throws DBException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		Request insertedRequest = null;
		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS);

			int k = 1;
			statement.setLong(k++, request.getUserId());
			statement.setLong(k++, request.getFlightId());
			statement.setLong(k++, request.getCarModelId());
			statement.setInt(k++, request.getCarEnginePower());
			statement.setInt(k++, request.getCarNumOfSeats());
			statement.setInt(k++, request.getCarEngineTypeId());

			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();

			if (resultSet.next()) {
				insertedRequest = new Request();
				insertedRequest.setId(resultSet.getLong(1));
				insertedRequest.setUserId(request.getUserId());
				insertedRequest.setFlightId(request.getFlightId());
				insertedRequest.setCarModelId(request.getCarModelId());
				insertedRequest.setCarEnginePower(request.getCarEnginePower());
				insertedRequest.setCarNumOfSeats(request.getCarNumOfSeats());
				insertedRequest.setCarEngineTypeId(request.getCarEngineTypeId());

				LOG.trace(Messages.TRACE_INSERT_REQUEST + insertedRequest);
			}

		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_INSERT_REQUEST + request, e);
			throw new DBException(Messages.ERR_CANNOT_INSERT_REQUEST + request, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return insertedRequest;
	}

	@Override
	public boolean deleteRequestById(long id) throws DBException {
		int result = 0;

		Connection connection = null;
		PreparedStatement statement = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_DELETE_REQUEST_BY_ID);

			statement.setLong(1, id);

			result = statement.executeUpdate();

			if (result == 1) {
				LOG.error(Messages.TRACE_DELETE_REQUEST_BY_ID + id);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_DELETE_REQUEST_BY_ID + id, e);
			throw new DBException(Messages.ERR_CANNOT_DELETE_REQUEST_BY_ID, e);
		}
		return result == 1;
	}

	@Override
	public boolean deleteRequestByFlightId(long flightId) throws DBException {
		int result = 0;

		Connection connection = null;
		PreparedStatement statement = null;

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.prepareStatement(SQL_DELETE_REQUEST_BY_FLIGHT_ID);

			statement.setLong(1, flightId);

			result = statement.executeUpdate();

			if (result == 1) {
				LOG.error(Messages.TRACE_DELETE_REQUEST_BY_FLIGHT_ID + flightId);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_DELETE_REQUEST_BY_FLIGHT_ID + flightId, e);
			throw new DBException(Messages.ERR_CANNOT_DELETE_REQUEST_BY_FLIGHT_ID, e);
		}
		return result == 1;
	}

}
