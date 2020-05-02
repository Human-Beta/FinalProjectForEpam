package ua.nure.shishov.finaltask.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.nure.shishov.finaltask.db.Fields;
import ua.nure.shishov.finaltask.db.entity.Flight;
import ua.nure.shishov.finaltask.exception.DBException;

public abstract class FlightDao {

	public abstract List<Flight> getFlights() throws DBException;

	public abstract Flight getFlightById(long id) throws DBException;

	public abstract boolean updateFlight(Flight flight) throws DBException;

	public abstract boolean updateFlightStatusByIdAndUser(long flightId, long userId, int statusId) throws DBException;

	public abstract boolean updateFlightUserAndCarAndStatusById(long flightId, long userId, long carId, int statusId) throws DBException;

	public abstract Flight insertFlight(Flight flight) throws DBException;

	// //////////////////////////////////////////////////////////
	// Util methods
	// //////////////////////////////////////////////////////////

	/**
	 * Extracts a flight entity from the result set.
	 * 
	 * @param rs Result set from which a flight entity will be extracted.
	 * @return Flight entity
	 * @throws SQLException
	 */
	public Flight extractFlight(ResultSet rs) throws SQLException {
		Flight flight = new Flight();

		flight.setId(rs.getLong(Fields.ENTITY_ID));
		flight.setFromCityId(rs.getInt(Fields.FLIGHT_FROM_CITY_ID));
		flight.setToCityId(rs.getInt(Fields.FLIGHT_TO_CITY_ID));
		flight.setDateOfCreation(rs.getTimestamp(Fields.FLIGHT_DATE_OF_CREATION));
		flight.setStatusId(rs.getInt(Fields.FLIGHT_STATUS_ID));

		return flight;
	}

	/**
	 * Copy a flight entity.
	 * 
	 * @param flight The original object to be copied.
	 * @return Copy of {@link Flight} object.
	 */
	protected Flight copyFlight(Flight flight) {
		Flight copy = new Flight();

		copy.setId(flight.getId());
		copy.setFromCityId(flight.getFromCityId());
		copy.setToCityId(flight.getToCityId());
		copy.setDateOfCreation(flight.getDateOfCreation());
		copy.setStatusId(flight.getStatusId());
		copy.setCarId(flight.getCarId());
		copy.setUserId(flight.getUserId());

		return copy;
	}

}
