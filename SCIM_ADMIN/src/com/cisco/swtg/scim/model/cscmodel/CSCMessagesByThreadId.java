package com.cisco.swtg.scim.model.cscmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "messagesByThreadIDResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSCMessagesByThreadId implements Serializable {
	private static final long serialVersionUID = 6907269266208L;

	@XmlElement(name = "result")
	List<Message> returns;

	public List<Message> getReturns() {
		if (returns == null) {
			returns = new ArrayList<Message>();
		}
		return returns;
	}

	public void setReturns(List<Message> returns) {
		this.returns = returns;
	}

}
