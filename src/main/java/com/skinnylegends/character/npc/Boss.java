package com.skinnylegends.character.npc;

import com.skinnylegends.character.NPC;
import com.skinnylegends.character.Player;

public abstract class Boss extends NPC {
    public Boss(String name) {
        super(name, 250, 20, 15);
    }

    @Override
    protected int[] getArgsForWeapon(Player player) {
        // TODO: Make per boss, and random
        return new int[]{ 50, 10 };
    }

    @Override
    protected int getDefenseForArmor(Player player) {
        return 16;
    }

    @Override
    public ParentCharacter getParent() {
        return ParentCharacter.BOSS;
    }

    @Override
    public int getExperience() {
        return 1000;
    }
}