package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.rear_admirals.york_pirates.College.*;
import static com.rear_admirals.york_pirates.PirateGame.Chemistry;
import static com.rear_admirals.york_pirates.PirateGame.Physics;
import static com.rear_admirals.york_pirates.ShipType.*;

public class SailingScreen extends BaseScreen {

    private Ship playerShip;

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
    private List<Label> objectiveLabels;
    private Label derwentObjective;//Etc

    private Float timer;
    private int seconds;

    public SailingScreen(final PirateGame main){
        super(main);

        playerShip = main.getPlayer().getPlayerShip();

        mainStage.addActor(playerShip);

        Table uiTable = new Table();

        Label pointsTextLabel = new Label("Points: ", main.getSkin(),"default_black");
        pointsLabel = new Label(Integer.toString(main.getPlayer().getPoints()), main.getSkin(), "default_black");
        pointsLabel.setAlignment(Align.left);

        Label goldTextLabel = new Label("Gold:", main.getSkin(),"default_black");
        goldLabel = new Label(Integer.toString(main.getPlayer().getGold()), main.getSkin(), "default_black");
        goldLabel.setAlignment(Align.left);

        objectiveLabels = new ArrayList<Label>();
        objectiveLabels.add(new Label("[X] Ally Derwent", main.getSkin(), "default_black"));
        objectiveLabels.add(new Label("[ ] Ally James", main.getSkin(), "default_black"));
        objectiveLabels.add(new Label("[ ] Ally Vanbrugh", main.getSkin(), "default_black"));
        objectiveLabels.add(new Label("[ ] Ally OtherCollege", main.getSkin(), "default_black"));
        objectiveLabels.add(new Label("[ ] Ally OtherCollege2", main.getSkin(), "default_black"));

        uiTable.add(pointsTextLabel);
        uiTable.add(pointsLabel).width(pointsTextLabel.getWidth());
        uiTable.row();
        uiTable.add(goldTextLabel).fill();
        uiTable.add(goldLabel).fill();

        Table objectivesTable = new Table();
        for(Label label : objectiveLabels) {
            objectivesTable.row();
            objectivesTable.add(label).fill();
        }

        uiTable.align(Align.topRight);
        uiTable.setFillParent(true);
        objectivesTable.align(Align.topLeft);
        objectivesTable.setFillParent(true);

        uiStage.addActor(uiTable);
        uiStage.addActor(objectivesTable);

        mapMessage = new Label("", main.getSkin(), "default_black");
        hintMessage = new Label("", main.getSkin(),"default_black");

        Table messageTable = new Table();
        messageTable.add(mapMessage);
        messageTable.row();
        messageTable.add(hintMessage);

        messageTable.setFillParent(true);
        messageTable.top();

        uiStage.addActor(messageTable);

        obstacleList = new ArrayList<BaseActor>();
        removeList = new ArrayList<BaseActor>();
        regionList = new ArrayList<BaseActor>();

        // set up tile map, renderer and camera
        tiledMap = new TmxMapLoader().load("game_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, viewwidth, viewheight);
        tiledCamera.update();

        MapObjects objects = tiledMap.getLayers().get("ObjectData").getObjects();
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

        objects = tiledMap.getLayers().get("PhysicsData").getObjects();
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

                if (objectName.equals("derwent")) solid.setCollege(Derwent);
                else if (objectName.equals("james")) solid.setCollege(James);
                else if (objectName.equals("vanbrugh")) solid.setCollege(Vanbrugh);
                else if (objectName.equals("chemistry"))solid.setDepartment(Chemistry);
                else if (objectName.equals("physics")) solid.setDepartment(Physics);
                //else if(objectName.equals("")) solid.setxyz(abc);
                obstacleList.add(solid);
            } else {
                System.err.println("Unknown PhysicsData object.");
            }
        }

        objects = tiledMap.getLayers().get("RegionData").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle r = rectangleObject.getRectangle();

                BaseActor region = new BaseActor();
                region.setPosition(r.x, r.y);
                region.setSize(r.width, r.height);
                region.setRectangleBoundary();
                region.setName(object.getName());

                if (object.getName().equals("derwentregion")) region.setCollege(Derwent);
                else if (object.getName().equals("jamesregion")) region.setCollege(James);
                else if (object.getName().equals("vanbrughregion")) region.setCollege(Vanbrugh);
                //else if (object.getName().equals("collegeregion")) region.setCollege(college);
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

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            pirateGame.setScreen(new MainMenu(pirateGame));
        }
        uiStage.act(delta);

        mainStage.act(delta);
        update(delta);

        // render
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

    @Override
    public void dispose () {
        mainStage.dispose();
        uiStage.dispose();
        playerShip.getSailingTexture().dispose();
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
