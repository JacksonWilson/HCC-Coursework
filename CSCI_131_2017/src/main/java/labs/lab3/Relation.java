package labs.lab3;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import utils.KeyboardReader;

public class Relation {
    public static final int cardinality = 7;
    private int[][] relation;
    private List<Character> elements = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g');

    public Relation() {
        relation = new int[cardinality][cardinality];
        clearRelation();
    }

    public Relation(boolean populate) {
        this();
        if (populate)
            populateRelation();
    }

    public void clearRelation() {
        for (int[] row : relation) {
            for (int i = 0; i < row.length; i++) {
                row[i] = 0;
            }
        }
    }

    public void populateRelation() {
        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            int numElements;
            char relatedElement;
            for (Character element : elements) {
                numElements = keyReader.readInt(0, elements.size() - 1, "Number of elements related to \'" + element + "\': ");
                for (int i = 0; i < numElements; i++) {
                    relatedElement = keyReader.readChar("Element \'" + element + "\' is related to: ");
                    relation[convertLetterToNumber(element)][convertLetterToNumber(relatedElement)] = 1;
                }
                System.out.println();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

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

    public boolean isReflexive() {
        for (int r = 0; r < relation.length; r++) {
            if (relation[r][r] != 1)
                return false;
        }
        return true;
    }

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
        return elements.indexOf(Character.toLowerCase(c));
    }

    private Character convertNumberToLetter(int index) {
        return elements.get(index);
    }
}