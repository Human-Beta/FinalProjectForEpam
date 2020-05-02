package ua.nure.shishov.finaltask.db;

import com.mysql.cj.util.StringUtils;

public enum FlightSort {
	DATE, ID, STATUS;

	/**
	 * Gives FlightSort method by its name.
	 *
	 * @param sortName Name of sort method.
	 * @return Flight sort method. Default - DATE
	 */
	public static FlightSort getSort(String sortName) {
		if (StringUtils.isNullOrEmpty(sortName)) {
			return DATE;
		}
		
		for (FlightSort sort : values()) {
			if (sort.getName().equalsIgnoreCase(sortName)) {
				return sort;
			}
		}
		return DATE;
	}

	/**
	 * Gives FlightSort method name.
	 * 
	 * @return Flight sort method name in lower case.
 	 */
	public String getName() {
		return name().toLowerCase();
	}
}
