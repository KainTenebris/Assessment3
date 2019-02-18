package com.rear_admirals.york_pirates;

import static java.lang.Math.max;
import static java.lang.Math.pow;
/**Creates the Department type building and lets the user purchase from them
 *@param name The name of the college
 *@param product the product the user can buy from the department
 *@param pirateGame The game instance itself
 */
public class Department {

    private final String name;
    private Stat product;
    private int base_price;
    private PirateGame pirateGame;

    public enum Stat {
        Attack, Defence, Minigame;
    }

    //Constructor
    /**Initialises a department objects with a name, product, base price and pirate game as parameters
    *@param name The name of the college
    *@param product the product the user can buy from the department
    *@param pirateGame The game instance itself             */
    public Department(String name, Stat product, PirateGame pirateGame) {
        this.name = name;
        this.product = product;
        this.base_price = 10;
        this.pirateGame = pirateGame;
    }
    
    //Getters
    /**Returns the name of the department
     * @return name The name of the Department
     */
    public String getName() {
        return name; }
    /**Returns the product of the department
     *@return product Thi is the product the user can buy
     */
    public Stat getProduct() {
        return product; }
    /**Returns the base price of a product
     * @return bace_price This is the price of the product
     */
    public int getPrice() {
        if (product == Stat.Defence || product == Stat.Attack) {
            return base_price;
        } else {return 0;}
    }
    //Upgrades stat of the ship(product of dep) by 1
    /**Based on the product type the department offers, the stat of the ship is increased by 1
     * @return True if the action was completed
     */

    public boolean purchase(){
        if (!pirateGame.getPlayer().payGold(getPrice())){ return false; }

        switch (product) {
            case Attack:
                pirateGame.getPlayer().getPlayerShip().setAttack(pirateGame.getPlayer().getPlayerShip().getAttack() + 1);
                return true;
            case Defence:
                pirateGame.getPlayer().getPlayerShip().setDefence(pirateGame.getPlayer().getPlayerShip().getDefence() + 1);
                return true;
            default:
                return false;
        }
    }
}
