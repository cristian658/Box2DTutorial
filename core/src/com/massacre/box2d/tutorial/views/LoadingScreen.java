package com.massacre.box2d.tutorial.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.massacre.box2d.tutorial.Box2DTutorial;
import com.massacre.box2d.tutorial.views.actor.LoadingBarPart;

public class LoadingScreen implements Screen {

    private final Box2DTutorial parent;

    private TextureAtlas atlas;
    private TextureAtlas.AtlasRegion title;

    private TextureAtlas.AtlasRegion dash;

    private SpriteBatch sb;
    public final int IMAGE = 0;		// loading images
    public final int FONT = 1;		// loading fonts
    public final int PARTY = 2;		// loading particle effects
    public final int SOUND = 3;		// loading sounds
    public final int MUSIC = 4;		// loading music

    private int currentLoadingStage = 0;

    // timer for exiting loading screen
    public float countDown = 5f; // 5 seconds of waiting before menu screen

    private Animation flameAnimation;
    private float stateTime;
    private Image titleImage;
    private Stage stage;
    private Table table;
    private Table loadingTable;
    private TextureAtlas.AtlasRegion background;
    private TextureAtlas.AtlasRegion copyright;
    private Image copyrightImage;

    public LoadingScreen(Box2DTutorial box2DTutorial) {
        this.parent = box2DTutorial;
        stage = new Stage(new ScreenViewport());
        loadAssets();

        parent.assMan.queueAddImages();
        System.out.println("Loading images....");

        //sb = new SpriteBatch();
        //sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    private void loadAssets() {
        parent.assMan.queueAddLoadingImages();
        parent.assMan.manager.finishLoading();

        atlas = parent.assMan.manager.get("images/loading.atlas");
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");
        flameAnimation = new Animation(0.07f, atlas.findRegions("flames/flames"), Animation.PlayMode.LOOP);  //new
        background = atlas.findRegion("flamebackground");
        copyright = atlas.findRegion("copyright");
    }

    @Override
    public void show() {
        stateTime = 0f;
        titleImage = new Image(title);
        copyrightImage = new Image(copyright);


        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        table.setBackground(new TiledDrawable(background));

        loadingTable = new Table();
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        table.add(titleImage).align(Align.center).pad(10, 0, 0, 0).colspan(10);
        table.row(); // move to next row
        table.add(loadingTable).width(400);
        table.row();
        table.add(copyrightImage).align(Align.center).pad(200, 0, 0, 0).colspan(10);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); //  clear the screen
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += delta;

        TextureRegion currentFrame = (TextureRegion) flameAnimation.getKeyFrame(stateTime,true);


        if (parent.assMan.manager.update()) {
            currentLoadingStage+= 1;
            if(currentLoadingStage <= 5){
                loadingTable.getCells().get((currentLoadingStage-1)*2).getActor().setVisible(true);  // new
                loadingTable.getCells().get((currentLoadingStage-1)*2+1).getActor().setVisible(true);
            }
            switch(currentLoadingStage){
                case FONT:
                    System.out.println("Loading fonts....");
                    parent.assMan.queueAddFonts();
                    break;
                case PARTY:
                    System.out.println("Loading Particle Effects....");
                    parent.assMan.queueAddParticleEffects();
                    break;
                case SOUND:
                    System.out.println("Loading Sounds....");
                    parent.assMan.queueAddSounds();
                    break;
                case MUSIC:
                    System.out.println("Loading fonts....");
                    parent.assMan.queueAddMusic();
                    break;
                case 5:
                    System.out.println("Finished");
                    break;
            }
            if (currentLoadingStage >5){
                countDown -= delta;  // timer to stay on loading screen for short period once done loading
                currentLoadingStage = 5;  // cap loading stage to 5 as will use later to display progress bar and more than 5 would go off the screen
                if(countDown < 0){ // countdown is complete
                    parent.changeScreen(Box2DTutorial.MENU);  /// go to menu screen
                }
            }
            stage.act();
            stage.draw();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
