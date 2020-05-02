package ua.nure.shishov.finaltask.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.command.Command;
import ua.nure.shishov.finaltask.web.command.CommandContainer;

/**
 * Main servlet controller.
 * 
 * @author N.Shishov
 * 
 */
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 2973835230654184470L;

	private static final Logger LOG = Logger.getLogger(Controller.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("Controller starts.");

		String commandName = req.getParameter(Parameter.COMMAND);
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + commandName);
		
		Command command = CommandContainer.getCommand(commandName);
		LOG.trace("Get the command object --> " + command);

		ActionPath actionPath = new ActionPath();
		try {
			actionPath = command.execute(req, resp);
			LOG.trace("Get the ActionPath object --> " + actionPath);
		} catch (AppException e) {
			LOG.error(e.getMessage());
			req.setAttribute(Parameter.ERROR_MESSAGE, e.getMessage());
		}

		if (Action.FORWARD == actionPath.getAction()) {
			req.getRequestDispatcher(actionPath.getPath()).forward(req, resp);
		} else if (Action.REDIRECT == actionPath.getAction()) {
			resp.sendRedirect(actionPath.getPath());
		}

		LOG.debug("Controller finished. And now " + actionPath.getAction().name() + " to address --> "
				+ actionPath.getPath());
	}
}
