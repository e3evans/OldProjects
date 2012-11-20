package com.aurora.quicklinksservices.util;

import com.aurora.quicklinksservices.daos.QuickLinksAPPDAOImpl;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		QuickLinksAPPDAOImpl qad = new QuickLinksAPPDAOImpl();
		qad.findAvailAppListByRole("test");
	}

}
