package by.interoct.generator.start;

import by.interoct.generator.ui.controller.impl.IndexController;
import by.interoct.generator.ui.helper.ScenePool;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScenePool scenePool = ScenePool.instance();
        scenePool.initialize(primaryStage);

        primaryStage.setTitle("Select necessary actions");
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setScene(scenePool.get(IndexController.class).getScene());
        primaryStage.show();
    }
}