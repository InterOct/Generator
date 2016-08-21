package by.interoct.generator.ui.controller.impl;

import by.interoct.generator.ui.constant.EffectsUtil;
import by.interoct.generator.ui.constant.Extensions;
import by.interoct.generator.ui.constant.ListenersUtil;
import by.interoct.generator.ui.controller.BaseController;
import by.interoct.generator.ui.helper.ScenePool;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class IndexController extends BaseController {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTFListeners();
    }

    public void close() {
        ScenePool.instance().getWindow().close();
    }

    public void actionGenerateVmReferences() {
        pathToZul.setDisable(!cbGenerateVmReferences.isSelected());
        btnNext.setDisable(!isBtnNextEnabled());
    }

    public void actionGenerateVmFromZul() {
        pathToZulVm.setDisable(!cbGenerateVmFromZul.isSelected());
        btnNext.setDisable(!isBtnNextEnabled());
    }

    @Override
    public void update() {
        btnNext.setDisable(!isBtnNextEnabled());
    }

    private void initTFListeners() {
        tfPathToZul1.focusedProperty().addListener(ListenersUtil.pathValidationListener(this, tfPathToZul1, Extensions.ZUL));
        tfPathToZul2.focusedProperty().addListener(ListenersUtil.pathValidationListener(this, tfPathToZul2, Extensions.ZUL));
        tfPathToVM.focusedProperty().addListener(ListenersUtil.pathValidationListener(this, tfPathToVM, Extensions.VM));
    }


    public void actionNext() throws IOException {

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
