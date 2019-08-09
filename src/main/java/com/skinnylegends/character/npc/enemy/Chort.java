package com.skinnylegends.character.npc.enemy;

import com.skinnylegends.character.npc.Enemy;
import com.skinnylegends.character.Player;

import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;

public class Chort extends Enemy {
    public Chort() {
        super("Chort", 40, 12, 6);
    }

    @Override
    public int getExperience() {
        return ThreadLocalRandom.current().nextInt(5, 10);
    }

    @Override
    protected int[] getArgsForWeapon(Player player) {
        int baseAttack = ThreadLocalRandom.current().nextInt(20, 26);
        int baseStamina = ThreadLocalRandom.current().nextInt(6, 10);
        return new int[]{ baseAttack, baseStamina };
    }

    @Override
    protected int getDefenseForArmor(Player player) {
        return ThreadLocalRandom.current().nextInt(7, 9);
    }

    @Override
    public String getType() {
        return "Chort";
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./chort.gif").toString());
    }
}