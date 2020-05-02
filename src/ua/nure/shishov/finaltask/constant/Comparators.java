package ua.nure.shishov.finaltask.constant;

import java.util.Comparator;

import ua.nure.shishov.finaltask.db.FlightStatus;
import ua.nure.shishov.finaltask.db.bean.FlightBean;

public final class Comparators {
	private Comparators() {
	}

	public static final Comparator<FlightBean> FLIGHT_BEAN_DATE = (f1, f2) -> f1.getDateOfCreation()
			.compareTo(f2.getDateOfCreation());

	public static final Comparator<FlightBean> FLIGHT_BEAN_ID = (f1, f2) -> {
		long id1 = f1.getId();
		long id2 = f2.getId();

		int res = 0;
		if (id1 < id2) {
			res = -1;
		} else if (id1 > id2) {
			res = 1;
		}

		return res;
	};

	public static final Comparator<FlightBean> FLIGHT_BEAN_STATUS = (f1, f2) -> {
		int statusId1 = FlightStatus.getStatus(f1.getStatusName()).ordinal();
		int statusId2 = FlightStatus.getStatus(f2.getStatusName()).ordinal();

		return statusId1 - statusId2;
	};

}
