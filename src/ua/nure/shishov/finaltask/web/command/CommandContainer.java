package ua.nure.shishov.finaltask.web.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ua.nure.shishov.finaltask.web.command.admin.AddCarCommand;
import ua.nure.shishov.finaltask.web.command.admin.ChangeCarCommand;
import ua.nure.shishov.finaltask.web.command.admin.DeleteCarCommand;
import ua.nure.shishov.finaltask.web.command.admin.RegisterUserCommand;
import ua.nure.shishov.finaltask.web.command.admin.UserListCommand;
import ua.nure.shishov.finaltask.web.command.auth.LoginCommand;
import ua.nure.shishov.finaltask.web.command.auth.LogoutCommand;
import ua.nure.shishov.finaltask.web.command.common.FlightListCommand;
import ua.nure.shishov.finaltask.web.command.dispatcher.AddFlightCommand;
import ua.nure.shishov.finaltask.web.command.dispatcher.AllRequestListCommand;
import ua.nure.shishov.finaltask.web.command.dispatcher.AssignCarAndUserForFlightCommand;
import ua.nure.shishov.finaltask.web.command.dispatcher.CarListCommand;
import ua.nure.shishov.finaltask.web.command.driver.CancelFlightCommand;
import ua.nure.shishov.finaltask.web.command.driver.FinishFlightCommand;
import ua.nure.shishov.finaltask.web.command.driver.MakeRequestCommand;
import ua.nure.shishov.finaltask.web.command.driver.UserFlightListCommand;
import ua.nure.shishov.finaltask.web.command.driver.UserRequestListCommand;

public class CommandContainer {

	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private CommandContainer() {
	}

	private static Map<String, Command> commands = new HashMap<>();

	static {
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());

//		common
		commands.put("flightList", new FlightListCommand());

//		driver
		commands.put("makeRequest", new MakeRequestCommand());
		commands.put("userRequestList", new UserRequestListCommand());
		commands.put("userFlightList", new UserFlightListCommand());
		commands.put("finishFlight", new FinishFlightCommand());
		commands.put("cancelFlight", new CancelFlightCommand());

//		dispatcher
		commands.put("allRequestList", new AllRequestListCommand());
		commands.put("assignCarAndUserForFlight", new AssignCarAndUserForFlightCommand());
		commands.put("carList", new CarListCommand());
		commands.put("addFlight", new AddFlightCommand());
		
//		admin
		commands.put("addCar", new AddCarCommand());
		commands.put("changeCar", new ChangeCarCommand());
		commands.put("deleteCar", new DeleteCarCommand());
		commands.put("userList", new UserListCommand());
		commands.put("registerUser", new RegisterUserCommand());
		
		
		commands.put("noCommand", new NoCommand());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName Name of the command.
	 * @return Command object.
	 */
	public static Command getCommand(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}
		Command command = commands.get(commandName);

		LOG.trace("Found a command --> " + command);
		return command;
	}
}
