package com.aurora.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="gsafeed")
public class GSAFeed {
	
	
	private ArrayList<Record> group;

	@XmlElementWrapper(name="group")
	@XmlElement(name="record")
	public ArrayList<Record> getGroup() {
		return group;
	}

	public void setGroup(ArrayList<Record> group) {
		this.group = group;
	}

	

}
