import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

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

        JComboBox jComboBox = new JComboBox<FractalGenerator>();
        jComboBox.addItem(new Mandelbrot());
        jComboBox.addItem(new Tricorn());
        jComboBox.addItem(new BurningShip());

        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);

        ResetListener resetListener = new ResetListener(jComboBox, chooser, frame);

        jComboBox.addActionListener(resetListener);
        jComboBox.setActionCommand(Button.COMBOBOX.toString());

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(resetListener);
        resetButton.setActionCommand(Button.RESET.toString());

        JPanel topJPanel = new JPanel();
        topJPanel.add(new JLabel("Fractal type:"));
        topJPanel.add(jComboBox);
        frame.add(topJPanel, BorderLayout.NORTH);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(resetListener);
        saveButton.setActionCommand(Button.SAVE.toString());

        JPanel bottomJPanel = new JPanel();
        bottomJPanel.add(saveButton);
        bottomJPanel.add(resetButton);
        frame.add(bottomJPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    class ResetListener implements ActionListener {
        JComboBox jComboBox;
        JFileChooser chooser;
        JFrame frame;
        ResetListener(JComboBox a, JFileChooser b, JFrame c) {
            jComboBox = a;
            chooser = b;
            frame = c;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (Button.valueOf(e.getActionCommand())) {
                case COMBOBOX:
                    fractal = (FractalGenerator) jComboBox.getSelectedItem();
                    fractal.getInitialRange(range);
                    drawFractal();
                    break;
                case RESET:
                    fractal.getInitialRange(range);
                    drawFractal();
                    break;
                case SAVE:
                    String errorMessage = null;
                    try {
                        if(chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                            File file = chooser.getSelectedFile();
                            if(file.canWrite())
                                ImageIO.write(display.getImage(), "png", file);
                            else
                                errorMessage = "Cannot save to this path/file";
                        }
                    }
                    catch (Exception exception) {
                        errorMessage = exception.getMessage();
                    }
                    finally {
                        if(errorMessage != null)
                            JOptionPane.showMessageDialog(frame, errorMessage, "Cannot save image" , JOptionPane.ERROR_MESSAGE);
                    }
            }
        }
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
/**
    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fractal.getInitialRange(range);
            drawFractal();
        }
    }
 **/
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

enum Button {
    COMBOBOX,
    RESET,
    SAVE
}
