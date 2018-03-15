package com.excilys.formation.cdb.pagination;

import java.util.LinkedList;
import java.util.List;

public abstract class Page<T> {
	
	public static int PAGE_SIZE = 20;
	
	private int pageNumber;
	private List<T> entities;
	
	public Page(int pageNumber) {
		this.pageNumber = pageNumber;
		entities = new LinkedList<T>();
	}
}
