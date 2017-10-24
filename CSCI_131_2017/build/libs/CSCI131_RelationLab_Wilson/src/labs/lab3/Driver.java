package labs.lab3;

/**
 * Creates a new relation with user input and displays matrix and properties to output.
 * 
 * @author Jackson Wilson
 */
public class Driver {

    public static void main(String[] args) {
        Relation relation = new Relation(true);
        relation.displayMatrixOfRelation();
        System.out.println("The relation is" + (relation.isReflexive() ? " " : " not ") + "reflexive.");
        System.out.println("The relation is" + (relation.isSymmetric() ? " " : " not ") + "symmetric.");
    }
}