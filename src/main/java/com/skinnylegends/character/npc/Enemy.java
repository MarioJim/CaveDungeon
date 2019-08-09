package com.skinnylegends.character.npc;

import com.skinnylegends.character.NPC;

public abstract class Enemy extends NPC {
    public Enemy(String name, int healthPoints, int baseDamage, int baseDefense) {
        super(name, healthPoints, baseDamage, baseDefense);
    }

    @Override
    public ParentCharacter getParent() {
        return ParentCharacter.ENEMY;
    }

    public abstract int getExperience();
}