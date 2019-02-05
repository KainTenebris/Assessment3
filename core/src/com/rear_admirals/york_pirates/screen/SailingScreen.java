package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.screen.combat.CombatScreen;
import com.rear_admirals.york_pirates.base.BaseActor;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.base.BaseScreen;
import com.rear_admirals.york_pirates.Ship;

import java.util.*;

import static com.rear_admirals.york_pirates.College.*;
import static com.rear_admirals.york_pirates.PirateGame.*;
import static com.rear_admirals.york_pirates.ShipType.*;

public class SailingScreen extends BaseScreen {

    public Ship playerShip;

    //Map Variables
    private ArrayList<BaseActor> obstacleList;
    private ArrayList<BaseActor> removeList;
    private ArrayList<BaseActor> regionList;

    private int tileSize = 64;
    private int tileCountWidth = 80;
    private int tileCountHeight = 80;

    //calculate game world dimensions
    private final int mapWidth = tileSize * tileCountWidth;
    private final int mapHeight = tileSize * tileCountHeight;
    private TiledMap tiledMap;

    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera tiledCamera;
    private int[] backgroundLayers = {0,1,2};
    private int[] foregroundLayers = {3};

    private Label pointsLabel;
    private Label goldLabel;
    private Label mapMessage;
    private Label hintMessage;
    private Label bossMessage;
    private LinkedHashMap<String, Label> objectiveLabels;

    private Float timer;
    private int seconds;

    private boolean isFinalBossReady;

    //Constructor
    public SailingScreen(PirateGame main){
        super(main);

        this.isFinalBossReady = false;
        this.playerShip = main.getPlayer().getPlayerShip();
        this.mainStage.addActor(playerShip);
        
        
        
        //Lists
        obstacleList = new ArrayList<BaseActor>();
        removeList = new ArrayList<BaseActor>();
        regionList = new ArrayList<BaseActor>();

        
        
        //Labels
        //create labels
        this.pointsLabel = new Label(Integer.toString(main.getPlayer().getPoints()), main.getSkin(), "default_black");
        this.goldLabel = new Label(Integer.toString(main.getPlayer().getGold()), main.getSkin(), "default_black");
        this.mapMessage = new Label("", main.getSkin(), "default_black");
        this.hintMessage = new Label("", main.getSkin(),"default_black");
        this.bossMessage = new Label("", main.getSkin(), "default_black");
        Label pointsTextLabel = new Label("Points: ", main.getSkin(),"default_black");
        Label goldTextLabel = new Label("Gold:", main.getSkin(),"default_black");
        
        //align labels
        pointsLabel.setAlignment(Align.left);
        goldLabel.setAlignment(Align.left);
        
        //map for labels
        objectiveLabels = new LinkedHashMap<String, Label>();
        
        //add labels to map
        objectiveLabels.put("objectives title", new Label("Conquer all of York!", main.getSkin(), "default_black"));
        for(College college : colleges.values()) {
            if(playerShip.getCollege() == college) {
                objectiveLabels.put(college.getName(), new Label(college.getName() + " Allied: " + "Y", main.getSkin(), "default_black"));
            } else {
                objectiveLabels.put(college.getName(), new Label(college.getName() + "Allied: " + "N", main.getSkin(), "default_black"));
            }
        }
        objectiveLabels.put("YSJ", new Label("Defeat the Admiral of YSJ: N", main.getSkin(), "default_black"));
        
        
        
        //Tables
        //create tables
        Table uiTable = new Table();
        Table objectivesTable = new Table();
        Table messageTable = new Table();
        
        //align tables
        uiTable.align(Align.topRight);
        objectivesTable.align(Align.topLeft);
        uiTable.setFillParent(true);
        objectivesTable.setFillParent(true);
        messageTable.setFillParent(true);
        messageTable.top();
        
        //add to tables
        uiTable.add(pointsTextLabel);
        uiTable.add(pointsLabel).width(pointsTextLabel.getWidth());
        uiTable.row();
        uiTable.add(goldTextLabel).fill();
        uiTable.add(goldLabel).fill();
        for(Label label : objectiveLabels.values()) {
            objectivesTable.row();
            objectivesTable.add(label).fill();
        }
        messageTable.add(mapMessage);
        messageTable.row();
        messageTable.add(hintMessage);
        messageTable.row();
        messageTable.add(bossMessage);
        
        
        
        //Stages
        uiStage.addActor(uiTable);
        uiStage.addActor(objectivesTable);
        uiStage.addActor(messageTable);
        
        

        // set up tile map, renderer and camera
        tiledMap = new TmxMapLoader().load("game_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, viewwidth, viewheight);
        tiledCamera.update();

        MapLayers maplayers = tiledMap.getLayers();

        MapObjects objects = maplayers.get("ObjectData").getObjects();
        for (MapObject object : objects) {
            String name = object.getName();

            // all object data assumed to be stored as rectangles
            RectangleMapObject rectangleObject = (RectangleMapObject)object;
            Rectangle r = rectangleObject.getRectangle();

            if (name.equals("player")){
                playerShip.setPosition(r.x, r.y);
            } else{
                System.err.println("Unknown tilemap object: " + name);
            }
        }

        objects = maplayers.get("PhysicsData").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle r = rectangleObject.getRectangle();

                BaseActor solid = new BaseActor();
                solid.setPosition(r.x, r.y);
                solid.setSize(r.width, r.height);
                solid.setName(object.getName());
                solid.setRectangleBoundary();
                String objectName = object.getName();

                if (objectName.equals("derwent")) solid.setCollege(colleges.get("Derwent"));
                else if (objectName.equals("james")) solid.setCollege(colleges.get("James"));
                else if (objectName.equals("vanbrugh")) solid.setCollege(colleges.get("Vanbrugh"));
                else if (objectName.equals("chemistry"))solid.setDepartment(departments.get("Chemistry"));
                else if (objectName.equals("physics")) solid.setDepartment(departments.get("Physics"));
                //else if(objectName.equals("")) solid.setxyz(abc);
                obstacleList.add(solid);
            } else {
                System.err.println("Unknown PhysicsData object.");
            }
        }

