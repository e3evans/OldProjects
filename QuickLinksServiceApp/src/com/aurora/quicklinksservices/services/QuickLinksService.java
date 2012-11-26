package com.aurora.quicklinksservices.services;

import java.util.List;

public interface QuickLinksService {
	
	public List retrieveAvailAppListByRole(String roleCd);
	public List retrieveUserDetails(String userid);
	public List findUserAppsByUser(Long userid);

}
