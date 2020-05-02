package ua.nure.shishov.finaltask.web.command.driver;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.bean.RequestBean;
import ua.nure.shishov.finaltask.db.dao.RequestBeanDao;
import ua.nure.shishov.finaltask.db.entity.User;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.Action;
import ua.nure.shishov.finaltask.web.ActionPath;
import ua.nure.shishov.finaltask.web.command.Command;

public class UserRequestListCommand extends Command {

	private static final long serialVersionUID = -7993882394890176072L;

	private static final Logger LOG = Logger.getLogger(UserRequestListCommand.class);

	@Override
	public ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		LOG.debug("Command starts.");

		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute(Parameter.LOGGED_USER);
		LOG.trace(Messages.TRACE_GET_SESSION_ATTRIBUTE + Parameter.LOGGED_USER + " --> " + user);
		
		RequestBeanDao requestBeanDao = factory.getRequestBeanDao();
		List<RequestBean> requestBeans = requestBeanDao.getRequestBeansOfUser(user);
		LOG.trace("Request bean were gotten from DB.");

		Collections.<RequestBean>sort(requestBeans,
				(a, b) -> a.getFlightDirection().compareTo(b.getFlightDirection()));
		LOG.trace("Request beans were sorted by flight`s direction.");
		
		req.setAttribute(Parameter.REQUEST_BEANS_USER, requestBeans);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.REQUEST_BEANS_USER);

		LOG.debug("Command finished.");
		return new ActionPath(Action.FORWARD, Path.PAGE_USER_REQUEST_LIST);
	}

}
