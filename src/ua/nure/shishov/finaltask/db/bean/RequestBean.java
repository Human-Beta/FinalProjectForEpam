package ua.nure.shishov.finaltask.db.bean;

import ua.nure.shishov.finaltask.db.entity.Entity;

public class RequestBean extends Entity {

	private static final long serialVersionUID = 613734173162572606L;

//	for making link on user
	private long userId;
	private String userLogin;

//	for making link on flight
	private long flightId;
	private String flightDirection; // fromCity + toCity

	private String carModelName;
	private int carEnginePower;
	private String carEngineTypeName;
	private int carNumOfSeats;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public long getFlightId() {
		return flightId;
	}

	public void setFlightId(long flightId) {
		this.flightId = flightId;
	}

	public String getFlightDirection() {
		return flightDirection;
	}

	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public int getCarEnginePower() {
		return carEnginePower;
	}

	public void setCarEnginePower(int carEnginePower) {
		this.carEnginePower = carEnginePower;
	}

	public int getCarNumOfSeats() {
		return carNumOfSeats;
	}

	public void setCarNumOfSeats(int carNumOfSeats) {
		this.carNumOfSeats = carNumOfSeats;
	}

	public String getCarEngineTypeName() {
		return carEngineTypeName;
	}

	public void setCarEngineTypeName(String carEngineTypeName) {
		this.carEngineTypeName = carEngineTypeName;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		return id == ((RequestBean) obj).getId();
	}

	@Override
	public String toString() {
		return "RequestBean [userId=" + userId + ", userLogin=" + userLogin + ", flightId=" + flightId
				+ ", flightDirection=" + flightDirection + ", carModelName=" + carModelName + ", carEnginePower="
				+ carEnginePower + ", carNumOfSeats=" + carNumOfSeats + ", carEngineType=" + carEngineTypeName
				+ ", getId()=" + getId() + "]";
	}

}
