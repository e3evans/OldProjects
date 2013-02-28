package org.aurora.sitesapps.utils;

import java.util.Comparator;

import org.aurora.sitesapps.beans.Application;

public class AppComparator implements Comparator<Application> {

	@Override
	public int compare(Application app1, Application app2) {
		return app1.getAppName().toUpperCase()
				.compareTo(app2.getAppName().toUpperCase());
	}
}