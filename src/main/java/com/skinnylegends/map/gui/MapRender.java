package com.skinnylegends.map.gui;

import com.skinnylegends.map.Map;
import com.skinnylegends.map.Room;
import com.skinnylegends.character.Player;
import com.skinnylegends.game.Game;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class MapRender extends Scene {
    private Game game;
    private Map m;
    private int roomX, roomY;

    public MapRender(Game game, Map map) {
        super(new BorderPane());
        this.game = game;
        this.m = map;
        this.roomX = map.startX;
        this.roomY = map.startY;
        setupKeyboardControls(this);
        m.getRoom(roomX, roomY).checkIfDoorsShouldOpen();
        ImageView[] walls = m.getRoom(roomX, roomY).renderWalls();
        StackPane center = m.getRoom(roomX, roomY).renderCenter();
        BorderPane root = new BorderPane(center, walls[0], walls[1], walls[2], walls[3]);
        root.setStyle("-fx-background-color: #1C1117;");
        setRoot(root);
    }

    private void setupKeyboardControls(MapRender mapRender) {
        setOnKeyPressed(new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                    case UP:
                        m.getRoom(roomX, roomY).movePlayer(-1, 0, mapRender);
                        break;
                    case D:
                    case RIGHT:
                        m.getRoom(roomX, roomY).movePlayer(0, 1, mapRender);
                        break;
                    case S:
                    case DOWN:
                        m.getRoom(roomX, roomY).movePlayer(1, 0, mapRender);
                        break;
                    case A:
                    case LEFT:
                        m.getRoom(roomX, roomY).movePlayer(0, -1, mapRender);
                        break;
                    case E:
                        game.setInventoryScene();
                        break;
                    default:
                }
                BorderPane root = (BorderPane) getRoot();
                root.setCenter(m.getRoom(roomX, roomY).renderCenter());
            }
        });
    }

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    public void moveToRoom(Direction direction, Player player) {
        try {
            switch (direction) {
                case UP:
                    this.roomX--;
                    m.getRoom(roomX, roomY).setPlayer(player, Room.sizeX - 1, Room.centerY);
                    break;
                case RIGHT:
                    this.roomY++;
                    m.getRoom(roomX, roomY).setPlayer(player, Room.centerX, 0);
                    break;
                case DOWN:
                    this.roomX++;
                    m.getRoom(roomX, roomY).setPlayer(player, 0, Room.centerY);
                    break;
                case LEFT:
                    this.roomY--;
                    m.getRoom(roomX, roomY).setPlayer(player, Room.centerX, Room.sizeY - 1);
                    break;
            }
            m.getRoom(roomX, roomY).checkIfDoorsShouldOpen();
            ImageView[] walls = m.getRoom(roomX, roomY).renderWalls();
            StackPane center = m.getRoom(roomX, roomY).renderCenter();
            BorderPane root = new BorderPane(center, walls[0], walls[1], walls[2], walls[3]);
            root.setStyle("-fx-background-color: #1C1117;");
            setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Game getGame() {
        return game;
    }

    public int[] getPos() {
        return new int[]{ roomX, roomY};
    }
}