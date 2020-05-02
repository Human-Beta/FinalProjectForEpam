package ua.nure.shishov.finaltask.web;

import java.io.Serializable;

import ua.nure.shishov.finaltask.constant.Path;

public final class ActionPath implements Serializable {

	private static final long serialVersionUID = 324458175912578554L;
	
	private Action action = Action.FORWARD;
	private String path = Path.PAGE_ERROR_PAGE;

	public ActionPath() {
	}

	public ActionPath(Action action, String path) {
		this.action = action;
		this.path = path;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "ActionPath [action=" + action + ", path=" + path + "]";
	}

}
