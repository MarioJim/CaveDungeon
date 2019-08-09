package com.skinnylegends.character.npc.boss;

import com.skinnylegends.character.npc.Boss;

import javafx.scene.image.Image;

public class BigDemon extends Boss {
    public BigDemon() {
        super("BigDemon");
    }

    @Override
    public String getType() {
        return "BigDemon";
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./bigDemon.gif").toString());
    }
}