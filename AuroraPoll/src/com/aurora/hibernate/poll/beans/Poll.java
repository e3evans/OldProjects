package com.aurora.hibernate.poll.beans;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Title:        Poll
 * Description:  Class to encapsulate all of the information needed for a Poll
 * Copyright:    Copyright (c) 2010
 * Company:      Aurora Health Care
 *
 * @author Aurora
 * @version 3.0
 */

@Entity
@Table(name = "TPT27_POLL_QUESTION")
public class Poll implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8393445514766399361L;
	@Id
    @Column(name = "PT27_POLL_DATE", nullable = false)
    private Date date;
    @Column(name = "PT27_POLL_QUESTION_DESC")
    private String question;
    @Column(name = "PT27_CORRECT_POLL_ANSWER")
    private String answer;

	@OneToMany(mappedBy = "poll",fetch = FetchType.LAZY, cascade=CascadeType.ALL,orphanRemoval=true)
    private List<PollOption> pollOptions;
	
	@Transient
	private int totalAnswers;

	public int getTotalAnswers() {
		return totalAnswers;
	}

	public void setTotalAnswers(int totalAnswers) {
		this.totalAnswers = totalAnswers;
	}

	public Poll() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<PollOption> getPollOptions() {
        return pollOptions;
    }

    public void setPollOptions(List<PollOption> pollOptions) {
        this.pollOptions = pollOptions;
    }

    @Transient
    public String getFormattedDate() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String dateStr = "";
        if (date != null)
            dateStr = df.format(date);
        return dateStr;
    }

    @Transient
    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String dateStr = "";
        if (date != null)
            dateStr = df.format(date);
        return dateStr;
    }
}
