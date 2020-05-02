package ua.nure.shishov.finaltask.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.nure.shishov.finaltask.db.Fields;
import ua.nure.shishov.finaltask.db.entity.CarModel;
import ua.nure.shishov.finaltask.exception.DBException;

public abstract class CarModelDao {
	public abstract List<CarModel> getCarModels() throws DBException;

	public abstract CarModel getCarModelByName(String name) throws DBException;

	// ////////////////////////////////////
	// Util methods
	///////////////////////////////////////

	/**
	 * Extracts a car model entity from the result set.
	 * 
	 * @param rs Result set from which a car`s model entity will be extracted.
	 * @return CarModel entity
	 * @throws SQLException
	 */
	protected CarModel extractCarModel(ResultSet rs) throws SQLException {
		CarModel carModel = new CarModel();

		carModel.setId(rs.getLong(Fields.ENTITY_ID));
		carModel.setName(rs.getString(Fields.CAR_MODEL_NAME));

		return carModel;
	}
}
