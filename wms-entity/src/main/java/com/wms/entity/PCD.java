package com.wms.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "province_city_district")
@Data
public class PCD {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int pid;
	private String name;

	@Override
	public String toString() {
		return "PCD [id=" + id + ", pid=" + pid + ", name=" + name + "]";
	}

}