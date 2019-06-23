package com.wms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author OwenHuang
 * @Date 2013/5/28
 */
@Entity
@Table(name="sfc_country")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;

	@Id
	@Column(name = "country_id", length=36)
	private int id;

	@Column(name = "country_name", length=25)
	private java.lang.String countryName;
	
	@Column(name = "country_eng_name", length=50)
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
		Country other = (Country) obj;
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