package labs.lab12;

/**
 * Determines if the inputs are palindromes.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Palindrome">https://en.wikipedia.org/wiki/Palindrome</a>
 * @author Jackson Wilson
 */
public class PalindromeFinder {

    /**
     * Determines if a number is a palindrome using recursion.
     * 
     * @param n A potential palindrome number.
     * @return Whether the number is a palindrome.
     */
    public static boolean isPalindrome(Integer n) {
        String nStr = n.toString();
        if (nStr.length() == 0 || nStr.length() == 1)
            return true;

        if (nStr.charAt(0) == nStr.charAt(nStr.length() - 1))
            return isPalindrome(Integer.parseInt(nStr.substring(1, nStr.length() - 1)));

        return false;
    }
}