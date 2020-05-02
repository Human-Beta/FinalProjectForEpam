package ua.nure.shishov.finaltask.db;

public enum CarEngineType {
	GASOLINE, GAS, ELECTRICITY;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
