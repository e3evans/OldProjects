package com.aurora.contorllers;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.aurora.org.googlesearchpoc.forms.SearchForm;

@Controller
@RequestMapping("VIEW")
public class ViewController {

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView defaultView (RenderRequest request, RenderResponse responses, @SuppressWarnings("rawtypes") Map model,@ModelAttribute("searchForm")SearchForm form){
		
		if (form == null)form = new SearchForm();

		model.put("searchForm", form);
		return new ModelAndView("view","searchForm",form);
	}
	
	@ActionMapping(params = "action=doSearch")
	public void doSearch(ActionRequest request, ActionResponse response,@ModelAttribute("searchForm")SearchForm form){
		response.setRenderParameter("searchString", form.getSearchString());
		System.out.println(form.getSearchString());
		System.out.println("ACTION");
	}
	
}
