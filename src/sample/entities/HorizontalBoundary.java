package sample.entities;

import sample.util.Point;

public class HorizontalBoundary extends Boundary {
    public HorizontalBoundary(Point location, double height, double width) {
        super(location, height, width);
    }

    @Override
    protected Point calculateEnd(Point location, double height, double width) {
        return location.plusX(width).plusY(height);
    }

    @Override
    protected double getLineWidth() {
        return getHeight();
    }

    @Override
    public String toString() {
        return "HorizontalBoundary{" +
                "start=" + getLocation() +
                ", height=" + getHeight() +
                ", width=" + getWidth() +
                ", end=" + getEnd() +
                '}';
    }
}
