package com.skinnylegends.map;

import com.skinnylegends.character.Character;
import com.skinnylegends.character.NPC;
import com.skinnylegends.character.Player;
import com.skinnylegends.character.npc.enemy.MimicChest;
import com.skinnylegends.map.gui.MapRender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;

public class Room implements Serializable {
    public enum Type {
        INITIAL, EASY, HARD, TREASURE, BOSS
    }
    private Type type;
    // 0 top, 1 right, 2 bottom, 3 left
    private boolean[] doors;
    private boolean areDoorsOpen;
    private Tile[][] tiles;
    private int[] playerPos;
    public static final int sizeX = 7, sizeY = 11;
    public static final int centerX = (int) Math.floor((sizeX - 1) / 2.0);
    public static final int centerY = (int) Math.floor((sizeY - 1) / 2.0);

    Room(Type type, boolean[] doors) {
        this.type = type;
        this.doors = doors;
        this.areDoorsOpen = false;
        tiles = new Tile[sizeX][sizeY];
    }

    Type getType() {
        return type;
    }

    boolean getDoor(int place) {
        return doors[place];
    }

    public void checkIfDoorsShouldOpen() {
        // TODO: Uncomment, commented for testing purposes
        // TODO: Fix where the function is called
//        for (int x = 0; x < sizeX; x++)
//            for (int y = 0; y < sizeY; y++)
//                if (tiles[x][y].hasCharacter() == 'b' || tiles[x][y].hasCharacter() == 'e') {
//                    this.areDoorsOpen = false;
//                    return;
//                }
        this.areDoorsOpen = true;
    }

    void generateRoom(Random random) {
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                tiles[x][y] = new Tile(random.nextInt(30));
        switch (type) {
            case EASY:
            case HARD:
                spawnEnemies(random);
                break;
            case TREASURE:
                tiles[centerX][centerY].addChest();
                break;
            case BOSS:
                tiles[centerX][centerY].addBoss(random.nextInt(4));
        }
    }

    private void spawnEnemies(Random random) {
        ArrayList<int[]> possibleTiles = new ArrayList<>();
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                if (x != centerX && y != centerY)
                    possibleTiles.add(new int[] { x, y });
        int numEnemies = 2 + random.nextInt(3);
        // Choose numEnemies random tiles from possibleTiles
        ArrayList<int[]> chosenTiles = new ArrayList<>();
        for (int i = 0; i < numEnemies; i++) {
            int index = random.nextInt(possibleTiles.size());
            chosenTiles.add(possibleTiles.get(index));
        }
        // Add enemies for every chosen tile
        for (int i = 0; i < numEnemies; i++)
            tiles[chosenTiles.get(i)[0]][chosenTiles.get(i)[1]].addEnemy(type, random.nextInt(8));
    }

    public void setPlayer(Player player, int x, int y)
            throws ArrayIndexOutOfBoundsException, TileAlreadyOccupiedException {
        if (x < 0 || y < 0 || x >= sizeX || y >= sizeY)
            throw new ArrayIndexOutOfBoundsException();
        tiles[x][y].setPlayer(player);
        playerPos = new int[] { x, y };
    }

