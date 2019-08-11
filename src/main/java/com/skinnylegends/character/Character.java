package com.skinnylegends.character;

import java.io.Serializable;
import javafx.scene.image.Image;

public abstract class Character implements Serializable {
    private String name;
    private int healthPoints;

    Character(String name, int healthPoints) {
        this.name = name;
        this.healthPoints = healthPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public abstract String getType();

    public enum ParentCharacter {
        BOSS('b'),
        CHEST('c'),
        ENEMY('e'),
        PLAYER('p');

        private char characterInMap;

        ParentCharacter(char characterInMap) {
            this.characterInMap = characterInMap;
        }

        public char getCharacterInMap() {
            return characterInMap;
        }
    }

    public abstract ParentCharacter getParent();

    public abstract Image render();
}
