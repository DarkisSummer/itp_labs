public class Palindrome {
    public static void main(String[] args) {
        for(int i = 0; i < args.length; i++) {
            String s = args[i];
            System.out.printf("%s - %s palindrome%n",s,isPalindrome(s)?"a":"not a");
        }
    }

    public static String reverseString(String s) { // creating a reverse word of inputted
        int length = s.length();
        String reverse = "";
        for(int i = length-1; i >= 0; i--) {
            reverse = reverse + s.charAt(i);
        }
        return reverse;
    }

    public static boolean isPalindrome(String s) { // checking if word is palindrome
        return s.equals(reverseString(s));
    }
}
