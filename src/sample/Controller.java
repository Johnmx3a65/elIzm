package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;

public class Controller {

    @FXML
    private TextField filePathField;

    @FXML
    private Button toSecondPageButton;

    @FXML
    void initialize() {
        onActionToSecondPageButton();

    }

    private boolean isFileInput(){
        String filePath = filePathField.getText();

        if(filePath.isEmpty()){
            return false;
        }

        File f = new File(filePathField.getText());
        return f.isFile();
    }

    private String readFromFile() throws IOException {
        if (!isFileInput()) {
            throw new FileNotFoundException("File not found");
        }
        InputStream fs = new FileInputStream(filePathField.getText());
        int count = fs.available();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stringBuffer.append((char) fs.read());
        }
        fs.close();
        return stringBuffer.toString();

    }

    private double[] getPropertyFromFile() throws IOException {
        String fileText = readFromFile();
        String[] stringProperties = fileText.split(";");
        double[] doubleProperties = new double[stringProperties.length];
        for(int i = 0; i < stringProperties.length; i++){
            doubleProperties[i] = Double.parseDouble(stringProperties[i]);
        }
        return doubleProperties;
    }

    private void setPropertyToSecondPage(SetPropertyController secondPage) throws IOException {
        double[] properties = getPropertyFromFile();

        if (properties.length != 5) {
            throw new IOException("Incorrect count of properties");
        } else {
            secondPage.setPipeDiameterDouble(properties[0]);
            secondPage.setPipelineLengthDouble(properties[1]);
            secondPage.setPipeHeightDouble(properties[2]);
            secondPage.setInputTankHeightDouble(properties[3]);
            secondPage.setOutputTankHeightDouble(properties[4]);
        }

    }

    private void loadPropertySetPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/setPropertyFile.fxml"));
        try {
            loader.load();
            SetPropertyController secondPage = loader.getController();
            setPropertyToSecondPage(secondPage);
            secondPage.setInitializeByMyController(true);
            secondPage.initialize();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            ErrorPopUp errorPopUp = new ErrorPopUp("ERRORS", e.getMessage());
            errorPopUp.showAndWait();
            throw e;
        }
    }

    private void onActionToSecondPageButton(){
        toSecondPageButton.setOnAction(event -> {
            try {
                loadPropertySetPage();
                toSecondPageButton.getScene().getWindow().hide();
            } catch (IOException ignored) {
            }
        });
    }
}

