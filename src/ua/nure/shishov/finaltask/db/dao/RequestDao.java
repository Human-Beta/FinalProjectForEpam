package ua.nure.shishov.finaltask.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.nure.shishov.finaltask.db.Fields;
import ua.nure.shishov.finaltask.db.entity.Request;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.DBException;

public abstract class RequestDao {
	public abstract List<Request> getRequests() throws DBException;

	public abstract List<Request> getRequestsOfUser(User user) throws DBException;

	public abstract Request insertRequest(Request request) throws DBException;

	public abstract boolean deleteRequestById(long requestId) throws DBException;
	
	public abstract boolean deleteRequestByFlightId(long flightId) throws DBException;
	
	// //////////////////////////////////////////////////////////
	// Util methods
	// //////////////////////////////////////////////////////////

	/**
	 * Extracts a request entity from the result set.
	 * 
	 * @param rs Result set from which a request entity will be extracted.
	 * @return Request entity
	 * @throws SQLException
	 */
	protected Request extractRequest(ResultSet rs) throws SQLException {
		Request request = new Request();

		request.setId(rs.getLong(Fields.ENTITY_ID));
		request.setUserId(rs.getLong(Fields.REQUEST_USER_ID));
		request.setFlightId(rs.getLong(Fields.REQUEST_FLIGHT_ID));
		request.setCarModelId(rs.getLong(Fields.REQUEST_CAR_MODEL_ID));
		request.setCarEnginePower(rs.getInt(Fields.REQUEST_CAR_ENGINE_POWER));
		request.setCarNumOfSeats(rs.getInt(Fields.REQUEST_CAR_NUM_OF_SEATS));
		request.setCarEngineTypeId(rs.getInt(Fields.REQUEST_CAR_ENGINE_TYPE_ID));

		return request;
	}


}
