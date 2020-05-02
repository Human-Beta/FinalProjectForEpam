package ua.nure.shishov.finaltask.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.nure.shishov.finaltask.db.Fields;
import ua.nure.shishov.finaltask.db.entity.City;
import ua.nure.shishov.finaltask.exception.DBException;

public abstract class CityDao {
	
	public abstract List<City> getCities() throws DBException;

	// ////////////////////////////////////
	// Util methods
	///////////////////////////////////////

	/**
	 * Extracts a city entity from the result set.
	 * 
	 * @param rs Result set from which a city entity will be extracted.
	 * @return City entity
	 * @throws SQLException
	 */
	protected City extractCity(ResultSet rs) throws SQLException {
		City city = new City();
		city.setId(rs.getLong(Fields.ENTITY_ID));
		city.setName(rs.getString(Fields.CITY_NAME));
		return city;
	}
}
