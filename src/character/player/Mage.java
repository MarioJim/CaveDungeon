package src.character.player;

import src.character.Player;
import src.item.Inventory;
import src.item.weapon.*;
import src.item.potion.*;

import javafx.scene.image.Image;

public class Mage extends Player {
    public Mage(String name, char gender) {
        super(name, 20, gender, new Inventory(), 1.0, 1.2);
        this.inventory.addItemToInventory(new EnchantedBook(15, 5), 0);
        this.inventory.addItemToInventory(new Wand(15, 5), 1);
        this.inventory.addItemToInventory(new HealthPotion(15), 0);
        this.inventory.addItemToInventory(new StaminaPotion(10), 1);
        this.inventory.addItemToInventory(new StaminaPotion(10), 2);
    }

    @Override
    public String getType() {
        return "Mage";
    }

    @Override
    public Image render() {
        String path = "./img/mage_" + getGender() + ".gif";
        return new Image(getClass().getResource(path).toString());
    }
}
