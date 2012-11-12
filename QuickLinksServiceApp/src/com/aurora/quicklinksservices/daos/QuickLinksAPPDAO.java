package com.aurora.quicklinksservices.daos;

import java.util.List;

public interface QuickLinksAPPDAO {

	public List findAvailAppListByRole(String roleCd);
}
