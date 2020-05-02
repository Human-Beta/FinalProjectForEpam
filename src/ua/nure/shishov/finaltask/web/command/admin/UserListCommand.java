package ua.nure.shishov.finaltask.web.command.admin;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.UserRole;
import ua.nure.shishov.finaltask.db.bean.UserBean;
import ua.nure.shishov.finaltask.db.dao.UserBeanDao;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class UserListCommand extends Command {

	private static final long serialVersionUID = 1228044271595488786L;

	private static final Logger LOG = Logger.getLogger(UserListCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		UserBeanDao userBeanDao = factory.getUserBeanDao();
		List<UserBean> userBeans = userBeanDao.getUserBeans();
		LOG.trace("User beans were gotten from DB.");

		Collections.<UserBean>sort(userBeans, (u1, u2) -> u1.getLogin().compareTo(u2.getLogin()));
		LOG.trace("User beans were sorted by login.");

		req.setAttribute(Parameter.USER_BEANS, userBeans);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.USER_BEANS);

		UserRole[] userRoles = Stream.of(UserRole.values())
				.filter(r -> r != UserRole.ADMIN)
				.toArray(UserRole[]::new);

		req.setAttribute(Parameter.USER_ROLES, userRoles);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.USER_ROLES);

		LOG.debug("Command finished.");
		return new ActionPath(Action.FORWARD, Path.PAGE_USER_LIST);
	}

}
