package com.aurora.org.controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.aurora.hibernate.poll.beans.Poll;
import com.aurora.hibernate.poll.beans.PollOption;
import com.aurora.hibernate.poll.dao.PollDAO;
import com.aurora.org.form.PollForm;




@Controller
@RequestMapping("VIEW")
public class ViewController {
	
	public static String PREF_LAST_POLL_TAKEN = "com.aurora.polls.lastpolltake";
	
	@Autowired
	private PollDAO pollDAO;
	
	public static final String JSP_VIEW = "view";
	
	private static Log log = LogFactory.getLog(ViewController.class);
	

	
	@RequestMapping
	public ModelAndView renderPoll(RenderRequest request, RenderResponse response){
		log.debug("Beginning Default Poll Render.");
		Poll poll = pollDAO.getLatestPoll();
		PollForm pollForm = new PollForm(poll);
		request.getPortletSession().setAttribute("currentPoll", poll);
		
		
		String lastPoll = request.getPreferences().getValue(PREF_LAST_POLL_TAKEN, "EMPTY");
		/*
		 * If Poll has never been taken show poll entry
		 */
		if (null==lastPoll)lastPoll="EMPTY";
		if ("EMPTY".equals(lastPoll)|| !poll.getDateString().equals(lastPoll)){
			ModelAndView modelAndView = new ModelAndView("view");
			modelAndView.addObject("pollForm",pollForm);
			return modelAndView;
		}
		/*
		 * Display results and Poll Archive
		 */
		Iterator<PollOption> i = poll.getPollOptions().iterator();
		int totalResults = 0;
		while (i.hasNext()){
			PollOption pOpt = i.next();
			totalResults += pOpt.getCount();
		}
		pollForm.setTotalResults(totalResults);
		List<Poll> lastPolls = pollDAO.getLastXNumPolls(5);
		for (int x=0;x<lastPolls.size();x++){
			int totalCount = 0;
			Iterator<PollOption> it = lastPolls.get(x).getPollOptions().iterator();	
			while(it.hasNext()){
				PollOption pOpt = it.next();
				totalCount +=pOpt.getCount();
			}
			lastPolls.get(x).setTotalAnswers(totalCount);

		}
		pollForm.setArchivePolls(lastPolls);
		log.debug("Beginning ShowPollResults Poll Render.");
		ModelAndView modelAndView = new ModelAndView("results");
		modelAndView.addObject("pollForm",pollForm);

		return modelAndView;
		
	}
	

	@ResourceMapping(value="renderPoll")
	public ModelAndView renderPoll(ResourceRequest request, ResourceResponse response,@ModelAttribute("pollForm")PollForm pollForm){
		Poll poll;
		if (request.getPortletSession().getAttribute("currentPoll")==null){
			poll = pollDAO.getLatestPoll();
		}else{
			poll = (Poll) request.getPortletSession().getAttribute("currentPoll");
		}
		
		incrementPoll(poll, request.getParameter("selectedPoll"));
		pollForm.setPoll(poll);
		Iterator<PollOption> i = poll.getPollOptions().iterator();
		int totalResults = 0;
		while (i.hasNext()){
			PollOption pOpt = i.next();
			totalResults += pOpt.getCount();
		}
		pollForm.setTotalResults(totalResults);
		List<Poll> lastPolls = pollDAO.getLastXNumPolls(5);
		for (int x=0;x<lastPolls.size();x++){
			int totalCount = 0;
			Iterator<PollOption> it = lastPolls.get(x).getPollOptions().iterator();	
			while(it.hasNext()){
				PollOption pOpt = it.next();
				totalCount +=pOpt.getCount();
			}
			lastPolls.get(x).setTotalAnswers(totalCount);

		}
		pollForm.setArchivePolls(lastPolls);
		log.debug("Beginning ShowPollResults Poll Render.");
		ModelAndView modelAndView = new ModelAndView("resultsFrag");
		modelAndView.addObject("pollForm",pollForm);
		try {
			request.getPreferences().setValue(PREF_LAST_POLL_TAKEN, poll.getDateString());
			request.getPreferences().store();
		} catch (ReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return modelAndView;
	}
	
	
	public PollDAO getPollDAO() {
		return pollDAO;
	}

	public void setPollDAO(PollDAO pollDAO) {
		this.pollDAO = pollDAO;
	}
	
	private void incrementPoll(Poll poll,String optionKey){
//		Poll poll = (Poll)request.getPortletSession().getAttribute("currentPoll");		
		Iterator<PollOption> i = poll.getPollOptions().iterator();
		PollOption p = null;
		while (i.hasNext()){
			p = i.next();
			if (p.getKey().toString().equals(optionKey.trim())){
				pollDAO.incrementPoll(p);
				break;
			}
		}
	}
}
