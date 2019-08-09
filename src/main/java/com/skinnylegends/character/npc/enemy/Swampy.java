package com.skinnylegends.character.npc.enemy;

import com.skinnylegends.character.npc.Enemy;
import com.skinnylegends.character.Player;

import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;

public class Swampy extends Enemy {
    public Swampy() {
        super("Swampy", 50, 16, 8);
    }

    @Override
    public int getExperience() {
        return ThreadLocalRandom.current().nextInt(10, 15);
    }

    @Override
    protected int[] getArgsForWeapon(Player player) {
        int baseAttack = ThreadLocalRandom.current().nextInt(24, 30);
        int baseStamina = ThreadLocalRandom.current().nextInt(8, 12);
        return new int[]{ baseAttack, baseStamina };
    }

    @Override
    protected int getDefenseForArmor(Player player) {
        return ThreadLocalRandom.current().nextInt(8, 11);
    }

    @Override
    public String getType() {
        return "Swampy";
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./swampy.gif").toString());
    }
}