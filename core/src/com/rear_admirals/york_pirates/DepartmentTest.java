package com.rear_admirals.york_pirates;

import static org.junit.Assert.*;
import org.junit.Test;

import com.rear_admirals.york_pirates.Department.Stat;

public class DepartmentTest {

	@Test
	public void testDepartment() {
		Department CS = new Department("CS", Stat.Attack, null);
		assertNotNull(CS);
	}

	@Test
	public void testGetName() {
		Department CS = new Department("CS", Stat.Attack, null);

		assertTrue(CS.getName().equals("CS"));
	}

	@Test
	public void testGetProduct() {
		Department CS = new Department("CS", Stat.Attack, null);

		assertEquals(CS.getProduct(), Stat.Attack);
	}

	@Test
	public void testGetPrice() {
		Department CS = new Department("CS", Stat.Attack, null);

		assertEquals(CS.getPrice(), 10);
	}

	@Test
	public void testPurchase() {
		System.out.println("Black Box: Department.purchase(int). An instance of PirateGame is required - i.e. the full game needs to run.");
	}

}
