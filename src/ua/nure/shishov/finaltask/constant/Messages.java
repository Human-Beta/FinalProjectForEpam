package ua.nure.shishov.finaltask.constant;

public final class Messages {

	private Messages() {

	}

//  /////////////////////////////////////////////////
//	ERROR
//	/////////////////////////////////////////////////

	public static final String ERR_PARAMETERS_EMPTY = "Some of parameters are empty: ";

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain a data source.";
	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool.";
	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection.";
	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement.";
	public static final String ERR_CANNOT_CLOSE_RESULT_SET = "Cannot close a result set.";

	public static final String ERR_CANNOT_OBTAIN_USERS = "Cannot obtain a users.";
	public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain a user by id --> ";
	public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain a user by its login --> ";
	public static final String ERR_CANNOT_INSERT_USER = "Cannot insert the user --> ";

	public static final String ERR_CANNOT_OBTAIN_USER_BEANS = "Cannot obtain a user beans.";

	public static final String ERR_CANNOT_OBTAIN_REQUESTS = "Cannot obtain a requests.";
	public static final String ERR_CANNOT_OBTAIN_REQUESTS_OF_USER = "Cannot obtain a requests of user --> ";
	public static final String ERR_CANNOT_INSERT_REQUEST = "Cannot insert the request --> ";
	public static final String ERR_CANNOT_DELETE_REQUEST_BY_ID = "Cannot delete the request by id --> ";
	public static final String ERR_CANNOT_DELETE_REQUEST_BY_FLIGHT_ID = "Cannot delete the request by flight id --> ";

	public static final String ERR_CANNOT_OBTAIN_REQUEST_BEANS = "Cannot obtain a request beans";
	public static final String ERR_CANNOT_OBTAIN_REQUEST_BEANS_OF_USER = "Cannot obtain a request beans of user --> ";

	public static final String ERR_CANNOT_OBTAIN_FLIGHTS = "Cannot obtain a flights.";
	public static final String ERR_CANNOT_OBTAIN_FLIGHT_BY_ID = "Cannot obtain a flight by id --> ";
	public static final String ERR_CANNOT_UPDATE_FLIGHT = "Cannot update flight --> ";
	public static final String ERR_CANNOT_UPDATE_FLIGHT_BY_ID = "Cannot update flight by id --> ";
	public static final String ERR_CANNOT_INSERT_FLIGHT = "Cannot insert flight --> ";

	public static final String ERR_CANNOT_OBTAIN_FLIGHT_BEANS = "Cannot obtain a flight beans";

	public static final String ERR_CANNOT_OBTAIN_CAR_BEANS = "Cannot obtain a car beans.";
	public static final String ERR_CANNOT_OBTAIN_CAR_BEANS_BY_STATE = "Cannot obtain a car beans by state.";

	public static final String ERR_CANNOT_OBTAIN_CAR_MODELS = "Cannot obtain a car models";
	public static final String ERR_CANNOT_OBTAIN_CAR_MODEL_BY_NAME = "Cannot obtain the car model by name --> ";
	public static final String ERR_CANNOT_INSERT_CAR = "Cannot insert the car --> ";
	public static final String ERR_CANNOT_DELETE_CAR_BY_ID = "Cannot delete a car by id --> ";
	public static final String ERR_CANNOT_UPDATE_CAR = "Cannot update a car --> ";
	public static final String ERR_CANNOT_UPDATE_CAR_STATE_BY_ID = "Cannot update a car state by id --> ";

	public static final String ERR_CANNOT_OBTAIN_CITIES = "Cannot obtain a cities.";

//  /////////////////////////////////////////////////
//	TRACE
//	/////////////////////////////////////////////////

	public static final String TRACE_OBTAIN_CAR_BEANS = "Obtain a car beans.";
	public static final String TRACE_OBTAIN_CAR_BEANS_BY_STATE = "Obtain a car beans by state.";

	public static final String TRACE_OBTAIN_CAR_MODELS = "Obtain a car models";
	public static final String TRACE_OBTAIN_CAR_MODEL_BY_NAME = "Obtain a car model by name --> ";
	public static final String TRACE_INSERT_CAR = "Insert the car --> ";
	public static final String TRACE_DELETE_CAR_BY_ID = "Delete the car by id --> ";
	public static final String TRACE_UPDATE_CAR = "Update a car --> ";
	public static final String TRACE_UPDATE_CAR_BY_ID = "Update a car by id --> ";
	public static final String TRACE_UPDATE_CAR_STATE_BY_ID = "Update a car state by id --> ";

	public static final String TRACE_OBTAIN_FLIGHTS = "Obtain a flights.";
	public static final String TRACE_OBTAIN_FLIGHT_BY_ID = "Obtain a flight by id --> ";
	public static final String TRACE_UPDATE_FLIGHT = "Update flight --> ";
	public static final String TRACE_UPDATE_FLIGHT_BY_ID = "Update flight by id --> ";
	public static final String TRACE_INSERT_FLIGHT = "Insert flight --> ";

	public static final String TRACE_OBTAIN_FLIGHT_BEANS = "Obtain a flight beans";

	public static final String TRACE_OBTAIN_REQUEST_BEANS = "Obtain a request beans";
	public static final String TRACE_OBTAIN_REQUEST_BEANS_OF_USER = "Obtain a request beans of user --> ";

	public static final String TRACE_OBTAIN_REQUESTS = "Obtain a requests.";
	public static final String TRACE_OBTAIN_REQUESTS_OF_USER = "Obtain a requests of user --> ";
	public static final String TRACE_INSERT_REQUEST = "Insert the request --> ";
	public static final String TRACE_DELETE_REQUEST_BY_ID = "Delete the request by id --> ";
	public static final String TRACE_DELETE_REQUEST_BY_FLIGHT_ID = "Delete the request by flight id --> ";

	public static final String TRACE_OBTAIN_USERS = "Obtain a users.";
	public static final String TRACE_OBTAIN_USER_BY_LOGIN = "Obtain a user by login --> ";
	public static final String TRACE_OBTAIN_USER_BY_ID = "Obtain a user by id --> ";
	public static final String TRACE_INSERT_USER = "Insert the user --> ";

	public static final String TRACE_OBTAIN_USER_BEANS = "Obtain a user beans.";

	public static final String TRACE_OBTAIN_CITIES = "Obtain a cities.";

	public static final String TRACE_SET_REQUEST_ATTRIBUTE = "Set the request attribute: ";
	public static final String TRACE_GET_REQUEST_PARAMETER = "Get the request parameter: ";
	public static final String TRACE_SET_SESSION_ATTRIBUTE = "Set the session attribute: ";
	public static final String TRACE_GET_SESSION_ATTRIBUTE = "Get the session attribute: ";



}
