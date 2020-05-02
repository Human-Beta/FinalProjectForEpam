package ua.nure.shishov.finaltask.exception;

public class DBException extends AppException {

	private static final long serialVersionUID = -7661436095247000351L;

	public DBException() {
		super();
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(String message) {
		super(message);
	}

}
