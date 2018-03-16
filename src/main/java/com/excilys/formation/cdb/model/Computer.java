package com.excilys.formation.cdb.model;

import java.time.LocalDate;

public class Computer {
	
	private Long id;
	private String name;
	private Company company;
	private LocalDate introduced;
	private LocalDate discontinued;

	public Computer() {}
	
	public Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public Long getId() {
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

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Computer ").append(id).append(" : ").append(name).append(" (")
				.append(introduced).append(" - ").append(discontinued).append(") from ").append(company).toString();
	}
	
	public static class ComputerBuilder {
		private Long id;
		private String name;
		private Company company;
		private LocalDate introduced;
		private LocalDate discontinued;
		
		public ComputerBuilder withId(Long id) {
			this.id = id;
			return this;
		}
		
		public ComputerBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ComputerBuilder withIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public ComputerBuilder withDiscontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public ComputerBuilder withCompany(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
	}
}
