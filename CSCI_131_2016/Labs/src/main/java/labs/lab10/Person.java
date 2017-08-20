package labs.lab10;

public class Person implements Comparable<Person> {
	private String firstName;
	private String lastName;
	private String id;

	public Person(String fName, String lName, String ID) {
		firstName = fName;
		lastName = lName;
		id = ID;
	}

	public final String getFirstName() {
		return firstName;
	}

	public final String getLastName() {
		return lastName;
	}

	public final String getID() {
		return id;
	}

	public final boolean equals(Object o) {
		if(o == null) {
			return false;
		}

		if(this == o) {
			return true;
		}

		if(o instanceof Person) {
			return id.equalsIgnoreCase(((Person)o).id);
		}

		if(o instanceof String) {
			return id.equalsIgnoreCase((String)o);
		}

		return false;
	}

	public final int compareTo(Person p) {
		return id.compareToIgnoreCase(p.id);
	}

	public final String toString() {
		return lastName + ", " + firstName + " (" + id + ")";
	}
}