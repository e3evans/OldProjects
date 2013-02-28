package org.aurora.quicklinks.services;

import java.util.List;

import org.aurora.quicklinks.beans.Application;
import org.aurora.quicklinks.beans.UserApplication;

/**
 * @author
 */
public interface AppService {

	public List<UserApplication> listUserAppByUserId(String userid);

	public List<Application> listApplication();

	// public UserApplication retrieveUserApp(String userid, String appId,
	// String seqNo);

	public UserApplication createUserApp(String userid, String appId,
			String seqNo);

	// public List<Application> retrieveAppMenuAutoList(String paramString);

	public void updateUserApp(UserApplication userApp, String userId);

	public List<UserApplication> listAllUserAppByUserId(String userid);

}