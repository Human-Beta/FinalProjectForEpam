package ua.nure.shishov.finaltask.db;

public final class Fields {

	private Fields() {

	}

	public static final String ENTITY_ID = "id";

	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_ROLE_ID = "role_id";

	public static final String FLIGHT_FROM_CITY_ID = "from_city_id";
	public static final String FLIGHT_TO_CITY_ID = "to_city_id";
	public static final String FLIGHT_DATE_OF_CREATION = "date_of_creation";
	public static final String FLIGHT_STATUS_ID = "status_id";

	public static final String REQUEST_USER_ID = "user_id";
	public static final String REQUEST_FLIGHT_ID = "flight_id";
	public static final String REQUEST_CAR_MODEL_ID = "car_model_id";
	public static final String REQUEST_CAR_ENGINE_POWER = "car_engine_power";
	public static final String REQUEST_CAR_NUM_OF_SEATS = "car_num_of_seats";
	public static final String REQUEST_CAR_ENGINE_TYPE_ID = "car_engine_type_id";

	public static final String CAR_MODEL_NAME = "name";
	public static final String CAR_MODEL_ID = "model_id";
	public static final String CAR_ENGINE_POWER = "engine_power";
	public static final String CAR_NUM_OF_SEATS = "num_of_seats";
	public static final String CAR_ENGINE_TYPE_ID = "engine_type_id";
	public static final String CAR_STATE_ID = "state_id";

	public static final String CITY_NAME = "name";

//	beans

	public static final String USER_BEAN_LOGIN = "login";
	public static final String USER_BEAN_FIRST_NAME = "first_name";
	public static final String USER_BEAN_LAST_NAME = "last_name";
	public static final String USER_BEAN_ROLE_NAME = "role_name";

	public static final String FLIGHT_BEAN_FROM_CITY_NAME = "from_city_name";
	public static final String FLIGHT_BEAN_DIRECTION = "direction";
	public static final String FLIGHT_BEAN_TO_CITY_NAME = "to_city_name";
	public static final String FLIGHT_BEAN_DATE_OF_CREATION = "date_of_creation";
	public static final String FLIGHT_BEAN_STATUS_NAME = "status_name";
	public static final String FLIGHT_BEAN_USER_ID = "user_id";
	public static final String FLIGHT_BEAN_USER_LOGIN_NAME = "user_login_name";
	public static final String FLIGHT_BEAN_USER_FIRST_NAME = "user_first_name";
	public static final String FLIGHT_BEAN_USER_LAST_NAME = "user_last_name";
	public static final String FLIGHT_BEAN_CAR_ID = "car_id";
	public static final String FLIGHT_BEAN_CAR_MODEL = "car_model";
	public static final String FLIGHT_BEAN_CAR_ENGINE_POWER = "car_engine_power";
	public static final String FLIGHT_BEAN_CAR_NUM_OF_SEATS = "car_num_of_seats";
	public static final String FLIGHT_BEAN_CAR_ENGINE_TYPE = "car_engine_type";
	public static final String FLIGHT_BEAN_CAR_STATE = "car_state";

	public static final String REQUEST_BEAN_USER_ID = "user_id";
	public static final String REQUEST_BEAN_USER_LOGIN = "user_login";
	public static final String REQUEST_BEAN_FLIGHT_ID = "flight_id";
	public static final String REQUEST_BEAN_FLIGHT_DIRECTION = "flight_direction";
	public static final String REQUEST_BEAN_CAR_MODEL_NAME = "car_model_name";
	public static final String REQUEST_BEAN_CAR_ENGINE_POWER = "car_engine_power";
	public static final String REQUEST_BEAN_CAR_NUM_OF_SEATS = "car_num_of_seats";
	public static final String REQUEST_BEAN_CAR_ENGINE_TYPE = "car_engine_type";

	public static final String CAR_BEAN_MODEL_NAME = "model_name";
	public static final String CAR_BEAN_ENGINE_POWER = "engine_power";
	public static final String CAR_BEAN_NUM_OF_SEATS = "num_of_seats";
	public static final String CAR_BEAN_ENGINE_TYPE = "engine_type";
	public static final String CAR_BEAN_STATE_NAME = "state_name";

}
