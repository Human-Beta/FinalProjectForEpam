package ua.nure.shishov.finaltask.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mysql.cj.util.StringUtils;

import ua.nure.shishov.finaltask.constant.Messages;
import ua.nure.shishov.finaltask.constant.Parameter;
import ua.nure.shishov.finaltask.constant.Path;
import ua.nure.shishov.finaltask.db.UserRole;

/**
 * Security filter. Disabled by default. Uncomment Security filter section in
 * web.xml to enable.
 * 
 * @author N.Shishov
 * 
 */
public class CommandAccessFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

	// commands access
	private Map<UserRole, List<String>> accessMap = new HashMap<>();
	private List<String> commons = new ArrayList<>();
	private List<String> outOfControl = new ArrayList<>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.debug("Filter initialization starts");

//		roles
		accessMap.put(UserRole.DRIVER, asList(filterConfig.getInitParameter("driver")));
		accessMap.put(UserRole.DISPATCHER, asList(filterConfig.getInitParameter("dispatcher")));

		List<String> forAdmin = asList(filterConfig.getInitParameter("dispatcher"));
		forAdmin.addAll(asList(filterConfig.getInitParameter("admin")));
		accessMap.put(UserRole.ADMIN, forAdmin);
		LOG.trace("Access map: " + accessMap);

//		common
		commons = asList(filterConfig.getInitParameter("commons"));
		LOG.trace("Common commands: " + commons);

//		out of control
		outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
		LOG.trace("Out of control commands: " + outOfControl);

		LOG.debug("Filter initialization finished");
	}

	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("Filter starts");
		
		FilterResult result = checkAccess(request);
		
		LOG.debug("Filter finished");
		String errorMessage = "";
		
		switch (result) {
		case OK:
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
			break;
		case IS_NOT_AUTHORIZED:
			request.getRequestDispatcher(Path.PAGE_LOGIN).forward(request, response);
			break;
		case COMMAND_IS_EMPTY:
			errorMessage = "Incorrect command name.";
			sendToError(errorMessage, request, response);
			break;
		case DO_NOT_HAVE_PERMISSION:
			errorMessage = "You do not have permission to access the requested resource";
			sendToError(errorMessage, request, response);
			break;
		case NO_COMMAND:
			errorMessage = "This command does not exist --> " + request.getParameter(Parameter.COMMAND);
			sendToError(errorMessage, request, response);
			break;
		case OTHER:
			errorMessage = "Something wrong..";
			sendToError(errorMessage, request, response);
			break;
		}

	}
	
	private void sendToError(String errorMessage, ServletRequest request, ServletResponse response) throws ServletException, IOException {
		request.setAttribute(Parameter.ERROR_MESSAGE, errorMessage);
		LOG.trace(Messages.TRACE_SET_REQUEST_ATTRIBUTE + Parameter.ERROR_MESSAGE + " --> " + errorMessage);

		request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
	}

	/**
	 * Check user have permission to resource.
	 * 
	 * @param request ServletRequest entity.
	 * @return <b>boolean</b> User have or have not permission.
	 */
	private FilterResult checkAccess(ServletRequest request) {

		HttpServletRequest httpReq = (HttpServletRequest) request;

		String commandName = httpReq.getParameter(Parameter.COMMAND);
		if(commandName != null) {
			commandName = commandName.trim();
		}
		LOG.trace(Messages.TRACE_GET_REQUEST_PARAMETER + Parameter.COMMAND + " --> " + commandName);

		if (StringUtils.isNullOrEmpty(commandName)) {
			return FilterResult.COMMAND_IS_EMPTY;
		}

		if (outOfControl.contains(commandName)) {
			return FilterResult.OK;
		}

		HttpSession session = httpReq.getSession(false);
		LOG.trace("Get session --> " + session);

		if (session == null) {
			return FilterResult.IS_NOT_AUTHORIZED;
		}

		UserRole userRole = (UserRole) session.getAttribute(Parameter.USER_ROLE);
		LOG.trace(Messages.TRACE_GET_SESSION_ATTRIBUTE + Parameter.USER_ROLE + " --> " + userRole);

		if (userRole == null) {
			return FilterResult.IS_NOT_AUTHORIZED;
		}

		boolean b = accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
		return b ? FilterResult.OK : FilterResult.NO_COMMAND;
	}

}
