package src.character.npc;

import src.character.NPC;

public abstract class Enemy extends NPC {
    public Enemy(String name, int healthPoints, int baseDamage, int baseDefense) {
        super(name, healthPoints, baseDamage, baseDefense);
    }

    public abstract int getExperience();
    // public abstract Potion dropPotion(Player player);
}