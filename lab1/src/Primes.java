public class Primes {
    public static void main(String[] args) {
        for (int n = 2; n <= 100; n++) {
            if (isPrime(n)) {
                System.out.println(n + " is prime");
            }
            else {
                System.out.println(n);
            }
        }
    }

    public static boolean isPrime(int n) { // checks if number is prime
        boolean check = true;
        for (int i = 2; i <= n/2; i++) {
            if (n % i == 0) {
                check = false;
                break;
            }
        }
        return check;
    }
}
