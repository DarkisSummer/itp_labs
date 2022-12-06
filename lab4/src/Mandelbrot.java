import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }


    // Zn = (Zn-1)^2 + c
    @Override
    public int numIterations(double x, double y) {
        double real = 0;
        double imaginary = 0;
        for(int i = 0; i < MAX_ITERATIONS; i++) {
            double newReal = real * real - imaginary * imaginary + x;
            double newImaginary = 2 * real * imaginary + y;
            if (real * real + imaginary * imaginary >= 4)
                return i;
            real = newReal;
            imaginary = newImaginary;
        }
        return -1;
    }
}
