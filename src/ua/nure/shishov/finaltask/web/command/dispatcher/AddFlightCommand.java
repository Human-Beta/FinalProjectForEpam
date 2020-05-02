package ua.nure.shishov.finaltask.web.command.dispatcher;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.cj.util.StringUtils;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.dao.FlightDao;
import ua.nure.shishov.finaltask.db.entity.Flight;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class AddFlightCommand extends Command {

	private static final long serialVersionUID = 6435363100060900526L;

	private static final Logger LOG = Logger.getLogger(AddFlightCommand.class);

	
	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");
		
		String fromCityIdStr = req.getParameter(Parameter.FROM_CITY_ID);
		String toCityIdStr = req.getParameter(Parameter.TO_CITY_ID);
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + Parameter.FROM_CITY + " --> " + fromCityIdStr);
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + Parameter.TO_CITY + " --> " + toCityIdStr);
		
		if(StringUtils.isNullOrEmpty(fromCityIdStr) || StringUtils.isNullOrEmpty(toCityIdStr)) {
			LOG.debug("Some of request parameters are empty.");
			throw new AppException("Some of request parameters are empty.");
		}
		
		int fromCity = Integer.parseInt(fromCityIdStr.trim());
		int toCity = Integer.parseInt(toCityIdStr.trim());
		
		Flight flight = new Flight();
		flight.setFromCityId(fromCity);
		flight.setToCityId(toCity);
		flight.setDateOfCreation(Timestamp.valueOf(LocalDateTime.now()));
		
		FlightDao flightDao = factory.getFlightDao();
		Flight insertedFlight = flightDao.insertFlight(flight);
		LOG.trace(Messages.TRACE_INSERT_FLIGHT + insertedFlight);
		

		LOG.debug("Command finished.");
		return new ActionPath(Action.REDIRECT, Path.COMMAND_FLIGHT_LIST);
	}

}
