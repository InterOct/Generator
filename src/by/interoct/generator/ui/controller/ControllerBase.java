package by.interoct.generator.ui.controller;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Branavets_AY on 8/5/2016.
 */
public abstract class ControllerBase implements Updatable, Initializable, PostInitialized {

    private Stage window;
    private List<Scene> sceneChain;
    private Scene nextScene;
    private Scene previousScene;

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public Scene getNextScene() {
        return nextScene;
    }

    public void setNextScene(Scene nextScene) {
        this.nextScene = nextScene;
    }

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public List<Scene> getSceneChain() {
        return sceneChain;
    }

    public void setSceneChain(List<Scene> sceneChain) {
        this.sceneChain = sceneChain;
    }


    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void postInit() {

    }
}

