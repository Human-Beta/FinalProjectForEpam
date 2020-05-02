package ua.nure.shishov.finaltask.db.bean;

import ua.nure.shishov.finaltask.db.entity.Entity;

public class CarBean extends Entity {

	private static final long serialVersionUID = -2431857697895556359L;

	private String modelName;
	private int enginePower;
	private int numOfSeats;
	private String engineTypeName;
	private String stateName;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getEnginePower() {
		return enginePower;
	}

	public void setEnginePower(int enginePower) {
		this.enginePower = enginePower;
	}

	public int getNumOfSeats() {
		return numOfSeats;
	}

	public void setNumOfSeats(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}

	public String getEngineTypeName() {
		return engineTypeName;
	}

	public void setEngineTypeName(String engineTypeName) {
		this.engineTypeName = engineTypeName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public String toString() {
		return "CarBean [modelName=" + modelName + ", enginePower=" + enginePower + ", numOfSeats=" + numOfSeats
				+ ", engineTypeName=" + engineTypeName + ", stateName=" + stateName + ", getId()=" + getId() + "]";
	}

}
