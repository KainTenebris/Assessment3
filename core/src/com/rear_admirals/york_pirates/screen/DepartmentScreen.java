package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.*;
import com.rear_admirals.york_pirates.base.BaseScreen;

public class DepartmentScreen extends BaseScreen {

    private Player player;
    private Label pointsLabel;
    private Label goldLabel;
    private int toHeal;

    //Constructor
    public DepartmentScreen(final PirateGame main, final Department department){
        super(main);
        
        this.player = main.getPlayer();
        this.toHeal = player.getPlayerShip().getHealthMax() - player.getPlayerShip().getHealth();
        
        //Labels
        //Create labels
        this.pointsLabel = new Label(Integer.toString(main.getPlayer().getPoints()), main.getSkin());
        this.goldLabel = new Label(Integer.toString(main.getPlayer().getGold()), main.getSkin());
        final Label message = new Label("", main.getSkin());
        Label pointsTextLabel = new Label("Points: ", main.getSkin());
        Label goldTextLabel = new Label("Gold:", main.getSkin());
        Label title = new Label(department.getName(), main.getSkin());
        
        //set alignment for labels
        pointsLabel.setAlignment(Align.left);
        goldLabel.setAlignment(Align.left);

        //TextButtons
        //create Buttons
        final TextButton upgrade;
        final TextButton heal = new TextButton("Repair Ship for "+ toHeal/10 +" gold", main.getSkin());
        final TextButton leave = new TextButton("Leave", main.getSkin());
        
        //edit text on buttons
        if(department.getProduct() == Department.Stat.Minigame) {
            upgrade = new TextButton("Enter the tavern to gamble!", main.getSkin());
        } else {
            upgrade = new TextButton("Upgrade Ship "+ department.getProduct().toString() + " for " + department.getPrice() + " gold", main.getSkin());
        }
        if (toHeal == 0) { heal.setText("Your ship is already fully repaired!"); }
        
        //Listeners for buttons
        upgrade.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(department.getProduct() == Department.Stat.Minigame) {
                    startMinigame();
                    upgrade.setText("Enter the tavern to gamble!");
                } else {
                    department.purchase();
                    upgrade.setText("Upgrade Ship " + department.getProduct().toString() + " for " + department.getPrice() + " gold");
                }
            }
        });
        heal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (toHeal==0){
                    heal.setText("Your ship is already fully repaired!");
                }
                else {
                    if (player.payGold(toHeal / 10)) {
                        player.getPlayerShip().setHealth(player.getPlayerShip().getHealthMax());
                        message.setText("Successful repair");
                        heal.setText("Your ship is already fully repaired!");
                    } else {
                        message.setText("You don't have the funds to repair your ship");
                    }
                }
            }
        });
        leave.addListener(new ClickListener() {
           public void clicked(InputEvent event, float x, float y) {
               pirateGame.setScreen(pirateGame.getSailingScene());
           }
        });

        //Tables
        //Create tables
        Table uiTable = new Table();
        Table optionsTable = new Table();
        
        //
        uiTable.setFillParent(true);
        optionsTable.setFillParent(true);
        uiTable.align(Align.topRight);
        
        //add to tables
        uiTable.add(pointsTextLabel);
        uiTable.add(pointsLabel).width(pointsTextLabel.getWidth());
        uiTable.row();
        uiTable.add(goldTextLabel).fill();
        uiTable.add(goldLabel).fill();
        optionsTable.add(title);
        optionsTable.row();
        optionsTable.add(upgrade);
        optionsTable.row();
        optionsTable.add(heal);
        optionsTable.row();
        optionsTable.add(leave);
        
        //Stages
        uiStage.addActor(uiTable);
        mainStage.addActor(optionsTable);

        Gdx.input.setInputProcessor(mainStage);
    }

    //updates the labels
    @Override
    public void update(float delta){
        goldLabel.setText(Integer.toString(pirateGame.getPlayer().getGold()));
        pointsLabel.setText(Integer.toString(pirateGame.getPlayer().getPoints()));
        toHeal = player.getPlayerShip().getHealthMax() - player.getPlayerShip().getHealth();
    }

    //Starts the minigame
    public void startMinigame() {
        pirateGame.beforeMinigameScreen = this;
        pirateGame.setScreen(new MiniGameScreen(pirateGame));
    }

    //used when returning to department from minigame
    public void resume() {
        Gdx.input.setInputProcessor(mainStage);
    }
}
