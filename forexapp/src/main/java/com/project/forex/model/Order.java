package com.project.forex.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="order_fx")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {

	@Id
	@Column(name="id_fx")
	@GeneratedValue(strategy = GenerationType.AUTO)
	//private Integer id;
	private Long id;

	@Column(name="userid_fx")
	private String userId;

	@Column(name="ordertype_fx")
	private String orderType;
	
	@Column(name="currency_fx")
	private String currency;
	
	@Column(name="price_fx")
	//@Value("${forex.rate.gbp.usd}")
	private BigDecimal price;
	
	@Column(name="amount_fx")
	private BigDecimal amount;
	
	@Column(name="status_fx")
	private String status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	//@Value("${forex.rate.gbp.usd}")
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		
		if(this == obj) {
			return true;
		}

		if(!(obj instanceof Order)) {
			return false;
		}
		
		Order orderObj = (Order)obj;
		
		if(orderObj.getCurrency() == null 
				|| orderObj.getPrice() == null 
				|| orderObj.getAmount() == null) {
			return false;
		}
		
		if((currency == orderObj.getCurrency()) 
				&& (price == orderObj.getPrice()) 
				&& (amount == orderObj.getAmount())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCd = 987;
		
		if(currency != null) {
			hashCd = 12 * hashCd + currency.hashCode();
		}
		
		if(price != null) {
			hashCd = 12 * hashCd + price.hashCode();
		}

		if(amount != null) {
			hashCd = 12 * hashCd + amount.hashCode();
		}

		System.out.println("Order hashCode : " + hashCd);

		return hashCd;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBldr = new StringBuilder();
		stringBldr.append("[id=" + id);
		stringBldr.append(", userId=" + userId);
		stringBldr.append(", orderType=" + orderType);
		stringBldr.append(", currency=" + currency);
		stringBldr.append(", price=" + price);
		stringBldr.append(", amount=" + amount);
		stringBldr.append(", status=" + status + "]");
		return stringBldr.toString();
	}
}
