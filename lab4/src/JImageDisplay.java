import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;

public class JImageDisplay extends JComponent {
    private BufferedImage image;
    public JImageDisplay(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width,height));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public void clearImage() {
        int[] blank = new int[getWidth() * getHeight()];
        image.setRGB(0, 0, getWidth(), getHeight(), blank, 0, 1);
    }

    public void drawPixel(int x, int y, int rgbColor) {
        image.setRGB(x, y, rgbColor);
    }
}