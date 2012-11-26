package com.aurora.hibernate.poll.dao;

import java.util.Date;
import java.util.List;

import com.aurora.hibernate.poll.beans.Poll;
import com.aurora.hibernate.poll.beans.PollOption;

public interface PollDAO {

	public abstract Poll getLatestPoll();
	public abstract Poll getPollByDate(Date date);
	public abstract List <Poll> getLastXNumPolls(int x);
	public abstract void incrementPoll(PollOption pollOption);
}
