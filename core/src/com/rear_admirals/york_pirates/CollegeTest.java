package com.rear_admirals.york_pirates;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Test;

public class CollegeTest {

	@Test
	public void testCollege() {
		College goodricke = new College("Goodricke");
		assertNotNull(goodricke);
	}

	@Test
	public void testGetName() {
		College goodricke = new College("Goodricke");
		assertTrue(goodricke.getName().equals("Goodricke"));
	}
	
	//Tests both isBossDead() and setBossDead(Boolean)
	@Test
	public void testIsBossDead() {
		College goodricke = new College("Goodricke");
		assertFalse(goodricke.isBossDead());
		
		goodricke.setBossDead(true);
		assertTrue(goodricke.isBossDead());
	}

	//Tests both getAlly() and addAlly(College)
	@Test
	public void testAddAlly() {
		College goodricke = new College("Goodricke");
		ArrayList<College> allies = new ArrayList<College>();
		allies.add(goodricke);
		
		assertEquals(goodricke.getAlly(), allies);
		
		College allyCollege = new College("ally");
		goodricke.addAlly(allyCollege);
		allies.add(allyCollege);
		
		assertEquals(goodricke.getAlly(), allies);
	}
}
