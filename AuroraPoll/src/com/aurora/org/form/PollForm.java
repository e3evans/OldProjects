package com.aurora.org.form;

import com.aurora.hibernate.poll.beans.Poll;

public class PollForm {
	
	public Poll poll;
	public String pollSelection;
	
	public PollForm(){}
	public PollForm(Poll poll){
		this.poll=poll;
	}
	
	public Poll getPoll() {
		return poll;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	public String getPollSelection() {
		return pollSelection;
	}
	public void setPollSelection(String pollSelection) {
		this.pollSelection = pollSelection;
	}

}
