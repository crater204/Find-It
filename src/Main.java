import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends JPanel {

    public void paint(Graphics g) {
        BufferedImage img = readImage();
        ArrayList<Point> edgePoints = findEdgePoints(img);
        Point center = findCenter(edgePoints);
        g.drawImage(img, 0, 0, this);
        for(int i = 0 ; i < edgePoints.size(); i++ ) {
            Point p = edgePoints.get(i);
           // g.drawOval(p.x, p.y, 5, 5);
        }
        g.setColor(Color.red);
        // g.fillOval(center.x, center.y, 10,10);

        int distance = findAverageDistance(center, edgePoints);
        drawBoundaries(g, center, distance);
    }

    private BufferedImage readImage() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/test4.jpg"));
        } catch (IOException ioe) {
            System.out.println(ioe);
            System.exit(-12);
        }
        return img;
    }

    private ArrayList<Point> findEdgePoints(BufferedImage img) {
        final int width = img.getWidth();
        final int height = img.getHeight();

        int[][] pixels = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = img.getRGB(x, y);
            }
        }
        System.out.println("Did it!");

        ArrayList<Point> edgePoints = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height - 1; y++) {

                int compare = Math.abs(pixels[x][y] - pixels[x][y + 1]);
                if (compare >= 1000000) {
                    edgePoints.add(new Point(x, y));
                }
            }
        }
        return edgePoints;
    }

    private Point findCenter(ArrayList<Point> points) {

        int x = 0;
        int y = 0;

        for(int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            x += p.x;
            y += p.y;
        }

        Point center = new Point(x / points.size(), y / points.size());
        return center;
    }

    private int findAverageDistance(Point center, ArrayList<Point> points) {

        double distance = 0;

        for(int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            distance += Math.sqrt((center.x - p.x) * (center.x - p.x) + (center.y - p.y) * (center.y - p.y));
        }

        return (int)(distance / points.size()) ;
    }

    private void drawBoundaries(Graphics g, Point center, int distance) {
        g.setColor(Color.blue);
        // g.fillOval(center.x + distance, center.y, 5, 5);
        // g.fillOval(center.x - distance, center.y, 5, 5);
        // g.fillOval(center.x, center.y + distance, 5, 5);
        // g.fillOval(center.x, center.y - distance, 5 ,5);

        // top left
        g.fillRect(center.x - distance - 15, center.y - distance - 15, 45, 15);
        g.fillRect(center.x - distance - 15, center.y - distance - 15, 15, 45);

        //bottom left
        g.fillRect(center.x - distance - 15, center.y + distance + 15, 45, 15);
        g.fillRect(center.x - distance - 15, center.y + distance - 15, 15, 45);

        // top right
        g.fillRect(center.x + distance - 15, center.y - distance - 15, 45, 15);
        g.fillRect(center.x + distance + 15, center.y - distance - 15, 15, 45);

        //bottom right
        g.fillRect(center.x + distance - 15, center.y + distance + 15, 45, 15);
        g.fillRect(center.x + distance + 15, center.y + distance - 15, 15, 45);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new Main());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setVisible(true);
    }
}
