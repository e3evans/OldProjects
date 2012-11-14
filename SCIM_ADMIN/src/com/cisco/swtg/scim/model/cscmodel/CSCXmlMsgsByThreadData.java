package com.cisco.swtg.scim.model.cscmodel;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CSCXmlMsgsByThreadData implements Serializable {
	private static final long serialVersionUID = 690726879266208L;
	
	@JsonProperty("return")
	private List<CSCXmlMsgReturnData> cSCXmlMsgReturnData;

	public List<CSCXmlMsgReturnData> getcSCXmlMsgReturnData() { return cSCXmlMsgReturnData; }
	public void setcSCXmlMsgReturnData(List<CSCXmlMsgReturnData> cSCXmlMsgReturnData) { this.cSCXmlMsgReturnData = cSCXmlMsgReturnData; }
	
}





