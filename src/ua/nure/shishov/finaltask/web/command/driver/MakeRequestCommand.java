package ua.nure.shishov.finaltask.web.command.driver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mysql.cj.util.StringUtils;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.dao.CarModelDao;
import ua.nure.shishov.finaltask.db.dao.RequestDao;
import ua.nure.shishov.finaltask.db.entity.CarModel;
import ua.nure.shishov.finaltask.db.entity.Request;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class MakeRequestCommand extends Command {

	private static final long serialVersionUID = 5424236290189719877L;

	private static final Logger LOG = Logger.getLogger(MakeRequestCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		HttpSession session = req.getSession(false);

		User user = (User) session.getAttribute(Parameter.LOGGED_USER);
		LOG.trace(Messages.TRACE_GET_SESSION_ATTRIBUTE + Parameter.LOGGED_USER + " --> " + user);

		long userId = user.getId();
		String flightIdStr = req.getParameter(Parameter.FLIGHT_ID);
		String carModelIdStr = req.getParameter(Parameter.CAR_MODEL_ID);
		String carEnginePowerStr = req.getParameter(Parameter.CAR_ENGINE_POWER);
		String carNumOfSeatsStr = req.getParameter(Parameter.CAR_NUM_OF_SEATS);
		String carEngineTypeIdStr = req.getParameter(Parameter.CAR_ENGINE_TYPE_ID);

		StringBuilder message = new StringBuilder()
				.append(Parameter.FLIGHT_ID).append(" --> ").append(flightIdStr)
				.append(Parameter.CAR_MODEL_ID).append(" --> ").append(carModelIdStr)
				.append(Parameter.CAR_ENGINE_POWER).append(" --> ").append(carEnginePowerStr)
				.append(Parameter.CAR_NUM_OF_SEATS).append(" --> ").append(carNumOfSeatsStr)
				.append(Parameter.CAR_ENGINE_TYPE_ID).append(" --> ").append(carEngineTypeIdStr);
		
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + message);

		if (StringUtils.isNullOrEmpty(flightIdStr) || StringUtils.isNullOrEmpty(carModelIdStr)
				|| StringUtils.isNullOrEmpty(carEnginePowerStr) || StringUtils.isNullOrEmpty(carNumOfSeatsStr)
				|| StringUtils.isNullOrEmpty(carEngineTypeIdStr)) {
			
			LOG.debug(Messages.ERR_PARAMETERS_EMPTY + message);
			throw new AppException(Messages.ERR_PARAMETERS_EMPTY + message);
		}

		CarModelDao carModelDao = factory.getCarModelDao();
		CarModel carModel = carModelDao.getCarModelByName(carModelIdStr.trim());
		LOG.trace("Car model was gotten from DB. Car model --> " + carModel);

		long carModelId = Integer.parseInt(carModelIdStr.trim());
		long flightId = Integer.parseInt(flightIdStr.trim());
		int carEnginePower = Integer.parseInt(carEnginePowerStr.trim());
		int carNumOfSeats = Integer.parseInt(carNumOfSeatsStr.trim());
		int carEngineTypeId = Integer.parseInt(carEngineTypeIdStr.trim());

		Request request = new Request();
		request.setUserId(userId);
		request.setFlightId(flightId);
		request.setCarModelId(carModelId);
		request.setCarEnginePower(carEnginePower);
		request.setCarNumOfSeats(carNumOfSeats);
		request.setCarEngineTypeId(carEngineTypeId);

		RequestDao requestDao = factory.getRequestDao();
		Request insertedRequest = requestDao.insertRequest(request);

		LOG.trace(Messages.TRACE_INSERT_REQUEST + insertedRequest);

		LOG.debug("Command finished.");
		return new ActionPath(Action.REDIRECT, Path.COMMAND_FLIGHT_LIST);
	}

}
