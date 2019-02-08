package com.rear_admirals.york_pirates;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testGetPlayerShip() {
        Player player = new Player(null);
        assertNull(player.getPlayerShip());

        System.out.println("Black Box: Player.getPlayerShip() with an actual ship, only tested with null");
    }

    //Tests getPoints and setPoints
    @Test
    public void testGetPoints() {
        Player player = new Player(null);
        assertEquals(player.getPoints(), 0);

        player.setPoints(50);
        assertEquals(player.getPoints(), 50);
    }

    //Tests getGold, setGold, addGold and payGold
    @Test
    public void testGetGold() {
        Player player = new Player(null);
        assertEquals(player.getGold(), 50);

        player.setGold(0);
        assertEquals(player.getGold(), 0);

        player.addGold(30);
        assertEquals(player.getGold(), 30);

        player.payGold(20);
        assertEquals(player.getGold(), 10);
    }

    @Test
    public void testSetPlayerShip() {
        System.out.print("Black Box: Player.setPlayerShip(Ship), untested since libgdx cannot assign a texture without the full game running");
    }
}