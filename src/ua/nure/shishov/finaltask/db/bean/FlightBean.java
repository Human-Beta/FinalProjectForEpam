package ua.nure.shishov.finaltask.db.bean;

import java.sql.Timestamp;

import ua.nure.shishov.finaltask.db.entity.Entity;

public class FlightBean extends Entity {

	private static final long serialVersionUID = 180692127520745741L;

	private String direction;
	private Timestamp dateOfCreation;
	private String statusName;

//	for making link on user
	private long userId;
	private String userLoginName;

//	for making link on car
	private long carId;
	private String carModelName;

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Timestamp getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Timestamp dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public long getCarId() {
		return carId;
	}

	public void setCarId(long carId) {
		this.carId = carId;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	@Override
	public String toString() {
		return "FlightBean [direction=" + direction + ", dateOfCreation=" + dateOfCreation + ", statusName="
				+ statusName + ", userId=" + userId + ", userLoginName=" + userLoginName + ", carId=" + carId
				+ ", carModelName=" + carModelName + ", getId()=" + getId() + "]";
	}

}
