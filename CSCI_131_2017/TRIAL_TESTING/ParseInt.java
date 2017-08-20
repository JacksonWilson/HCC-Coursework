import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Pattern;

public class ParseInt {

    private static String[] strs;
    private static int[] expectedInts;

    static {
        strs = new String[1000000];
        expectedInts = new int[10000];
        Random rand = new Random(System.nanoTime());
        for (int i = 0; i < expectedInts.length; i++) {
            strs[i] = generateNumericString(rand, 8);
            try {
                expectedInts[i] = Integer.parseInt(strs[i]);
            } catch (NumberFormatException nfe) {
                System.out.println("Failed to parse int. " + i);
            }
        }

        for (int i = expectedInts.length; i < strs.length; i++) {
            strs[i] = generateString(rand, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 8);
        }
    }

    public static void main(String[] args) {
        exceptionTrial();
        regexTrial();
        compiledRegexTrial();
        customCharacterCheckTrial();
    }
    
    private static boolean assertExpectedInts(int[] actualInts) {
        for (int i = 0; i < expectedInts.length; i++) {
            if (expectedInts[i] != actualInts[i])
                return false;
        }
        for (int i = expectedInts.length; i < actualInts.length; i++) {
            if (actualInts[i] != 0)
                return false;
        }
        return true;
    }
    
    private static String generateString(Random rng, String characters, int length)  {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        return new String(text);
    }
    
    private static String generateNumericString(Random rng, int length) {
        return generateString(rng, "0123456789", length);
    }

    private static void exceptionTrial() {
        int[] ints = new int[strs.length];
        long startTime = System.nanoTime();
        for (int i = 0, c = 0; i < strs.length; i++) {
            try {
                ints[c] = Integer.parseInt(strs[i]);
                c++;
            } catch (NumberFormatException nfe) {}
        }
        long endTime = System.nanoTime();
        System.out.println(assertExpectedInts(ints) + " Exception Trial: " + new DecimalFormat("#,###").format(endTime - startTime) + " nanoseconds.");
    }

    private static void regexTrial() {
        int[] ints = new int[strs.length];
        long startTime = System.nanoTime();
        for (int i = 0, c = 0; i < strs.length; i++) {
            if (strs[i].matches("(\\+|-)?\\d+")) {
                try {
                    ints[c] = Integer.parseInt(strs[i]);
                    c++;
                } catch (NumberFormatException nfe) {}
            }
        }
        long endTime = System.nanoTime();
        System.out.println(assertExpectedInts(ints) + " Regex Trial: " + new DecimalFormat("#,###").format(endTime - startTime) + " nanoseconds.");
    }

    private static void compiledRegexTrial() {
        Pattern numPattern = Pattern.compile("(\\+|-)?\\d+");
        int[] ints = new int[strs.length];
        long startTime = System.nanoTime();
        for (int i = 0, c = 0; i < strs.length; i++) {
            if (numPattern.matcher(strs[i]).matches()) {
                try {
                    ints[c] = Integer.parseInt(strs[i]);
                    c++;
                } catch (NumberFormatException nfe) {}
            }
        }
        long endTime = System.nanoTime();
        System.out.println(assertExpectedInts(ints) + " CompiledRegex Trial: " + new DecimalFormat("#,###").format(endTime - startTime) + " nanoseconds.");
    }

    private static void customCharacterCheckTrial() {
        int[] ints = new int[strs.length];
        long startTime = System.nanoTime();
        for (int i = 0, c = 0; i < strs.length; i++) {
            if (checkCharactersAreNumeric(strs[i])) {
                try {
                    ints[c] = Integer.parseInt(strs[i]);
                    c++;
                } catch (NumberFormatException nfe) {}
            }
        }
        long endTime = System.nanoTime();
        System.out.println(assertExpectedInts(ints) + " CustomCharacterCheck Trial: " + new DecimalFormat("#,###").format(endTime - startTime) + " nanoseconds.");
    }

    private static boolean checkCharactersAreNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }
}