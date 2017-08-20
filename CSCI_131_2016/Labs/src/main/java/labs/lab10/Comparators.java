package labs.lab10;

import csci130.*;
import java.util.*;

class Comparators {
	public static final void main(String[] args) {
		TreeSet<Person> set = new TreeSet<Person>();
//		TreeSet<Person> set = new TreeSet<Person>(new PersonComparator());

		String firstName = null;
		while(true) {
			System.out.println("Enter a person's first name or \"END\" to quit:");
			firstName = KeyboardReader.readLine();

			if(firstName.equalsIgnoreCase("END")) {
				break;
			}

			System.out.println("Enter the person's last name:");
			String lastName = KeyboardReader.readLine();
			System.out.println("Enter the person's id:");
			String id = KeyboardReader.readLine();

			Person newGuy = new Person(firstName, lastName, id);

			if(!set.add(newGuy)) {
				System.out.println("\n*************************** ERROR *********************************\n");
				System.out.println("A person matching \"" + newGuy + "\" already exists.");
				System.out.println("This system only knows how to handle people with unique ID's.");
				System.out.println("Please try again");
			}
			System.out.println();
		}

		System.out.println("\n\n\tYou entered the following people:\n\n");
		for(Person p : set) {
			System.out.println(p);
		}
	}
}
