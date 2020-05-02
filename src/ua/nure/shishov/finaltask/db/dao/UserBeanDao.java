package ua.nure.shishov.finaltask.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.nure.shishov.finaltask.db.Fields;
import ua.nure.shishov.finaltask.db.bean.UserBean;
import ua.nure.shishov.finaltask.exception.DBException;

public abstract class UserBeanDao {
	public abstract List<UserBean> getUserBeans() throws DBException;

	// //////////////////////////////////////////////////////////
	// Util methods
	// //////////////////////////////////////////////////////////

	/**
	 * Extracts a user bean entity from the result set.
	 * 
	 * @param rs Result set from which a user bean entity will be extracted.
	 * @return UserBEan entity
	 * @throws SQLException
	 */
	protected UserBean extractUserBean(ResultSet rs) throws SQLException {
		UserBean userBean = new UserBean();

		userBean.setId(rs.getLong(Fields.ENTITY_ID));
		userBean.setLogin(rs.getString(Fields.USER_BEAN_LOGIN));
		userBean.setFirstName(rs.getString(Fields.USER_BEAN_FIRST_NAME));
		userBean.setLastName(rs.getString(Fields.USER_BEAN_LAST_NAME));
		userBean.setRoleName(rs.getString(Fields.USER_BEAN_ROLE_NAME));
		
		return userBean;
	}
}
