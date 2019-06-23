package com.wms.utils.comparator;

import java.util.Comparator;

import com.wms.entity.User;

public class UserComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o1.getId().compareToIgnoreCase(o2.getId());
	}
}
