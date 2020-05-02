package ua.nure.shishov.finaltask.web.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class LogoutCommand extends Command {
	
	private static final long serialVersionUID = 6133624298140101915L;
	
	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);
	
	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) {
		LOG.debug("Command starts");
		HttpSession session = req.getSession(false);

		if (session != null) {
			session.invalidate();
			LOG.trace("Invalidate session.");
		}
		
		req.getContextPath();

		LOG.debug("Command finished.");
		return new ActionPath(Action.REDIRECT, Path.PAGE_LOGIN);
	}

}
