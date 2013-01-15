package org.aurora.caregiver.loginfilter;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.portal.auth.ExplicitLoginFilter;
import com.ibm.portal.auth.ExplicitLoginFilterChain;
import com.ibm.portal.auth.FilterChainContext;
import com.ibm.portal.auth.exceptions.AuthenticationException;
import com.ibm.portal.auth.exceptions.AuthenticationFailedException;
import com.ibm.portal.auth.exceptions.PasswordInvalidException;
import com.ibm.portal.auth.exceptions.SystemLoginException;
import com.ibm.portal.auth.exceptions.UserIDInvalidException;
import com.ibm.portal.security.SecurityFilterConfig;
import com.ibm.portal.security.exceptions.SecurityFilterInitException;
import com.ibm.websphere.security.WSSecurityException;


public class CaregiverLoginSpecificUser implements ExplicitLoginFilter{

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
			String userID, char[] password, FilterChainContext portalContext, Subject subj,
			String realm, ExplicitLoginFilterChain chain) throws LoginException,
			WSSecurityException, PasswordInvalidException,
			UserIDInvalidException, AuthenticationFailedException,
			AuthenticationException, SystemLoginException,
			com.ibm.portal.auth.exceptions.LoginException {
		
		String host=request.getRemoteHost();
		System.out.println("HOST:  "+request.getRemoteHost());
		String targetURL= portalContext.getRedirectURL();
		System.out.println("TARGET URL:  "+targetURL);
		if (host.equals("192.168.1.87")){
			chain.login(request, response, "waslocal","waslocal".toCharArray() , portalContext, subj, realm);
		}
		
		
		
		// TODO Auto-generated method stub
		
	}

	
}
