package labs.lab3;

public class Driver {

    public static void main(String[] args) {
        Relation relation = new Relation(true);
        relation.displayMatrixOfRelation();
        System.out.println("The relation is" + (relation.isReflexive() ? " " : " not ") + "reflexive.");
        System.out.println("The relation is" + (relation.isSymmetric() ? " " : " not ") + "symmetric.");
    }
}