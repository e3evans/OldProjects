package com.aurora.quicklinks.beans;



import java.io.Serializable;

public class MenuApp implements Serializable
{
  private Application app;
  private UserApplication userApp;
  private boolean subAppsAllAutoReg = true;

  public Application getApp() {
    return this.app;
  }

  public void setApp(Application app) {
    this.app = app;
  }

  public UserApplication getUserApp() {
    return this.userApp;
  }

  public void setUserApp(UserApplication userApp) {
    this.userApp = userApp;
  }

  public boolean isAlreadyRegistered() {
    return (this.userApp != null) && (this.userApp.getActiveCd().equals("A"));
  }
  
  public boolean isDefaultApp() {
	    return (this.userApp != null && this.userApp.getFlagDefault() != null && this.userApp.getFlagDefault().equals("true"));
	  }

  public void setSubAppsAllAutoReg(boolean subAppsAllAutoReg) {
    this.subAppsAllAutoReg = subAppsAllAutoReg;
  }

  public boolean getSubAppsAllAutoReg() {
    return this.subAppsAllAutoReg;
  }
}