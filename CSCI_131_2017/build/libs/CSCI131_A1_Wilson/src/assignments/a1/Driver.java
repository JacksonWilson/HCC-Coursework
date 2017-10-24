package assignments.a1;

/**
 * Creates a new relation with user input and displays matrix, ordered pairs, and properties to output.
 * 
 * @author Jackson Wilson
 */
public class Driver {

    public static void main(String[] args) {
        Relation relation = new Relation(true);
        relation.displayMatrixOfRelation();
        relation.displayOrderedPairs();
        System.out.println("The relation is" + (relation.isReflexive() ? " " : " not ") + "reflexive.");
        System.out.println("The relation is" + (relation.isIrreflexive() ? " " : " not ") + "irreflexive.");
        System.out.println("The relation is" + (relation.isSymmetric() ? " " : " not ") + "symmetric.");
        System.out.println("The relation is" + (relation.isAsymmetric() ? " " : " not ") + "asymmetric.");
        System.out.println("The relation is" + (relation.isAntiSymmetric() ? " " : " not ") + "anti-symmetric.");
        System.out.println("The relation is" + (relation.isTransitive() ? " " : " not ") + "transitive.");
    }
}