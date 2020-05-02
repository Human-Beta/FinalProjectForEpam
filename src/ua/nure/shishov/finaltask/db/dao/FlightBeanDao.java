package ua.nure.shishov.finaltask.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.nure.shishov.finaltask.db.Fields;
import ua.nure.shishov.finaltask.db.FlightStatus;
import ua.nure.shishov.finaltask.db.bean.FlightBean;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.DBException;

public abstract class FlightBeanDao {
	
	public abstract List<FlightBean> getFlightBeans() throws DBException;
	
	public abstract List<FlightBean> getFlightBeansByStatus(FlightStatus flightStatus) throws DBException;
	
	public abstract List<FlightBean> getFlightBeansByUser(User user) throws DBException;

	// //////////////////////////////////////////////////////////
	// Util methods
	// //////////////////////////////////////////////////////////

	/**
	 * Extracts a flight bean entity from the result set.
	 * 
	 * @param rs Result set from which a flight bean entity will be extracted.
	 * @return FlightBean entity
	 * @throws SQLException
	 */
	protected FlightBean extractFlightBean(ResultSet rs) throws SQLException {
		FlightBean flightBean = new FlightBean();

		flightBean.setId(rs.getLong(Fields.ENTITY_ID));
		flightBean.setDirection(rs.getString(Fields.FLIGHT_BEAN_DIRECTION));
		
		flightBean.setDateOfCreation(rs.getTimestamp(Fields.FLIGHT_BEAN_DATE_OF_CREATION));
		flightBean.setStatusName(rs.getString(Fields.FLIGHT_BEAN_STATUS_NAME));
		
		flightBean.setUserId(rs.getLong(Fields.FLIGHT_BEAN_USER_ID));
		flightBean.setUserLoginName(rs.getString(Fields.FLIGHT_BEAN_USER_LOGIN_NAME));

		flightBean.setCarId(rs.getLong(Fields.FLIGHT_BEAN_CAR_ID));
		flightBean.setCarModelName(rs.getString(Fields.FLIGHT_BEAN_CAR_MODEL));
		
		return flightBean;
	}
	

}
