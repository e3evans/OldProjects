<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Popularity Service Query Builder</title>
<body>
<h2>Popularity Service Query Builder</h2>

<form method="POST"  action=popularityurldisplay.htm>
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
		<td><b>fieldname</b> <font color=red> *Required.  </font>
		<blockquote>
		<input type=radio name=fieldname value="url" checked />URLs
		<input type=radio name=fieldname value="cdets"  />CDETS Bugs
		<input type=radio name=fieldname value="sw-image-names"  />SW Filenames
		<input type=radio name=fieldname value="pid" />PIDs
		<input type=radio name=fieldname value="errmsg"  />Error Messages
		<input type=radio name=fieldname value="mib"  />MIBs
		<input type=radio name=fieldname value="rfc" />RFCs
		<input type=radio name=fieldname value="c3caseid"  />C3CaseIDs
		<input type=radio name=fieldname value="serial_no"  />SerialNumbers
		</blockquote>
		</td>
	</tr>


	<tr>
		<td><b>fieldvalue</b> <font color=red> *Required.  </font> Possible values are a specific bugid, or a specific url, etc.  Default is all.
		<blockquote>
		<input type=text name=fieldvalue value="all" />
		</blockquote>
		</td>
	</tr>

	<tr>
		<td><b>popularitythreshold</b> <font color=red> *Required.  </font> Possible values are 0-n.  Default is 0.
		<blockquote>
		<input type=text name=popularitythreshold value="0" />
		</blockquote>
		</td>
	</tr>

        <tr>
                <td><b>popularitythresholdsource</b> <font color=red> *Required.  </font>
                <blockquote>
                <input type=radio name=popularitythresholdsource value="hits" checked />C3 + CSC
                <input type=radio name=popularitythresholdsource value="hits_c3"  />C3 
                <input type=radio name=popularitythresholdsource value="hits_csc"  />CSC 
                </blockquote>
                </td>
        </tr>

        <tr>
                <td><b>qualitythreshold</b> <font color=red> *Required.  </font> Possible values are 0-n.  Default is 0.
                <blockquote>
                <input type=text name=qualitythreshold value="0" />
                </blockquote>
                </td>
        </tr>

        <tr>
                <td><b>qualitythresholdsource</b> <font color=red> *Required.  </font>  
                <blockquote>
                <input type=radio name=qualitythresholdsource value="quality_score" checked />C3 Resolution Summary + CSC Answered + CSC Rated >= 4 stars
                <input type=radio name=qualitythresholdsource value="q_resolution_summary" />C3 Resolution Summary
                <input type=radio name=qualitythresholdsource value="q_answered"  />CSC Answered
                <input type=radio name=qualitythresholdsource value="q_ratings"  />CSC Rated >= 4 stars
                </blockquote>
                </td>
        </tr>

        <tr>
                <td><b>sortsource</b> <font color=red> *Required.  </font>     
                <blockquote>
                <input type=radio name=sortsource value="hits" checked/>C3 + CSC 
                <input type=radio name=sortsource value="hits_c3" />C3 
                <input type=radio name=sortsource value="hits_csc_disc" />CSC <BR>
                <input type=radio name=sortsource value="quality_score" />C3 Resolution Summary + CSC Answered + CSC Rating >= 4 stars
                <input type=radio name=sortsource value="q_resolution_summary" />C3 Resolution Summary 
                <input type=radio name=sortsource value="q_answered" />CSC Answered
                <input type=radio name=sortsource value="q_ratings" />CSC Rating >= 4 stars
                </blockquote>
                </td>
        </tr>

        <tr>
                <td><b>returncount</b> <font color=red> *Required.  </font> Number of results to return.  Possible values are 1-1000.  Default is 5.
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
