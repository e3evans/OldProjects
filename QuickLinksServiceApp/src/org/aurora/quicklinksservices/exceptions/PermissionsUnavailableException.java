package org.aurora.quicklinksservices.exceptions;

/**
 * This exception is thrown to indicate that the user's permissions are
 * unavailable due to an exception while loading them from portal-services.
 */
public class PermissionsUnavailableException extends Exception {

	private static final long serialVersionUID = 6425840004814001386L;

	public PermissionsUnavailableException() {
		super();
	}

	public PermissionsUnavailableException(String message) {
		super(message);
	}

	public PermissionsUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionsUnavailableException(Throwable cause) {
		super(cause);
	}
}