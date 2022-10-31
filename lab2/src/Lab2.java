import java.util.Scanner;

public class Lab2 {
    public static void main(String[] args) {
        System.out.println("Input 3 3d dot coordinates");
        Scanner in = new Scanner(System.in);
        Point3d a = new Point3d(in), b = new Point3d(in), c = new Point3d(in);
        if(a.equals(b) || a.equals(c) || b.equals(c)) {
            System.out.println("Some points are equal, area = 0");
        }
        else {
            System.out.printf("Area = %f\n", computeArea(a, b, c));
        }
    }

    public static double computeArea(Point3d a, Point3d b, Point3d c) {
        double ab = a.distanceTo(b), ac = a.distanceTo(c), bc = b.distanceTo(c);
        double p = (ab+ac+bc)/2;
        return Math.sqrt(p*(p-ab)*(p-ac)*(p-bc));
    }
}
