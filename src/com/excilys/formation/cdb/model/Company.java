package com.excilys.formation.cdb.model;

public class Company {
	private Long id;
	private String name;
	
	public Company() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("Company ").append(id).append(" : ").append(name).toString();
	}

}
