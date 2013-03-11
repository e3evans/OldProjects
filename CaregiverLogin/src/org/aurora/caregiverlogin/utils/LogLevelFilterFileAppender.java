package org.aurora.caregiverlogin.utils;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

public class LogLevelFilterFileAppender extends FileAppender {
	private static final String LOG_FILE_NAME = "/aurora/logs/CaregiverLogin.log";
	private static final String LOGINS_LOG_FILE_NAME = "/aurora/logs/Logins.log";

	public LogLevelFilterFileAppender() {

	}

	public LogLevelFilterFileAppender(Layout layout, String fileName,
			boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		super(layout, fileName, append, bufferedIO, bufferSize);
	}

	public LogLevelFilterFileAppender(Layout layout, String fileName,
			boolean append) throws IOException {
		super(layout, fileName, append);
	}

	public LogLevelFilterFileAppender(Layout layout, String fileName)
			throws IOException {
		super(layout, fileName);
	}

	@Override
	public void activateOptions() {
		MDC.put(LOG_FILE_NAME, fileName);
		super.activateOptions();
	}

	@Override
	public void append(LoggingEvent event) {
		try {
			if (Level.WARN.equals(event.getLevel())) {
				setFile(LOGINS_LOG_FILE_NAME, fileAppend, bufferedIO,
						bufferSize);
			} else {
				setFile(LOG_FILE_NAME, fileAppend, bufferedIO, bufferSize);
			}
		} catch (IOException ie) {
			errorHandler
					.error("Error occured while setting file for the log level "
							+ event.getLevel(), ie, ErrorCode.FILE_OPEN_FAILURE);
		}
		super.append(event);
	}
}