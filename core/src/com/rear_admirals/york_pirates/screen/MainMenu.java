package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.base.BaseScreen;
import com.rear_admirals.york_pirates.screen.combat.CombatScreen;
import com.rear_admirals.york_pirates.Ship;

import static com.rear_admirals.york_pirates.PirateGame.*;
import static com.rear_admirals.york_pirates.ShipType.*;
import static com.rear_admirals.york_pirates.College.*;

public class MainMenu extends BaseScreen {
    private Stage stage;
    private float screen_width;
    private float screen_height;
    
    //Constructor
    public MainMenu(final PirateGame pirateGame){
        super(pirateGame);
        
        this.screen_width = stage.getWidth();
        this.screen_height = stage.getHeight();

        Gdx.graphics.setTitle("York Pirates!");

        
        
        //Labels
        //create labels
        Label title = new Label("Rear Admirals", pirateGame.getSkin(), "title");
        
        //set alignment of labels
        title.setAlignment(Align.center);
        
        
        
        //TextButtons
        //create TextButtons
        TextButton sailing_mode = new TextButton("Start Game", pirateGame.getSkin()); // Starts sailing mode.
        TextButton quickplay = new TextButton("Quick Play", pirateGame.getSkin());
        TextButton quit = new TextButton("Quit", pirateGame.getSkin());
        
        //adds listeners to buttons
        quickplay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Ship newPlayerShip = new Ship(Warship, pirateGame.getPlayer().getPlayerShip().getCollege());
                pirateGame.getPlayer().setPlayerShip(newPlayerShip);
                ((SailingScreen)pirateGame.getSailingScene()).playerShip = newPlayerShip;
                pirateGame.setScreen(pirateGame.getSailingScene());
                dispose();
            }
        });
        sailing_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                pirateGame.setScreen(pirateGame.getSailingScene());
                dispose();
            }
        });
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               Gdx.app.exit();
            }
        });
        
        
        
        //Tables
        //create tables
        Table table = new Table();
        
        tableContainer.setFillParent(true);
        tableContainer.setPosition(0,0);
        tableContainer.align(Align.center);
        
        //adds to tables
        table.add(title).padBottom(viewwidth/20).width(viewwidth/2);
        table.row(); // Ends the current row
        table.add(sailing_mode).uniform().padBottom(viewheight/40).size(viewwidth/2,viewheight/10);
        table.row();
        table.add(quickplay).uniform().padBottom(viewheight/40).fill();
        table.row();
        table.add(quit).uniform().fill().padBottom(viewheight/40);
        
        
        
        //Stages
        Container<Table> tableContainer = new Container<Table>();
        this.stage = new Stage(new FitViewport(1920,1080));

        tableContainer.setActor(table);
        stage.addActor(tableContainer);

        Gdx.input.setInputProcessor(stage);
    }

    //creates the screen
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act();
    }

    //resizes screen
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    //disposes of screen
    @Override
    public void dispose() { stage.dispose(); }
}


