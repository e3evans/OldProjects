package com.aurora.org.form;

import java.util.List;

import com.aurora.hibernate.poll.beans.Poll;

public class PollForm {
	
	public Poll poll;
	public String pollSelection;
	public int totalResults;
	public List<Poll> archivePolls;
	public List<Integer> archiveCounts;
	
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
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	public List<Poll> getArchivePolls() {
		return archivePolls;
	}
	public void setArchivePolls(List<Poll> archivePolls) {
		this.archivePolls = archivePolls;
	}
	public List<Integer> getArchiveCounts() {
		return archiveCounts;
	}
	public void setArchiveCounts(List<Integer> archiveCounts) {
		this.archiveCounts = archiveCounts;
	}


}
