package com.rear_admirals.york_pirates;

import static java.lang.Math.max;
import static java.lang.Math.pow;

public class Department {

    private final String name;
    private Stat product;
    private int base_price;
    private PirateGame pirateGame;

    public enum Stat {
        Attack, Defence, Minigame;
    }

    //Constructor
    public Department(String name, Stat product, PirateGame pirateGame) {
        this.name = name;
        this.product = product;
        this.base_price = 10;
        this.pirateGame = pirateGame;
    }
    
    //Getters
    public String getName() { return name; }
    public Stat getProduct() { return product; }
    public int getPrice() {
        if (product == Stat.Defence || product == Stat.Attack) {
            return base_price;
        } else {return 0;}
    }

    //Upgrades stat of the ship(product of dep) by 1
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
