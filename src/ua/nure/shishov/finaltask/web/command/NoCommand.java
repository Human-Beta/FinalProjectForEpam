package ua.nure.shishov.finaltask.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;

public class NoCommand extends Command {

	private static final long serialVersionUID = -2698984010498012869L;
	
	private static final Logger LOG = Logger.getLogger(NoCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		String errorMessage = "No such command";
		req.setAttribute("errorMessage", errorMessage);
		LOG.error("Set the request attribute: errorMessage --> " + errorMessage);

		LOG.debug("Command finished.");
		return new ActionPath(Action.FORWARD, Path.PAGE_ERROR_PAGE);
	}

}
