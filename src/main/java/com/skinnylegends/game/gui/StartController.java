package com.skinnylegends.game.gui;

import com.skinnylegends.game.Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class StartController {
    private Game game;

    @FXML
    private Pane wrapPane, selectPane, loadPane;
    @FXML
    private TextField name;

    public StartController() {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setController(this);
        try {
            URI fxmlDocPath = getClass().getResource("./loadPane.fxml").toURI();
            FileInputStream fxmlStream = new FileInputStream(new File(fxmlDocPath));
            loadPane = loader.load(fxmlStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(Game game) {
        this.game = game;
    }

    @FXML
    private void newGame() {
        game.setCreatePlayerScene();
    }

    @FXML
    private void loadGame() {
        changePane(selectPane, loadPane);
    }

    @FXML
    private void continueBtn() throws ClassNotFoundException, IOException {
        checkAccount();
    }

    @FXML
    private void backBtn() {
        changePane(loadPane, selectPane);
    }

    private void checkAccount() throws IOException, ClassNotFoundException {
        if(game.findFile(name.getText(), 'P').exists()) {
            game.loadPlayerAndMap(name.getText());
            game.addPlayerToMapAndContinue();
        }
        else {
            System.out.println("User doesn't exist");
        }
    }

    private void changePane(Pane paneRemoved, Pane paneAdded) {
        wrapPane.getChildren().remove(paneRemoved);
        wrapPane.getChildren().add(paneAdded);
    }
}