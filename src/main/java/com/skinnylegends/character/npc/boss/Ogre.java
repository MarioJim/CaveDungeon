package com.skinnylegends.character.npc.boss;

import com.skinnylegends.character.npc.Boss;

import javafx.scene.image.Image;

public class Ogre extends Boss {
    public Ogre() {
        super("Ogre");
    }

    @Override
    public String getType() {
        return "Ogre";
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./ogre.gif").toString());
    }
}