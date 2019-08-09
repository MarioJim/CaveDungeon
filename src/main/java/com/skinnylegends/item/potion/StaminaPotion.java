package com.skinnylegends.item.potion;

import com.skinnylegends.item.Potion;
import javafx.scene.image.Image;

public class StaminaPotion extends Potion {
    public StaminaPotion() {
        super("Stamina Potion");
    }

    @Override
    public char getType() {
        return 's';
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./staminapotion.png").toString());
    }
}
