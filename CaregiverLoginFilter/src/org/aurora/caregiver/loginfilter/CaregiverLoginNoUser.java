package org.aurora.caregiver.loginfilter;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.portal.auth.FilterChainContext;
import com.ibm.portal.auth.ImplicitLoginFilter;
import com.ibm.portal.auth.ImplicitLoginFilterChain;
import com.ibm.portal.auth.exceptions.AuthenticationException;
import com.ibm.portal.auth.exceptions.AuthenticationFailedException;
import com.ibm.portal.auth.exceptions.SessionTimeOutException;
import com.ibm.portal.auth.exceptions.SystemLoginException;
import com.ibm.portal.security.SecurityFilterConfig;
import com.ibm.portal.security.exceptions.SecurityFilterInitException;
import com.ibm.websphere.security.WSSecurityException;

public class CaregiverLoginNoUser implements ImplicitLoginFilter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(SecurityFilterConfig arg0)
			throws SecurityFilterInitException {
		// TODO Auto-generated method stub

	}

	@Override
	public void login(HttpServletRequest request, HttpServletResponse response,
			FilterChainContext chainContext, String arg3, ImplicitLoginFilterChain chain)
			throws LoginException, WSSecurityException,
			SessionTimeOutException, AuthenticationFailedException,
			AuthenticationException, SystemLoginException,
			com.ibm.portal.auth.exceptions.LoginException {
		// TODO Auto-generated method stub
		String host=request.getRemoteHost();
		System.out.println("HOST:  "+host);
		if (host.equals("10.46.9.19")){
			System.out.println("EXECUTING Implicity Login!!");
			chain.login(request, response, chainContext, "waslocal");
		}
		
		

	}

}
