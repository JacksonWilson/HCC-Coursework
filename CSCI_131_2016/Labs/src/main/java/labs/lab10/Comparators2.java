package labs.lab10;

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.TreeSet;

import labs.lab7.KeyboardReader;

public class Comparators2 {
    private static final KeyboardReader keyReader;

    static {
        keyReader = new KeyboardReader(System.in);
    }

    public static void main(String[] args) {
        try {
            System.out.print("Type of data structure: [1]TreeSet, [2]Hashtable, [3]TreeMap, or [4]HashSet: ");

            switch(keyReader.readInt(1, 4)) {
            case 1: getPeopleWithTreeSet();
                break;
            case 2: getPeopleWithHashtable();
                break;
            case 3: getPeopleWithTreeMap();
                break;
            case 4: getPeopleWithHashSet();
                break;
            }
                
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                keyReader.close();
            } catch (IOException e) {}
        }
    }

    private static void getPeopleWithTreeSet() throws IOException {
        TreeSet<Person> treeSet = new TreeSet<Person>();
//		TreeSet<Person> set = new TreeSet<Person>(new PersonComparator());

		do {
			Person newGuy = getPerson();
            if (newGuy == null)
                break;

			if(!treeSet.add(newGuy)) {
				System.out.println("\n*************************** ERROR *********************************\n");
				System.out.println("A person with that ID \"" + newGuy.getID() + "\" already exists.");
				System.out.println("This system only knows how to handle people with unique ID's.");
				System.out.println("Please try again");
			}

            System.out.println("\n\n\n\tYou entered the following people:\n\n");
            treeSet.forEach(p -> System.out.println(p));
		} while (true);
    }

    private static void getPeopleWithHashtable() throws IOException {
        Hashtable<String, Person> hashtable = new Hashtable<>();

		do {
			Person newGuy = getPerson();
            if (newGuy == null)
                break;

			if(hashtable.put(newGuy.getID(), newGuy) != null) {
				System.out.println("\n*************************** ERROR *********************************\n");
				System.out.println("A person with that ID \"" + hashtable.get(newGuy.getID()) + "\" already exists.");
				System.out.println("This system only knows how to handle people with unique ID's.");
				System.out.println("Please try again");
			}

            System.out.println("\n\n\n\tYou entered the following people:\n\n");
            hashtable.forEach((k, p) -> System.out.println(p));
		} while (true);
    }

    private static void getPeopleWithTreeMap() throws IOException {
        TreeMap<String, Person> treeMap = new TreeMap<>();

		do {
			Person newGuy = getPerson();
            if (newGuy == null)
                break;

			if(treeMap.put(newGuy.getID(), newGuy) != null) {
				System.out.println("\n*************************** ERROR *********************************\n");
				System.out.println("A person with that ID \"" + treeMap.get(newGuy.getID()) + "\" already exists.");
				System.out.println("This system only knows how to handle people with unique ID's.");
				System.out.println("Please try again");
			}

            System.out.println("\n\n\n\tYou entered the following people:\n\n");
            treeMap.forEach((k, p) -> System.out.println(p));
		} while (true);
    }

    private static void getPeopleWithHashSet() throws IOException {
        HashSet<Person> hashSet = new HashSet<>();

		do {
			Person newGuy = getPerson();
            if (newGuy == null)
                break;

			if(!hashSet.add(newGuy)) {
				System.out.println("\n*************************** ERROR *********************************\n");
				System.out.println("A person with that ID \"" + newGuy.getID() + "\" already exists.");
				System.out.println("This system only knows how to handle people with unique ID's.");
				System.out.println("Please try again");
			}

            System.out.println("\n\n\n\tYou entered the following people:\n\n");
            hashSet.forEach(p -> System.out.println(p));
		} while (true);
    }

    private static Person getPerson() throws IOException {
        System.out.println("Enter a person's first name or \"END\" to quit:");
        String firstName = keyReader.readLine();

        if(firstName.equalsIgnoreCase("END")) {
            return null;
        }

        System.out.println("Enter the person's last name:");
        String lastName = keyReader.readLine();
        System.out.println("Enter the person's id:");
        String id = keyReader.readLine();

        return new Person(firstName, lastName, id);
    }
}