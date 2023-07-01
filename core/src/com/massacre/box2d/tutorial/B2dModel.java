package com.massacre.box2d.tutorial;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.massacre.box2d.tutorial.controller.KeyboardController;
import com.massacre.box2d.tutorial.loader.B2dAssetManager;

public class B2dModel {

    public World world;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRenderer;

    private Body bodyd;
    private Body bodys;
    private Body bodyk;
    private KeyboardController controller;
    private B2dAssetManager assMan;

    public boolean isSwimming = false;

    public Body player;

    private Sound ping;
    private Sound boing;

    public static final int BOING_SOUND = 0; // new
    public static final int PING_SOUND = 1; //new


    public B2dModel(KeyboardController controller, OrthographicCamera cam, B2dAssetManager assetManager) {
        this.controller = controller;
        this.cam = cam;

        this.assMan = assetManager;
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new B2dContactListener(this));
        createFloor();
        //createObject();
        //createMovingObject();

        // tells our asset manger that we want to load the images set in loadImages method
        assMan.queueAddSounds();
        // tells the asset manager to load the images and wait until finsihed loading.
        assMan.manager.finishLoading();
        // loads the 2 sounds we use
        ping = assMan.manager.get("sounds/ping.wav", Sound.class);
        boing = assMan.manager.get("sounds/boing.wav", Sound.class);


        BodyFactory bodyFactory = BodyFactory.getInstance(world);
        /*
        // add a new rubber ball at position 1, 1
        bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER);

        // add a new steel ball at position 4, 1
        bodyFactory.makeCirclePolyBody(4, 1, 2, BodyFactory.STEEL);

        // add a new stone at position -4,1
        bodyFactory.makeCirclePolyBody(-4, 1, 2, BodyFactory.STONE);

        Body p = bodyFactory.makePolygonShapeBody(new Vector2[]{new Vector2(1, 5), new Vector2(0, 2), new Vector2(2.5f, 0), new Vector2(5, 2), new Vector2(4, 5)},0,0 , BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody) ;

        bodyFactory.makeConeSensor(p, 2f);
        */
        // add a player
        this.player = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody,false);

        // add some water
        Body water =  bodyFactory.makeBoxPolyBody(1, -8, 40, 4, BodyFactory.RUBBER, BodyDef.BodyType.StaticBody,false);

        bodyFactory.makeAllFixturesSensors(water);

        water.setUserData("IAMTHESEA");



    }


    public void logicStep(float delta) {
        if(controller.left){
            player.applyForceToCenter(-10, 0,true);
        }else if(controller.right){
            player.applyForceToCenter(10, 0,true);
        }else if(controller.up){
            player.applyForceToCenter(0, 10,true);
        }else if(controller.down){
            player.applyForceToCenter(0, -10,true);
        }
        // check if mouse1 is down (player click) then if true check if point intersects
        if(controller.isMouse1Down && pointIntersectsBody(player,controller.mouseLocation)){
            System.out.println("Player was clicked");
        }

        if(isSwimming){
            player.applyForceToCenter(0, 50, true);
        }
        world.step(delta , 3, 3);


    }

    private void createObject() {

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);

        // add it to the world
        bodyd = world.createBody(bodyDef);
        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyd.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    private void createFloor() {
        // create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -10);
        // add it to the world
        bodys = world.createBody(bodyDef);
        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50, 1);
        // create the physical object in our body)
        // without this our body would just be data in the world
        bodys.createFixture(shape, 0.0f);
        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    private void createMovingObject(){

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0,-12);


        // add it to the world
        bodyk = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyk.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();

        bodyk.setLinearVelocity(0, 1.75f);
    }
    public boolean pointIntersectsBody(Body body, Vector2 mouseLocation){
        Vector3 mousePos = new Vector3(mouseLocation,0); //convert mouseLocation to 3D position
        cam.unproject(mousePos); // convert from screen potition to world position
        if(body.getFixtureList().first().testPoint(mousePos.x, mousePos.y)){
            return true;
        }
        return false;
    }

    public void playSound(int sound) {
        switch(sound){
            case BOING_SOUND:
                boing.play();
                break;
            case PING_SOUND:
                ping.play();
                break;
        }
    }

}
