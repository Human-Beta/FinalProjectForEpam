package ua.nure.shishov.finaltask.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.nure.shishov.finaltask.db.Fields;
import ua.nure.shishov.finaltask.db.bean.RequestBean;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.DBException;

public abstract class RequestBeanDao {

	public abstract List<RequestBean> getRequestBeans() throws DBException;
	public abstract List<RequestBean> getRequestBeansOfUser(User user) throws DBException;

	// //////////////////////////////////////////////////////////
	// Util methods
	// //////////////////////////////////////////////////////////

	/**
	 * Extracts a request bean entity from the result set.
	 * 
	 * @param rs Result set from which a request bean entity will be extracted.
	 * @return RequestBean entity
	 * @throws SQLException
	 */
	protected RequestBean extractRequestBean(ResultSet rs) throws SQLException {
		RequestBean requestBean = new RequestBean();

		requestBean.setId(rs.getLong(Fields.ENTITY_ID));
		requestBean.setUserId(rs.getLong(Fields.REQUEST_BEAN_USER_ID));
		requestBean.setUserLogin(rs.getString(Fields.REQUEST_BEAN_USER_LOGIN));
		requestBean.setFlightId(rs.getLong(Fields.REQUEST_BEAN_FLIGHT_ID));
		requestBean.setFlightDirection(rs.getString(Fields.REQUEST_BEAN_FLIGHT_DIRECTION));
		requestBean.setCarModelName(rs.getString(Fields.REQUEST_BEAN_CAR_MODEL_NAME));
		requestBean.setCarEnginePower(rs.getInt(Fields.REQUEST_BEAN_CAR_ENGINE_POWER));
		requestBean.setCarNumOfSeats(rs.getInt(Fields.REQUEST_BEAN_CAR_NUM_OF_SEATS));
		requestBean.setCarEngineTypeName(rs.getString(Fields.REQUEST_BEAN_CAR_ENGINE_TYPE));

		return requestBean;
	}

}
