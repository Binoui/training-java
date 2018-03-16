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
	
	public abstract void refresh();
	public abstract int getLastPageNumber();

	public List<T> previous() { 
		if (pageNumber > 0) {
			pageNumber++;
			refresh();
		}
			
		return entities;
	}
	
	public List<T> next() { 
		if (pageNumber < getLastPageNumber()) {
			pageNumber++;
			refresh();
		}
			
		return entities;
	}
}
