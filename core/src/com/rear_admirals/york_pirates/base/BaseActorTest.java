package com.rear_admirals.york_pirates.base;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.Department;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseActorTest {

    //tests getDepartment and setDepartment
    @Test
    public void testGetDepartment() {
        BaseActor test = new BaseActor();
        assertNull(test.getDepartment());

        Department testDep = new Department("testDep", Department.Stat.Attack, null);
        test.setDepartment(testDep);
        assertEquals(test.getDepartment(), testDep);
    }

    //tests getCollege and setCollege
    @Test
    public void testGetCollege() {
        BaseActor test = new BaseActor();
        assertNull(test.getCollege());

        College testColl = new College("testColl");
        test.setCollege(testColl);
        assertEquals(test.getCollege(), testColl);
    }

    @Test
    public void testSetOriginCentre() {
        BaseActor test = new BaseActor();

        test.setSize(20, 20);

        test.setOriginCentre();
        assertEquals(test.getOriginX(), test.getWidth()/2, 0.1);
        assertEquals(test.getOriginY(), test.getHeight()/2, 0.1);
    }

    //Also tests getBoundingPolygon
    @Test
    public void testSetRectangleBoundary() {
        BaseActor test = new BaseActor();
        Polygon testBound;

        test.setSize(20, 20);

        float w = test.getWidth();
        float h = test.getHeight();
        float[] vertices = {0, 0, w, 0, w, h, 0, h};
        testBound = new Polygon(vertices);
        testBound.setOrigin(test.getOriginX(), test.getOriginY());

        test.setRectangleBoundary();

        assertArrayEquals(test.getBoundingPolygon().getTransformedVertices(), testBound.getTransformedVertices(), 0.1f);
    }

    @Test
    public void testSetEllipseBoundary() {
        BaseActor test = new BaseActor();
        Polygon testBound;

        test.setSize(20, 20);

        int n = 8;
        float w = test.getWidth();
        float h = test.getHeight();
        float[] vertices = new float[2*n];
        for (int i = 0; i < n; i++) {
            float t = i*6.28f/n;
            // x-coordinate
            vertices[2*i] = w/2 * MathUtils.cos(t) + w/2;
            // y-coordinate
            vertices[2*i+1] = h/2 * MathUtils.sin(t) + h/2;
        }
        testBound = new Polygon(vertices);
        testBound.setOrigin(test.getOriginX(), test.getOriginY());

        assertArrayEquals(testBound.getTransformedVertices(), test.getBoundingPolygon().getTransformedVertices(), 0.1f);
    }

    @Test
    public void testOverlaps() {
        BaseActor test1 = new BaseActor();
        BaseActor test2 = new BaseActor();

        test1.setSize(5, 5);
        test2.setSize(5, 5);

        test1.setRectangleBoundary();
        test2.setRectangleBoundary();

        assertTrue(test1.overlaps(test2, true));
        assertFalse(test1.overlaps(test2, false));

        test1.moveBy(10, 0);

        assertFalse(test1.overlaps(test2, false));


    }
}