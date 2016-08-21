package by.interoct.generator.ui.controller.impl;

import by.interoct.generator.logic.ZULGenerator;
import by.interoct.generator.ui.constant.EffectsUtil;
import by.interoct.generator.ui.constant.Extensions;
import by.interoct.generator.ui.constant.ListenersUtil;
import by.interoct.generator.ui.controller.BaseController;
import by.interoct.parser.dom.entity.Element;
import by.interoct.parser.dom.exception.ParserException;
import by.interoct.parser.dom.exception.ReadSourceException;
import by.interoct.parser.dom.helper.ParserFactory;
import by.interoct.parser.dom.helper.ReadSourceFactory;
import by.interoct.parser.dom.logic.ReadSource;
import by.interoct.parser.dom.logic.impl.Parser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GenerateVmReferencesPreviewController extends BaseController {
    @FXML
    public TextArea taBefore;
    @FXML
    public TextArea taAfter;
    @FXML
    public TextField saveLocation;
    @FXML
    public Button btnSave;

    private Scene previousScene;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveLocation.focusedProperty().addListener(ListenersUtil.pathValidationListener(this, saveLocation, Extensions.ZUL));
    }

    public void postInit() {
        try (ReadSource readSource = ReadSourceFactory.getInstance()
                .getReadSource(path)) {
            Parser xmlParser = ParserFactory.getInstance().getParser(readSource);
            Element root = xmlParser.getRootElement();
            taBefore.setText(root.toString());
            taAfter.setText(ZULGenerator.getInstance().generate(root, true).toString());
            saveLocation.setText(path);
            saveLocation.requestFocus();
            taAfter.requestFocus();
            btnSave.requestFocus();
        } catch (ReadSourceException | ParserException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(ActionEvent actionEvent) {
        Stage window = getStage(actionEvent);
        window.setScene(previousScene);
    }

    private Stage getStage(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        return (Stage) source.getScene().getWindow();
    }

    @Override
    public void update() {
        btnSave.setDisable(!EffectsUtil.OK_HIGHLIGHT.equals(saveLocation.getEffect()));
    }

    public void save(ActionEvent actionEvent) {
        try (FileOutputStream os = new FileOutputStream(saveLocation.getText())) {
            os.write(taAfter.getText().getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}