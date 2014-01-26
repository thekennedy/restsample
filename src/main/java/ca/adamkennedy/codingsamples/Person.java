package ca.adamkennedy.codingsamples;

/**
 * Person bean class
 * @author akennedy
 *
 */
public class Person implements Comparable<Person> {

	private String firstname;
	private String lastname;
	private double age;
	private double balance;
	private int id;

	/**
	 * Person builder
	 * @author akennedy
	 *
	 */
	public static class Builder {
		private String firstname;
		private String lastname;
		private double age;
		private double balance;
		private int id = -1;

		public void setId(int id) {
			this.id = id;
		}
		public void setBalance(double balance) {
			this.balance = balance;
		}

		/**
		 * Cleans a string from non-numeric characters frequently found in currency
		 * Sets the balance to the resulting double
		 * Removes commas and dollar signs
		 *  
		 * @param StringBalance
		 */
		public void setStringBalance(String StringBalance) throws NumberFormatException {
			StringBalance = StringBalance.replace(",","");
			StringBalance = StringBalance.replace("$","");			
			this.balance = Double.parseDouble(StringBalance);
		}		
		public void setAge(double age) {
			this.age = age;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public Person build() {
			return new Person(this);
		}
	}
	private Person(Builder builder) {
		this.age = builder.age;
		this.balance = builder.balance;
		this.firstname = builder.firstname;
		this.lastname = builder.lastname;
		this.id = builder.id;

	}

	@Override
	public String toString() {
		return "Person [firstname=" + firstname + ", lastname=" + lastname + ", age=" + age + ", balance=" + balance + ", id=" + id + "]";
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public double getAge() {
		return age;
	}

	public double getBalance() {
		return balance;
	}

	public int getId() {
		return id;
	}
	

	public boolean equals(Object other) {

		if (other == null)
		{
			return false;
		}		
		if(this == other) return true;

		if (this.getClass() != other.getClass())
		{
			return false;
		}
		Person otherGuy = (Person)other;

		if (this.getId() != otherGuy.getId()) return false;

		if(!this.getLastname().equals(otherGuy.getLastname())) {
			return false;
		}
		if(!this.getFirstname().equals(otherGuy.getFirstname())) {
			return false;
		}	    

		if (this.getAge() != otherGuy.getAge()) return false;
		if (this.getBalance() != otherGuy.getBalance()) return false;

		return true;
	}

	public int compareTo(Person otherGuy) {
	    final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
	    
		if(this.equals(otherGuy)) return EQUAL;
		
		int comparison = this.lastname.compareTo(otherGuy.lastname);
		if (comparison != EQUAL) return comparison;
		
		comparison = this.firstname.compareTo(otherGuy.firstname);
		if (comparison != EQUAL) return comparison;		
		
		if (this.age < otherGuy.age) return BEFORE;
		if (this.age > otherGuy.age) return AFTER;
		
		if (this.balance < otherGuy.balance) return BEFORE;
		if (this.balance > otherGuy.balance) return AFTER;
		
		
		if (this.getId() < otherGuy.getId()) return BEFORE;
		if (this.getId() > otherGuy.getId()) return AFTER;
		
		
		return EQUAL;
	}
}
