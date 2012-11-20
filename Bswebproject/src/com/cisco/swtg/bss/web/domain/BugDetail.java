package com.cisco.swtg.bss.web.domain;

import java.util.List;

/**
 * This class is used as domain object for BugDetail and contains only
 * setter/getter
 * 
 * @author teprasad
 */
public class BugDetail {

	private String bugId;
	private String headLine;
	private String releaseNoteText;
	private String srNumbersAssociatedTo;
	private String statusName;
	private String severityCode;
	private String bugLastModifiedDate;
	private String productSoftwareFamily;
	private String platformName;
	private String foundIn;
	private String fixedInVersions;
	private String softwareComponentName;
	private List<BugDetail> relatedBugList;
	private String technology;
	private Boolean visible;
	private String versionAffectedNames;

	private String severityName;
	private String description;
	private String serviceRequests;

	private String duplicateBugMessage;

	private String errorCode;
	private String errorMessage;
	/*
	 * Interpreting bug info is used to show Interpreting Bug info on detail
	 * page and list will be constructed based on certain conditions
	 */
	private List<String> interpretingBugInfo;

	public String getBugId() {
		return bugId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
	}

	public String getHeadLine() {
		return headLine;
	}

	public void setHeadLine(String headLine) {
		this.headLine = headLine;
	}

	public String getReleaseNoteText() {
		return releaseNoteText;
	}

	public void setReleaseNoteText(String releaseNoteText) {
		this.releaseNoteText = releaseNoteText;
	}

	public String getSrNumbersAssociatedTo() {
		return srNumbersAssociatedTo;
	}

	public void setSrNumbersAssociatedTo(String srNumbersAssociatedTo) {
		this.srNumbersAssociatedTo = srNumbersAssociatedTo;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSeverityCode() {
		return severityCode;
	}

	public void setSeverityCode(String severityCode) {
		this.severityCode = severityCode;
	}

	public String getBugLastModifiedDate() {
		return bugLastModifiedDate;
	}

	public void setBugLastModifiedDate(String bugLastModifiedDate) {
		this.bugLastModifiedDate = bugLastModifiedDate;
	}

	public String getProductSoftwareFamily() {
		return productSoftwareFamily;
	}

	public void setProductSoftwareFamily(String productSoftwareFamily) {
		this.productSoftwareFamily = productSoftwareFamily;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getFoundIn() {
		return foundIn;
	}

	public void setFoundIn(String foundIn) {
		this.foundIn = foundIn;
	}

	public String getFixedInVersions() {
		return fixedInVersions;
	}

	public void setFixedInVersions(String fixedInVersions) {
		this.fixedInVersions = fixedInVersions;
	}

	public String getSoftwareComponentName() {
		return softwareComponentName;
	}

	public void setSoftwareComponentName(String softwareComponentName) {
		this.softwareComponentName = softwareComponentName;
	}

	public List<BugDetail> getRelatedBugList() {
		return relatedBugList;
	}

	public void setRelatedBugList(List<BugDetail> relatedBugList) {
		this.relatedBugList = relatedBugList;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getVersionAffectedNames() {
		return versionAffectedNames;
	}

	public void setVersionAffectedNames(String versionAffectedNames) {
		this.versionAffectedNames = versionAffectedNames;
	}

	public String getSeverityName() {
		return severityName;
	}

	public void setSeverityName(String severityName) {
		this.severityName = severityName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceRequests() {
		return serviceRequests;
	}

	public void setServiceRequests(String serviceRequests) {
		this.serviceRequests = serviceRequests;
	}

	public List<String> getInterpretingBugInfo() {
		return interpretingBugInfo;
	}

	public void setInterpretingBugInfo(List<String> interpretingBugInfo) {
		this.interpretingBugInfo = interpretingBugInfo;
	}

	public String getDuplicateBugMessage() {
		return duplicateBugMessage;
	}

	public void setDuplicateBugMessage(String duplicateBugMessage) {
		this.duplicateBugMessage = duplicateBugMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}