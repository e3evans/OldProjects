package com.aurora.controllers;

import java.io.IOException;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.aurora.org.googlesearch.forms.EditForm;

@Controller
@RequestMapping("edit_defaults")
public class EditController {
	
	public static String PREF_SEARCH_ENV = "com.aurora.org.search_environment";

	@RequestMapping
	public ModelAndView defaultEditView(RenderRequest request,RenderResponse response, @SuppressWarnings("rawtypes") Map Model,@ModelAttribute("editForm")EditForm editForm){
		
		if (editForm==null)editForm = new EditForm();
		editForm.setSearch_environment(request.getPreferences().getValue(PREF_SEARCH_ENV, "Not Set"));
		
		return new ModelAndView("edit","editForm",editForm);
	}
	
	@ActionMapping("doSavePrefs")
	public void savePrefs(ActionRequest request, ActionResponse response,@ModelAttribute("editForm")EditForm editForm){
		System.out.println(editForm.getSearch_environment());
		if (null!=editForm.getSearch_environment()){
			try {
				request.getPreferences().setValue(PREF_SEARCH_ENV, editForm.getSearch_environment());
				request.getPreferences().store();
			} catch (ValidatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReadOnlyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
