package com.wms.utils.comparator;

import java.util.Comparator;

import com.wms.entity.Country;

public class CountryComparator implements Comparator<Country> {

	@Override
	public int compare(Country o1, Country o2) {
		int value = 0;
		if(o1.getId() > o2.getId()){
			value = 1;
		}else if(o1.getId() < o2.getId()){
			value = -1;
		}
		return value;
	}

}
