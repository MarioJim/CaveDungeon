package com.skinnylegends.map;

import com.skinnylegends.character.Character;
import com.skinnylegends.character.Player;
import com.skinnylegends.character.npc.Chest;
import com.skinnylegends.character.npc.enemy.*;
import com.skinnylegends.character.npc.boss.*;

import java.io.Serializable;

public class Tile implements Serializable {
    private int spriteNum;
    private Character character;

    Tile() {
        int rand = (int) (Math.random() * 30) - 21;
        spriteNum = Math.max(rand, 1);
    }

    void addEnemy(int state) {
        switch (state) {
        case 2:
            character = (Math.random() < 0.5) ? new Zombie() : new Skeleton();
            break;
        case 3:
            int randomEnemy = (int) (Math.random() * 8);
            if (randomEnemy < 4)
                character = new Chort();
            else if (randomEnemy < 7)
                character = new Swampy();
            else
                character = new Necromancer();
            break;
        }
    }

    char hasCharacter() {
        if (character == null)
            return ' ';
        else {
            switch (character.getParent()) {
                case PLAYER:
                    return 'p';
                case CHEST:
                    return 'c';
                case ENEMY:
                    return 'e';
                case BOSS:
                    return 'b';
                default:
                    return '?';
            }
        }
    }

    void addBoss() {
        int randomBoss = (int) (Math.random() * 4);
        switch (randomBoss) {
            case 0:
                character = new BigDemon();
                break;
            case 1:
                character = new BigZombie();
                break;
            case 2:
                character = new Ogre();
                break;
            case 3:
                character = new Wizzard();
                break;
        }
    }

    public void setPlayer(Player player) throws TileAlreadyOccupiedException {
        if (this.character != null)
            throw new TileAlreadyOccupiedException();
        this.character = player;
    }

    public Character getCharacter() {
        return character;
    }

    void clearCharacter() {
        character = null;
    }

    void addChest() {
        character = new Chest();
    }

    String getSpritePath() {
        return getClass().getResource("./floor/" + spriteNum + ".png").toString();
    }
}