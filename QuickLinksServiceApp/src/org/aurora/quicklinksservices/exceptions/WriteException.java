package org.aurora.quicklinksservices.exceptions;

public class WriteException extends Exception {

	private static final long serialVersionUID = 2706784012514954059L;

	public WriteException() {
		super();
	}

	public WriteException(String message) {
		super(message);
	}

	public WriteException(String message, Throwable cause) {
		super(message, cause);
	}

	public WriteException(Throwable cause) {
		super(cause);
	}
}