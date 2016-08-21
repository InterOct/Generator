package by.interoct.generator.ui.helper;

import by.interoct.generator.ui.controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author aleks on 22.08.2016.
 */
public class SceneContainer<T extends BaseController> {
    private Scene scene;
    private Stage window;
    private FXMLLoader loader;

    public SceneContainer() {
    }

    public SceneContainer(Scene scene, Stage window, FXMLLoader loader) {
        this.scene = scene;
        this.window = window;
        this.loader = loader;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public T getController() {
        return (T) loader.getController();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SceneContainer that = (SceneContainer) o;

        if (scene != null ? !scene.equals(that.scene) : that.scene != null) return false;
        if (window != null ? !window.equals(that.window) : that.window != null) return false;
        return loader != null ? loader.equals(that.loader) : that.loader == null;

    }

    @Override
    public int hashCode() {
        int result = scene != null ? scene.hashCode() : 0;
        result = 31 * result + (window != null ? window.hashCode() : 0);
        result = 31 * result + (loader != null ? loader.hashCode() : 0);
        return result;
    }
}
