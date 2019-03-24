package tests.game;

import tests.map.*;
import tests.character.*;
import tests.character.player.*;
import tests.character.npc.Enemy;
import tests.character.npc.enemy.*;
import tests.item.*;
import tests.item.weapon.*;
import tests.character.gui.*;
import tests.item.potion.*;

import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class Game extends Application {
    Scanner scanner = new Scanner(System.in);

    private Map[] levels;
    public Player newPlayer;
    public static Stage gameStage;


    public static void main(String[] args) {
        Application.launch(args);
        // Print the name without errors
        //System.out.println(newPlayer.getName());
    }

    @Override
    public void start(Stage stage) throws Exception {
        gameStage = stage;
        Scene createPlayer = new CreatePlayer(this);
        gameStage.setScene(createPlayer);
        gameStage.setTitle("CaveDungeon 0.19.03.22 OMEGA");
        gameStage.show();
        // ERROR
        //System.out.println(newPlayer.getName());
    }

    public Game() {
        long gameSeed = System.currentTimeMillis();
        // long gameSeed = 1550961460665L;
        System.out.println("Seed: " + gameSeed);
        levels = new Map[1];
        for (int i = 0; i < levels.length; ++i)
            levels[i] = new Map(gameSeed + i);
        mapTests();
        // createPlayer();
        // playerTests();
        // battleTests();
    }

    void mapTests() {
        for (int i = 0; i < levels.length; ++i)
            System.out.println(levels[i].mapToString());
    }

    void createPlayer() {
        System.out.println("Input the values for create player (!)");
        String name;
        char gender;
        System.out.println("Write your name: ");
        name = scanner.nextLine();
        System.out.println("Select your gender (M or F): ");
        gender = scanner.next().charAt(0);
        System.out.println("Select your player Melee(1) or Mage(2): ");
        switch (scanner.nextInt()) {
        case 1:
            newPlayer = new Melee(name, 25, gender, new Inventory());
            break;
        case 2:
            newPlayer = new Mage(name, 25, gender, new Inventory());
            break;
        default:
            newPlayer = null;
            System.out.println("Player not found!");
        }
    }

    public void playerTests() {
        Weapon elvenSword = new Sword();
        Weapon bow = new Bow();
        Potion healthPotion = new HealthPotion(15);
        Potion staminaPotion = new StaminaPotion(10);
        Scanner scanner = new Scanner(System.in);
        int selectedWeapon;
        newPlayer.getInventory().addItemToInventory(elvenSword, 0);
        newPlayer.getInventory().addItemToInventory(bow, 1);
        newPlayer.getInventory().addItemToInventory(healthPotion, 0);
        newPlayer.getInventory().addItemToInventory(staminaPotion, 1);
        System.out.println("Which weapon do you want to equip?");
        System.out.println(newPlayer.getInventory().printWeapons());
        selectedWeapon = scanner.nextInt();
        newPlayer.getInventory().equipWeapon(selectedWeapon);
        System.out.println(newPlayer.getInventory().printWeapons());

        System.out.println(newPlayer.playerToString());
    }

    public void battleTests() {
        char startBattle;
        Enemy newEnemie;
        do {
            System.out.println("Select your enemy: (1) Zombie, (2) Skeleton, (3) Chort, (4) Swampy, (5) Necromancer");
            switch (scanner.nextInt()) {
            case 1:
                newEnemie = new Zombie();
                break;
            case 2:
                newEnemie = new Skeleton();
                break;
            case 3:
                newEnemie = new Chort();
                break;
            case 4:
                newEnemie = new Swampy();
                break;
            case 5:
                newEnemie = new Necromancer();
                break;
            default:
                newEnemie = null;
                break;
            }
            System.out.println("______________START BATTLE________________");
            Battle.startBattle(newPlayer, newEnemie);
            System.out.println("\nStart new battle? (Y or N)");
            startBattle = scanner.next().charAt(0);
        } while (startBattle == 'Y');

    }

    // public void launchCreatePlayer() {
    // launch(CreatePlayer.class, this);
    // }

    public void setNewPlayer(Player newPlayer) {
        this.newPlayer = newPlayer;
    }

    public Player getNewPlayer() {
      return newPlayer;
    }

    public static Stage getGameStage() {
      return gameStage;
    }

}
