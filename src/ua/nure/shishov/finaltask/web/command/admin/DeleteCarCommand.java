package ua.nure.shishov.finaltask.web.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.cj.util.StringUtils;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.dao.CarDao;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class DeleteCarCommand extends Command {

	private static final long serialVersionUID = 4718918426788616886L;

	private static final Logger LOG = Logger.getLogger(DeleteCarCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		String carIdStr = req.getParameter(Parameter.CAR_ID);
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + Parameter.CAR_ID + " --> " + carIdStr);

		if (StringUtils.isNullOrEmpty(carIdStr)) {
			LOG.debug(Messages.ERR_PARAMETERS_EMPTY + Parameter.CAR_ID + " --> " + carIdStr);
			throw new AppException(Messages.ERR_PARAMETERS_EMPTY + Parameter.CAR_ID + " --> " + carIdStr);
		}

		long carId = Long.parseLong(carIdStr.trim());

		CarDao carDao = factory.getCarDao();
		boolean isDelete = carDao.deleteCarById(carId);

		if (isDelete) {
			LOG.trace(Messages.TRACE_DELETE_CAR_BY_ID + carId);
		}

		String forwardCommand = req.getParameter(Parameter.FORWARD_COMMAND);
		if (forwardCommand != null) {
			forwardCommand = forwardCommand.trim();
		}
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + Parameter.FORWARD_COMMAND + " --> " + forwardCommand);

		ActionPath actionPath = new ActionPath(Action.REDIRECT, Path.COMMAND_CAR_LIST);

		if (!StringUtils.isNullOrEmpty(forwardCommand)) {
			actionPath.setPath("controller?command=" + forwardCommand);
		}

		LOG.debug("Command finished.");
		return actionPath;
	}

}
