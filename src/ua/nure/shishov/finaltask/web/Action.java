package ua.nure.shishov.finaltask.web;

public enum Action {
	FORWARD, REDIRECT;

	public String getName() {
		return name().toLowerCase();
	}
}
