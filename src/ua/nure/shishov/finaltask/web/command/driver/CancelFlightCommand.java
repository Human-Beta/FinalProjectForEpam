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
import ua.nure.shishov.finaltask.db.dao.FlightDao;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class CancelFlightCommand extends Command {

	private static final long serialVersionUID = 4829867877231761481L;
	
	private static final Logger LOG = Logger.getLogger(CancelFlightCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");
		
		String flightIdStr = req.getParameter(Parameter.FLIGHT_ID);
		StringBuilder message = new StringBuilder()
				.append(Parameter.FLIGHT_ID).append(" --> ").append(flightIdStr);

		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + message);
		
		if(StringUtils.isNullOrEmpty(flightIdStr)) {
			LOG.error(Messages.ERR_PARAMETERS_EMPTY + message);
			throw new AppException(Messages.ERR_PARAMETERS_EMPTY + message);
		}
		
		long flightId = Long.parseLong(flightIdStr);
		
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute(Parameter.LOGGED_USER);
		LOG.trace(Messages.TRACE_GET_SESSION_ATTRIBUTE + Parameter.LOGGED_USER + user);
		
		FlightDao flightDao = factory.getFlightDao();
		boolean isFlightUpdate = flightDao.updateFlightStatusByIdAndUser(flightId, user.getId(), FlightStatus.CANCELED.ordinal());
		if (isFlightUpdate) {
			LOG.trace(Messages.TRACE_UPDATE_FLIGHT_BY_ID + flightId);
		}
		
		LOG.debug("Command finished.");
		return new ActionPath(Action.REDIRECT, Path.COMMAND_USER_FLIGHT_LIST);
	}

}
