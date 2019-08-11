package com.skinnylegends.game;

import com.skinnylegends.character.gui.battle.Battle;
import com.skinnylegends.character.gui.createplayer.CreatePlayer;
import com.skinnylegends.character.NPC;
import com.skinnylegends.character.Player;
import com.skinnylegends.item.Armor;
import com.skinnylegends.item.Potion;
import com.skinnylegends.item.Weapon;
import com.skinnylegends.item.gui.droppedItem.DroppedItemGUI;
import com.skinnylegends.item.gui.inventory.InventoryGUI;
import com.skinnylegends.map.gui.MapRender;
import com.skinnylegends.map.Map;
import com.skinnylegends.game.gui.Start;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;

public class Game extends Application {
    private Map[] levels;
    private Player player;
    private Stage stage;
    private Scene mapRender;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        long gameSeed = System.currentTimeMillis();
        System.out.println("Seed: " + gameSeed);
        levels = new Map[1];
        for (int i = 0; i < levels.length; ++i)
            levels[i] = new Map(gameSeed + i);
        stage.setScene(new Start(this));
        //Player is saved at the close of Game
        stage.setOnCloseRequest(event -> {
            try {
                if(findFile(player.getName(), 'P').delete() && findFile(player.getName(), 'M').delete()) {
                    System.out.println("Game saved successfully");
                    savePlayerAndMap();
                }
            } catch (IOException e) {
                System.out.println("File not found");
            } catch (NullPointerException e) {
                System.out.println("Game not saved | Player not loaded or created");
            }
        });
        stage.setTitle("CaveDungeon 1.O BETA");
        stage.show();
    }

    private void playerTests() {
        System.out.println(player.playerToString());
        System.out.println("Your equipped weapon is: " + player.getInventory().getEquippedWeapon().getName());
        System.out.println("Limit HP: " + player.getLimitHp() + " Current HP: " + player.getHealthPoints());
        System.out.println("Limit Stamina: " + player.getLimitStamina() + " Current Stamina: " + player.getStamina());
        System.out.println(
                "Next Level EXP: " + player.getExpRequiredForNextLevel() + " Current EXP: " + player.getExperience());
    }

    //This method runs only when a new user is created
    public void setPlayer(Player player) {
        this.player = player;
        player.getInventory().equipWeapon(0);
        try {
            savePlayerAndMap();
        } catch (IOException e) {
            System.out.println("Game not saved | Player not loaded or created");
        }
        // TODO: Change experience at the start
        player.checkLevelUp(400);
    }

    //This method runs only when an user is loaded
    public void addPlayerToMapAndContinue() {
        levels[0].setPlayer(player);
        // TODO: Stop printing map
        for (Map level : levels)
            System.out.println(level.mapToString());
        mapRender = new MapRender(this, levels[0]);
        playerTests();
        setRoomScene();
    }

    public Player getPlayer() {
        return player;
    }

    public void setCreatePlayerScene() {
        stage.setScene(new CreatePlayer(this));
    }

    public void setRoomScene() {
        stage.setScene(mapRender);
    }

    public void setInventoryScene() {
        stage.setScene(new InventoryGUI(this));
    }

    public void startBattle(NPC npc) {
        stage.setScene(new Battle(this.player, npc, this));
    }

    public void itemDropped(Weapon newWeapon) {
        stage.setScene(new DroppedItemGUI(this, newWeapon));
    }

    public void itemDropped(Potion newPotion) {
        stage.setScene(new DroppedItemGUI(this, newPotion));
    }

    public void itemDropped(Armor newArmor) {
        stage.setScene(new DroppedItemGUI(this, newArmor));
    }
    
    private void savePlayerAndMap() throws IOException {
        FileOutputStream playerFile = new FileOutputStream(findFile(player.getName(), 'P'));
        ObjectOutputStream savePlayer = new ObjectOutputStream(playerFile);
        savePlayer.writeObject(player);
        savePlayer.close();
        try {
            int[] pos = ((MapRender) mapRender).getPos();
            levels[0].getRoom(pos[0], pos[1]).removePlayer();
        } catch (NullPointerException e) {
            System.out.println("No player to remove from map");
        }
        FileOutputStream mapFile = new FileOutputStream(findFile(player.getName(), 'M'));
        ObjectOutputStream saveMap = new ObjectOutputStream(mapFile);
        saveMap.writeObject(levels);
        saveMap.close();
    }

    public void loadPlayerAndMap(String name) throws IOException, ClassNotFoundException {
        FileInputStream playerFileIn = new FileInputStream(findFile(name, 'P'));
        ObjectInputStream playerObjectIn = new ObjectInputStream(playerFileIn);
        player = (Player) playerObjectIn.readObject();
        System.out.println("Player loaded: " + player.getName());
        playerObjectIn.close();
        playerFileIn.close();
        FileInputStream mapFileIn = new FileInputStream(findFile(name, 'M'));
        ObjectInputStream mapObjectIn = new ObjectInputStream(mapFileIn);
        levels = (Map[]) mapObjectIn.readObject();
        System.out.println("Map loaded");
        mapObjectIn.close();
        mapFileIn.close();
    }

    public File findFile(String name, char type) {
        String classpath = System.getProperty("java.class.path");
        String userSavesPath = classpath + File.separatorChar + "saves" + File.separatorChar + name + File.separatorChar;
        File userSavesDir = new File(userSavesPath);
        userSavesDir.mkdirs();
        switch (type) {
            case 'P':
                return new File(userSavesDir, "player.atm");
            case 'M':
                return new File(userSavesDir, "map.atm");
            default:
                return userSavesDir;
        }
    }
}
