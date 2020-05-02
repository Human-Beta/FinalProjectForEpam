package ua.nure.shishov.finaltask.db.dao;

import ua.nure.shishov.finaltask.db.dao.mysql.MySqlDaoFactory;

public interface DaoFactory {

	enum Factories {
		MYSQL
	}

	UserDao getUserDao();

	RequestDao getRequestDao();

	RequestBeanDao getRequestBeanDao();

	FlightDao getFlightDao();

	FlightBeanDao getFlightBeanDao();

	CarDao getCarDao();

	CarBeanDao getCarBeanDao();

	CarModelDao getCarModelDao();

	CityDao getCityDao();
	
	UserBeanDao getUserBeanDao();

	static DaoFactory getDaoFactory(Factories factoryName) {
		switch (factoryName) {
		case MYSQL:
			return new MySqlDaoFactory();
		default:
			return null;
		}
	}

}
