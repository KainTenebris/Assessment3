package com.rear_admirals.york_pirates;

import java.util.ArrayList;

public class College {

	private final String name;
	private ArrayList<College> ally;
	private boolean bossDead;

	//Constructor
	public College(String name) {
		this.name = name;
		this.ally = new ArrayList<College>();
		this.ally.add(this);
		this.bossDead = false;
	}

	//Getters
	public String getName() { return name; }	
	public boolean isBossDead() { return bossDead; }
	public ArrayList<College> getAlly() { return ally; }
	
	//Setters
	public void addAlly(College newAlly){ ally.add(newAlly); }
	public void setBossDead(boolean bossDead) { this.bossDead = bossDead; }
}
