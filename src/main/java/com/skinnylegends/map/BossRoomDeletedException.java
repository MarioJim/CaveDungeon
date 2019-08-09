package com.skinnylegends.map;

public class BossRoomDeletedException extends Exception {
    public BossRoomDeletedException() {
        super("Boss room deleted during map generation");
    }
}