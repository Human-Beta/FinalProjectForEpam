package ua.nure.shishov.finaltask.db.entity;

import java.sql.Timestamp;

/**
 * Flight entity
 * 
 * @author N.Shishov
 *
 */
public class Flight extends Entity {

	private static final long serialVersionUID = 4817063272631472271L;

	private long fromCityId;
	private long toCityId;
	private Timestamp dateOfCreation;
	private int statusId;
	private long userId;
	private long carId;

	public long getFromCityId() {
		return fromCityId;
	}

	public void setFromCityId(long fromCity) {
		this.fromCityId = fromCity;
	}

	public long getToCityId() {
		return toCityId;
	}

	public void setToCityId(long toCity) {
		this.toCityId = toCity;
	}

	public Timestamp getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Timestamp dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCarId() {
		return carId;
	}

	public void setCarId(long carId) {
		this.carId = carId;
	}

	@Override
	public String toString() {
		return "Flight [fromCityId=" + fromCityId + ", toCityId=" + toCityId + ", dateOfCreation=" + dateOfCreation
				+ ", statusId=" + statusId + ", userId=" + userId + ", carId=" + carId + ", getId()=" + getId() + "]";
	}

}
