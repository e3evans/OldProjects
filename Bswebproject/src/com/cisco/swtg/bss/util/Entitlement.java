package com.cisco.swtg.bss.util;

import java.util.Map;

public class Entitlement {
	public void setUserEntitlement(Integer integer,
			Map<String, Object> sessionBean) {

		switch (integer) {
		case 1:
			sessionBean.put("SEARCH_BY_KEY", true);
			sessionBean.put("USER_TYPE", "GUEST");
			// Advanced Search Page
			sessionBean.put("ADV_SEARCH_SEVERITY", false);
			sessionBean.put("ADV_SEARCH_MODIFIED_DATE_RANGE", false);
			// Filter Options
			sessionBean.put("FILTER_OPTIONS_SR_ATTACHED", false);
			sessionBean.put("FILTER_OPTIONS_STATUS_GROUPS", false);
			sessionBean.put("FILTER_OPTIONS_INCLUDE_BUG", false);
			// sessionBean.put("FILTER_OPTIONS_PRODUCT",true);
			// sessionBean.put("FILTER_OPTIONS_SOFTWARE_VERSION",true);
			// sessionBean.put("FILTER_OPTIONS_MODIFIED_DATE_RANGE",true);
			sessionBean.put("FILTER_OPTIONS_TECH_GROUPS", false);
			sessionBean.put("FILTER_OPTIONS_BUG_COMPONENTS", false);
			// Bug Detail
			sessionBean.put("BUG_DETAIL_HEADLINE_ONLY", true);
			// details are coming

			// Search Result
			sessionBean.put("SEARCH_RESULT_EXPANDABLE", false);
			sessionBean.put("SEARCH_RESULT_SORTABLE", false);
			sessionBean.put("FILTER_OPTIONS", false);
			sessionBean.put("EXPORT_TO_EXCEL", false);

			break;
		case 2:
			sessionBean.put("USER_TYPE", "CUSTOMER");
			// Advanced Search Page
			sessionBean.put("ADV_SEARCH_SEVERITY", true);
			sessionBean.put("ADV_SEARCH_MODIFIED_DATE_RANGE", true);
			// Filter Options
			sessionBean.put("FILTER_OPTIONS_SR_ATTACHED", true);
			sessionBean.put("FILTER_OPTIONS_STATUS_GROUPS", true);
			sessionBean.put("FILTER_OPTIONS_INCLUDE_BUG", false);
			// sessionBean.put("FILTER_OPTIONS_PRODUCT",true);
			// sessionBean.put("FILTER_OPTIONS_SOFTWARE_VERSION",true);
			// sessionBean.put("FILTER_OPTIONS_MODIFIED_DATE_RANGE",true);
			sessionBean.put("FILTER_OPTIONS_TECH_GROUPS", true);
			sessionBean.put("FILTER_OPTIONS_BUG_COMPONENTS", false);
			// Bug Detail
			sessionBean.put("BUG_DETAIL_HEADLINE_ONLY", false);
			// Search Result
			sessionBean.put("SEARCH_RESULT_EXPANDABLE", true);
			sessionBean.put("SEARCH_RESULT_SORTABLE", true);
			sessionBean.put("FILTER_OPTIONS", true);
			sessionBean.put("EXPORT_TO_EXCEL", true);
			break;
		case 3:
			sessionBean.put("USER_TYPE", "PARTNER");
			// Advanced Search Page
			sessionBean.put("ADV_SEARCH_SEVERITY", true);
			sessionBean.put("ADV_SEARCH_MODIFIED_DATE_RANGE", true);
			// Filter Options
			sessionBean.put("FILTER_OPTIONS_SR_ATTACHED", true);
			sessionBean.put("FILTER_OPTIONS_STATUS_GROUPS", true);
			sessionBean.put("FILTER_OPTIONS_INCLUDE_BUG", true);
			// sessionBean.put("FILTER_OPTIONS_PRODUCT",true);
			// sessionBean.put("FILTER_OPTIONS_SOFTWARE_VERSION",true);
			// sessionBean.put("FILTER_OPTIONS_MODIFIED_DATE_RANGE",true);
			sessionBean.put("FILTER_OPTIONS_TECH_GROUPS", true);
			sessionBean.put("FILTER_OPTIONS_BUG_COMPONENTS", true);
			// Bug Detail
			sessionBean.put("BUG_DETAIL_HEADLINE_ONLY", false);

			sessionBean.put("SEARCH_RESULT_EXPANDABLE", true);
			sessionBean.put("SEARCH_RESULT_SORTABLE", true);
			sessionBean.put("BUG_DETAIL", true);
			sessionBean.put("FILTER_OPTIONS", true);
			sessionBean.put("EXPORT_TO_EXCEL", true);
			break;
		case 4:
			sessionBean.put("USER_TYPE", "EMPLOYEE");
			// Advanced Search Page
			sessionBean.put("ADV_SEARCH_SEVERITY", true);
			sessionBean.put("ADV_SEARCH_MODIFIED_DATE_RANGE", true);
			// Filter Options
			sessionBean.put("FILTER_OPTIONS_SR_ATTACHED", true);
			sessionBean.put("FILTER_OPTIONS_STATUS_GROUPS", true);
			sessionBean.put("FILTER_OPTIONS_INCLUDE_BUG", true);
			// sessionBean.put("FILTER_OPTIONS_PRODUCT",true);
			// sessionBean.put("FILTER_OPTIONS_SOFTWARE_VERSION",true);
			// sessionBean.put("FILTER_OPTIONS_MODIFIED_DATE_RANGE",true);
			sessionBean.put("FILTER_OPTIONS_TECH_GROUPS", true);
			sessionBean.put("FILTER_OPTIONS_BUG_COMPONENTS", true);
			// Bug Detail
			sessionBean.put("BUG_DETAIL_HEADLINE_ONLY", false);
			// Search Result
			sessionBean.put("SEARCH_RESULT_EXPANDABLE", true);
			sessionBean.put("SEARCH_RESULT_SORTABLE", true);
			sessionBean.put("FILTER_OPTIONS", true);
			sessionBean.put("EXPORT_TO_EXCEL", true);
			// If related bug detail is not there still Related Bug tab is
			// coming
			break;
		case 5:
			sessionBean.put("USER_TYPE", "ADMIN");
			// Advanced Search Page
			sessionBean.put("ADV_SEARCH_SEVERITY", true);
			sessionBean.put("ADV_SEARCH_MODIFIED_DATE_RANGE", true);
			// Filter Options
			sessionBean.put("FILTER_OPTIONS_SR_ATTACHED", true);
			sessionBean.put("FILTER_OPTIONS_STATUS_GROUPS", true);
			sessionBean.put("FILTER_OPTIONS_INCLUDE_BUG", true);
			// sessionBean.put("FILTER_OPTIONS_PRODUCT",true);
			// sessionBean.put("FILTER_OPTIONS_SOFTWARE_VERSION",true);
			// sessionBean.put("FILTER_OPTIONS_MODIFIED_DATE_RANGE",true);
			sessionBean.put("FILTER_OPTIONS_TECH_GROUPS", true);
			sessionBean.put("FILTER_OPTIONS_BUG_COMPONENTS", true);
			// Bug Detail
			sessionBean.put("BUG_DETAIL_HEADLINE_ONLY", false);
			// Search Result
			sessionBean.put("SEARCH_RESULT_EXPANDABLE", true);
			sessionBean.put("SEARCH_RESULT_SORTABLE", true);
			sessionBean.put("FILTER_OPTIONS", true);
			sessionBean.put("EXPORT_TO_EXCEL", true);
			break;

		default:
			sessionBean.put("SEARCH_BY_KEY", true);
			sessionBean.put("USER_TYPE", "GUEST");
			// Advanced Search Page
			sessionBean.put("ADV_SEARCH_SEVERITY", false);
			sessionBean.put("ADV_SEARCH_MODIFIED_DATE_RANGE", false);
			// Filter Options
			sessionBean.put("FILTER_OPTIONS_SR_ATTACHED", false);
			sessionBean.put("FILTER_OPTIONS_STATUS_GROUPS", false);
			sessionBean.put("FILTER_OPTIONS_INCLUDE_BUG", false);
			// sessionBean.put("FILTER_OPTIONS_PRODUCT",true);
			// sessionBean.put("FILTER_OPTIONS_SOFTWARE_VERSION",true);
			// sessionBean.put("FILTER_OPTIONS_MODIFIED_DATE_RANGE",true);
			sessionBean.put("FILTER_OPTIONS_TECH_GROUPS", false);
			sessionBean.put("FILTER_OPTIONS_BUG_COMPONENTS", false);
			// Bug Detail
			sessionBean.put("BUG_DETAIL_HEADLINE_ONLY", true);
			// details are coming

			// Search Result
			sessionBean.put("SEARCH_RESULT_EXPANDABLE", false);
			sessionBean.put("SEARCH_RESULT_SORTABLE", false);
			sessionBean.put("FILTER_OPTIONS", false);
			sessionBean.put("EXPORT_TO_EXCEL", false);
			break;
		}
	}
}
