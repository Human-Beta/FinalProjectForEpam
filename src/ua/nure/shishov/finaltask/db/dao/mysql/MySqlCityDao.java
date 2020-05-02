package ua.nure.shishov.finaltask.db.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.db.DBManager;
import ua.nure.shishov.finaltask.db.dao.CityDao;
import ua.nure.shishov.finaltask.db.entity.City;
import ua.nure.shishov.finaltask.exception.DBException;

public class MySqlCityDao extends CityDao {

	public static final Logger LOG = Logger.getLogger(MySqlCityDao.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_ALL_CITIES = "SELECT * FROM cities";

	/////////////////////////////////////////////////////////////

	@Override
	public List<City> getCities() throws DBException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<City> cities = new ArrayList<>();

		connection = DBManager.getInstance().getConnection();

		try {
			statement = connection.createStatement();

			resultSet = statement.executeQuery(SQL_FIND_ALL_CITIES);
			while (resultSet.next()) {
				cities.add(extractCity(resultSet));
			}
			
			if(!cities.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_CITIES);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CITIES, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CITIES, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}
		
		return cities;
	}

}
