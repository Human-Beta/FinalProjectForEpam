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
import ua.nure.shishov.finaltask.db.dao.CarModelDao;
import ua.nure.shishov.finaltask.db.entity.CarModel;
import ua.nure.shishov.finaltask.exception.DBException;

public class MySqlCarModelDao extends CarModelDao {

	public static final Logger LOG = Logger.getLogger(MySqlCarModelDao.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_ALL_CAR_MODELS = "SELECT * FROM car_models";
	private static final String SQL_FIND_CAR_MODEL_BY_NAME = "SELECT * FROM car_models WHERE name=?";

	/////////////////////////////////////////////////////////////

	@Override
	public List<CarModel> getCarModels() throws DBException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<CarModel> carModels = new ArrayList<>();

		connection = DBManager.getInstance().getConnection();
		try {
			statement = connection.createStatement();

			resultSet = statement.executeQuery(SQL_FIND_ALL_CAR_MODELS);

			while (resultSet.next()) {
				carModels.add(extractCarModel(resultSet));
			}
			
			if(!carModels.isEmpty()) {
				LOG.trace(Messages.TRACE_OBTAIN_CAR_MODELS);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CAR_MODELS, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_MODELS, e);
		} finally {
			DBManager.getInstance().close(connection, statement, resultSet);
		}

		return carModels;
	}

	@Override
	public CarModel getCarModelByName(String name) throws DBException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		CarModel carModel = null;

		connection = DBManager.getInstance().getConnection();
		try {
			statement = connection.prepareStatement(SQL_FIND_CAR_MODEL_BY_NAME);
			statement.setString(1, name);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				carModel = extractCarModel(resultSet);
				LOG.error(Messages.TRACE_OBTAIN_CAR_MODEL_BY_NAME + name);
			}
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CAR_MODEL_BY_NAME + name, e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_MODEL_BY_NAME + name, e);
		}

		return carModel;
	}

}
