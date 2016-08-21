package by.interoct.generator.start;

import by.interoct.generator.ui.controller.impl.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../ui/fxml/main.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainController controller = fxmlLoader.getController();
        controller.setWindow(primaryStage);

        primaryStage.setTitle("Select necessary actions");
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
