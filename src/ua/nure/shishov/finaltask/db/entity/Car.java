package ua.nure.shishov.finaltask.db.entity;

/**
 * Car entity.
 * 
 * @author N.Shishov
 * 
 */
public class Car extends Entity {

	private static final long serialVersionUID = -1547941741239369457L;

	private int modelId;
	private int enginePower;
	private int numOfSeats;
	private int engineTypeId;
	private int stateId;

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
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

	public int getEngineTypeId() {
		return engineTypeId;
	}

	public void setEngineTypeId(int engineTypeId) {
		this.engineTypeId = engineTypeId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	@Override
	public String toString() {
		return "Car [modelId=" + modelId + ", enginePower=" + enginePower + ", numOfSeats=" + numOfSeats
				+ ", engineTypeId=" + engineTypeId + ", stateId=" + stateId + ", getId()=" + getId() + "]";
	}

}
