package com.excilys.formation.cdb.model;

import java.time.LocalDate;

public class Computer {
	
	private Long id;
	private String name;
	private Company company;
	private LocalDate introduced;
	private LocalDate discontinued;

	public Computer() {}

	public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

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

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompanyId(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Computer ").append(id).append(" : ").append(name).append(" (")
				.append(introduced).append(" - ").append(discontinued).append(") from company ").append(company).toString();
	}
}
