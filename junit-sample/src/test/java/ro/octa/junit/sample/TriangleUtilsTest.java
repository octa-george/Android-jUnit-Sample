package ro.octa.junit.sample;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ro.octa.junit.sample.model.Point;
import ro.octa.junit.sample.model.Triangle;
import ro.octa.junit.sample.model.TriangleType;

/**
 * Created on 7/25/16.
 */
public class TriangleUtilsTest {

    private TriangleUtils utils;

    @Before
    public void setUp() {
        utils = new TriangleUtils();
    }

    @Test
    public void triangleUtils_calculateArea() {
        Assert.assertNotNull(utils);

        // given
        Triangle triangle = getAnyTriangle();

        // when
        double area = utils.calculateArea(triangle);

        // then
        Assert.assertTrue(area == 4);
    }

    @Test
    public void triangleUtils_calculateDistance() {
        Assert.assertNotNull(utils);

        // given
        Point p1 = new Point(1, 1);
        Point p2 = new Point(3, 4);

        // when
        int distance = utils.getDistance(p1, p2);

        // then
        Assert.assertTrue(distance == 5);
    }

    @Test
    public void triangleUtils_getType_Equilateral() {
        Assert.assertNotNull(utils);

        // given
        Triangle triangle = getEquilateralTriangle();

        // when
        TriangleType type = utils.getType(triangle);

        // then
        Assert.assertEquals(type, TriangleType.EQUILATERAL);
    }

    @Test
    public void triangleUtils_getType_Isosceles() {
        Assert.assertNotNull(utils);

        // given
        Triangle triangle = getIsoscelesTriangle();

        // when
        TriangleType type = utils.getType(triangle);

        // then
        Assert.assertEquals(type, TriangleType.ISOSCELES);
    }

    @Test
    public void triangleUtils_getType_Right() {
        Assert.assertNotNull(utils);

        // given
        Triangle triangle = getRightTriangle();

        // when
        TriangleType type = utils.getType(triangle);

        // then
        Assert.assertEquals(type, TriangleType.RIGHT);
    }

    @Test
    public void triangleUtils_getType_Any() {
        Assert.assertNotNull(utils);

        // given
        Triangle triangle = getAnyTriangle();

        // when
        TriangleType type = utils.getType(triangle);

        // then
        Assert.assertEquals(type, TriangleType.ANY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void triangleUtils_getType_ThrowsError() {
        Assert.assertNotNull(utils);

        // given
        Point point = new Point(1, 1);
        Triangle triangle = new Triangle(point, point, point);

        // when
        utils.getType(triangle);
    }

    private Triangle getEquilateralTriangle() {
        Point a = new Point(1, 1);
        Point b = new Point(2, 2);
        Point c = new Point(3, 1);
        return new Triangle(a, b, c);
    }

    private Triangle getIsoscelesTriangle() {
        Point a = new Point(2, 1);
        Point b = new Point(4, 3);
        Point c = new Point(4, 1);
        return new Triangle(a, b, c);
    }

    private Triangle getRightTriangle() {
        Point a = new Point(1, 2);
        Point b = new Point(3, 1);
        Point c = new Point(4, 4);
        return new Triangle(a, b, c);
    }

    private Triangle getAnyTriangle() {
        Point a = new Point(1, 3);
        Point b = new Point(2, 5);
        Point c = new Point(4, 1);
        return new Triangle(a, b, c);
    }
}
