package sample;

import javafx.scene.control.Alert;

public class ErrorPopUp {
    private final Alert alert;

    public ErrorPopUp(String title, String content){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
    }

    public void showAndWait(){
        alert.showAndWait();
    }

}
