package ua.nure.shishov.finaltask.web.command.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.cj.util.StringUtils;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.FlightStatus;
import ua.nure.shishov.finaltask.db.dao.FlightDao;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class AssignCarAndUserForFlightCommand extends Command {

	private static final long serialVersionUID = -3693060931136752636L;

	private static final Logger LOG = Logger.getLogger(AssignCarAndUserForFlightCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		String carIdStr = req.getParameter(Parameter.CAR_ID);
		String flightIdStr = req.getParameter(Parameter.FLIGHT_ID);
		String userIdStr = req.getParameter(Parameter.USER_ID);

		StringBuilder message = new StringBuilder()
				.append(Parameter.CAR_ID).append(" --> ").append(carIdStr)
				.append(Parameter.FLIGHT_ID).append(" --> ").append(flightIdStr)
				.append(Parameter.USER_ID).append(" --> ").append(userIdStr);

		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + message);

		if (StringUtils.isNullOrEmpty(carIdStr) || StringUtils.isNullOrEmpty(flightIdStr)
				|| StringUtils.isNullOrEmpty(userIdStr)) {
			LOG.debug(Messages.ERR_PARAMETERS_EMPTY + message);
			throw new AppException(Messages.ERR_PARAMETERS_EMPTY + message);
		}

		long userId = Long.parseLong(userIdStr.trim());
		long carId = Long.parseLong(carIdStr.trim());
		long flightId = Long.parseLong(flightIdStr.trim());

		FlightDao flightDao = factory.getFlightDao();
		boolean isFlightUpdate = flightDao.updateFlightUserAndCarAndStatusById(flightId, userId, carId, FlightStatus.IN_PROGRESS.ordinal());
		if (isFlightUpdate) {
			LOG.trace(Messages.TRACE_UPDATE_FLIGHT_BY_ID + flightId);
		}

		LOG.debug("Command finished.");
		return new ActionPath(Action.REDIRECT, Path.COMMAND_ALL_REQUEST_LIST);
	}

}
