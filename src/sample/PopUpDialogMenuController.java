package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUpDialogMenuController {

    private boolean isInitializeByMyController = false;

    @FXML
    private Button closeMyPopUpDialogMenu;

    @FXML
    private Label waterSpeedLabel;

    @FXML
    private Label waterConsumptionLabel;

    public void setWaterSpeedLabel(Double waterSpeed) {
        String formattedDouble = String.format("%.2f", waterSpeed);
        waterSpeedLabel.setText(formattedDouble);
    }

    public void setWaterConsumptionLabel(Double waterConsumption) {
        String formattedDouble = String.format("%.2f", waterConsumption);
        waterConsumptionLabel.setText(formattedDouble);
    }

    public void setInitializeByMyController(boolean initializeByMyController) {
        isInitializeByMyController = initializeByMyController;
    }

    @FXML
    public void initialize() {
        if (isInitializeByMyController) {
            onCloseMyPopUpDialogMenuAction();
        }

    }

    private void onCloseMyPopUpDialogMenuAction() {
        closeMyPopUpDialogMenu.setOnAction(event -> {
            closeMyPopUpDialogMenu.getScene().getWindow().hide();
        });
    }


}
