package ua.nure.shishov.finaltask.constant;

public final class Path {
	private Path() {

	}

//	out of control
	public static final String PAGE_ERROR_PAGE = "WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_LOGIN = "login.jsp";
	
//	common
	public static final String PAGE_FLIGHT_LIST = "WEB-INF/jsp/common/flight_list.jsp";
	public static final String COMMAND_FLIGHT_LIST = "controller?command=flightList";

//	driver
	public static final String PAGE_USER_REQUEST_LIST = "WEB-INF/jsp/driver/user_request_list.jsp";
	public static final String PAGE_USER_FLIGHT_LIST = "WEB-INF/jsp/driver/user_flight_list.jsp";
	public static final String COMMAND_USER_FLIGHT_LIST = "controller?command=userFlightList";

//	dispatcher
	public static final String PAGE_DISPATCHER_PANEL = "WEB-INF/jsp/dispatcher/dispatcher_panel.jsp";
	public static final String PAGE_ALL_REQUEST_LIST = "WEB-INF/jsp/dispatcher/all_request_list.jsp";
	public static final String PAGE_CAR_LIST = "WEB-INF/jsp/dispatcher/car_list.jsp";
	public static final String COMMAND_ALL_REQUEST_LIST = "controller?command=allRequestList";
	public static final String COMMAND_CAR_LIST = "controller?command=carList";

//	admin
	public static final String PAGE_ADMIN_PANEL = "";
	public static final String COMMAND_USER_LIST = "controller?command=userList";
	public static final String PAGE_USER_LIST = "WEB-INF/jsp/admin/user_list.jsp";

}