        objects = maplayers.get("RegionData").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle r = rectangleObject.getRectangle();

                BaseActor region = new BaseActor();
                region.setPosition(r.x, r.y);
                region.setSize(r.width, r.height);
                region.setRectangleBoundary();
                region.setName(object.getName());

                if (object.getName().equals("derwentregion")) region.setCollege(colleges.get("Derwent"));
                else if (object.getName().equals("jamesregion")) region.setCollege(colleges.get("James"));
                else if (object.getName().equals("vanbrughregion")) region.setCollege(colleges.get("Vanbrugh"));
                //else if (object.getName().equals("collegeregion")) region.setCollege(colleges.get("college"));
                regionList.add(region);
            } else {
                System.err.println("Unknown RegionData object.");
            }
        }

        timer = 0f;
        seconds = 0;

        InputMultiplexer im = new InputMultiplexer(uiStage, mainStage);
        Gdx.input.setInputProcessor(im);
    }

    @Override
    public void update(float delta) {
        removeList.clear();
        goldLabel.setText(Integer.toString(pirateGame.getPlayer().getGold()));
        this.playerShip.playerMove(delta);

        if(isFinalBossReady && Gdx.input.isKeyPressed(Input.Keys.U)) {
            goToBossLevel();
            objectiveLabels.put("YSJ", new Label("Defeat the Admiral of YSJ: Y", pirateGame.getSkin(), "default_black"));
            uiStage.addActor(new Label("You have conquered York! Final score: " + pirateGame.getPlayer().getPoints() + "\nPress any key to return to the main menu.",
                    pirateGame.getSkin(), "default_black"));
            while(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {

            }
            pirateGame.reset();
            pirateGame.setScreen(new MainMenu(pirateGame));
        }

        boolean x = false;
        Random ran = new Random();
        for (BaseActor region : regionList) {
            String name = region.getName();
            if (playerShip.overlaps(region, false)) {
                x = true;

                College college = region.getCollege();
                boolean isAlly = playerShip.getCollege().getAlly().contains(college);
                if(isAlly) {
                    mapMessage.setText("<Ally> " + capitalizeFirstLetter(name.substring(0, name.length() - 6)) + " Territory");
                } else {
                    mapMessage.setText("<Hostile> " + capitalizeFirstLetter(name.substring(0, name.length() - 6)) + " Territory");
                }
                int enemyChance = ran.nextInt(10001);
                if (!isAlly && enemyChance <= 10) {
                    pirateGame.setScreen(new CombatScreen(pirateGame, new Ship(Brig, college)));
                }
            }
        }

        if (!x) {
            mapMessage.setText("Neutral Territory");
        }


        boolean y = false;
        for (BaseActor obstacle : obstacleList) {
            String name = obstacle.getName();
            if (playerShip.overlaps(obstacle, true)) {
                y = true;
                if (!(obstacle.getDepartment() == null)) {
                    mapMessage.setText(capitalizeFirstLetter(name) + " Island");
                    hintMessage.setText("Press F to interact");
                    if (Gdx.input.isKeyPressed(Input.Keys.F)) pirateGame.setScreen(new DepartmentScreen(pirateGame, obstacle.getDepartment()));
                }
                // Obstacle must be a college if college not null
                else if (!(obstacle.getCollege() == null)) {
                    hintMessage.setText("Press F to interact");
                    College college = obstacle.getCollege();
                    boolean isAlly = playerShip.getCollege().getAlly().contains(college);
                    if(isAlly) {
                        mapMessage.setText("<Ally> " + capitalizeFirstLetter(name) + " Island");
                    } else {
                        mapMessage.setText("<Hostile> " + capitalizeFirstLetter(name) + " Island");
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                        if (!isAlly && !obstacle.getCollege().isBossDead()) {
                            pirateGame.setScreen(new CombatScreen(pirateGame, new Ship(15, 15, 15, Galleon, college, college.getName() + " Boss", true)));
                            if(playerShip.getCollege().getAlly().contains(college)) {
                                objectiveLabels.get(college.getName()).setText(college.getName() + " Allied: " + "Y");
                            }
                            College playerCollege = playerShip.getCollege();
                            boolean allAllied = true;
                            for(College col : colleges.values()) {
                                if(!playerCollege.getAlly().contains(col)) {
                                    allAllied = false;
                                    break;
                                }
                            }
                            if(allAllied) {
                                isFinalBossReady = true;
                                bossMessage.setText("Press U to fight the YSJ Admiral and conquer York!");
                            }
                        } else {
                            pirateGame.setScreen(new CollegeScreen(pirateGame, college));
                        }
                    }
                }
            }
        }

        if (!y) hintMessage.setText("");

        for (BaseActor object : removeList) {
            object.remove();
        }

        // camera adjustment
        Camera mainCamera = mainStage.getCamera();

        // center camera on player
        mainCamera.position.x = playerShip.getX() + playerShip.getOriginX();
        mainCamera.position.y = playerShip.getY() + playerShip.getOriginY();

        // bound camera to layout
        mainCamera.position.x = MathUtils.clamp(mainCamera.position.x, viewwidth / 2, mapWidth - viewwidth / 2);
        mainCamera.position.y = MathUtils.clamp(mainCamera.position.y, viewheight / 2, mapHeight - viewheight / 2);
        mainCamera.update();

        // adjust tilemap camera to stay in sync with main camera
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);

        timer += delta;
        if (timer > 1) {
            pirateGame.getPlayer().addPoints(1 + seconds/60);//Every minute, score per second increases by 1
            seconds++;
            timer -= 1;
        }

        pointsLabel.setText(Integer.toString(pirateGame.getPlayer().getPoints()));
    }

    //draws everything
    @Override
    public void render(float delta) {
        uiStage.act(delta);
        mainStage.act(delta);
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.render(backgroundLayers);
        mainStage.draw();

        tiledMapRenderer.render(foregroundLayers);

        uiStage.draw();

        if (!playerShip.isAnchor()){
            playerShip.addAccelerationAS(playerShip.getRotation(), 10000);
        } else{
            playerShip.setAccelerationXY(0,0);
            playerShip.setDeceleration(100);
        }
    }

    //disposes of the screen
    @Override
    public void dispose () {
        mainStage.dispose();
        uiStage.dispose();
        playerShip.getSailingTexture().dispose();
    }

    //capitalises letters, needed because maps stores strings completely in lowercase
    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    //changes screen to bossScreen
    public void goToBossLevel() {
        pirateGame.setScreen(new BossScreen(pirateGame));
    }
}
