package sample.entities;

import sample.util.Point;

public class VerticalBoundary extends Boundary {
    public VerticalBoundary(Point location, double height, double width) {
        super(location, height, width);
    }

    @Override
    protected Point calculateEnd(Point location, double height, double width) {
        return location.plusY(getHeight());
    }

    @Override
    protected double getLineWidth() {
        return getWidth();
    }
}
