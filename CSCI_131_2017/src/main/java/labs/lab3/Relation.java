package labs.lab3;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import utils.KeyboardReader;

/**
 * A relation matrix.
 * 
 * @author Jackson Wilson
 */
public class Relation {
    private static final List<Character> ELEMENTS = Arrays.asList(
        'a', 'b', 'c', 'd', 'e', 'f', 'g');
    public static final int cardinality = ELEMENTS.size();
    private int[][] relation;

    /**
     * Instantiates relation matrix with 0's.
     */
    public Relation() {
        relation = new int[cardinality][cardinality];
        clearRelation();
    }

    /**
     * Instantiates relation matrix with 0's and optionally populates the relation.
     */
    public Relation(boolean populate) {
        this();
        if (populate)
            populateRelation();
    }

    /**
     * Sets all relations to 0.
     */
    public void clearRelation() {
        for (int[] row : relation) {
            for (int i = 0; i < row.length; i++) {
                row[i] = 0;
            }
        }
    }

    /**
     * Prompts user for relations and populates matrix with corresponding 1's.
     */
    public void populateRelation() {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            int numElements;
            char relatedElement;
            for (Character element : ELEMENTS) {
                numElements = keyReader.readInt(0, cardinality - 1,
                    "Number of elements related to \'" + element + "\': ");
                for (int i = 0; i < numElements; i++) {
                    relatedElement = keyReader.readChar("Element \'"
                        + element + "\' is related to: ");
                    relation[convertLetterToNumber(element)]
                        [convertLetterToNumber(relatedElement)] = 1;
                }
                System.out.println();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Formatted printout of the relation's matrix.
     */
    public void displayMatrixOfRelation() {
        System.out.println("Matrix of the relation\n----------------------");
        for (int[] row : relation) {
            for (int element : row) {
                System.out.print(" " + element);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Short circuting matrix reflection checker.
     */
    public boolean isReflexive() {
        for (int r = 0; r < relation.length; r++) {
            if (relation[r][r] != 1)
                return false;
        }
        return true;
    }

    /**
     * Short circuting matrix symmetry checker.
     */
    public boolean isSymmetric() {
        for (int r = 0, c = 1; r < relation.length - 1; r++) {
            for (; c < relation[r].length; c++) {
                if (relation[r][c] != relation[c][r])
                    return false;
            }
            c = r + 1;
        }
        return true;
    }

    private int convertLetterToNumber(char c) {
        return ELEMENTS.indexOf(Character.toLowerCase(c));
    }

    private Character convertNumberToLetter(int index) {
        return ELEMENTS.get(index);
    }
}