package sample;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    private SetPropertyController secondPage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField filePathField;

    @FXML
    private Button toSecondPageButton;

    @FXML
    void initialize() {
        toSecondPageButton.setOnAction(event -> {
            toSecondPageButton.getScene().getWindow().hide();
            loadPropertySetPage();
        });

    }

    private boolean isFileInput(){
        String filePath = filePathField.getText();

        if(filePath.isEmpty()){
            return false;
        }

        File f = new File(filePathField.getText());
        return f.isFile();
    }

    private String readFromFile(){
        InputStream fs = null;
        try {
            if (!isFileInput()){
                throw new FileNotFoundException("File not found");
            }
             fs = new FileInputStream(filePathField.getText());
            int count = fs.available();
            StringBuilder stringBuffer = new StringBuilder();
            for (int i = 0; i < count; i++){
                stringBuffer.append((char)fs.read());
            }
            return stringBuffer.toString();
        } catch (IOException e){
            ErrorPopUp ep = new ErrorPopUp("ERROR", e.getMessage());
            ep.showAndWait();
        }finally {
            if(fs != null){
                try {
                    fs.close();
                } catch (IOException e) {
                    ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", "Error close file");
                    errorPopUp.showAndWait();
                }
            }
        }
        return null;
    }

    private double[] getPropertyFromFile(){
        String fileText = readFromFile();
        if(fileText != null){
            String[] stringProperties = fileText.split(";", 5);
            double[] doubleProperties = new double[stringProperties.length];
            for(int i = 0; i < stringProperties.length; i++){
                doubleProperties[i] = Double.parseDouble(stringProperties[i]);
            }
            return doubleProperties;
        }
        return null;
    }

    private void setPropertyToSecondPage(SetPropertyController secondPage){
        try{
            double[] properties = getPropertyFromFile();

            if (properties == null){
                throw new IOException("Error get property");
            }else if(properties.length != 5){
                throw new IOException("Incorrect count of properties");
            }else {
                secondPage.setPipeDiameterDouble(properties[0]);
                secondPage.setPipelineLengthDouble(properties[1]);
                secondPage.setPipeHeightDouble(properties[2]);
                secondPage.setInputTankHeightDouble(properties[3]);
                secondPage.setOutputTankHeightDouble(properties[4]);
            }
        }catch (IOException e){
            ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", e.getMessage());
            errorPopUp.showAndWait();
        }
    }

    private void loadPropertySetPage(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/setPropertyFile.fxml"));
        try {
            loader.load();
            secondPage = loader.getController();
            setPropertyToSecondPage(secondPage);
            secondPage.setInitializeByMyController(true);
            secondPage.initialize();
        }catch (IOException e){
            ErrorPopUp errorPopUp = new ErrorPopUp("ERRORS", e.getMessage());
            errorPopUp.showAndWait();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }


}

