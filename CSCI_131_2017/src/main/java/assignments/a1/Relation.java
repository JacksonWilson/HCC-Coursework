package assignments.a1;

import java.io.IOException;

import utils.KeyboardReader;

/**
 * A relation matrix.
 * 
 * @author Jackson Wilson
 */
public class Relation {
    private static final char[] ELEMENTS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g' };
    public static final int cardinality = ELEMENTS.length;
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
                numElements = keyReader.readInt(0, cardinality,
                    "Number of elements related to \'" + element + "\': ");
                for (int i = 0; i < numElements; i++) {
                    relatedElement = keyReader.readChar("Element \'"
                        + element + "\' is related to: ", ELEMENTS);
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
     * Formatted printout of the relation's ordered pairs.
     */
    public void displayOrderedPairs() {
        String toPrint = "R = {";
        for (int r = 0; r < relation.length; r++) {
            for (int c = 0; c < relation[r].length; c++) {
                if (relation[r][c] == 1)
                    toPrint += "(" + convertNumberToLetter(r) + "," + convertNumberToLetter(c) + ") , ";
            }
        }
        if (toPrint.length() != 5)
            toPrint = toPrint.substring(0, toPrint.length() - 3);
        System.out.println(toPrint + "}\n");
    }

    /**
     * Short circuting matrix reflection checker.
     * 
     * @return Whether the relation matrix is reflexive or not.
     */
    public boolean isReflexive() {
        for (int r = 0; r < relation.length; r++) {
            if (relation[r][r] != 1)
                return false;
        }
        return true;
    }

    /**
     * Short circuting matrix irreflection checker.
     * 
     * @return Whether the relation matrix is irreflexive or not.
     */
    public boolean isIrreflexive() {
        for (int r = 0; r < relation.length; r++) {
            if (relation[r][r] == 1)
                return false;
        }
        return true;
    }

    /**
     * Short circuting matrix symmetry checker.
     * 
     * @return Whether the relation matrix is symmetric or not.
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

    /**
     * Short circuting matrix asymmetry checker.
     * 
     * @return Whether the relation matrix is asymmetric or not.
     */
    public boolean isAsymmetric() {
        return isAntiSymmetric() && isIrreflexive();
    }
    
    /**
     * Short circuting matrix anti-symmetry checker.
     * 
     * @return Whether the relation matrix is anti-symmetric or not.
     */
    public boolean isAntiSymmetric() {
        for (int r = 0, c = 1; r < relation.length - 1; r++) {
            for (; c < relation[r].length; c++) {
                if (relation[r][c] == 1 && relation[c][r] == 1) {
                    System.out.println(relation[r][c] + " " + relation[c][r]);
                    return false;
                }
            }
            c = r + 1;
        }
        return true;
    }

    /**
     * Short circuting matrix transitivity checker.
     * 
     * @return Whether the relation matrix is transitive or not.
     */
    public boolean isTransitive() {
        int[][] copiedRelation = new int[cardinality][cardinality];
        copyRelation(copiedRelation);

        int[][] product = new int[cardinality][cardinality];
        booleanProduct(copiedRelation, product);

        for (int r = 0; r < relation.length; r++) {
            for (int c = 0; c < relation[r].length; c++) {
                if (relation[r][c] != product[r][c])
                    return false;
            }
        }
        return true;
    }

    /**
     * Copies this relation into another matrix.
     * 
     * @param dstRelation Destination for copied relation matrix.
     * @throws IllegalArgumentException If dstRelation is not the same cardinality as this relation.
     */
    private void copyRelation(int[][] dstRelation) throws IllegalArgumentException {
        if (dstRelation.length != relation.length || dstRelation[0].length != relation[0].length)
            throw new IllegalArgumentException("Expected dstRelation to have the same cardinality as relation: " + cardinality);
        
        for (int r = 0; r < relation.length; r++) {
            for (int c = 0; c < relation[r].length; c++)
                dstRelation[r][c] = relation[r][c];
        }
    }

    /**
     * 
     * @param otherRelation The other relation matrix.
     * @param product The out matrix for the product.
     * @throws IllegalArgumentException If otherRelation or product does not have the same cardinality as this relation.
     */
    private void booleanProduct(int[][] otherRelation, int[][] product) throws IllegalArgumentException {
        if (otherRelation.length != relation[0].length)
            throw new IllegalArgumentException("Expected otherRelation to have the same cardinality as relation: " + cardinality);
        if (product.length != relation.length || product[0].length != otherRelation[0].length)
            throw new IllegalArgumentException("Expected product to be: " + relation.length + "x" + otherRelation[0].length);
        for (int r = 0; r < relation.length; r++) {
            for (int c = 0; c < relation[r].length; c++) {
                for (int i = 0; i < relation.length; i++) {
                    if (relation[r][i] == 1 && otherRelation[i][c] == 1) {
                        product[r][c] = 1;
                        break;
                    }
                    product[r][c] = 0;
                }
            }
        }
    }

    /**
     * Accepts a letter from the array of valid elements and returns the corresponding
     * matrix index.
     * 
     * @param ch A character.
     * @return The corresponding matrix index.
     */
    private int convertLetterToNumber(char ch) {
        for (int i = 0; i < ELEMENTS.length; i++) {
            if (ELEMENTS[i] == Character.toLowerCase(ch))
                return i;
        }
        return -1;
    }

    /**
     * Accepts an int corresponding to an index from the array of valid elements.
     * 
     * @param index A valid index of the ELEMENTS array.
     * @return ch A character from the ELEMENTS array.
     */
    private char convertNumberToLetter(int index) {
        return ELEMENTS[index];
    }
}
