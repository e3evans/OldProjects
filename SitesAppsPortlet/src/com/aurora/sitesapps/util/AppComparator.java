package com.aurora.sitesapps.util;

import java.util.Comparator;

import com.aurora.sitesapps.beans.Application;

public class AppComparator implements Comparator<Application>{

	@Override
	public int compare(Application app1, Application app2) {
		// TODO Auto-generated method stub
		return app1.getAppName().toUpperCase().compareTo(app2.getAppName().toUpperCase());
	}

}