    void setPlayer(Player player) {
        try {
            setPlayer(player, centerX, centerY);
        } catch (TileAlreadyOccupiedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removePlayer() {
        tiles[playerPos[0]][playerPos[1]].clearCharacter();
    }

    public void movePlayer(int x, int y, MapRender mapRender) {
        Player p = (Player) tiles[playerPos[0]][playerPos[1]].getCharacter();
        try {
            int[] lastPlayerPos = playerPos;
            setPlayer(p, playerPos[0] + x, playerPos[1] + y);
            tiles[lastPlayerPos[0]][lastPlayerPos[1]].clearCharacter();
        } catch (ArrayIndexOutOfBoundsException e) {
            // If doors are closed, do nothing
            if (!areDoorsOpen) return;
            // Check if Player can move to another room
            if (playerPos[0] == centerX) {
                if (playerPos[1] == 0 && doors[3]) {
                    tiles[playerPos[0]][playerPos[1]].clearCharacter();
                    mapRender.moveToRoom(MapRender.Direction.LEFT, p);
                } else if (playerPos[1] == sizeY - 1 && doors[1]) {
                    tiles[playerPos[0]][playerPos[1]].clearCharacter();
                    mapRender.moveToRoom(MapRender.Direction.RIGHT, p);
                }
            } else if (playerPos[1] == centerY) {
                if (playerPos[0] == 0 && doors[0]) {
                    tiles[playerPos[0]][playerPos[1]].clearCharacter();
                    mapRender.moveToRoom(MapRender.Direction.UP, p);
                } else if (playerPos[0] == sizeX - 1 && doors[2]) {
                    tiles[playerPos[0]][playerPos[1]].clearCharacter();
                    mapRender.moveToRoom(MapRender.Direction.DOWN, p);
                }
            }
        } catch (TileAlreadyOccupiedException e) {
            NPC npc = (NPC) tiles[playerPos[0] + x][playerPos[1] + y].getCharacter();
            if (npc.getParent() == Character.ParentCharacter.CHEST) {
                if (Math.random() < 0.25)
                    mapRender.getGame().startBattle(new MimicChest());
                else if (Math.random() > 0.5)
                    mapRender.getGame().itemDropped(npc.dropWeapon(p));
                else
                    mapRender.getGame().itemDropped(npc.dropArmor(p));
            } else {
                mapRender.getGame().startBattle(npc);
            }
            tiles[playerPos[0] + x][playerPos[1] + y].clearCharacter();
        }
    }
    
    String roomToString() {
        StringBuilder res = new StringBuilder();
        for (int y = -1; y < sizeY + 1; y++)
            res.append((doors[0] && y == centerY) ? "d" : "-");
        res.append("\n");
        for (int x = 0; x < sizeX; x++) {
            res.append((doors[3] && x == centerX) ? "d" : "|");
            for (int y = 0; y < sizeY; y++)
                res.append(tiles[x][y].hasCharacter());
            res.append((doors[1] && x == centerX) ? "d" : "|");
            res.append("\n");
        }
        for (int y = -1; y < sizeY + 1; y++)
            res.append((doors[2] && y == centerY) ? "d" : "-");
        res.append("\n");
        return res.toString();
    }

    public ImageView[] renderWalls() {
        ImageView[] walls = new ImageView[4];
        for (int i = 0; i < walls.length; i++) {
            String doorState = doors[i] ? (areDoorsOpen ? "op" : "cl") : "no";
            String p1 = "./wall/" + i + "_" + doorState + ".png";
            walls[i] = new ImageView(getClass().getResource(p1).toString());
        }
        return walls;
    }

    public StackPane renderCenter() {
        GridPane floor = new GridPane();
        GridPane characters = new GridPane();
        for (int i = 0; i < sizeX; i++) {
            characters.getRowConstraints().add(new RowConstraints(64));
            for (int j = 0; j < sizeY; j++) {
                ImageView ivFloor = new ImageView(tiles[i][j].getSpritePath());
                ivFloor.setSmooth(false);
                ivFloor.setFitWidth(64);
                ivFloor.setFitHeight(64);
                ivFloor.setPreserveRatio(true);
                floor.add(ivFloor, j, i);

                ImageView ivChar;
                if (tiles[i][j].getCharacter() != null)
                    ivChar = new ImageView(tiles[i][j].getCharacter().render());
                else
                    ivChar = new ImageView(getClass().getResource("./emptyTile.png").toString());

                ivChar.setSmooth(false);
                if (tiles[i][j].hasCharacter() == 'b')
                    ivChar.setFitWidth(128);
                else
                    ivChar.setFitWidth(64);
                ivChar.setPreserveRatio(true);
                characters.add(ivChar, j, i);
                if (j == 0)
                    characters.getColumnConstraints().add(new ColumnConstraints(64));
                GridPane.setHalignment(ivChar, HPos.CENTER);
                GridPane.setValignment(ivChar, VPos.BOTTOM);
            }
        }
        return new StackPane(floor, characters);
    }

    static Type getRandomType(Random random) {
        int rNum = random.nextInt(100);
        if (rNum < 45) return Type.EASY;
        else if (rNum < 90) return Type.HARD;
        else return Type.TREASURE;
    }
}