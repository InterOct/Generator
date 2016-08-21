package by.interoct.generator.ui.controller.impl;

import by.interoct.generator.ui.constant.EffectsUtil;
import by.interoct.generator.ui.constant.Extensions;
import by.interoct.generator.ui.constant.ListenersUtil;
import by.interoct.generator.ui.controller.ControllerBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MainController extends ControllerBase {

    private static final String PATH_TO_GEN_VM_REF_PR = "../../../ui/fxml/generateVmReferencesPreview.fxml";
    private static final String PATH_TO_GEN_VM_FROM_ZUL = "../../../ui/fxml/generateVmFromZul.fxml";

    @FXML
    private CheckBox cbGenerateVmFromZul;
    @FXML
    private AnchorPane pathToZulVm;
    @FXML
    private Button btnNext;
    @FXML
    private CheckBox cbGenerateVmReferences;
    @FXML
    private AnchorPane pathToZul;
    @FXML
    private TextField tfPathToZul1;
    @FXML
    private TextField tfPathToZul2;
    @FXML
    private TextField tfPathToVM;

    private GenerateVmReferencesPreviewController generateVmReferencesPreviewController;
    private GenerateVmFromZulController generateVmFromZulController;
    private Scene nextScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTFListeners();
    }

    public void close() {
        Stage stage = getWindow();
        stage.close();
    }

    public void actionGenerateVmReferences(ActionEvent actionEvent) {
        pathToZul.setDisable(!cbGenerateVmReferences.isSelected());
        btnNext.setDisable(!isBtnNextEnabled());
    }

    public void actionGenerateVmFromZul(ActionEvent actionEvent) {
        pathToZulVm.setDisable(!cbGenerateVmFromZul.isSelected());
        btnNext.setDisable(!isBtnNextEnabled());
    }

    @Override
    public void update() {
        btnNext.setDisable(!isBtnNextEnabled());
    }

    private void initGenerateVmReferencesPreviewScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(PATH_TO_GEN_VM_REF_PR));
        Parent root = (Parent) loader.load();
        nextScene = new Scene(root);
        generateVmReferencesPreviewController = loader.getController();
    }


    private void initGenerateVmFromZulController() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(PATH_TO_GEN_VM_FROM_ZUL));
        Parent root = (Parent) loader.load();
        nextScene = new Scene(root);
        generateVmReferencesPreviewController = loader.getController();
    }

    private void initTFListeners() {
        tfPathToZul1.focusedProperty().addListener(ListenersUtil.pathValidationListener(this, tfPathToZul1, Extensions.ZUL));
        tfPathToZul2.focusedProperty().addListener(ListenersUtil.pathValidationListener(this, tfPathToZul2, Extensions.ZUL));
        tfPathToVM.focusedProperty().addListener(ListenersUtil.pathValidationListener(this, tfPathToVM, Extensions.VM));
    }


    public void actionNext() throws IOException {
        Stage window = getWindow();

        List<Scene> scenes = new ArrayList<>();
        scenes.add(getWindow().getScene());
        if (cbGenerateVmReferences.isSelected()) {
            initGenerateVmReferencesPreviewScene();
            generateVmReferencesPreviewController.setSceneChain(scenes);
            generateVmReferencesPreviewController.setPreviousScene(window.getScene());
            generateVmReferencesPreviewController.setPath(tfPathToZul1.getText());
            generateVmReferencesPreviewController.postInit();
            scenes.add(nextScene);
        }
        if (cbGenerateVmFromZul.isSelected()) {
            generateVmFromZulController.setSceneChain(scenes);

        }


        window.setScene(nextScene);
    }


    private boolean isBtnNextEnabled() {
        return (
                cbGenerateVmFromZul.isSelected() && !cbGenerateVmReferences.isSelected() &&
                        EffectsUtil.OK_HIGHLIGHT.equals(tfPathToZul2.getEffect()) &&
                        EffectsUtil.OK_HIGHLIGHT.equals(tfPathToVM.getEffect())
        ) || (
                cbGenerateVmReferences.isSelected() && !cbGenerateVmFromZul.isSelected() &&
                        EffectsUtil.OK_HIGHLIGHT.equals(tfPathToZul1.getEffect())
        ) || (
                cbGenerateVmFromZul.isSelected() && cbGenerateVmReferences.isSelected() &&
                        EffectsUtil.OK_HIGHLIGHT.equals(tfPathToZul2.getEffect()) &&
                        EffectsUtil.OK_HIGHLIGHT.equals(tfPathToVM.getEffect()) &&
                        EffectsUtil.OK_HIGHLIGHT.equals(tfPathToZul1.getEffect())
        );
    }
}
