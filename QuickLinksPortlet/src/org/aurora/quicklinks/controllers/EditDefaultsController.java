package org.aurora.quicklinks.controllers;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.aurora.quicklinks.beans.FeaturedAppForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

@Controller
@RequestMapping("edit_defaults")
public class EditDefaultsController {

	protected final Logger logger = Logger
			.getLogger(EditDefaultsController.class);

	@RequestMapping
	public String defaultEditView(RenderRequest request,
			RenderResponse response,
			@ModelAttribute("FeaturedAppForm") FeaturedAppForm featuredAppForm,
			Model model) {
		PortletPreferences pf = request.getPreferences();
		if (pf != null) {
			featuredAppForm.setAppCategory(pf
					.getValue("appCategory", "Not Set"));
			featuredAppForm.setAppDesc(pf.getValue("appDesc", "Not Set"));
			featuredAppForm.setAppId(pf.getValue("appID", "Not Set"));
			featuredAppForm.setAppName(pf.getValue("appName", "Not Set"));
			featuredAppForm.setSeqNo(pf.getValue("seqNO", "Not Set"));
		}
		model.addAttribute("FeaturedAppForm", featuredAppForm);
		return "featuredapp";
	}

	@ActionMapping("doSavePrefs")
	public void savePrefs(ActionRequest request, ActionResponse response,
			@ModelAttribute("FeaturedAppForm") FeaturedAppForm featuredAppForm,
			Model model) {
		try {
			PortletPreferences pf = request.getPreferences();
			pf.setValue("appName", featuredAppForm.getAppName());
			pf.setValue("appDesc", featuredAppForm.getAppDesc());
			pf.setValue("appID", featuredAppForm.getAppId());
			pf.setValue("seqNO", featuredAppForm.getSeqNo());
			pf.setValue("appCategory", featuredAppForm.getAppCategory());
			pf.store();
		} catch (IOException e) {
			logger.error("Exception in savePrefs", e);
		} catch (PortletException e) {
			logger.error("Exception in savePrefs", e);
		}
		model.addAttribute("Success", "Succesfully saved the preferences");
	}
}