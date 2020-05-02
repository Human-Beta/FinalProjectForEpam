package ua.nure.shishov.finaltask.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.nure.shishov.finaltask.db.CarState;
import ua.nure.shishov.finaltask.db.Fields;
import ua.nure.shishov.finaltask.db.bean.CarBean;
import ua.nure.shishov.finaltask.exception.DBException;

public abstract class CarBeanDao {

	public abstract List<CarBean> getCarBeans() throws DBException;

	public abstract List<CarBean> getCarBeansByState(CarState carState) throws DBException;
	
	public abstract List<CarBean> getUnusedCarBeansByState(CarState carState) throws DBException;

	// ////////////////////////////////////
	// Util methods
	///////////////////////////////////////

	/**
	 * Extracts a car bean entity from the result set.
	 * 
	 * @param rs Result set from which a car bean entity will be extracted.
	 * @return CarBean entity
	 * @throws SQLException
	 */
	protected CarBean extractCarBean(ResultSet rs) throws SQLException {
		CarBean carBean = new CarBean();

		carBean.setId(rs.getLong(Fields.ENTITY_ID));
		carBean.setModelName(rs.getString(Fields.CAR_BEAN_MODEL_NAME));
		carBean.setEnginePower(rs.getInt(Fields.CAR_BEAN_ENGINE_POWER));
		carBean.setNumOfSeats(rs.getInt(Fields.CAR_BEAN_NUM_OF_SEATS));
		carBean.setEngineTypeName(rs.getString(Fields.CAR_BEAN_ENGINE_TYPE));
		carBean.setStateName(rs.getString(Fields.CAR_BEAN_STATE_NAME));

		return carBean;
	}

}
