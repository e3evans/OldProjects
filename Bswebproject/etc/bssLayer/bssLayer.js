// this file is located at:
//
//   <server_root>/js/src/bss/bssLayer.js

 // This is a layer file. It's like any other Dojo module, except that we
 // don't put any code other than require/provide statements in it. When we
 // make a build, this will be replaced by a single minified copy of all
 // the modules listed below, as well as their dependencies, all in the
 // right order:

 dojo.provide("bssLayer.bssLayer");

 // some basics and sophisticated components combined //together.
//These comments will vanish in the compressed file.
 

dojo.require("dojo.parser");
dojo.require("dojo.date.stamp");
dojo.require("dojo.string");
dojo.require("dojo.data.ItemFileReadStore");

dojo.require("dojox.data.QueryReadStore");
dojo.require("dojox.layout.ContentPane");

//dojo.require("dijit.dijit-all");
dojo.require("dijit.form.RadioButton");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.Form");

dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");


dojo.require("xwt.widget.layout.Dashlet");
dojo.require("xwt.widget.layout.Breadcrumb");

dojo.require("xwt.widget.form.ListBox");
dojo.require("xwt.widget.form.TextButton");
dojo.require("xwt.widget.form.FilteringSelect");
dojo.require("xwt.widget.form.ComboBox");
dojo.require("xwt.widget.form.Label");
dojo.require("xwt.widget.form.Rating");
dojo.require("xwt.widget.form.DropDown");

dojo.require("xwt.widget.table.Column");
dojo.require("xwt.widget.table.Table");
dojo.require("xwt.widget.table.Toolbar");
dojo.require("xwt.widget._ConfigureTheme");

dojo.require("xwt.widget.anchoredoverlay.AnchoredOverlay");
dojo.require("xwt.widget.quickview.QuickView");

dojo.require("xwt.widget.notification.ErrorMessage");