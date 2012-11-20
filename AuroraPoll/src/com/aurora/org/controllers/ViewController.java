package com.aurora.org.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.aurora.hibernate.poll.beans.Poll;
import com.aurora.hibernate.poll.beans.PollOption;
import com.aurora.hibernate.poll.dao.PollDAO;
import com.aurora.org.form.PollForm;




@Controller
@RequestMapping("VIEW")
public class ViewController {
	
	@Autowired
	private PollDAO pollDAO;
	
	public static final String JSP_VIEW = "view";
	
	private static Log log = LogFactory.getLog(ViewController.class);
	
	@RequestMapping(params="action=submitPoll")
	public void action(ActionRequest request,ActionResponse response,PollForm pollForm){
		
		Poll poll = (Poll)request.getPortletSession().getAttribute("currentPoll");		
		Iterator<PollOption> i = poll.getPollOptions().iterator();
		PollOption p = null;
		while (i.hasNext()){
			p = i.next();
			if (p.getKey().toString().equals(pollForm.getPollSelection().trim())){
				pollDAO.incrementPoll(p);
				break;
			}
		}
	
		
		
		response.setRenderParameter("action", "displayResults");
	}
	
	@RequestMapping
	public ModelAndView renderPoll(RenderRequest request, RenderResponse response){
		log.debug("Beginning Default Poll Render.");
		ModelAndView modelAndView = new ModelAndView("view");
		Poll poll = pollDAO.getLatestPoll();
		PollForm pollForm = new PollForm(poll);
		request.getPortletSession().setAttribute("currentPoll", poll);
		modelAndView.addObject("pollForm",pollForm);
		return modelAndView;
	}
	
	
	
	@RequestMapping(params = "action=displayResults")
	public ModelAndView renderPollResults(RenderRequest request, RenderResponse response,@ModelAttribute("pollForm")PollForm pollForm){
		Poll poll = pollDAO.getLatestPoll();
		pollForm.setPoll(poll);
//		List<PollOption> pollOptions = poll.getPollOptions();
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
	

	public PollDAO getPollDAO() {
		return pollDAO;
	}

	public void setPollDAO(PollDAO pollDAO) {
		this.pollDAO = pollDAO;
	}
	
	
}
