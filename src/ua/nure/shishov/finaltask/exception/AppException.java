package ua.nure.shishov.finaltask.exception;

public class AppException extends Exception {

	private static final long serialVersionUID = -986373485219571343L;

	public AppException() {
		super();
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

}
