package com.wms.utils.comparator;

import java.util.Comparator;

import com.wms.mybatis.entity.SfcUser;

public class SfcUserComparator implements Comparator<SfcUser> {

	@Override
	public int compare(SfcUser o1, SfcUser o2) {
		return o1.getId().compareToIgnoreCase(o2.getId());
	}
}
