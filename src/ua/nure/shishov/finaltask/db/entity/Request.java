package ua.nure.shishov.finaltask.db.entity;

/**
 * Request entity.
 * 
 * @author N.Shishov
 * 
 */
public class Request extends Entity {

	private static final long serialVersionUID = 785381704783992845L;

	private long userId;
	private long flightId;

	private long carModelId;
	private int carEnginePower;
	private int carNumOfSeats;
	private int carEngineTypeId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getFlightId() {
		return flightId;
	}

	public void setFlightId(long flightId) {
		this.flightId = flightId;
	}

	public long getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(long carModelId) {
		this.carModelId = carModelId;
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

	public int getCarEngineTypeId() {
		return carEngineTypeId;
	}

	public void setCarEngineTypeId(int carEngineTypeId) {
		this.carEngineTypeId = carEngineTypeId;
	}

	@Override
	public String toString() {
		return "Request [userId=" + userId + ", flightId=" + flightId + ", carModelId=" + carModelId
				+ ", carEnginePower=" + carEnginePower + ", carNumOfSeats=" + carNumOfSeats + ", carEngineTypeId="
				+ carEngineTypeId + ", getId()=" + getId() + "]";
	}

}
