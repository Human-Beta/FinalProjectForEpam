package ua.nure.shishov.finaltask.web.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.cj.util.StringUtils;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.dao.UserDao;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class RegisterUserCommand extends Command {

	private static final long serialVersionUID = -4603594810528500602L;

	private static final Logger LOG = Logger.getLogger(RegisterUserCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		String userLogin = req.getParameter(Parameter.USER_LOGIN);
		String userPassword = req.getParameter(Parameter.USER_PASSWORD);
		String userFirstName = req.getParameter(Parameter.USER_FIRST_NAME);
		String userLastName = req.getParameter(Parameter.USER_LAST_NAME);
		String userRoleIdStr = req.getParameter(Parameter.USER_ROLE_ID);

		StringBuilder message = new StringBuilder()
				.append(Parameter.USER_LOGIN).append(" --> ").append(userLogin)
				.append(Parameter.USER_FIRST_NAME).append(" --> ").append(userFirstName)
				.append(Parameter.USER_LAST_NAME).append(" --> ").append(userLastName)
				.append(Parameter.USER_ROLE_ID).append(" --> ").append(userRoleIdStr);

		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + message);

		if (StringUtils.isNullOrEmpty(userLogin) || StringUtils.isNullOrEmpty(userPassword)
				|| StringUtils.isNullOrEmpty(userFirstName) || StringUtils.isNullOrEmpty(userLastName)
				|| StringUtils.isNullOrEmpty(userRoleIdStr)) {
			LOG.debug(Messages.ERR_PARAMETERS_EMPTY + message);
			throw new AppException(Messages.ERR_PARAMETERS_EMPTY + message);
		}
		
		User user = new User();
		user.setLogin(userLogin.trim());
		user.setPassword(userPassword);
		user.setFirstName(userFirstName.trim());
		user.setLastName(userLastName.trim());
		user.setRoleId(Integer.parseInt(userRoleIdStr.trim()));
		
		UserDao userDao = factory.getUserDao();
		User insertedUser = userDao.insertUser(user);
		LOG.trace(Messages.TRACE_INSERT_USER + insertedUser);
		
		LOG.debug("Command finished.");
		return new ActionPath(Action.REDIRECT, Path.COMMAND_USER_LIST);
	}

}
