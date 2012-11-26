package com.aurora.hibernate.poll.beans;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Date;

/**
 * Title:        PollOptionKey
 * Description:  Class to encapsulate all of the information needed for a PollOptionKey
 * Copyright:    Copyright (c) 2010
 * Company:      Aurora Health Care
 *
 * @author Aurora
 * @version 3.0
 */

@Embeddable
public class PollOptionKey implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5068246593867515123L;
	@Column(name = "PT28_POLL_DATE", nullable = false)
    private Date date;
    @Column(name = "PT28_POLL_ANSWER_SEQ", nullable = false)
    private Integer seqNo;

    public PollOptionKey() {
    }

    public PollOptionKey(Date date, Integer seqNo) {
        this.date = date;
        this.seqNo = seqNo;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PollOptionKey)) return false;

        PollOptionKey that = (PollOptionKey) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (seqNo != null ? !seqNo.equals(that.seqNo) : that.seqNo != null) return false;
        return true;
    }

    public int hashCode() {
        int result;
        result = (date != null ? date.hashCode() : 0);
        result = 31 * result + (seqNo != null ? seqNo.hashCode() : 0);
        return result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }
}
