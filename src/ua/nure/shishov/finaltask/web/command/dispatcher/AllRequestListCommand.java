package ua.nure.shishov.finaltask.web.command.dispatcher;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.CarEngineType;
import ua.nure.shishov.finaltask.db.CarState;
import ua.nure.shishov.finaltask.db.bean.CarBean;
import ua.nure.shishov.finaltask.db.bean.RequestBean;
import ua.nure.shishov.finaltask.db.dao.CarBeanDao;
import ua.nure.shishov.finaltask.db.dao.RequestBeanDao;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class AllRequestListCommand extends Command {

	private static final long serialVersionUID = -8603476671094124167L;

	private static final Logger LOG = Logger.getLogger(AllRequestListCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		RequestBeanDao requestBeanDao = factory.getRequestBeanDao();
		List<RequestBean> requestBeans = requestBeanDao.getRequestBeans();
		LOG.trace("Request beans were gotten from DB.");

		Collections.<RequestBean>sort(requestBeans,
				(r1, r2) -> r1.getFlightDirection().compareTo(r2.getFlightDirection()));
		LOG.trace("Request beans were sorted by flight direction.");

//		to send map in request beans in json format
		Gson gson = new Gson();

		Map<RequestBean, String> requestBeansMap = new LinkedHashMap<>();
		requestBeans.stream().forEach(r -> requestBeansMap.put(r, gson.toJson(r)));

		req.setAttribute(Parameter.REQUEST_BEANS_MAP, requestBeansMap);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.REQUEST_BEANS_MAP);

//		serviceable and unused cars
		CarBeanDao carBeanDao = factory.getCarBeanDao();
		List<CarBean> carBeans = carBeanDao.getUnusedCarBeansByState(CarState.SERVICEABLE);
		LOG.trace("Car beans were gotten from DB.");

		req.setAttribute(Parameter.CAR_BEANS, carBeans);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CAR_BEANS);

		req.setAttribute(Parameter.CAR_BEANS_JSON, gson.toJson(carBeans));
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CAR_BEANS_JSON);

//		car engine types
		req.setAttribute(Parameter.CAR_ENGINE_TYPES, CarEngineType.values());
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.CAR_ENGINE_TYPES);

		LOG.debug("Command finished.");
		return new ActionPath(Action.FORWARD, Path.PAGE_ALL_REQUEST_LIST);

	}

}
