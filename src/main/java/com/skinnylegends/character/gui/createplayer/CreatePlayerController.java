package com.skinnylegends.character.gui.createplayer;

import com.skinnylegends.character.Player;
import com.skinnylegends.character.player.*;
import com.skinnylegends.game.Game;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;

public class CreatePlayerController {
    private int playerSelected;
    private Player newPlayer;
    private Game game;

    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> gender;

    @FXML
    private void selectMelee() {
        playerSelected = 1;
    }
    @FXML
    private void selectMage() {
        playerSelected = 2;
    }

    @FXML
    private void createPlayer() {
        if (name.getText().equals("")) {
            System.out.println("Enter a name");
            return;
        } else if (gender.getSelectionModel().isEmpty()) {
            System.out.println("Choose a gender");
            return;
        } else if (playerSelected != 1 && playerSelected != 2) {
            System.out.println("Choose a type of player");
            return;
        }
        checkAccount();
    }

    private char genderSelected() {
        return gender.getValue().toLowerCase().charAt(0);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void checkAccount() {
        if (game.findFile(name.getText(), 'P').exists())
            System.out.println("User already exists");
        else {
            switch (playerSelected) {
                case 1:
                    newPlayer = new Melee(name.getText(), genderSelected());
                    break;
                case 2:
                    newPlayer = new Mage(name.getText(), genderSelected());
                    break;
                }
            // Set the newPlayer to game
            game.setPlayer(newPlayer);
            game.addPlayerToMapAndContinue();
        }
    }
}
