package com.skinnylegends.map;

import java.io.Serializable;

public class TileAlreadyOccupiedException extends Exception implements Serializable {
    public TileAlreadyOccupiedException() {
        super("This tile is already occupied");
    }
}