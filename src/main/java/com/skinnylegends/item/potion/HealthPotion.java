package com.skinnylegends.item.potion;

import com.skinnylegends.item.Potion;
import javafx.scene.image.Image;

public class HealthPotion extends Potion {
    public HealthPotion() {
        super("Health Potion");
    }

    @Override
    public char getType() {
        return 'h';
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./healthpotion.png").toString());
    }
}
