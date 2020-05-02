package ua.nure.shishov.finaltask.web.command.driver;

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
import ua.nure.shishov.finaltask.db.CarState;
import ua.nure.shishov.finaltask.db.FlightSort;
import ua.nure.shishov.finaltask.db.bean.FlightBean;
import ua.nure.shishov.finaltask.db.dao.FlightBeanDao;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class UserFlightListCommand extends Command {

	private static final long serialVersionUID = -8666468384126230916L;
	private static final Logger LOG = Logger.getLogger(UserFlightListCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {

		LOG.debug("Command starts.");
		
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute(Parameter.LOGGED_USER);
		LOG.trace("User was gotten from session: user --> " + user);

		FlightBeanDao flightBeanDao = factory.getFlightBeanDao();
		List<FlightBean> openFlightBeans = flightBeanDao.getFlightBeansByUser(user);
		LOG.trace("Open flight beans were gotten from DB.");

//		sorting
		String sortName = req.getParameter(Parameter.SORT);
		if(sortName != null) {
			sortName = sortName.trim();
		}
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + Parameter.SORT + " --> " + sortName);

		FlightSort fligthSort = FlightSort.getSort(sortName);

		sortflightBeans(openFlightBeans, fligthSort);
		LOG.trace("Flight beans were sorted by " + fligthSort.getName());

		req.setAttribute(Parameter.FLIGHT_BEANS, openFlightBeans);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.FLIGHT_BEANS);

//		flight sorts
		req.setAttribute(Parameter.SORTS, FlightSort.values());
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.SORTS);
		
//		car states
		req.setAttribute(Parameter.CAR_STATES, CarState.values());
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CAR_STATES);

		LOG.debug("Command finished.");
		return new ActionPath(Action.FORWARD, Path.PAGE_USER_FLIGHT_LIST);
	}

	private void sortflightBeans(List<FlightBean> flightBeans, FlightSort flightSort) {

		switch (flightSort) {
		default:
		case DATE:
			Collections.<FlightBean>sort(flightBeans, Comparators.FLIGHT_BEAN_DATE);
			break;
		case ID:
			Collections.<FlightBean>sort(flightBeans, Comparators.FLIGHT_BEAN_ID);
			break;
		case STATUS:
			Collections.<FlightBean>sort(flightBeans, Comparators.FLIGHT_BEAN_STATUS);
			break;
		}
	}
}
