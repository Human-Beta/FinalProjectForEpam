package ua.nure.shishov.finaltask.db;

/**
 * Vehicle state entity.
 * 
 * @author N.Shishov
 * 
 */
public enum CarState {
	SERVICEABLE, DEFECTIVE;

	@Override
	public String toString() {
		return name().toLowerCase();
	}

}
