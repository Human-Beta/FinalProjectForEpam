package ua.nure.shishov.finaltask.web.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mysql.cj.util.StringUtils;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.UserRole;
import ua.nure.shishov.finaltask.db.dao.UserDao;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class LoginCommand extends Command {

	private static final long serialVersionUID = -581799255693940839L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");
		HttpSession session = req.getSession();

		String login = req.getParameter(Parameter.LOGIN);
		String password = req.getParameter(Parameter.PASSWORD);
		String forwardCommandName = req.getParameter(Parameter.FORWARD_COMMAND);

		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + Parameter.LOGIN + " --> " + login);

		if (StringUtils.isNullOrEmpty(login) || StringUtils.isNullOrEmpty(password)) {
			LOG.debug("Login/password is empty.");
			throw new AppException("Login/password can`t be empty");
		}

		login = login.trim();

		UserDao userDao = factory.getUserDao();
		User user = userDao.getUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !password.equals(user.getPassword())) {
			LOG.info("Can`t find the user with such login/password. User --> " + user);
			throw new AppException("Can`t find the user with such login/password.");
		}

		ActionPath actionPath = new ActionPath();
		actionPath.setAction(Action.REDIRECT);

		if (!StringUtils.isNullOrEmpty(forwardCommandName)) {
			actionPath.setPath("controller?command=" + forwardCommandName.trim());
		} else {
			actionPath.setPath(Path.COMMAND_FLIGHT_LIST);
		}
		
		UserRole userRole = UserRole.getRole(user);
		LOG.trace("Session`s parameter: user role --> " + userRole);

		session.setAttribute(Parameter.LOGGED_USER, user);
		LOG.trace(Messages.TRACE_SET_SESSION_ATTRIBUTE + Parameter.LOGGED_USER + " --> " + user);

		session.setAttribute(Parameter.USER_ROLE, userRole);
		LOG.trace(Messages.TRACE_SET_SESSION_ATTRIBUTE + Parameter.USER_ROLE + " --> " + userRole);

		LOG.info("User " + user + " logged as " + userRole.getName());

		LOG.debug("Command finished.");
		return actionPath;
	}

}
