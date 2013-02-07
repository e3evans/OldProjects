package com.aurora.sitesapps.services;

import java.util.List;


import com.aurora.sitesapps.exception.AppException;
import com.aurora.sitesapps.beans.AppCategory;
import com.aurora.sitesapps.beans.Application;

/**
 * 
 * @author 
 *
 */
public interface AppService {
	public List<Application> listApplication(String categoryId) throws AppException;
	public List<Application> listPopularApplication(String categoryId) throws AppException;
	public List<AppCategory> listAppCategory() throws AppException;
	public List<Application> listApplication() throws AppException;

}
