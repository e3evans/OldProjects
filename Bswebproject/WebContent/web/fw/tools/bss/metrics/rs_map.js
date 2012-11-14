/**
 * rs_map.js
 * $Revision: 1.12.6.1 $
 * universal tag - rs_map.js 2.0.20100703.1200, Copyright 2010 Tealium.com Inc. All Rights Reserved.
 */
var ut_rs_map={
    "//blogs.cisco.com":"Cisco-blogs",
	"//social.cisco.com":"cisco-us",
    "//socialmedia.cisco.com":"cisco-us",
    "//together.cisco.com":"cisco-us",
    "/web/solutions/small_business/":"cisco-ussolsmb",
    ".cisco.com/web/solutions/smb/heroes/ca":"",
    ".cisco.com/web/solutions/smb/heroes/":"cisco-ussolsmb",
    ".cisco.com/web/solutions/sp/":"cisco-ussolserviceprovider",
    ".cisco.com/web/solutions/dms/":"cisco-ussoldms",
    ".cisco.com/web/solutions/ps/":"cisco-ussolps",
    
    ".cisco.com/web/strategy/government/us-fed":"cisco-ussolfederal",
    ".cisco.com/web/strategy/government/us_fed":"cisco-ussolfederal",
    ".cisco.com/web/strategy/government/usfed":"cisco-ussolfederal",
    ".cisco.com/web/strategy/government/federal":"cisco-ussolfederal",
    ".cisco.com/web/strategy/government/us_state_local/index.html":"cisco-ussolstatelocal",
    ".cisco.com/web/solutions/cmsg/":"cisco-ussolmediaent",
    ".cisco.com/web/strategy/smart_connected_communities.html":"cisco-ussolsmartconcom",
    ".cisco.com/web/solutions/netsys/routers/index.html":"cisco-home-marketing",
    ".cisco.com/web/services/ordering/index.html":"cisco-usprodservices",

	//".cisco.com/en/US/products/ps6681":"==cisco-us,cisco-proddms",
    ".cisco.com/en/US/docs/":"exclude",
    ".cisco.com/en/US/partner/docs/":"exclude",
    ".cisco.com/en/US/customer/docs/":"exclude",
    ".cisco.com/en/US/ordering":"exclude",
    ".cisco.com/web/learning/":"exclude",
    ".cisco.com/cisco/web/support/":"exclude",
    ".cisco.com/web/ordering/":"exclude",
    ".cisco.com/kobayashi/":"exclude",
    ".cisco.com/cisco/web/psa/":"exclude",
    ".cisco.com/en/US/support/":"exclude",
    ".cisco.com/en/US/hmpgs/":"exclude",
    ".cisco.com/en/US/customer/hmpgs/index.html":"exclude",
    ".cisco.com/en/US/partner/hmpgs/":"exclude",
    ".cisco.com/index.html":"exclude",
    ".cisco.com/warp/public/":"exclude",
	".cisco.com/web/strategy/smart_connected_comm_letushelp.html":"exclude",

    "tools.cisco.com/search/":"",
    "tools.cisco.com/RPF/register/":"",
    "tools.cisco.com/gdrp/coiga/showsurvey.do":"",
    "tools.cisco.com/WWChannels/LOCATR/":"",
    "tools.cisco.com":"exclude",
	"lisp4.cisco.com":"exclude",
    "newsroom.cisco.com":"exclude",
    "forums.cisco.com":"exclude",
    "videolounge.cisco.com":"exclude",
    "learningnetwork.cisco.com":"exclude",
	".cisco.apply2jobs.com/index.cfm":"exclude",
    ".cisco.com/assets/sol/bn/carousel/hero_content.html":"exclude",
    ".cisco.com/assets/sol/bn/letushelp_content.html":"exclude",
    "investor.cisco.com":"exclude",
	"cisco-servicefinder.com":"exclude",	
	"cisco-servicefinder.com/servicefinder.aspx":"exclude",
	"translate.googleusercontent.com/translate_c":"exclude",
	"webcache.googleusercontent.com/search":"exclude",
	"swds-sj.cisco.com/cgi-bin/sfp/specialpub.cgi":"exclude",
	"tools-lt.cisco.com/swift/licensing/privateregistrationservlet":"exclude",
 	
	"cisco-apps.cisco.com/pcgi-bin/sreg2/":"",
	"cisco-apps.cisco.com/cgi-bin/sreg2/":"",
	"cisco-apps.cisco.com":"exclude",
	
	"apps.cisco.com/gdrp/coiga/showsurvey.do":"",
	"apps.cisco.com":"exclude", 
	
	"tools-dev.cisco.com":"exclude",
	"tools-akamai.cisco.com":"exclude",
	"tools-lt.cisco.com":"exclude",
	"cepx-staging-lt1.cisco.com":"exclude",
	"translate.googleusercontent.com":"exclude",
    "webcache.googleusercontent.com":"exclude",
	"developer.cisco.com":"exclude",
	"home.cisco.com":"exclude",
	"homestore.cisco.com":"exclude",
	".cisco.com/discuss/":"exclude",
    "homesupport.cisco.com/en-us/home":"exclude",

    "iapath:cisco.com#Networking Solutions#Borderless Networks#Branch":"==cisco-us,cisco-ussolbranch",
    "iapath:cisco.com#Networking Solutions#Borderless Networks#Campus":"==cisco-us,cisco-ussolcampus",
    "iapath:cisco.com#Networking Solutions#Borderless Networks#Mobility":"==cisco-us,cisco-ussolmobility",
    "iapath:cisco.com#Networking Solutions#Borderless Networks#Security":"==cisco-us,cisco-ussolsecurity",
    "iapath:cisco.com#Networking Solutions#Borderless Networks#Security#Teleworker":"==cisco-us,cisco-ussolteleworker",
    "iapath:cisco.com#Networking Solutions#Collaboration#TelePresence":"==cisco-us,cisco-ussoltelepresence",
    "iapath:cisco.com#Networking Solutions#Collaboration#Unified Communications":"==cisco-us,cisco-ussolunifiedcomm",
    "iapath:cisco.com#Networking Solutions#Borderless Networks#WAN and MAN":"==cisco-us,cisco-ussolwanman",
    "iapath:cisco.com#Products#Cisco Products#Security#Email Security#Cisco IronPort":"cisco-ironport",
    "iapath:cisco.com#Products#Cisco Products#Security#Web Security#Cisco IronPort":"cisco-ironport",
    "iapath:cisco.com#Products#Cisco Products#Security#Security Management#Cisco IronPort":"cisco-ironport",
    ".cisco.com/en/US/products/ps10164/anti_malware_index.html":"cisco-ironport",
    ".cisco.com/en/US/products/ps10164/web_rep_index.html":"cisco-ironport",
    ".cisco.com/en/US/products/ps10164/web_security_index.html":"cisco-ironport",
    "iapath:cisco.com#Products#Cisco Products#Network Management and Automation":"cisco-usprodnetworkmgmt",
    
    "www.cisco.com":""
}

