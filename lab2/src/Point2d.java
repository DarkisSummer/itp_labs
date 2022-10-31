public class Point2d {
    private double xCoord;
    private double yCoord;
    public Point2d(double x, double y) {
        xCoord = x;
        yCoord = y;
    }
    public Point2d() {
        this(0, 0);
    }
    public double getX() {
        return xCoord;
    }
    public double getY() {
        return yCoord;
    }
    public void setX(double val) {
        xCoord = val;
    }
    public void setY(double val) {
            yCoord = val;
    }
    public double round2(double x) {
        return Math.round(x * 100) / 100.0;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Point2d other_point2d)) {
            return false;
        }
        return other_point2d.xCoord == xCoord && other_point2d.yCoord == yCoord;
    }
}