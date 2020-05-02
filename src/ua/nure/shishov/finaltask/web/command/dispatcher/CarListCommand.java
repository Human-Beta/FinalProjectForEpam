package ua.nure.shishov.finaltask.web.command.dispatcher;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.CarEngineType;
import ua.nure.shishov.finaltask.db.CarState;
import ua.nure.shishov.finaltask.db.bean.CarBean;
import ua.nure.shishov.finaltask.db.dao.CarBeanDao;
import ua.nure.shishov.finaltask.db.dao.CarModelDao;
import ua.nure.shishov.finaltask.db.entity.CarModel;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class CarListCommand extends Command {

	private static final long serialVersionUID = 701317846543958487L;

	private static final Logger LOG = Logger.getLogger(CarListCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

//		cars
		CarBeanDao carBeanDao = factory.getCarBeanDao();
		List<CarBean> carBeans = carBeanDao.getCarBeans();
		LOG.trace("Car beans were gotten from DB.");

		Collections.sort(carBeans, (CarBean c1, CarBean c2) -> c1.getModelName().compareTo(c2.getModelName()));
		LOG.trace("Car beans were sorted by model name.");

		req.setAttribute(Parameter.CAR_BEANS, carBeans);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CAR_BEANS);

		
//		models
		CarModelDao carModelDao = factory.getCarModelDao();
		List<CarModel> carModels = carModelDao.getCarModels();
		LOG.trace("Car models were gotten from DB.");
		
		req.setAttribute(Parameter.CAR_MODELS, carModels);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CAR_MODELS);
		
//		car engine types
		req.setAttribute(Parameter.CAR_ENGINE_TYPES, CarEngineType.values());
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CAR_ENGINE_TYPES);

//		state of car
		req.setAttribute(Parameter.CAR_STATES, CarState.values());
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CAR_STATES);

		
		LOG.debug("Command finished.");
		return new ActionPath(Action.FORWARD, Path.PAGE_CAR_LIST);
	}

}
