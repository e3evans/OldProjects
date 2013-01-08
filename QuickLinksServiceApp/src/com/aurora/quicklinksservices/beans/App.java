
package com.aurora.quicklinksservices.beans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// Referenced classes of package org.aurora.portalCommon.beans:
//            AppKey
@Entity
@Table(name = "S05DTDB.TPT2B_APPLICATION")
public class App
    implements Serializable
{

   /**
	 * 
	 */
	private static final long serialVersionUID = 1183612050348742148L;
	/*
	 * public static final String ACTIVE = "A";
    public static final String INACTIVE = "I";
    public static final String BOTH = "B";
    public static final String NO_NON_EMPLOYEES = "N";
    public static final String ONLY_INTERNAL = "I";
    public static final String ONLY_EXTERNAL = "E";
    public static final String ONLY_SECURED = "S";
    public static final String ONLY_UNSECURED = "U";
    public static final String ONLY_RESTRICTED = "R";
    public static final String ONLY_UNRESTRICTED = "U";
    public static final String ACCESS_DEFAULT = "D";
    public static final String ACCESS_MENU = "M";
    public static final String ACCESS_RESTRICTED = "R";
    public static final String ACCESS_NOACCESS = "N";
    public static final String ACCESS_EXECUTE = "E";*/
    private AppKey appKey;
    private String appName;
    private String appDesc;
    private String comments;
    private String appURL;
    private String appURLproxy;
    private String homeURL;
    private String homeURLproxy;
    private String helpURL;
    private String helpURLproxy;
    private String searchURL;
    private String searchURLproxy;
    private String feedbackURL;
    private String feedbackURLproxy;
    private String feedbackEmail;
    private String adminURL;
    private String adminURLproxy;
    private String regstrURL;
    private String regstrURLproxy;
    private String targetFrame;
    private String timeoutMinutes;
    private String activeCd;
    private String accessType;
    private String explodeAccDet;
    private String defAppBar;
    private String defAppBarColor;
    private String dispZeroApp;
    private String dispUserFields;
    private String digitalAgree;
    private String nonEmpAccess;
    private String intExtAccessInd;
    private String httpTypeAccessInd;
    private String loggedInAccess;
    private String notLoggedInAccess;
    private String allowDelegate;
    private String championEmpNo;
    private String authorEmpNo;
    private String supportEmpNo;
    private String supportComment;
    private Date appAddDate;
    private String trackUsage;
    private String autoRegister;
    private Timestamp alertTmsp;
    private String shortName;
    private String displayTopBar;
    private String displayLeftNav;
    private String windowWidth;
    private String windowHeight;
    

    public App()
    {
    }
    
    @EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "appId", column = @Column(name = "PT2B_APPID", nullable = false)),
			@AttributeOverride(name = "seqNo", column = @Column(name = "PT2B_SEQ_NO", nullable = false)) })


    public AppKey getAppKey()
    {
        return appKey;
    }

    public void setAppKey(AppKey appKey)
    {
        this.appKey = appKey;
    }
    
   /* public String getAppId()
    {
        if(getAppKey() != null)
        {
            return getAppKey().getAppId();
        } else
        {
            return null;
        }
    }

    public Integer getSeqNo()
    {
        if(getAppKey() != null)
        {
            return getAppKey().getSeqNo();
        } else
        {
            return null;
        }
    }
*/   
    @Column(name = "PT2B_APP_NAME", nullable = false, length = 19, insertable = true, updatable = true)
    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }
    
   /* public String getAdjName()
    {
        int cur = 0;
        String adjName;
        for(adjName = appName.trim(); adjName.indexOf("'", cur) > -1; cur++)
        {
            cur = adjName.indexOf("'", cur);
            if(adjName.length() > cur)
            {
                adjName = (new StringBuilder()).append(adjName.substring(0, cur)).append("\\'").append(adjName.substring(cur + 1)).toString();
            } else
            {
                adjName = (new StringBuilder()).append(adjName.substring(0, cur)).append("\\'").toString();
            }
            cur++;
        }

        return adjName;
    }*/

  /*  public String getAppNameEncoded()
    {
        String encoded = appName;
        try
        {
            encoded = URLEncoder.encode(appName, "UTF-8");
        }
        catch(UnsupportedEncodingException e)
        {
            System.out.println(e);
        }
        return encoded;*/
    
    @Column(name = "PT2B_APP_DESC",  updatable = true)
    public String getAppDesc()
    {
        return appDesc;
    }

    public void setAppDesc(String appDesc)
    {
        this.appDesc = appDesc;
    }
    @Column(name = "PT2B_APP_COMMENTS",  updatable = true)
    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }
    @Column(name = "PT2B_APP_URL",  updatable = true)
    public String getAppURL()
    {
        return appURL;
    }

    public void setAppURL(String appURL)
    {
        this.appURL = appURL;
    }
    @Column(name = "PT2B_APP_URL_RP",  updatable = true)
    public String getAppURLproxy()
    {
        return appURLproxy;
    }

    public void setAppURLproxy(String appURLproxy)
    {
        this.appURLproxy = appURLproxy;
    }
    @Column(name = "PT2B_DFT_HM_URL",  updatable = true)
    public String getHomeURL()
    {
        return homeURL;
    }

    public void setHomeURL(String homeURL)
    {
        this.homeURL = homeURL;
    }
    @Column(name = "PT2B_DFT_HM_URL_RP",  updatable = true)
    public String getHomeURLproxy()
    {
        return homeURLproxy;
    }

    public void setHomeURLproxy(String homeURLproxy)
    {
        this.homeURLproxy = homeURLproxy;
    }
    @Column(name = "PT2B_HELP_URL",  updatable = true)
    public String getHelpURL()
    {
        return helpURL;
    }

    public void setHelpURL(String helpURL)
    {
        this.helpURL = helpURL;
    }
    @Column(name = "PT2B_HELP_URL_RP",  updatable = true)
    public String getHelpURLproxy()
    {
        return helpURLproxy;
    }

    public void setHelpURLproxy(String helpURLproxy)
    {
        this.helpURLproxy = helpURLproxy;
    }
    @Column(name = "PT2B_SRCH_URL",  updatable = true)
    public String getSearchURL()
    {
        return searchURL;
    }

    public void setSearchURL(String searchURL)
    {
        this.searchURL = searchURL;
    }
    @Column(name = "PT2B_SRCH_URL_RP",  updatable = true)
    public String getSearchURLproxy()
    {
        return searchURLproxy;
    }

    public void setSearchURLproxy(String searchURLproxy)
    {
        this.searchURLproxy = searchURLproxy;
    }
    @Column(name = "PT2B_FDBK_URL",  updatable = true)
    public String getFeedbackURL()
    {
        return feedbackURL;
    }

    public void setFeedbackURL(String feedbackURL)
    {
        this.feedbackURL = feedbackURL;
    }
    @Column(name = "PT2B_FDBK_URL_RP",  updatable = true)
    public String getFeedbackURLproxy()
    {
        return feedbackURLproxy;
    }

    public void setFeedbackURLproxy(String feedbackURLproxy)
    {
        this.feedbackURLproxy = feedbackURLproxy;
    }
    @Column(name = "PT2B_FDBK_EMAIL",  updatable = true)
    public String getFeedbackEmail()
    {
        return feedbackEmail;
    }

    public void setFeedbackEmail(String feedbackEmail)
    {
        this.feedbackEmail = feedbackEmail;
    }
    @Column(name = "PT2B_ADMIN_URL",  updatable = true)
    public String getAdminURL()
    {
        return adminURL;
    }

    public void setAdminURL(String adminURL)
    {
        this.adminURL = adminURL;
    }
    @Column(name = "PT2B_ADMIN_URL_RP",  updatable = true)
    public String getAdminURLproxy()
    {
        return adminURLproxy;
    }

    public void setAdminURLproxy(String adminURLproxy)
    {
        this.adminURLproxy = adminURLproxy;
    }
    @Column(name = "PT2B_RGSTR_URL",  updatable = true)
    public String getRegstrURL()
    {
        return regstrURL;
    }

    public void setRegstrURL(String regstrURL)
    {
        this.regstrURL = regstrURL;
    }
    @Column(name = "PT2B_RGSTR_URL_RP",  updatable = true)
    public String getRegstrURLproxy()
    {
        return regstrURLproxy;
    }

    public void setRegstrURLproxy(String regstrURLproxy)
    {
        this.regstrURLproxy = regstrURLproxy;
    }
    @Column(name = "PT2B_TARGET_FRAME",  updatable = true)
    public String getTargetFrame()
    {
        return targetFrame;
    }

    public void setTargetFrame(String targetFrame)
    {
        this.targetFrame = targetFrame;
    }
    @Column(name = "PT2B_TIMEOUT_MINS",  updatable = true)
    public String getTimeoutMinutes()
    {
        return timeoutMinutes;
    }

    public void setTimeoutMinutes(String timeoutMinutes)
    {
        this.timeoutMinutes = timeoutMinutes;
    }
    @Column(name = "PT2B_ACTIVE_CD",  updatable = true)
    public String getActiveCd()
    {
        return activeCd;
    }

    public void setActiveCd(String activeCd)
    {
        this.activeCd = activeCd;
    }

   /* public boolean isActive()
    {
        return activeCd.equalsIgnoreCase("A");
    }*/
    @Column(name = "PT2B_ACCESS_TYPE",  updatable = true)
    public String getAccessType()
    {
        return accessType;
    }

    public void setAccessType(String accessType)
    {
        this.accessType = accessType;
    }
    @Column(name = "PT2B_EXPLODE_ACCESS",  updatable = true)
    public String getExplodeAccDet()
    {
        return explodeAccDet;
    }

    public void setExplodeAccDet(String explodeAccDet)
    {
        this.explodeAccDet = explodeAccDet;
    }
    @Column(name = "PT2B_DFLT_APP_BAR",  updatable = true)
    public String getDefAppBar()
    {
        return defAppBar;
    }

    public void setDefAppBar(String defAppBar)
    {
        this.defAppBar = defAppBar;
    }

    public boolean displayAppBar()
    {
        return !defAppBar.equals("N");
    }
    @Column(name = "PT2B_APP_BAR_COLOR",  updatable = true)
    public String getDefAppBarColor()
    {
        return defAppBarColor;
    }

    public void setDefAppBarColor(String defAppBarColor)
    {
        this.defAppBarColor = defAppBarColor;
    }
    @Column(name = "PT2B_DIS_ZERO_APPL",  updatable = true)
    public String getDispZeroApp()
    {
        return dispZeroApp;
    }

    public void setDispZeroApp(String dispZeroApp)
    {
        this.dispZeroApp = dispZeroApp;
    }
    @Column(name = "PT2B_DIS_USER_FLDS",  updatable = true)
    public String getDispUserFields()
    {
        return dispUserFields;
    }

    public void setDispUserFields(String dispUserFields)
    {
        this.dispUserFields = dispUserFields;
    }
    @Column(name = "PT2B_DIS_DGTL_AGREE",  updatable = true)
    public String getDigitalAgree()
    {
        return digitalAgree;
    }

    public void setDigitalAgree(String digitalAgree)
    {
        this.digitalAgree = digitalAgree;
    }
    @Column(name = "PT2B_NON_EMP_ACCESS",  updatable = true)
    public String getNonEmpAccess()
    {
        return nonEmpAccess;
    }

    public void setNonEmpAccess(String nonEmpAccess)
    {
        this.nonEmpAccess = nonEmpAccess;
    }
    @Column(name = "PT2B_INT_EXT_ACCESS",  updatable = true)
    public String getIntExtAccessInd()
    {
        return intExtAccessInd;
    }

    public void setIntExtAccessInd(String intExtAccessInd)
    {
        this.intExtAccessInd = intExtAccessInd;
    }
    @Column(name = "PT2B_HTTP_TYPE_ACC",  updatable = true)
    public String getHttpTypeAccessInd()
    {
        return httpTypeAccessInd;
    }

    public void setHttpTypeAccessInd(String httpTypeAccessInd)
    {
        this.httpTypeAccessInd = httpTypeAccessInd;
    }
    @Column(name = "PT2B_LOGIN_ACC",  updatable = true)
    public String getLoggedInAccess()
    {
        return loggedInAccess;
    }

    public void setLoggedInAccess(String loggedInAccess)
    {
        this.loggedInAccess = loggedInAccess;
    }
    @Column(name = "PT2B_NO_LOGIN_ACC",  updatable = true)
    public String getNotLoggedInAccess()
    {
        return notLoggedInAccess;
    }

    public void setNotLoggedInAccess(String notLoggedInAccess)
    {
        this.notLoggedInAccess = notLoggedInAccess;
    }
    @Column(name = "PT2B_ALLOW_DELEGATE",  updatable = true)
    public String getAllowDelegate()
    {
        return allowDelegate;
    }

    public void setAllowDelegate(String allowDelegate)
    {
        this.allowDelegate = allowDelegate;
    }
    @Column(name = "PT2B_CHAMP_EMP_NO",  updatable = true)
    public String getChampionEmpNo()
    {
        return championEmpNo;
    }

    public void setChampionEmpNo(String championEmpNo)
    {
        this.championEmpNo = championEmpNo;
    }
    @Column(name = "PT2B_AUTHOR_EMP_NO",  updatable = true)
    public String getAuthorEmpNo()
    {
        return authorEmpNo;
    }

    public void setAuthorEmpNo(String authorEmpNo)
    {
        this.authorEmpNo = authorEmpNo;
    }
    @Column(name = "PT2B_SPPRT_EMP_NO",  updatable = true)
    public String getSupportEmpNo()
    {
        return supportEmpNo;
    }

    public void setSupportEmpNo(String supportEmpNo)
    {
        this.supportEmpNo = supportEmpNo;
    }
    @Column(name = "PT2B_SPPRT_COMMENT",  updatable = true)
    public String getSupportComment()
    {
        return supportComment;
    }

    public void setSupportComment(String supportComment)
    {
        this.supportComment = supportComment;
    }
    @Column(name = "PT2B_APPL_ADD_DT",  updatable = true)
    public Date getAppAddDate()
    {
        return appAddDate;
    }

    public void setAppAddDate(Date appAddDate)
    {
        this.appAddDate = appAddDate;
    }
    @Column(name = "PT2B_TRACK_USAGE",  updatable = true)
    public String getTrackUsage()
    {
        return trackUsage;
    }

    public void setTrackUsage(String trackUsage)
    {
        this.trackUsage = trackUsage;
    }
    @Column(name = "PT2B_AUTO_REG",  updatable = true)
    public String getAutoRegister()
    {
        return autoRegister;
    }

    public void setAutoRegister(String autoRegister)
    {
        this.autoRegister = autoRegister;
    }
   // @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PT2B_ALERT_TMSP",  updatable = true)
    public Timestamp getAlertTmsp()
    {
        return alertTmsp;
    }

    public void setAlertTmsp(Timestamp alertTmsp)
    {
        this.alertTmsp = alertTmsp;
    }
    @Column(name = "PT2B_SHORT_NAME",  updatable = true)
    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    @Column(name = "PT2B_DISP_TOPBAR",  updatable = true)
    public String getDisplayTopBar()
    {
        return displayTopBar;
    }

    public void setDisplayTopBar(String displayTopBar)
    {
        this.displayTopBar = displayTopBar;
    }
    
    public boolean displayTopBar()
    {
        return !displayTopBar.equals("N");
    }
    @Column(name = "PT2B_DISP_LEFTNAV",  updatable = true)
    public String getDisplayLeftNav()
    {
        return displayLeftNav;
    }

    public void setDisplayLeftNav(String displayLeftNav)
    {
        this.displayLeftNav = displayLeftNav;
    }

    public boolean displayLeftNav()
    {
        return !displayLeftNav.equals("N");
    }
    @Column(name = "PT2B_WINDOW_WIDTH",  updatable = true)
    public String getWindowWidth()
    {
        return windowWidth;
    }

    public void setWindowWidth(String windowWidth)
    {
        this.windowWidth = windowWidth;
    }
    @Column(name = "PT2B_WINDOW_HEIGHT",  updatable = true)
    public String getWindowHeight()
    {
        return windowHeight;
    }

    public void setWindowHeight(String windowHeight)
    {
        this.windowHeight = windowHeight;
    }
   
    /*public Integer getUserCount()
    {
        return userCount;
    }

    public void setUserCount(Integer userCount)
    {
        this.userCount = userCount;
    }*/
}
