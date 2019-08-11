package com.skinnylegends.character.gui.battle;

import com.skinnylegends.character.Character;
import com.skinnylegends.character.Player;
import com.skinnylegends.character.npc.Boss;
import com.skinnylegends.character.NPC;
import com.skinnylegends.game.Game;
import com.skinnylegends.item.Inventory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ProgressBar;

public class BattleController {
    private Game game;
    private Player player;
    private Inventory inventory;
    private NPC npc;
    private int initNpcHealth;

    @FXML
    private Pane wrapperPane;
    @FXML
    private Pane selectAttack, battleView, selectPotion, playerAttack, enemyAttack, battleResult, equipPWeapon;
    @FXML
    private GridPane selectOption, abilityGrid;
    @FXML
    private HBox potionsHBox;
    @FXML
    private ProgressBar healthPBar, healthEBar, staminaBar;
    @FXML
    private Label playerName, playerLevel, playerStats, enemyName, enemyHealth;
    @FXML
    private Label weaponEquipped, attackMessage, pAttackInfo, nAttackInfo, finishText, finalInfo;
    @FXML
    private Label namePOne, namePTwo, namePThree, infoWOne, infoWTwo;
    @FXML
    private Button basicAttack, abilityOne, abilityTwo, abilityThree, usePOne, usePTwo, usePThree;
    @FXML
    private ImageView imgPlayer, imgNpc, imgWeapon, imgPOne, imgPTwo, imgPThree, imgWOne, imgWTwo;

