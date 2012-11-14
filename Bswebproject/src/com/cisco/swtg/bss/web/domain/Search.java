package com.cisco.swtg.bss.web.domain;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;

/**
 * This class is used as domain object for Search and contains only
 * setter/getter
 * 
 * @author teprasad
 * 
 */
@Configurable
@Scope("prototype")
public class Search {

	private String keywords;
	private String productCategory;
	private String product;
	private String productName;
	private String softwareVersion;
	private String versionType;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

}
