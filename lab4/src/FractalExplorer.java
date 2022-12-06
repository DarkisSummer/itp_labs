import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JButton;

public class FractalExplorer {
    private int size;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    FractalExplorer(int size, FractalGenerator fractal) {
        this.size = size;
        this.fractal = fractal;
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
    }

    private void createAndShowGUI() {
        display = new JImageDisplay(size, size);
        display.addMouseListener(new ImageMouseListener());
        display.setLayout(new BorderLayout());

        JFrame frame = new JFrame("FractalExplorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(display, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetListener());
        frame.add(resetButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal() {
        for(int x = 0; x < size; x++) {
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, size, x);
            for(int y = 0; y < size; y++) {
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, size, y);
                int iteration = fractal.numIterations(xCoord, yCoord);
                if (iteration == -1) {
                    display.drawPixel(x, y, 0);
                }
                else {
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    display.drawPixel(x, y, rgbColor);
                }
            }
        }
        display.repaint();
    }

    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fractal.getInitialRange(range);
            drawFractal();
        }
    }
    class ImageMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, size, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, size, e.getY());
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
        @Override
        public void mousePressed(MouseEvent e) {
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    public static void main(String[] args) {
        FractalExplorer fractal = new FractalExplorer(800, new Mandelbrot());
        fractal.createAndShowGUI();
        fractal.drawFractal();
    }
}
