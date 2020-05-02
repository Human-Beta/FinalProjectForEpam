package ua.nure.shishov.finaltask.web.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.cj.util.StringUtils;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.dao.CarDao;
import ua.nure.shishov.finaltask.db.entity.Car;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class ChangeCarCommand extends Command {

	private static final long serialVersionUID = -2476392183674199879L;

	private static final Logger LOG = Logger.getLogger(ChangeCarCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		String carIdStr = req.getParameter(Parameter.CAR_ID);
		String carModelIdStr = req.getParameter(Parameter.CAR_MODEL_ID);
		String carEnginePowerStr = req.getParameter(Parameter.CAR_ENGINE_POWER);
		String carNumOfSeatsStr = req.getParameter(Parameter.CAR_NUM_OF_SEATS);
		String carEngineTypeIdStr = req.getParameter(Parameter.CAR_ENGINE_TYPE_ID);
		String carStateIdStr = req.getParameter(Parameter.CAR_STATE_ID);
		StringBuilder message = new StringBuilder()
				.append(Parameter.CAR_ID).append(" --> ").append(carIdStr)
				.append(Parameter.CAR_MODEL_ID).append(" --> ").append(carModelIdStr)
				.append(Parameter.CAR_ENGINE_POWER).append(" --> ").append(carEnginePowerStr)
				.append(Parameter.CAR_NUM_OF_SEATS).append(" --> ").append(carNumOfSeatsStr)
				.append(Parameter.CAR_ENGINE_TYPE_ID).append(" --> ").append(carEngineTypeIdStr)
				.append(Parameter.CAR_STATE_ID).append(" --> ").append(carStateIdStr);

		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + message);
		if (StringUtils.isNullOrEmpty(carIdStr) ||StringUtils.isNullOrEmpty(carModelIdStr) || StringUtils.isNullOrEmpty(carEnginePowerStr)
				|| StringUtils.isNullOrEmpty(carNumOfSeatsStr) || StringUtils.isNullOrEmpty(carEngineTypeIdStr)) {
			LOG.debug(Messages.ERR_PARAMETERS_EMPTY + message);
			throw new AppException(Messages.ERR_PARAMETERS_EMPTY + message);
		}

		long carId = Integer.parseInt(carIdStr.trim());
		int carModelId = Integer.parseInt(carModelIdStr.trim());
		int carEnginePower = Integer.parseInt(carEnginePowerStr.trim());
		int carNumOfSeats = Integer.parseInt(carNumOfSeatsStr.trim());
		int carEngineTypeId = Integer.parseInt(carEngineTypeIdStr.trim());
		int carStateId = Integer.parseInt(carStateIdStr.trim());

		Car car = new Car();
		car.setId(carId);
		car.setModelId(carModelId);
		car.setEnginePower(carEnginePower);
		car.setNumOfSeats(carNumOfSeats);
		car.setEngineTypeId(carEngineTypeId);
		car.setStateId(carStateId);

		CarDao carDao = factory.getCarDao();
		boolean isUpdate = carDao.updateCar(car);
		if(isUpdate) {
			LOG.trace(Messages.TRACE_UPDATE_CAR_BY_ID + " --> " + carId);
		}
		LOG.debug("Command finished.");
		return new ActionPath(Action.REDIRECT, Path.COMMAND_CAR_LIST);
	}

}
