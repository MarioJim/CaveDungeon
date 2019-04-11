package src.character.npc;

import src.character.NPC;
import src.character.Player;

public abstract class Boss extends NPC {
    public Boss(String name) {
        super(name, 1000, 20, 15);
    }

    @Override
    protected int[] getArgsForWeapon(Player player) {
        int[] res = { 50, 10 };
        // TO DO
        // Make per boss, and random
        return res;
    }

    @Override
    public int getExperience() {
        return 1000;
    }
}