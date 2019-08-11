package com.skinnylegends.character.npc.boss;

import com.skinnylegends.character.npc.Boss;

import javafx.scene.image.Image;

public class Wizard extends Boss {
    public Wizard() {
        super("Wizard");
    }

    @Override
    public String getType() {
        return "Wizard";
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./wizard.gif").toString());
    }
}