package src.character.npc.enemy;

import src.character.npc.Enemy;
import src.character.Player;
import src.item.Weapon;
import src.item.weapon.*;
import src.character.player.*;

import java.util.concurrent.ThreadLocalRandom;

public class Zombie extends Enemy {
    public Zombie() {
        super("Zombie", 20, 5);
    }

    @Override
    public int getExperience() {
        return ThreadLocalRandom.current().nextInt(3, 4 + 1);
    }

    @Override
    public Weapon dropWeapon(Player player) {
        switch (ThreadLocalRandom.current().nextInt(1, 2)) {
        case 1:
            return (player instanceof Melee)
                    ? new Sword(ThreadLocalRandom.current().nextInt(12, 18), ThreadLocalRandom.current().nextInt(3, 7))

                    : new EnchantedBook(ThreadLocalRandom.current().nextInt(12, 18),
                            ThreadLocalRandom.current().nextInt(3, 7));
        case 2:
            return (player instanceof Melee)
                    ? new Bow(ThreadLocalRandom.current().nextInt(12, 18), ThreadLocalRandom.current().nextInt(3, 7))
                    : new Wand(ThreadLocalRandom.current().nextInt(12, 18), ThreadLocalRandom.current().nextInt(3, 7));
        default:
            return null;
        }
    }
}