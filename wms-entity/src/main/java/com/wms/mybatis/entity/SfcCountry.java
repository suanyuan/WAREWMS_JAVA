package com.wms.mybatis.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author 
 * @Date 
 */
@Entity
public class SfcCountry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;

	@Id
	private int id;

	private java.lang.String countryName;
	
	private java.lang.String countryEngName;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getCountryName() {
		return countryName;
	}

	public void setCountryName(java.lang.String countryName) {
		this.countryName = countryName;
	}

	public java.lang.String getCountryEngName() {
		return countryEngName;
	}

	public void setCountryEngName(java.lang.String countryEngName) {
		this.countryEngName = countryEngName;
	}

	public String toString() {
		return "Country [id=" + id + ", countryName=" + countryName
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryEngName == null) ? 0 : countryEngName.hashCode());
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + hashCode;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SfcCountry other = (SfcCountry) obj;
		if (countryEngName == null) {
			if (other.countryEngName != null)
				return false;
		} else if (!countryEngName.equals(other.countryEngName))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (hashCode != other.hashCode)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

}