package com.aurora.quicklinksservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AppKey
    implements Serializable
    {

    
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8529886211840887944L;
	/**
	 * 
	 */
	
	@Column(name="PT2B_APPID")
	private String appId;
	@Column(name="PT2B_SEQ_NO")
    private Integer seqNo;

    public AppKey()
    {
    }

    public AppKey(String appId, Integer seqNo)
    {
        this.appId = appId;
        this.seqNo = seqNo;
    }

    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof AppKey))
        {
            return false;
        }
        AppKey appKey = (AppKey)o;
        if(appId == null ? appKey.appId != null : !appId.equals(appKey.appId))
        {
            return false;
        }
        return seqNo == null ? appKey.seqNo == null : seqNo.equals(appKey.seqNo);
    }

    public int hashCode()
    {
        int result = appId == null ? 0 : appId.hashCode();
        result = 31 * result + (seqNo == null ? 0 : seqNo.hashCode());
        return result;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public Integer getSeqNo()
    {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo)
    {
        this.seqNo = seqNo;
    }
}



