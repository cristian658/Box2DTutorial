package com.massacre.box2d.tutorial.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class B2dAssetManager {

    public final AssetManager manager = new AssetManager();

    // Textures
    public final String playerImage = "images/player.png";
    public final String enemyImage = "images/enemy.png";

    public final String boingSound = "sounds/boing.wav";
    public final String pingSound = "sounds/ping.wav";
    // Music
    public final String playingSong = "music/Rolemusic-pl4y1ng.mp3";

    public final String gameImages = "images/game.atlas";
    public final String loadingImages = "images/loading.atlas";
    //Skin
    public final String skin = "skin/glassy-ui.json";


    public void queueAddImages(){
        manager.load(gameImages, TextureAtlas.class);
    }
    public void queueAddLoadingImages(){
        manager.load(loadingImages, TextureAtlas.class);
    }

    public void queueAddSounds(){
        manager.load(boingSound, Sound.class);
        manager.load(pingSound, Sound.class);
    }

    public void queueAddMusic(){
        manager.load(playingSong, Music.class);
    }

    public void queueAddSkin(){
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("skin/glassy-ui.atlas");
        manager.load(skin, Skin.class, params);
    }

    public void queueAddFonts(){
    }

    public void queueAddParticleEffects(){
    }
}
