package com.aurora.hibernate.poll.dao;

import java.util.Date;
import java.util.List;

import com.aurora.hibernate.poll.beans.Poll;

public interface PollDAO {

	public abstract Poll getLatestPoll();
	public abstract Poll getPollByDate(Date date);
	public abstract List<Poll> getLastTenPollResults();
}
