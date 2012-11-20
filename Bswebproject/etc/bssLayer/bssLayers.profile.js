//This profile is used just to illustrate the layout of a layered build.
//All layers have an implicit dependency on dojo.js.

//Normally you should not specify a layer object for dojo.js. It is normally
//implicitly built containing the dojo "base" functionality (dojo._base).
//However, if you prefer the Dojo 0.4.x build behavior, you can specify a
//"dojo.js" layer to get that behavior. It is shown below, but the normal
//0.9 approach is to *not* specify it.

//

dependencies = {
	layers: [
		
{ 
 name: "../bssLayer/bssLayer.js" ,
	
resourceName: "bssLayer.bssLayer",

layerDependencies: [ "dijit.dijit" ],
 
dependencies: [

 "bssLayer.bssLayer" ] } 

	],

	prefixes: [
		[ "dijit", "../dijit" ],
		[ "dojox", "../dojox" ],
		[ "bssLayer", "../bssLayer" ],
		[ "xwt", "../xwt"]
	]
}

//If you choose to optimize the JS files in a prefix directory (via the optimize= build parameter),
//you can choose to have a custom copyright text prepended to the optimized file. To do this, specify
//the path to a file tha contains the copyright info as the third array item in the prefixes array. For
//instance:
//	prefixes: [
//		[ "mycompany", "/path/to/mycompany", "/path/to/mycompany/copyright.txt"]
//	]
//
//	If no copyright is specified in this optimize case, then by default, the dojo copyright will be used.
