package com.skinnylegends.character.player;

import com.skinnylegends.character.Player;
import com.skinnylegends.item.Inventory;
import com.skinnylegends.item.armor.*;
import com.skinnylegends.item.weapon.*;
import com.skinnylegends.item.potion.*;

import javafx.scene.image.Image;

public class Melee extends Player {
    public Melee(String name, char gender) {
        super(name, 15, gender, new Inventory(), 1.2, 1.0);
        this.inventory.addItemToInventory(new Sword(15, 5), 0);
        this.inventory.addItemToInventory(new Bow(15, 5), 1);
        this.inventory.addItemToInventory(new Shield(2), 0);
        this.inventory.addItemToInventory(new Shield(2), 1);
        this.inventory.addItemToInventory(new Shield(2), 2);
        this.inventory.addItemToInventory(new HealthPotion(), 0);
        this.inventory.addItemToInventory(new StaminaPotion(), 1);
        this.inventory.addItemToInventory(new StaminaPotion(), 2);
    }

    @Override
    public String getType() {
        return "Melee";
    }

    @Override
    public Image render() {
        String path = "./melee_" + getGender() + ".gif";
        return new Image(getClass().getResource(path).toString());
    }
}
