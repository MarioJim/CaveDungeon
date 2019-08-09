package com.skinnylegends.character.npc.boss;

import com.skinnylegends.character.npc.Boss;

import javafx.scene.image.Image;

public class Wizzard extends Boss {
    public Wizzard() {
        super("Wizzard");
    }

    @Override
    public String getType() {
        return "Wizzard";
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./wizzard.gif").toString());
    }
}