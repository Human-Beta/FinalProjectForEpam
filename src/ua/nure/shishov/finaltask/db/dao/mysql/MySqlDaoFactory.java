package ua.nure.shishov.finaltask.db.dao.mysql;

import ua.nure.shishov.finaltask.db.dao.CarBeanDao;
import ua.nure.shishov.finaltask.db.dao.CarDao;
import ua.nure.shishov.finaltask.db.dao.CarModelDao;
import ua.nure.shishov.finaltask.db.dao.CityDao;
import ua.nure.shishov.finaltask.db.dao.DaoFactory;
import ua.nure.shishov.finaltask.db.dao.FlightBeanDao;
import ua.nure.shishov.finaltask.db.dao.FlightDao;
import ua.nure.shishov.finaltask.db.dao.RequestBeanDao;
import ua.nure.shishov.finaltask.db.dao.RequestDao;
import ua.nure.shishov.finaltask.db.dao.UserBeanDao;
import ua.nure.shishov.finaltask.db.dao.UserDao;

public class MySqlDaoFactory implements DaoFactory {

	@Override
	public UserDao getUserDao() {
		return new MySqlUserDao();
	}

	@Override
	public RequestDao getRequestDao() {
		return new MySqlRequestDao();
	}

	@Override
	public RequestBeanDao getRequestBeanDao() {
		return new MySqlRequestBeanDao();
	}

	@Override
	public FlightDao getFlightDao() {
		return new MySqlFlightDao();
	}

	@Override
	public FlightBeanDao getFlightBeanDao() {
		return new MySqlFlightBeanDao();
	}

	@Override
	public CarModelDao getCarModelDao() {
		return new MySqlCarModelDao();
	}

	@Override
	public CarBeanDao getCarBeanDao() {
		return new MySqlCarBeanDao();
	}

	@Override
	public CityDao getCityDao() {
		return new MySqlCityDao();
	}

	@Override
	public CarDao getCarDao() {
		return new MySqlCarDao();
	}

	@Override
	public UserBeanDao getUserBeanDao() {
		return new MySqlUserBeanDao();
	}

}
