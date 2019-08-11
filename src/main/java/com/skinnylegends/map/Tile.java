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

    Tile(int rand) {
        spriteNum = Math.max(rand - 21, 1);
    }

    void addEnemy(Room.Type type, int randomNum) {
        if (type == Room.Type.EASY) {
            character = randomNum < 4 ? new Zombie() : new Skeleton();
        } else {
            if (randomNum < 4) character = new Chort();
            else if (randomNum < 7) character = new Swampy();
            else character = new Necromancer();
        }
    }

    char hasCharacter() {
        return (character == null) ? ' ' : character.getParent().getCharacterInMap();
    }

    void addBoss(int randomBoss) {
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
                character = new Wizard();
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