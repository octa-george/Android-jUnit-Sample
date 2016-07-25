package ro.octa.junit.sample;

import ro.octa.junit.sample.model.Point;
import ro.octa.junit.sample.model.Triangle;
import ro.octa.junit.sample.model.TriangleType;

/**
 * Created on 7/25/16.
 */
public class TriangleUtils {

    public double calculateArea(Triangle triangle) {
        return Math.abs((triangle.getA().getX() - triangle.getC().getX()) * (triangle.getB().getY() - triangle.getA().getY()) -
                (triangle.getA().getX() - triangle.getB().getX()) * (triangle.getC().getY() - triangle.getA().getY())) * 0.5;
    }

    public int getDistance(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    public TriangleType getType(Triangle triangle) {
        int a = getDistance(triangle.getA(), triangle.getB());
        int b = getDistance(triangle.getA(), triangle.getC());
        int c = getDistance(triangle.getB(), triangle.getC());

        if (a <= 0 || b <= 0 || c <= 0)
            throw new IllegalArgumentException("Length of sides cannot be equal to or less than zero");

        if (a == b && b == c)
            return TriangleType.EQUILATERAL;
        else if ((a == b) || (b == c) || (c == a))
            return TriangleType.ISOSCELES;
        else if ((c > a && c > b) ? (Math.sqrt((a * a) + (b * b)) == c) :
                (a > c && a > b) ? (Math.sqrt((c * c) + (b * b)) == a) :
                        (b > c && b > a) && (Math.sqrt((c * c) + (a * a)) == b))
            return TriangleType.RIGHT;
        else
            return TriangleType.ANY;
    }

}
