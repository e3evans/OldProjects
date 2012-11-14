package com.cisco.swtg.scim.model.cscmodel;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CSCJsonCamData implements Serializable {
	private static final long serialVersionUID = 690726879266208L;

	private String jsoncallback;
	private String q;
	private String author;
	private String preparationTime;
	private String executionTime;
	private String renderingTime;
	private String cacheHit;
	
	private List<CSCThreadData> threads;
	private List<CSCMessageData> messages;
	
	public List<CSCMessageData> getMessages() { return messages; }
	public void setMessages(List<CSCMessageData> messages) { this.messages = messages; }
	
	public String getJsoncallback() { return jsoncallback; }
	public void setJsoncallback(String jsoncallback) { this.jsoncallback = jsoncallback; }
	
	public String getQ() { return q; }
	public void setQ(String q) { this.q = q; }
	
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	
	public String getPreparationTime() { return preparationTime; }
	public void setPreparationTime(String preparationTime) { this.preparationTime = preparationTime; }
	
	public String getExecutionTime() { return executionTime; }
	public void setExecutionTime(String executionTime) { this.executionTime = executionTime; }
	
	public String getRenderingTime() { return renderingTime; }
	public void setRenderingTime(String renderingTime) { this.renderingTime = renderingTime; }
	
	public String getCacheHit() { return cacheHit; }
	public void setCacheHit(String cacheHit) { this.cacheHit = cacheHit; }
	
	public List<CSCThreadData> getThreads() { return threads; }
	public void setThreads(List<CSCThreadData> threads) { this.threads = threads; }
		
}
