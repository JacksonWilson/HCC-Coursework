package labs.lab3;

/**
 * Driver for CSCI_131_RelationLab.
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