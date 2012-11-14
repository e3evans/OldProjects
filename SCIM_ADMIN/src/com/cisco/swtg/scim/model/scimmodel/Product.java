package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT")
public class Product implements Serializable {
	private static final long serialVersionUID = 62726879266208L;
	
	private String productNumber;
	private String itemDescription;
	
	@Id
	@Column(name="PRODUCT_NUMBER")
	public String getProductNumber() { return productNumber; }
	public void setProductNumber(String productNumber) { this.productNumber = productNumber; }
	
	@Column(name="ITEM_DESCRIPTION")
	public String getItemDescription() {	return itemDescription;	}
	public void setItemDescription(String itemDescription) {	this.itemDescription = itemDescription;	}
}
