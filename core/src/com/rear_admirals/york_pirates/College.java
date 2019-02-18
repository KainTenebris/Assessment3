package com.rear_admirals.york_pirates;

import java.util.ArrayList;
/** This class creates the collgege type building and enables it to have allies and a boss that is alive or dead*/
public class College {

	private final String name;
	private ArrayList<College> ally;
	private boolean bossDead;

	//Constructor
	/**This initialises a college building with a list for allies and a
	 * the existence of a boss that is not dead yet.
	 * @param name this takes the college name as parameters*/
	public College(String name) {
		this.name = name;
		this.ally = new ArrayList<College>();
		this.ally.add(this);
		this.bossDead = false;
	}

	//Getters
	/**Returns the name of the college
	 * @return name This returns the name of the college
	 */
	public String getName() {
		return name; }
	/**Returns true or false depending on if the boss is dead
	 * @return bossdead boolean*/
	public boolean isBossDead() {
		return bossDead; }
	/**Retrieves the ally list for that college
	 * @return ally list
	 */
	public ArrayList<College> getAlly() {
		return ally; }
	
	//Setters
	/**Lets us add a college to the ally list
	 * @param newAlly The new college that has become allies with the current college
	 */
	public void addAlly(College newAlly){
		ally.add(newAlly); }

	/**Sets the boss to dead when the boss of a college has been defeated. Takes boolean 'bossDead' as parameters
	 * @param bossDead The bossDead variable that tells us if it alive or not
	 */
	public void setBossDead(boolean bossDead) {
		this.bossDead = bossDead; }
}
