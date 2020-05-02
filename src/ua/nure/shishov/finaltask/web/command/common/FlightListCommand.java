package ua.nure.shishov.finaltask.web.command.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Comparators;
import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.CarEngineType;
import ua.nure.shishov.finaltask.db.FlightSort;
import ua.nure.shishov.finaltask.db.FlightStatus;
import ua.nure.shishov.finaltask.db.UserRole;
import ua.nure.shishov.finaltask.db.bean.FlightBean;
import ua.nure.shishov.finaltask.db.dao.CarModelDao;
import ua.nure.shishov.finaltask.db.dao.CityDao;
import ua.nure.shishov.finaltask.db.dao.FlightBeanDao;
import ua.nure.shishov.finaltask.db.dao.RequestDao;
import ua.nure.shishov.finaltask.db.entity.CarModel;
import ua.nure.shishov.finaltask.db.entity.City;
import ua.nure.shishov.finaltask.db.entity.Request;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class FlightListCommand extends Command {

	private static final long serialVersionUID = -3276114701129967167L;

	private static final Logger LOG = Logger.getLogger(FlightListCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		FlightBeanDao flightBeanDao = factory.getFlightBeanDao();

		List<FlightBean> flightBeans = null;

		HttpSession session = req.getSession(false);

		UserRole userRole = (UserRole) session.getAttribute(Parameter.USER_ROLE);
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + Parameter.USER_ROLE + " --> " + userRole);

//		for sorting
		String sortName = req.getParameter(Parameter.SORT);
		if (sortName != null) {
			sortName = sortName.trim();
		}
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + Parameter.SORT + " --> " + sortName);

		FlightSort selectedSort = FlightSort.DATE;

		if (userRole == UserRole.DRIVER) {
			flightBeans = flightBeanDao.getFlightBeansByStatus(FlightStatus.OPEN);
			LOG.trace("Flight beans were gotten from DB.");

//			put car models into HttpRequest scope
			CarModelDao carModelDao = factory.getCarModelDao();
			List<CarModel> carModels = carModelDao.getCarModels();
			LOG.trace("Car models were gotten from DB");

			req.setAttribute(Parameter.CAR_MODELS, carModels);
			LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CAR_MODELS);

//			put car engine types into HttpRequest scope
			req.setAttribute(Parameter.CAR_ENGINE_TYPES, CarEngineType.values());
			LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CAR_ENGINE_TYPES);

//			put user requests into HttpRequest scope
			User user = (User) session.getAttribute(Parameter.LOGGED_USER);
			LOG.trace("User was gotten from session: user --> " + user);

			RequestDao requestDao = factory.getRequestDao();
			List<Request> userRequests = requestDao.getRequestsOfUser(user);

			req.setAttribute(Parameter.REQUEST_BEANS_USER, userRequests);
			LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.REQUEST_BEANS_USER);

//			set flight sorts
			FlightSort[] driverFlightSorts = Arrays.stream(FlightSort.values()).filter(f -> f != FlightSort.STATUS)
					.toArray(FlightSort[]::new);

			req.setAttribute(Parameter.SORTS, driverFlightSorts);
			LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.SORTS);

			if (!FlightSort.STATUS.getName().equalsIgnoreCase(sortName)) {
				selectedSort = FlightSort.getSort(sortName);
			}
		} else if (userRole == UserRole.ADMIN || userRole == UserRole.DISPATCHER) {
			flightBeans = flightBeanDao.getFlightBeans();
			LOG.trace("Flight beans were gotten from DB.");

//			put cities into HttpRequest scope
			CityDao cityDao = factory.getCityDao();
			List<City> cities = cityDao.getCities();
			LOG.trace("Cities were gotten from DB");

			req.setAttribute(Parameter.CITIES, cities);
			LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CITIES);

//			set flight sorts
			req.setAttribute(Parameter.SORTS, FlightSort.values());
			LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.SORTS);

			selectedSort = FlightSort.getSort(sortName);
		}

//		sorting
		sortflightBeans(flightBeans, selectedSort);
		LOG.trace("Flight beans were sorted by " + selectedSort.getName());

		req.setAttribute(Parameter.FLIGHT_BEANS, flightBeans);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.FLIGHT_BEANS);

//		set selected sort
		req.setAttribute(Parameter.SELECTED_SORT, selectedSort);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.SELECTED_SORT + " --> " + selectedSort);

		LOG.debug("Command finished.");
		return new ActionPath(Action.FORWARD, Path.PAGE_FLIGHT_LIST);
	}

	private void sortflightBeans(List<FlightBean> flightBeans, FlightSort flightSort) {
		switch (flightSort) {
		default:
		case DATE:
			Collections.<FlightBean>sort(flightBeans, Comparators.FLIGHT_BEAN_DATE);
			LOG.trace("Flight beans were sorted by date.");
			break;
		case ID:
			Collections.<FlightBean>sort(flightBeans, Comparators.FLIGHT_BEAN_ID);
			LOG.trace("Flight beans were sorted by id.");
			break;
		case STATUS:
			Collections.<FlightBean>sort(flightBeans, Comparators.FLIGHT_BEAN_STATUS);
			LOG.trace("Flight beans were sorted by status.");
			break;
		}
	}

}
