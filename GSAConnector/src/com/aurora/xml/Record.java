package com.aurora.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="record")
public class Record {
	
	
	private String action;
	private String mimetype;
	private String displayurl;
	private String url;
	private ArrayList<Meta> metadata;
	
	@XmlAttribute
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@XmlAttribute
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	@XmlAttribute
	public String getDisplayurl() {
		return displayurl;
	}
	public void setDisplayurl(String displayurl) {
		this.displayurl = displayurl;
	}
	@XmlAttribute
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@XmlElementWrapper(name="metadata")
	@XmlElement(name="meta")
	public ArrayList<Meta> getMetadata() {
		return metadata;
	}
	public void setMetadata(ArrayList<Meta> metadata) {
		this.metadata = metadata;
	}
	
	
	
}
