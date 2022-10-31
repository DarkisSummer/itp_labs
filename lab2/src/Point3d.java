import java.util.Scanner;

public class Point3d extends Point2d {
    private double zCoord;

    public Point3d(double x, double y, double z) {
        super(x, y);
        zCoord = z;
    }

    public Point3d() {
        super();
        zCoord = 0;
    }

    public double getZ() {
        return zCoord;
    }

    public void setZ(double z) {
        this.zCoord = z;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Point3d other_point3d)) {
            return false;
        }
        return other_point3d.zCoord == zCoord && super.equals((Point2d) other);
    }

    public double distanceTo(Point3d other) {
        return round2(Math.sqrt(Math.pow(zCoord - other.zCoord, 2) + Math.pow(getX() - other.getX(), 2) + Math.pow(getY() - other.getY(), 2)));
    }

    public Point3d(Scanner in) {
        this(in.nextDouble(), in.nextDouble(), in.nextDouble());
    }
}