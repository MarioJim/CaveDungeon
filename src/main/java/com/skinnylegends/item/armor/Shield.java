package com.skinnylegends.item.armor;

import com.skinnylegends.item.Armor;
import javafx.scene.image.Image;

public class Shield extends Armor {
    // Names from https://torchlight.fandom.com/wiki/Shields_(T2)
    private final String[] names = { "Tribal Warshield", "Shelter's Disk", "Sentinel's Watch", "The Centerwing",
            "Core of Fate" };

    public Shield(int baseDefense) {
        super("", baseDefense);
        setName(names[(int) (Math.random() * names.length)]);
        sprite = (int) (Math.random() * 5);
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./shield" + sprite + ".png").toString());
    }
}
