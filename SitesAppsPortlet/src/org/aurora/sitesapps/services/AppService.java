package org.aurora.sitesapps.services;

import java.util.List;

import org.aurora.sitesapps.beans.AppCategory;
import org.aurora.sitesapps.beans.Application;

/**
 * @author
 */
public interface AppService {
	public List<Application> getAppsByCategory(String categoryId);

	public List<Application> getPopularApps(String categoryId);

	public List<AppCategory> getAppCategories();

	public List<Application> getIConnectApps();
}