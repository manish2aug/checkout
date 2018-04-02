package com.antawine.assessment.model;

import java.math.BigDecimal;

public class Discount {
	
	private String itemName;
	private double discountPercentage;
	private BigDecimal discountAmount;
	
	public Discount() {
	}
	
	public Discount(final String itemName, final double discountPercentage, final BigDecimal discountAmount) {
		super();
		this.itemName = itemName;
		this.discountPercentage = discountPercentage;
		this.discountAmount = discountAmount;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(final String itemName) {
		this.itemName = itemName;
	}
	
	public double getDiscountPercentage() {
		return discountPercentage;
	}
	
	public void setDiscountPercentage(final double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	
	public void setDiscountAmount(final BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	@Override
	public String toString() {
		return "Discount [itemName=" + itemName + ", discountPercentage=" + discountPercentage + ", discountAmount=" + discountAmount + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((discountAmount == null) ? 0 : discountAmount.hashCode());
		long temp;
		temp = Double.doubleToLongBits(discountPercentage);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		result = (prime * result) + ((itemName == null) ? 0 : itemName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Discount other = (Discount) obj;
		if (discountAmount == null) {
			if (other.discountAmount != null) {
				return false;
			}
		} else if (!discountAmount.equals(other.discountAmount)) {
			return false;
		}
		if (Double.doubleToLongBits(discountPercentage) != Double.doubleToLongBits(other.discountPercentage)) {
			return false;
		}
		if (itemName == null) {
			if (other.itemName != null) {
				return false;
			}
		} else if (!itemName.equals(other.itemName)) {
			return false;
		}
		return true;
	}
	
}
