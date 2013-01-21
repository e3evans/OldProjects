package com.aurora.controllers;

import java.io.IOException;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.aurora.org.caregiverlogin.forms.LoginEditForm;


@Controller
@RequestMapping("edit_defaults")
public class LoginEditController {
	
	@RequestMapping
	public ModelAndView defaultEditView(RenderRequest request, RenderResponse response, @SuppressWarnings("rawtypes") Map Model,@ModelAttribute("editForm")LoginEditForm editForm){
		
		if (editForm==null)editForm=new LoginEditForm();
		PortletPreferences prefs = request.getPreferences();
		editForm.setWcm_menuComponent(prefs.getValue(LoginViewController.PREF_WCM_COMPONENT, ""));
		editForm.setWcm_path(prefs.getValue(LoginViewController.PREF_WCM_PATH, ""));
		editForm.setWcm_library(prefs.getValue(LoginViewController.PRED_WCM_LIB, ""));
	
		return new ModelAndView("loginEdit","editForm",editForm);
	}
	
	@ActionMapping("doSavePrefs")
	public void savePrefs(ActionRequest request, ActionResponse response,@ModelAttribute("editForm")LoginEditForm editForm){
		PortletPreferences prefs = request.getPreferences();
		Map<String, String> formPrefs = editForm.getPreferences();
		try {
			for (Map.Entry<String, String>entry:formPrefs.entrySet()){
				prefs.setValue(entry.getKey(), entry.getValue());
			}
			prefs.store();
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
		
		
	}

}
