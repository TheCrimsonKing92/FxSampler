package sample.util;

public class Point {
    private double x;
    private double y;

    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(double x, double y) {
        return new Point(x, y);
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public Point minusX(double diff) { return Point.of(x - diff, y); }

    public Point minusY(double diff) { return Point.of(x, y - diff); }

    public Point plusX(double diff) {
        return Point.of(x + diff, y);
    }

    public Point plusY(double diff) {
        return Point.of(x, y + diff);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
