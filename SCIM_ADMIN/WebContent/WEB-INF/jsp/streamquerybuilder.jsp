<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Related Query Builder</title>
<body>
<h2>Related Service Query Builder</h2>



<form method="POST"  action=streamurldisplay.htm>
	<table border=0 >
		<tr>
		<td><b>format</b> <font color=red> *Required.  </font>
		<blockquote>
		<input type=radio name=format value="json" checked  />JSON
		<input type=radio name=format value="xml"  />XML
		<input type=radio name=format value="xls"  />Excel
		</blockquote>
		</td>
	</tr>

	<tr>
		<td><b>domain</b> <font color=red> *Required.  </font> Possible values:  <b>cisco</b>, <b>noncisco</b>, <b>all</b>,  and specific noncisco domains (eg.  <b>microsoft</b>, <b>vmware</b>).  The default is:  cisco
		<blockquote>
		<input type=text name=domain value="cisco" />
		</blockquote>
		</td>
	</tr>

        <tr>
                <td><b>accessibility</b> <font color=red> *Required.  </font> The default is:  external
                <blockquote>
                <input type=radio name=accessibility value="external" checked />External
                <input type=radio name=accessibility value="internal"  />Internal
                <input type=radio name=accessibility value="all"  />All
                </blockquote>
                </td>
        </tr>


	<tr>
		<td><b>source</b> <font color=red> *Required.  </font>
		<blockquote>
		<input type=radio name=source value="all" checked />ALL
		<input type=radio name=source value="c3"  />C3
		<input type=radio name=source value="csc"  />CSC
		</blockquote>
		</td>
	</tr>
	<tr>
		<td><b>pattern</b> <font color="red"> *Required.  </font> Example values:  <b>url:contains~~/en/US/</b>&nbsp;&nbsp;&nbsp;   <b>title:contains~~bgp</b>&nbsp;&nbsp;&nbsp;  The default is:  all. 
		<blockquote>
		<input type="text" name="pattern" value="all" />
		</blockquote>
		</td>
	</tr>

	<tr>
		<td><b>servername</b> <font color=red> *Required.  </font> Example values:  <b>www.cisco.com</b>  The default is:  all.
		<blockquote>
		<input type=text name=servername value="all" />
		</blockquote>
		</td>
	</tr>

	<tr>
		<td><b>daterange</b> <font color=red> *Required.  </font> Possible value is a begin date/end date pair.  eg. <b>01-JAN-12~01-JUN-12</b>.  Default startdate is 6 months ago.  Default enddate is today's date.
		<blockquote>
		<input type=text  name=daterange value="default" />
		</blockquote>
		</td>
	</tr>
	
	<tr>
		<td><b>streamType</b> <font color=red> *Required.  </font> possible values ICO.nonICO
		<blockquote>
		<input type=text  name=type value="" />
		</blockquote>
		</td>
	</tr>
	
	<tr>
      <td><b>nonICOfieldName</b> <font color=red> *Required.  </font>
		<blockquote>
	    <input type=radio name=fieldname value="userid" />userid
	    <input type=radio name=fieldname value="MDFID" />MDFID
		<input type=radio name=fieldname value="C3Workgroup" />C3Workgroup
		<input type=radio name=fieldname value="Community" />Community
		<input type=radio name=fieldname value="Tech/Subtech" />Tech/Subtech
	    </blockquote>
	  </td>
	</tr>
	
	<tr>
		<td><b>nonICOfieldValue</b> <font color=red> *Required.  </font> possible values are one or more comma delimited userid(s), C3Workgroup(s), Community(s), Tech/Subtech(s), MDFIDs
		<blockquote>
		<input type=text  name=fieldvalue value="" />
		</blockquote>
		</td>
	</tr>
	
	<tr>
		<td><b>nonICOrelatedTofieldname </b> <font color=red> *Required.  </font> possible values are 1 or more of the following ICOs:  bug, url, pid, c3case, serialnumber, mib, errormessage, swimage, rfc.: document, bug, c3case, mib, errormessage, rfc, pid, sw
		<blockquote>
		<input type=text  name=relatedto  value="" />
		</blockquote>
		</td>
	</tr>
	
	<tr>
      <td><b>ICOfieldName</b> <font color=red> *Required.  </font>
		<blockquote>
	    <input type=radio name=fieldname value="bug" checked />Bug
		<input type=radio name=fieldname value="url"  />url
		<input type=radio name=fieldname value="sw-image-names"  />SWimage
		<input type=radio name=fieldname value="pid" />PIDs
		<input type=radio name=fieldname value="errmsg"  />Error Messages
		<input type=radio name=fieldname value="mib"  />MIBs
		<input type=radio name=fieldname value="rfc" />RFCs
		<input type=radio name=fieldname value="c3case"  />C3Case
		<input type=radio name=fieldname value="serial_no"  />SerialNumber
	    </blockquote>
	     </td>
	</tr>
	
	<tr>
     <td>
		<blockquote>
		<b>rfc</b>
	    <input type=radio name=fieldname value="document" checked />document
		<input type=radio name=fieldname value="bug"  />bug
		<input type=radio name=fieldname value="c3case"  />c3case
		<input type=radio name=fieldname value="mib" />mib
		<input type=radio name=fieldname value="errmsg"  />Error Messages
		<input type=radio name=fieldname value="rfc" />RFCs
		<input type=radio name=fieldname value="pid" />PIDs
		<input type=radio name=fieldname value="sw" />SW
		
	    </blockquote>
	     </td>
	</tr>
	
	<tr>
      <td><b>ICOfieldValue</b> <font color=red> *Required.  </font>
		<blockquote>
	    <input type=radio name=fieldvalue value="bug" checked />Bug
		<input type=radio name=fieldvalue value="url"  />url
		<input type=radio name=fieldvalue value="sw-image-names"  />SWimage
		<input type=radio name=fieldvalue  value="pid" />PIDs
		<input type=radio name=fieldvalue value="errmsg"  />Error Messages
		<input type=radio name=fieldvalue value="mib"  />MIBs
		<input type=radio name=fieldvalue value="rfc" />RFCs
		<input type=radio name=fieldvalue value="c3case"  />C3Case
		<input type=radio name=fieldvalue value="serial_no"  />SerialNumber
	    </blockquote>
	     </td>
	</tr>
	
	<tr>
     <td>
		<blockquote>
		<b>rfc</b>
	    <input type=radio name=fieldvalue value="document" checked />document
		<input type=radio name=fieldvalue value="bug"  />bug
		<input type=radio name=fieldvalue value="c3case"  />c3case
		<input type=radio name=fieldvalue value="mib" />mib
		<input type=radio name=fieldvalue value="errmsg"  />Error Messages
		<input type=radio name=fieldvalue value="rfc" />RFCs
		<input type=radio name=fieldvalue value="pid" />PIDs
		<input type=radio name=fieldvalue value="sw" />SW
		
	    </blockquote>
	     </td>
	</tr>
	
	<tr>
	
	          <td><b>qualitySource</b> <font color=red> </font> 
                <blockquote>
                <input type=radio name=qualitysource value="RESOLUTION_SUMMARY" />RESOLUTION_SUMMARY
		        <input type=radio name=qualitysource value="ANSWERED" />ANSWERED
		        <input type=radio name=qualitysource value="RATING" />RATING
		        <input type=radio name=qualitysource value="CCIE_REF" />CCIE_REF
                </blockquote>
               </td>
    </tr>
     <tr>
                <td><b>qualityThreshold</b> <font color=red> </font> 
                <blockquote>
                <input type=text name=threshold value="0" />Possible values are 0-n.  Default is 0.
                </blockquote>
                </td>
     </tr>


        <tr>
                <td><b>returncount</b> <font color=red> </font> Number of results to return.  Possible values are 1-1000.  Default is 5.
                <blockquote>
                <input type=text name=returncount value="5" />
                </blockquote>
                </td>
        </tr>

        <tr>
                <td><b>begincount</b> <font color=red> *Required.  </font> Possible values are 1-n.  Default is 1.
                <blockquote>
                <input type=text name=begincount value="1" />
                </blockquote>
                </td>
        </tr>
	</table>

<input type=submit>
</form>
