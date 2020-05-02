package ua.nure.shishov.finaltask.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.nure.shishov.finaltask.db.Fields;
import ua.nure.shishov.finaltask.db.entity.Car;
import ua.nure.shishov.finaltask.exception.DBException;

public abstract class CarDao {

	public abstract Car insertCar(Car car) throws DBException;

	public abstract List<Car> getCars() throws DBException;

	public abstract boolean updateCar(Car car) throws DBException;

	public abstract boolean updateCarStateById(long carId, int carStateId) throws DBException;

	public abstract boolean deleteCarById(long carId) throws DBException;

	// ////////////////////////////////////
	// Util methods
	///////////////////////////////////////

	/**
	 * Extracts a car entity from the result set.
	 * 
	 * @param rs Result set from which a car entity will be extracted.
	 * @return Car entity
	 * @throws SQLException
	 */
	protected Car extractCar(ResultSet rs) throws SQLException {
		Car car = new Car();

		car.setId(rs.getLong(Fields.ENTITY_ID));
		car.setModelId(rs.getInt(Fields.CAR_MODEL_ID));
		car.setEnginePower(rs.getInt(Fields.CAR_ENGINE_POWER));
		car.setNumOfSeats(rs.getInt(Fields.CAR_NUM_OF_SEATS));
		car.setEngineTypeId(rs.getInt(Fields.CAR_ENGINE_TYPE_ID));
		car.setStateId(rs.getInt(Fields.CAR_STATE_ID));

		return car;
	}

	/**
	 * Copy a car entity.
	 * 
	 * @param car The original object to be copied.
	 * @return Copy of {@link Car} object.
	 */
	protected Car copyCar(Car car) {
		Car copy = new Car();

		copy.setId(car.getId());
		copy.setModelId(car.getModelId());
		copy.setEnginePower(car.getEnginePower());
		copy.setNumOfSeats(car.getNumOfSeats());
		copy.setEngineTypeId(car.getEngineTypeId());
		copy.setStateId(car.getStateId());

		return copy;
	}

}
