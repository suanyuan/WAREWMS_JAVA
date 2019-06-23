package com.wms.utils.comparator;

import java.util.Comparator;

import com.wms.mybatis.entity.SfcMenu;

/**
 * Menu Comparator
 * @author OwenHuang
 * @Date 2013/5/30
 */
public class SfcMenuComparator implements Comparator<SfcMenu>{
	
	@Override
	public int compare(SfcMenu o1, SfcMenu o2) {
		int i1 = o1.getDisplaySeq() != null ? o1.getDisplaySeq().intValue() : -1;
		int i2 = o2.getDisplaySeq() != null ? o2.getDisplaySeq().intValue() : -1;
		return i1 - i2;
	}

}