    public BattleController() {
        FXMLLoader[] loader = new FXMLLoader[6];
        for (int i = 0; i < loader.length; ++i) {
            loader[i] = new FXMLLoader();
            loader[i].setBuilderFactory(new JavaFXBuilderFactory());
            loader[i].setController(this);
        }
        try {
            selectAttack = loader[0].load(loadFXML("./selectAttack.fxml"));
            selectPotion = loader[1].load(loadFXML("./selectPotion.fxml"));
            playerAttack = loader[2].load(loadFXML("./playerAttack.fxml"));
            enemyAttack = loader[3].load(loadFXML("./enemyAttack.fxml"));
            battleResult = loader[4].load(loadFXML("./battleResult.fxml"));
            equipPWeapon = loader[5].load(loadFXML("./equipWeapon.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FileInputStream loadFXML(String path) throws URISyntaxException, FileNotFoundException {
        URI fxmlDocPath = getClass().getResource(path).toURI();
        return new FileInputStream(new File(fxmlDocPath));
    }

    @FXML
    public void initialize(Player player, NPC npc, Game game) {
        this.player = player;
        this.npc = npc;
        this.game = game;
        inventory = player.getInventory();
        initNpcHealth = npc.getHealthPoints();
        renderBattleView();
        renderSelectAttack();
        renderSelectPotion();
        renderEquipWeapon();
    }

    @FXML
    private void attack() {
        changePane(selectOption, selectAttack);
    }

    @FXML
    private void usePotion() {
        if (checkPotions())
            changePane(selectOption, selectPotion);
    }

    @FXML
    private void useBasicAttack() {
        attackSystem();
    }

    @FXML
    private void useAbilityOne() {
        attackSystem(0);
    }

    @FXML
    private void equipWeapon() {
        changePane(selectOption, equipPWeapon);
    }

    @FXML
    private void switchWeapon() {
        switchSystem();
    }

    @FXML
    private void useAbilityTwo() {
        attackSystem(1);
    }

    @FXML
    private void useAbilityThree() {
        attackSystem(2);
    }

    @FXML
    private void usePOne() {
        potionSystem(0);
    }

    @FXML
    private void usePTwo() {
        potionSystem(1);
    }

    @FXML
    private void usePThree() {
        potionSystem(2);
    }

    private void attackSystem(int index) {
        if (player.getStamina() < inventory.getEquippedWeapon().getAbility(index).getStaminaCost())
            attackMessage.setText("You don't have enough stamina");
        else {
            pAttackInfo.setText("You did an attack: -" + player.attack(npc, index));
            transitionAttack();
        }
    }

    private void attackSystem() {
        pAttackInfo.setText("You did an attack: -" + player.attack(npc));
        transitionAttack();
    }

    private void transitionAttack() {
        upgradeStats();
        changePane(selectAttack, playerAttack);
        boolean enemyDefeated = npc.getHealthPoints() <= 0;
        PauseTransition enemyT = new PauseTransition(Duration.seconds(3));
        enemyT.setOnFinished(event -> {
            if (!enemyDefeated) {
                nAttackInfo.setText(npc.getName() + " has made an attack and has damaged you: -" + npc.attack(player));
                upgradeStats();
                changePane(playerAttack, enemyAttack);
            } else {
                finishText.setText("You have defeated " + npc.getName());
                finalInfo.setText(player.checkLevelUp(npc.getExperience()));
                changePane(playerAttack, battleResult);
            }
        });

        PauseTransition continueT = new PauseTransition(Duration.seconds(3));
        continueT.setOnFinished(event -> {
            if (player.getHealthPoints() > 0) {
                if (enemyDefeated)
                    if(npc instanceof Boss) {
                        System.out.println("YOU WON!"); 
                        endGame();
                    }
                    else {
                        if (Math.random() > 0.5)
                            game.itemDropped(npc.dropWeapon(player));
                        else
                            game.itemDropped(npc.dropArmor(player));
                    }
                else
                    changePane(enemyAttack, selectOption);
            } 
            else endGame();
        });

        SequentialTransition seqT = new SequentialTransition(enemyT, continueT);
        seqT.play();
    }

    private void potionSystem(int index) {
        player.usePotion(index);
        changePane(selectPotion, selectOption);
        upgradeStats();
        renderSelectPotion();
    }

    private void switchSystem() {
        inventory.switchEquippedWeapons();
        nAttackInfo.setText(
                npc.getName() + " has made an attack and has damaged you: -" + npc.attack(player));
        upgradeStats();
        changePane(equipPWeapon, enemyAttack);

        PauseTransition continueT = new PauseTransition(Duration.seconds(3));
        continueT.setOnFinished(event -> {
            if (player.getHealthPoints() > 0)
                changePane(enemyAttack, selectOption);
            else
                System.exit(0);
        });
        renderSelectAttack();
        renderEquipWeapon();
        continueT.play();
    }

    private void renderSelectAttack() {
        String[] abilityName = new String[3];
        int[] damage = new int[3];
        int[] stamina = new int[3];
        for (int i = 0; i < 3; ++i) {
            abilityName[i] = inventory.getEquippedWeapon().getAbility(i).getName();
            damage[i] = (int) (inventory.getEquippedWeapon().getAbility(i).getBaseDamage() * player.getAttack());
            stamina[i] = inventory.getEquippedWeapon().getAbility(i).getStaminaCost();
        }
        basicAttack.setText("Basic Attack\nDamage: " + (int) (player.getAttack() * 5) + "  Stamina: 0");
        abilityOne.setText(abilityName[0] + "\nDamage: " + damage[0] + "  Stamina: " + stamina[0]);
        abilityTwo.setText(abilityName[1] + "\nDamage: " + damage[1] + "  Stamina: " + stamina[1]);
        abilityThree.setText(abilityName[2] + "\nDamage: " + damage[2] + "  Stamina: " + stamina[2]);
        imgWeapon.setImage(inventory.getEquippedWeapon().render());
    }

    private void renderBattleView() {
        playerName.setText(player.getName());
        playerLevel.setText("Level: " + player.getLevel());
        enemyName.setText(npc.getName());
        imgPlayer.setImage(player.render());
        if (npc.getParent() == Character.ParentCharacter.BOSS)
            imgNpc.setFitWidth(256);
        imgNpc.setImage(npc.render());
        upgradeStats();
    }

    private void renderSelectPotion() {
        if (inventory.getPotion(0) != null) {
            imgPOne.setImage(inventory.getPotion(0).render());
            namePOne.setText(inventory.getPotion(0).getName());
        } else {
            imgPOne.setImage(null);
            namePOne.setText("EMPTY");
        }
        if (inventory.getPotion(1) != null) {
            imgPTwo.setImage(inventory.getPotion(1).render());
            namePTwo.setText(inventory.getPotion(1).getName());
        } else {
            imgPTwo.setImage(null);
            namePTwo.setText("EMPTY");
        }
        if (inventory.getPotion(2) != null) {
            imgPThree.setImage(inventory.getPotion(2).render());
            namePThree.setText(inventory.getPotion(2).getName());
        } else {
            imgPThree.setImage(null);
            namePThree.setText("EMPTY");
        }
    }

    private void renderEquipWeapon() {
        weaponEquipped.setText(inventory.getEquippedWeapon().getName());
        if (inventory.getWeapon(0) != null) {
            imgWOne.setImage(inventory.getWeapon(0).render());
            infoWOne.setText(inventory.getWeapon(0).getName() + "\n" + inventory.getWeapon(0).printAbilities());
        } else {
            imgWOne.setImage(null);
            infoWOne.setText("EMPTY");
        }
        if (inventory.getWeapon(1) != null) {
            imgWTwo.setImage(inventory.getWeapon(1).render());
            infoWTwo.setText(inventory.getWeapon(1).getName() + "\n" + inventory.getWeapon(1).printAbilities());
        } else {
            imgWTwo.setImage(null);
            infoWTwo.setText("EMPTY");
        }
    }

    private void upgradeStats() {
        int PHp = Math.max(player.getHealthPoints(), 0);
        int PSt = Math.max(player.getStamina(), 0);
        int EHp = Math.max(npc.getHealthPoints(), 0);
        playerStats.setText("HP: " + PHp + "/" + player.getLimitHp() + "  Stamina: "
                + player.getStamina() + "/" + player.getLimitStamina());
        enemyHealth.setText("HP: " + EHp + "/" + initNpcHealth);
        healthPBar.setProgress((double) PHp / player.getLimitHp());
        staminaBar.setProgress((double) PSt / player.getLimitStamina());
        healthEBar.setProgress((double) EHp / initNpcHealth);
    }

    private boolean checkPotions() {
        for (int i = 0; i < 3; ++i)
            if (inventory.getPotion(i) != null)
                return true;
        return false;
    }

    private void changePane(Pane paneRemoved, Pane paneAdded) {
        wrapperPane.getChildren().remove(paneRemoved);
        wrapperPane.getChildren().add(paneAdded);
    }

    private void endGame() {
        File[] allContents = game.findFile(player.getName(), ' ').listFiles();
        if (allContents != null)
            for (File file : allContents)
                file.delete();
        if (game.findFile(player.getName(), ' ').delete())
            System.out.println("Save deleted");
        System.out.println("GAME OVER");
        System.exit(0);
    }
}
