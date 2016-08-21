package by.interoct.generator.ui.helper;

import by.interoct.generator.ui.controller.BaseController;
import by.interoct.generator.ui.helper.exception.ScenePoolException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

/**
 * @author aleks
 */
public class ScenePool {

    private static final String FXML = "../fxml";
    private HashMap<Class<? extends BaseController>, SceneContainer> sceneContainerMap;
    private Stage window;

    public static ScenePool instance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    public void initialize(Stage window) {
        this.window = window;
        try {
            sceneContainerMap = new HashMap<>();
            URL resource = getClass().getResource(FXML);
            File fxmlFolder = new File(resource.getPath());
            File[] files = fxmlFolder.listFiles();
            for (File file : files) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(FXML + '/' + file.getName()));
                Scene scene = new Scene((Parent) fxmlLoader.load());
                SceneContainer sceneContainer = new SceneContainer(scene, window, fxmlLoader);
                sceneContainerMap.put((Class<? extends BaseController>) fxmlLoader.getController().getClass(), sceneContainer);
            }
        } catch (Exception e) {
            throw new ScenePoolException("Error initialize ScenePool", e);
        }

    }

    public <T extends BaseController> SceneContainer<T> get(Class<T> clazz) {
        return sceneContainerMap.get(clazz);
    }

    public SceneContainer create() {
        throw new UnsupportedOperationException();
    }

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    private static class SingletonHolder {
        static final ScenePool HOLDER_INSTANCE = new ScenePool();
    }


}
