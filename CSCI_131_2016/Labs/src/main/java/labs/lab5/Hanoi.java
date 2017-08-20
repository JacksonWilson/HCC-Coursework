package labs.lab5;

import java.util.Scanner;

public class Hanoi {
    private Move[] moves;
    private int numDisks;
    private int moveCounter;

    public Hanoi(int numDisks) {
        this.numDisks = numDisks;
        this.moves = new Move[(int)Math.pow(2, numDisks) - 1];
    }

    public Move[] solve(int startPegNum, int tempPegNum, int destPegNum) {
        moveCounter = 0;
        recurse(numDisks, startPegNum, tempPegNum, destPegNum);
        printMoves();
        return moves;
    }

    public void printMoves() {
        for (Move m : moves)
            System.out.println(m);
    }

    public void recurse(int numDisks, int startPegNum, int tempPegNum, int destPegNum) {
        if (numDisks == 1) {
           moves[moveCounter++] = new Move(startPegNum, destPegNum);
       } else {
           recurse(numDisks - 1, startPegNum, destPegNum, tempPegNum);
           moves[moveCounter++] = new Move(startPegNum, destPegNum);
           recurse(numDisks - 1, tempPegNum, startPegNum, destPegNum);
       }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        int numDisks = 0, startPegNum = 0, destPegNum = 0, tempPegNum = 0;
        boolean validInput = false;
        do {
            System.out.print("Enter total number of disks to move: ");
            if (scanner.hasNextLine())
                input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    numDisks = Integer.parseInt(input);
                    if (numDisks < 0) {
                        System.out.println("Number cannot be negative.");
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }
            } else {
                System.out.println("Number cannot be empty.");
            }
        } while (!validInput);
        validInput = false;
        do {
            System.out.print("Enter the start peg\'s num: ");
            if (scanner.hasNextLine())
                input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    startPegNum = Integer.parseInt(input);
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }
            } else {
                System.out.println("Number cannot be empty.");
            }
        } while (!validInput);
        validInput = false;
        do {
            System.out.print("Enter the temporary peg\'s num: ");
            if (scanner.hasNextLine())
                input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    tempPegNum = Integer.parseInt(input);
                    if (tempPegNum != startPegNum) {
                        validInput = true;
                    } else {
                        System.out.println("Number must be unique. " + startPegNum + " has already been taken.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }
            } else {
                System.out.println("Number cannot be empty.");
            }
        } while (!validInput);validInput = false;
        do {
            System.out.print("Enter the destination peg\'s num: ");
            if (scanner.hasNextLine())
                input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    destPegNum = Integer.parseInt(input);
                    if (destPegNum != startPegNum && destPegNum != tempPegNum) {
                        validInput = true;
                    } else {
                        System.out.println("Number must be unique. " + startPegNum + " and " + tempPegNum + " have already been taken.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }
            } else {
                System.out.println("Number cannot be empty.");
            }
        } while (!validInput);
        

        scanner.close();
        Hanoi h1 = new Hanoi(numDisks);
        h1.solve(startPegNum, tempPegNum, destPegNum);
    }
}