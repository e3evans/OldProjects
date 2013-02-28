package org.aurora.sitesapps.services;

import java.util.List;

import org.aurora.sitesapps.beans.AppCategory;
import org.aurora.sitesapps.beans.Application;
import org.aurora.sitesapps.exceptions.AppException;

/**
 * @author
 */
public interface AppService {
	public List<Application> getAppsByCategory(String categoryId)
			throws AppException;

	public List<Application> getPopularApps(String categoryId)
			throws AppException;

	public List<AppCategory> getAppCategories() throws AppException;

	public List<Application> getIConnectApps() throws AppException;
}