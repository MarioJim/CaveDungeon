package com.skinnylegends.character.npc.enemy;

import com.skinnylegends.character.npc.Enemy;
import com.skinnylegends.character.Player;

import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;

public class Skeleton extends Enemy {
    public Skeleton() {
        super("Skeleton", 30, 8, 4);
    }

    @Override
    public int getExperience() {
        return ThreadLocalRandom.current().nextInt(5, 7);
    }

    @Override
    protected int[] getArgsForWeapon(Player player) {
        int baseAttack = ThreadLocalRandom.current().nextInt(16, 22);
        int baseStamina = ThreadLocalRandom.current().nextInt(4, 8);
        return new int[]{ baseAttack, baseStamina };
    }

    @Override
    protected int getDefenseForArmor(Player player) {
        return ThreadLocalRandom.current().nextInt(5, 8);
    }

    @Override
    public String getType() {
        return "Skeleton";
    }

    @Override
    public Image render() {

        return new Image(getClass().getResource("./skeleton.gif").toString());
    }
}