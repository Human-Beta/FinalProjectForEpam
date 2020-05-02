package ua.nure.shishov.finaltask.web.command.driver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mysql.cj.util.StringUtils;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.FlightStatus;
import ua.nure.shishov.finaltask.db.dao.CarDao;
import ua.nure.shishov.finaltask.db.dao.FlightDao;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class FinishFlightCommand extends Command {

	private static final long serialVersionUID = -6965699139895452564L;

	private static final Logger LOG = Logger.getLogger(FinishFlightCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		String flightIdStr = req.getParameter(Parameter.FLIGHT_ID);
		String carStateIdStr = req.getParameter(Parameter.CAR_STATE_ID);
		String carIdStr = req.getParameter(Parameter.CAR_ID);

		StringBuilder message = new StringBuilder()
				.append(Parameter.FLIGHT_ID).append(" --> ").append(flightIdStr).append(",")
				.append(Parameter.CAR_STATE_ID).append(" --> ").append(carStateIdStr).append(Parameter.CAR_ID).append(",")
				.append(" --> ").append(carIdStr);
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + message);

		if (StringUtils.isNullOrEmpty(flightIdStr) || StringUtils.isNullOrEmpty(carStateIdStr)
				|| StringUtils.isNullOrEmpty(carIdStr)) {
			LOG.error(Messages.ERR_PARAMETERS_EMPTY + message);
			throw new AppException(Messages.ERR_PARAMETERS_EMPTY + message);
		}

//		flight
		long flightId = Long.parseLong(flightIdStr.trim());

		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute(Parameter.LOGGED_USER);

		FlightDao flightDao = factory.getFlightDao();
		boolean isUpdate = flightDao.updateFlightStatusByIdAndUser(flightId, user.getId(), FlightStatus.CLOSED.ordinal());
		if (isUpdate) {
			LOG.trace(Messages.TRACE_UPDATE_FLIGHT_BY_ID + flightId);
		}

//		car
		int carStateId = Integer.parseInt(carStateIdStr.trim());
		long carId = Long.parseLong(carIdStr.trim());
		
		CarDao carDao = factory.getCarDao();
		isUpdate = carDao.updateCarStateById(carId, carStateId);
		if (isUpdate) {
			LOG.trace(Messages.TRACE_UPDATE_CAR_STATE_BY_ID + carId);
		}
		LOG.debug("Command finished.");
		return new ActionPath(Action.REDIRECT, Path.COMMAND_USER_FLIGHT_LIST);
	}

}
