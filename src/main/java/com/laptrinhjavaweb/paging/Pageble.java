package com.laptrinhjavaweb.paging;

import com.laptrinhjavaweb.sort.Sorter;

public interface Pageble {
	Integer getPage();
	Integer getOffSet();
	Integer getLimit();
	Sorter getSorter();
}
