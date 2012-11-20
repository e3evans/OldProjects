function ErrorMessage(basePath, locale) {
	
	
	this.supportedLocale = ['en','pl'];

	this.error_message_localized = {};

	this.propertiesLoaded = false;
	
	this.addJavascript = function (jsname,pos) {
		var th = document.getElementsByTagName(pos)[0];
		var s = document.createElement('script');
		s.setAttribute('type','text/javascript');
		s.setAttribute('src',jsname);
		th.appendChild(s);
		this.propertiesLoaded = true;
	};
	
	
	this.isLocaleSupported = function ( obj) {
		  var i = this.supportedLocale.length;
		  while (i--) {
		    if (this.supportedLocale[i] === obj) {
		      return true;
		    }
		  }
		  return false;
	};
	
	this.getSupportedLocale = function(_locale) {
		var tempLocale = _locale;
		if(_locale) {
			tempLocale = _locale.toLowerCase();
		}
		if(this.isLocaleSupported(_locale) == false) {
			 tempLocale = this.supportedLocale[0];
		}
		
		return tempLocale;
	};
	
	this.loadProperties = function (_locale) {
		
		var tempLocale = this.getSupportedLocale(_locale);
		var constructedJsName = this.basePath + "errorlist." + tempLocale + ".js";
		if(this.propertiesLoaded == false) {
			//alert("Locating " + constructedJsName);
			this.addJavascript(constructedJsName,"head");
		}
	};
	
	this.basePath = basePath;
	
	this.myLocale = this.getSupportedLocale(locale);
	this.loadProperties(this.myLocale);
	
	this.getMessage = function (errorKey) {
		if(this.propertiesLoaded = false) {
			this.loadProperties(this.myLocale);
		}
		
		message = this.error_message_localized[errorKey];
		if(!message) {
			//default message
		}
		
		return message;
	};
}