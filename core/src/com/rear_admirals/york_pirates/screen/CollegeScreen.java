package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.rear_admirals.york_pirates.base.BaseScreen;

public class CollegeScreen extends BaseScreen {
    private Player player;
    private Label pointsLabel;
    private Label goldLabel;
    private int toHeal;

    public CollegeScreen(PirateGame main, College college){
        super(main);
        this.player = main.getPlayer();
        toHeal = player.getPlayerShip().getHealthMax() - player.getPlayerShip().getHealth();
        
        //Labels
        //create the labels
        Label pointsTextLabel = new Label("Points: ", main.getSkin());
        Label goldTextLabel = new Label("Gold:", main.getSkin());
        final Label message = new Label("", main.getSkin());
        Label title = new Label("Welcome to " + college.getName(), main.getSkin(), "title");
        pointsLabel = new Label(Integer.toString(main.getPlayer().getPoints()), main.getSkin());
        goldLabel = new Label(Integer.toString(main.getPlayer().getGold()), main.getSkin());
        final TextButton leave = new TextButton("Leave", main.getSkin());
        
        //align the labels
        pointsLabel.setAlignment(Align.left);
        goldLabel.setAlignment(Align.left);
        
        //Buttons
        final TextButton heal = new TextButton("Repair Ship for "+ Integer.toString(toHeal/10) +" gold", main.getSkin());

        //when you are not damaged
        if (toHeal == 0) { heal.setText("Your ship is already fully repaired!"); }
        
        //heal button is clickable
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
                dispose();
            }
        });
        
        //Tables
        //create the tables
        Table uiTable = new Table();
        Table optionsTable = new Table();
        
        //add labels to tables
        uiTable.add(pointsTextLabel);
        uiTable.add(pointsLabel).width(pointsTextLabel.getWidth());
        uiTable.row();
        uiTable.add(goldTextLabel).fill();
        uiTable.add(goldLabel).fill();
        optionsTable.add(title);
        optionsTable.row();
        optionsTable.add(heal);
        optionsTable.row();
        optionsTable.add(message);
        optionsTable.row();
        optionsTable.add(leave);

        //align the tables
        uiTable.align(Align.topRight);
        uiTable.setFillParent(true);
        optionsTable.align(Align.center);
        optionsTable.setFillParent(true);
        
        //Stages
        uiStage.addActor(uiTable);
        mainStage.addActor(optionsTable);
        Gdx.input.setInputProcessor(mainStage);
    }
    
    @Override
    public void update(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            pirateGame.setScreen(pirateGame.getSailingScene());
            dispose();
        }

        toHeal = player.getPlayerShip().getHealthMax() - player.getPlayerShip().getHealth();
        goldLabel.setText(Integer.toString(pirateGame.getPlayer().getGold()));
        pointsLabel.setText(Integer.toString(pirateGame.getPlayer().getPoints()));
    }
}
