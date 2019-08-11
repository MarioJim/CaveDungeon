package com.skinnylegends.map;

import java.util.Arrays;

public class MapTest {
    public static void main(String[] args) {
        long gameSeed = System.currentTimeMillis();
//        gameSeed = 1565416609968l;
        System.out.println("Seed: " + gameSeed);
        Map[] maps = new Map[10];
        for (int i = 0; i < maps.length; i++) {
            maps[i] = new Map(gameSeed + i);
            System.out.println(maps[i].mapToString());
        }
    }
}
