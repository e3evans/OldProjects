package com.aurora.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.aurora.org.caregiverlogin.forms.LoginForm;
import com.ibm.portal.portlet.service.PortletServiceHome;
import com.ibm.portal.portlet.service.PortletServiceUnavailableException;
import com.ibm.portal.portlet.service.login.LoginHome;
import com.ibm.portal.portlet.service.login.LoginService;




@Controller
@RequestMapping("VIEW")
public class LoginViewController {
	
	public static String ENV_USERPASSWORD = "com.aurora.seedlist.userpassword";
	public static String PREF_WCM_PATH = "wcm.path";
	public static String PREF_WCM_COMPONENT ="wcm.menuComponent";
	public static String PRED_WCM_LIB = "wcm.library";
	public boolean BAD_LOGIN = false;
	
	LoginHome loginHome;
	@PostConstruct
	public void init(){
		try {
			InitialContext ctx = new InitialContext();
			PortletServiceHome psh = (PortletServiceHome)ctx.lookup(LoginHome.JNDI_NAME);
			loginHome = (LoginHome)psh.getPortletService(LoginHome.class);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortletServiceUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@RequestMapping
	public ModelAndView defaultView (RenderRequest request, RenderResponse responses, @SuppressWarnings("rawtypes") Map model,@ModelAttribute("loginForm")LoginForm form) throws UnsupportedEncodingException{
	   
		if (form==null)form = new LoginForm();
		form.setBadLogin(BAD_LOGIN);
		return new ModelAndView("loginView","loginForm",form);
	}
	
	@ResourceMapping(value="renderSecureImage")
	public void renderSecureImage(ResourceRequest request, ResourceResponse response) throws IOException{
		
		URL url = new URL("http://porporit1.ahc.root.loc:10039/cgc/wcm/myconnect/5bfc3ea5-f86c-474f-8db3-c4cee15c76d7/live-well-promo.png?MOD=AJPERES&amp;CACHEID=5bfc3ea5-f86c-474f-8db3-c4cee15c76d7");
//		URL url = new URL("http://www.google.com");
		URLConnection uc = url.openConnection();
		uc.setRequestProperty("Authorization", getB64Auth("n26760", "3N335ponderosa"));
		System.out.println("TYPE:  "+uc.getContentType());
		System.out.println("LENGTH:  "+uc.getContentLength());
		InputStream in = uc.getInputStream();
		
//		response.setContentType("image/gif");
		
		ServletOutputStream out = (ServletOutputStream) response.getPortletOutputStream();
		
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = in.read(buffer))!=-1){
		
			out.write(buffer,0,bytesRead);
		}
		
		
	}
	
	private String getB64Auth(String login, String password){
		String source = login+":"+password;
		String ret = "Basic " +Base64.encodeBase64(source.getBytes()).toString();
		return ret;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ActionMapping("doLogin")
	public void doLogin(ActionRequest request, ActionResponse response,@ModelAttribute("loginForm")LoginForm loginForm) throws IOException{
		LoginService loginService = loginHome.getLoginService(request, response);
		Map contextMap = new HashMap();
        contextMap.put(LoginService.DO_RESUME_SESSION_KEY, new Boolean(false));

		BAD_LOGIN=false;
		try {
			loginService.login(loginForm.getUserName(), loginForm.getPassword().toCharArray(), contextMap, null);
		} catch (Exception e){
			BAD_LOGIN=true;
		}finally{
			if (!BAD_LOGIN)response.sendRedirect("/cgc/myportal/connect/home");
		}
		
	}

}
