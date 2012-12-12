package com.aurora.quicklinks.beans;

public class UserApplication {
	private String appName;
	
	private String userid;

	private String appDesc;

	private String appURL;
	
	private String appId;
	
	private String seqNo;
	
	private String activeCd;
	
	private Integer dispSeq;
	public static final String ACTIVE = "A";
	public static final String INACTIVE = "I";
	public static final Integer NOTDISPLAYED = new Integer(0);
	
	public Integer getDispSeq() {
		return dispSeq;
	}

	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	

	public void setActiveCd(String activeCd) {
		this.activeCd = activeCd;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	private boolean checked;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getAppURL() {
		return appURL;
	}

	public void setAppURL(String appURL) {
		this.appURL = appURL;
	}
	public boolean isActive() {
        return this.activeCd.equalsIgnoreCase("A");
    }

	@Override
	public String toString() {
		return "UserApplication [appName=" + appName + ", userid=" + userid
				+ ", appDesc=" + appDesc + ", appURL=" + appURL + ", appId="
				+ appId + ", seqNo=" + seqNo + ", activeCd=" + activeCd
				+ ", checked=" + checked + "]";
	}

	public String getActiveCd() {
		// TODO Auto-generated method stub
		return this.activeCd;
	}

}
