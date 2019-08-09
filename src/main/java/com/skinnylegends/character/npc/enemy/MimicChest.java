package com.skinnylegends.character.npc.enemy;

import com.skinnylegends.character.npc.Enemy;
import com.skinnylegends.character.Player;

import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;

public class MimicChest extends Enemy {
    public MimicChest() {
        super("Mimic Chest", 50, 16, 8);
    }

    @Override
    public int getExperience() {
        return ThreadLocalRandom.current().nextInt(15, 20);
    }

    @Override
    protected int[] getArgsForWeapon(Player player) {
        int baseAttack = ThreadLocalRandom.current().nextInt(30, 36);
        int baseStamina = ThreadLocalRandom.current().nextInt(10, 14);
        return new int[]{ baseAttack, baseStamina };
    }

    @Override
    protected int getDefenseForArmor(Player player) {
        return ThreadLocalRandom.current().nextInt(10, 12);
    }

    @Override
    public String getType() {
        return "MimicChest";
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./mimic.gif").toString());
    }
}