// add custom objects with overrides of send, ntpagetag_sensor and ntpagetag_sensor_secure
cdc.ut.ntconfig = {
	nosend:{send: false},
	send:{send: true,ntpagetag_sensor: "//cisco-tags.cisco.com/tag/ntpagetag.gif", ntpagetag_sensor_secure: "//cisco-tags.cisco.com/tag/ntpagetag.gif"},
	auth_send:{send: true, ntpagetag_sensor: "//cisco-tags.cisco.com/tag/auth/ntpagetag.gif", ntpagetag_sensor_secure: "//cisco-tags.cisco.com/tag/auth/ntpagetag.gif"},
  devsend:{send: true, ntpagetag_sensor: "//cisco-tags-dev.cisco.com/tag/ntpagetag.gif", ntpagetag_sensor_secure: "//cisco-tags-dev.cisco.com:443/tag/ntpagetag.gif"},
  auth_devsend:{send: true, ntpagetag_sensor: "//cisco-tags-dev.cisco.com/tag/auth/ntpagetag.gif", ntpagetag_sensor_secure: "//cisco-tags-dev.cisco.com:443/tag/auth/ntpagetag.gif"},
  stagesend:{send: true, ntpagetag_sensor: "//cisco-tags-stg.cisco.com/tag/ntpagetag.gif", ntpagetag_sensor_secure: "//cisco-tags-stg.cisco.com/tag/ntpagetag.gif"},
  auth_stagesend:{send: true, ntpagetag_sensor: "//cisco-tags-stg.cisco.com/tag/auth/ntpagetag.gif", ntpagetag_sensor_secure: "//cisco-tags-stg.cisco.com/tag/auth/ntpagetag.gif"},
  local:{send: true,ntpagetag_sensor: "/web/fw/i/ntpagetag.gif", ntpagetag_sensor_secure: "/web/fw/i/ntpagetag.gif"},
  debug:{debug: true, send: true,ntpagetag_sensor: "//partners-tags.cisco.com/tag/utt/ntpagetag.gif", ntpagetag_sensor_secure: "//partners-tags.cisco.com/tag/utt/ntpagetag.gif"},
  auth_debug:{debug: true, send: true,ntpagetag_sensor: "//partners-tags.cisco.com/tag/auth/utt/ntpagetag.gif", ntpagetag_sensor_secure: "//partners-tags.cisco.com/tag/auth/utt/ntpagetag.gif"}

  //need for dev, stage, and prod. should/could all be relative
}

//use authenticated flag from the page to use normal or auth_send
try{
	if(cdc.util.isAuthenticated()){
		cdc.ut.ntconfig.debug = cdc.ut.ntconfig.auth_debug,
		cdc.ut.ntconfig.send = cdc.ut.ntconfig.auth_send,
		cdc.ut.ntconfig.devsend = cdc.ut.ntconfig.auth_devsend,
		cdc.ut.ntconfig.stagesend = cdc.ut.ntconfig.auth_stagesend
		//cdc.ut.liveManager.config.dop_sensor += ",//cisco-tags-dev.cisco.com/tag/auth/vs/flashtag.txt?Log=1";
	}
}catch(e){}

// assign the override object based on URL filters (contains)
cdc.ut.ntconfigmap = {
	// NOTE: Higher specificity goes at top.
	// e.g. 
	//	"www.cisco.com/web/fw/test/":cdc.ut.ntconfig.debug,
	//  "www.cisco.com":cdc.ut.ntconfig.local
	"171.69.46.119":cdc.ut.ntconfig.local,
	"cdc-site-dev.cisco.com":cdc.ut.ntconfig.send,
	"cdc-id-1.cisco.com":cdc.ut.ntconfig.send,
	"cepx-active-dev1.cisco.com":cdc.ut.ntconfig.local,
	"cepx-active-stage1.cisco.com":cdc.ut.ntconfig.local,	
	"www-stage1.cisco.com":cdc.ut.ntconfig.local,
	"www-lt1.cisco.com":cdc.ut.ntconfig.local,
	"sitegen-stage-1.cisco.com":cdc.ut.ntconfig.local,
	"cepx-active-stage1":cdc.ut.ntconfig.local,
	"cepx-active-lt1":cdc.ut.ntconfig.local,
	"ecmx-active-dev.cisco.com":cdc.ut.ntconfig.local,		
	"www.cisco.com/web/fw/test/":cdc.ut.ntconfig.debug,
	"www.cisco.com":cdc.ut.ntconfig.local

}

