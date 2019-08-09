package com.skinnylegends.character.npc;

import com.skinnylegends.character.NPC;
import com.skinnylegends.character.Player;

import javafx.scene.image.Image;
import java.util.concurrent.ThreadLocalRandom;

public class Chest extends NPC {
    public Chest() {
        super("Chest", 0, 0, 0);
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./chest/chest.gif").toString());
    }

    @Override
    public int getExperience() {
        return 0;
    }

    @Override
    protected int[] getArgsForWeapon(Player player) {
        int baseAttack = ThreadLocalRandom.current().nextInt(30, 36);
        int baseStamina = ThreadLocalRandom.current().nextInt(10, 14);
        return new int[]{ baseAttack, baseStamina };
    }

    @Override
    protected int getDefenseForArmor(Player player) {
        return ThreadLocalRandom.current().nextInt(8, 12);
    }

    @Override
    public String getType() {
        return "Chest";
    }

    @Override
    public ParentCharacter getParent() {
        return ParentCharacter.CHEST;
    }
}
