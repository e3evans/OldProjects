package com.aurora.hibernate.login.dao;

import com.aurora.hibernate.login.beans.User;

public interface LoginDAO {
	public User findUserDetails(String loginid);
}
