package org.aurora.quicklinks.exceptions;

/**
 * Description: This class holds information about exceptions occurring in the application
 * 
 * @author 
 * 
 * **********      Modification History      **********
 * Date: 
 * Modified By:
 * Change Description:
 */
public class AppException extends Exception{
	
	private static final long serialVersionUID = -4712399545600711352L;
	
	private String exceptionType;
	private String exceptionCode;
	private String exceptionMessage;
	private String exceptionDesc;
	/**
	 * @return the exceptionType
	 */
	public String getExceptionType() {
		return exceptionType;
	}
	/**
	 * @param exceptionType the exceptionType to set
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	/**
	 * @return the exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}
	/**
	 * @param exceptionCode the exceptionCode to set
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	/**
	 * @param exceptionMessage the exceptionMessage to set
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	/**
	 * @return the exceptionDesc
	 */
	public String getExceptionDesc() {
		return exceptionDesc;
	}
	/**
	 * @param exceptionDesc the exceptionDesc to set
	 */
	public void setExceptionDesc(String exceptionDesc) {
		this.exceptionDesc = exceptionDesc;
	}
	
	
}
