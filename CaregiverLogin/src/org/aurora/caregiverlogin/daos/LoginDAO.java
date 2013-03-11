package org.aurora.caregiverlogin.daos;

import org.aurora.caregiverlogin.beans.User;

public interface LoginDAO {
	public User findUserDetails(String loginid);
}