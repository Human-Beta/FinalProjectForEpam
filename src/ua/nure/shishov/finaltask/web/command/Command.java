package ua.nure.shishov.finaltask.web.command;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.shishov.finaltask.db.dao.DaoFactory;
import ua.nure.shishov.finaltask.db.dao.DaoFactory.Factories;
import ua.nure.shishov.finaltask.exception.AppException;
import ua.nure.shishov.finaltask.web.ActionPath;

/**
 * Main interface for the Command pattern implementation.
 * 
 * @author N.Shishov
 * 
 */
public abstract class Command implements Serializable {

	private static final long serialVersionUID = -6941567851684698632L;

	protected DaoFactory factory;

	public Command() {
		factory = DaoFactory.getDaoFactory(Factories.MYSQL);
	}

	/**
	 * Execution method for command.
	 * 
	 * @return ActionPath object which present action(forward/redirect) and path to
	 *         go once the command is executed.
	 */
	public abstract ActionPath execute(HttpServletRequest req, HttpServletResponse resp) throws AppException;

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
