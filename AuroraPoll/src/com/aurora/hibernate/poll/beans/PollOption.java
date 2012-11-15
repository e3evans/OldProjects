package com.aurora.hibernate.poll.beans;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Title:        PollOption
 * Description:  Class to encapsulate all of the information needed for a PollOption
 * Copyright:    Copyright (c) 2010
 * Company:      Aurora Health Care
 *
 * @author Aurora
 * @version 3.0
 */

@Entity
@Table(name = "S05DTDB.TPT28_POLL_ANSWER")
public class PollOption implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8254862200889585482L;
	@EmbeddedId
    private PollOptionKey key;
    @Column(name = "PT28_POLL_ANSWER_DESC")
    private String answer;
    @Column(name = "PT28_POLL_ANSWER_COUNT")
    private Integer count;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PT28_POLL_DATE", insertable = false, updatable = false)
    private Poll poll;

    public PollOption() {
    }

    public PollOption(PollOptionKey key) {
        this.key = key;
    }

    public PollOptionKey getKey() {
        return key;
    }

    public void setKey(PollOptionKey key) {
        this.key = key;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}

