package by.interoct.generator.ui.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Branavets_AY on 8/5/2016.
 */
public abstract class BaseController<T extends BaseData> implements Updatable, Initializable, PostInitializable {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void sInitialize() {

    }
}

