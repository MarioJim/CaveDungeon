package com.skinnylegends.character.npc.boss;

import com.skinnylegends.character.npc.Boss;

import javafx.scene.image.Image;

public class BigZombie extends Boss {
    public BigZombie() {
        super("BigZombie");
    }

    @Override
    public String getType() {
        return "BigZombie";
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./bigZombie.gif").toString());
    }
}