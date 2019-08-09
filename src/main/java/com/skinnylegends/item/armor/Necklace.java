package com.skinnylegends.item.armor;

import com.skinnylegends.item.Armor;
import javafx.scene.image.Image;

public class Necklace extends Armor {
    // Names from https://torchlight.fandom.com/wiki/Necklaces_(T2)
    private final String[] names = { "The Amulet of Ledo", "The Guardian's Gaze", "Coldheart", "The Emerald Eye",
            "The Glimmer of Hope", "Stormcore", "Amulet of the Old Ones" };

    public Necklace(int baseDefense) {
        super("", baseDefense);
        setName(names[(int) (Math.random() * names.length)]);
        sprite = (int) (Math.random() * 5);
    }

    @Override
    public Image render() {
        return new Image(getClass().getResource("./necklace" + sprite + ".png").toString());
    }
}
