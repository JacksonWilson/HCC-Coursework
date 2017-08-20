package labs.lab10;

import java.util.*;

public class PersonComparator implements Comparator<Person> {
	public final int compare(Person p1, Person p2) {
		int lastNameCompare = p1.getLastName().compareToIgnoreCase(p2.getLastName());
		if(lastNameCompare == 0) {

			int firstNameCompare = p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
			if(firstNameCompare == 0) {
				return p1.getID().compareToIgnoreCase(p2.getID());
			}
			return firstNameCompare;
		}
		return lastNameCompare;
	}
}