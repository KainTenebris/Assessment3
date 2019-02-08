package com.rear_admirals.york_pirates;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShipTypeTest {

    @Test
    public void testGetName() {
        ShipType test = new ShipType("test", 1, 1, 1, 1);

        assertTrue(test.getName().equals("test"));
    }

    @Test
    public void testGetAttack() {
        ShipType test = new ShipType("test", 1, 1, 1, 1);

        assertEquals(test.getAttack(), 1);
    }

    @Test
    public void testGetDefence() {
        ShipType test = new ShipType("test", 1, 1, 1, 1);

        assertEquals(test.getDefence(), 1);
    }

    @Test
    public void testGetAccuracy() {
        ShipType test = new ShipType("test", 1, 1, 1, 1);

        assertEquals(test.getAccuracy(), 1);
    }

    @Test
    public void testGetHealth() {
        ShipType test = new ShipType("test", 1, 1, 1, 1);

        assertEquals(test.getHealth(), 1);
    }
}