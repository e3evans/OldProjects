package org.aurora.portalSSO.util;

/**
 * This exception is thrown to indicate that the user's permissions are unavailable due to
 * an exception while loading them from portal-services.
 */
public class PermissionsUnavailableException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1467080396133234860L;

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
