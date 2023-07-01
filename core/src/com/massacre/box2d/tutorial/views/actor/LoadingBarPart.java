package com.massacre.box2d.tutorial.views.actor;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LoadingBarPart extends Actor {

    private static final float WIDTH = 30;
    private static final float HEIGHT = 25;
    private static final boolean NO_VISIBLE = false;
    private float stateTime = 0;
    private Animation flameAnimation;
    private TextureAtlas.AtlasRegion image;

    private TextureRegion currentFrame;

    public LoadingBarPart(TextureAtlas.AtlasRegion image, Animation flameAnimation) {
        super();
        this.image = image;
        this.flameAnimation = flameAnimation;
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setVisible(NO_VISIBLE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(image, getX(),getY(), 30, 30);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        batch.draw(currentFrame, getX()-5,getY(), 40, 40);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta; // Accumulate elapsed animation time
        currentFrame = (TextureRegion)flameAnimation.getKeyFrame(stateTime, true);
    }
}
