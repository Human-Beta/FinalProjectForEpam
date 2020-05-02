package ua.nure.shishov.finaltask.db;

/**
 * FlightStatus entity.
 * 
 * @author N.Shishov
 * 
 */
public enum FlightStatus {
	OPEN, IN_PROGRESS, CLOSED, CANCELED;

	/**
	 * Gives FlightStatus entity by name.
	 *
	 * @param statusName Name of flight status.
	 * @return FlightStatus entity.
	 */
	public static FlightStatus getStatus(String statusName) {
		for (FlightStatus status : values()) {
			if (status.getName().equalsIgnoreCase(statusName)) {
				return status;
			}
		}
		return null;
	}

	/**
	 * Gives FlightStatus name.
	 * 
	 * @return Flight status name in lower case.
	 */
	public String getName() {
		return name().toLowerCase();
	}
}
