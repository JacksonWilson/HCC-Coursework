package labs.lab12;

/**
 * Determines if the inputs are palindromes.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Palindrome">https://en.wikipedia.org/wiki/Palindrome</a>
 * @author Jackson Wilson
 */
public class PalindromeFinder {

    /**
     * Determines if a number is a palindrome.
     * 
     * @param n A potential palindrome number.
     * @return Whether the number is a palindrome.
     */
    public static boolean isPalindrome(Integer n) {
        String nStr = n.toString();
        for (int i = 0; i < nStr.length() / 2; i++) {
            if (nStr.charAt(i) != nStr.charAt(nStr.length() - i - 1))
                return false;
        }
        return true;
    }
}