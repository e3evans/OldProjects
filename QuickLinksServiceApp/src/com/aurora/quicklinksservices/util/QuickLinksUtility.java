package com.aurora.quicklinksservices.util;

import java.sql.Timestamp;
import java.util.Date;

public class QuickLinksUtility {

public static final Integer NOTDISPLAYED = new Integer(0);


public static Timestamp getCurrentTime()
{
  return new Timestamp(new Date().getTime());
}


}