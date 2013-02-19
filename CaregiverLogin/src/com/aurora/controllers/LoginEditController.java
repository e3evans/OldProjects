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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.aurora.org.caregiverlogin.forms.LoginEditForm;

@Controller
@RequestMapping("edit_defaults")
public class LoginEditController {

	protected static final Logger log = Logger
			.getLogger(LoginEditController.class.getSimpleName());

	@SuppressWarnings("rawtypes")
	@RequestMapping
	public ModelAndView defaultEditView(RenderRequest request,
			RenderResponse response, Map Model,
			@ModelAttribute("editForm") LoginEditForm editForm) {
		if (editForm == null)
			editForm = new LoginEditForm();
		PortletPreferences prefs = request.getPreferences();
		editForm.setWcm_menuComponent(prefs.getValue(
				LoginViewController.PREF_WCM_COMPONENT, ""));
		editForm.setWcm_path(prefs.getValue(LoginViewController.PREF_WCM_PATH,
				""));
		editForm.setWcm_library(prefs.getValue(
				LoginViewController.PRED_WCM_LIB, ""));
		return new ModelAndView("loginEdit", "editForm", editForm);
	}

	@ActionMapping("doSavePrefs")
	public void savePrefs(ActionRequest request, ActionResponse response,
			@ModelAttribute("editForm") LoginEditForm editForm) {
		PortletPreferences prefs = request.getPreferences();
		Map<String, String> formPrefs = editForm.getPreferences();
		try {
			for (Map.Entry<String, String> entry : formPrefs.entrySet()) {
				prefs.setValue(entry.getKey(), entry.getValue());
			}
			prefs.store();
		} catch (ReadOnlyException e) {
			log.error("ReadOnlyException in savePrefs", e);
		} catch (ValidatorException e) {
			log.error("ValidatorException in savePrefs", e);
		} catch (IOException e) {
			log.error("IOException in savePrefs", e);
		}
	}
}