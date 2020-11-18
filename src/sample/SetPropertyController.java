package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class SetPropertyController {

    private double pipelineLengthDouble;

    private double pipeDiameterDouble;

    private double pipeHeightDouble;

    private double outputTankHeightDouble;

    private double inputTankHeightDouble;

    private double outputTankCapacityDouble;

    private double inputTankCapacityDouble;

    private boolean isInitializeByMyController = false;

    private String filePath;

    @FXML
    private Button previousButton;

    @FXML
    private Button setPropertyButton;

    @FXML
    private TextField pipelineLength;

    @FXML
    private TextField pipeDiameter;

    @FXML
    private TextField pipeHeight;

    @FXML
    private TextField outputTankHeight;

    @FXML
    private TextField inputTankHeight;

    @FXML
    private TextField outputTankCapacity;

    @FXML
    private TextField inputTankCapacity;

    @FXML
    public void initialize() {
        if (isInitializeByMyController){
            onActionPreviousButton();
            onChangeTextFields();
            onSetPropertyButtonAction();
        }
    }

    private void onActionPreviousButton(){
        previousButton.setOnAction(event -> {
            try{
                loadPreviousPage();
                previousButton.getScene().getWindow().hide();
            }catch (IOException ignored){
            }
        });
    }

    private void onSetPropertyButtonAction(){
        setPropertyButton.setOnAction(event -> {
            try {
                loadMyPopUpDialogMenu();
            }catch (IOException ignored){

            }
        });
    }

    private void loadMyPopUpDialogMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/valuesOfWaterSpeedAndConsumption.fxml"));
        try {
            loader.load();
            PopUpDialogMenuController children = loader.getController();
            children.setWaterConsumptionLabel(calculateWaterConsumptionInMPerHour());
            children.setWaterSpeedLabel(calculateWaterSpeed());
            children.setInitializeByMyController(true);
            children.initialize();
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

    private double changingValueIsNotANumber(TextField textField)throws NumberFormatException{
        String text = textField.getText();
        double value;
        try {
            value = Double.parseDouble(text);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Value is not a number");
        }
        return value;
    }

    private void valueIsZero(TextField textField) throws IOException {
        double value = Double.parseDouble(textField.getText());
        try {
            if (value == 0){
                throw new IOException("This property can not be 0");
            }
        }catch (IOException e){
            throw new IOException(e.getMessage());
        }
    }

    private double changeValueInTextField(TextField textField, double doubleValueOfTextFiled){
        try {
            double value = changingValueIsNotANumber(textField);
            if (value < 0){
                throw new IOException("This property can not be negative");
            }
            if(!textField.getId().equals("pipeHeight")){
                valueIsZero(textField);
            }
            return value;
        }catch (NumberFormatException | IOException e){
            ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", e.getMessage());
            errorPopUp.showAndWait();
            textField.setText(String.valueOf(doubleValueOfTextFiled));
        }
        return doubleValueOfTextFiled;
    }

    private void changeInputTankCapacity(){
        inputTankCapacity.setOnAction(event -> {
            inputTankCapacityDouble = changeValueInTextField(inputTankCapacity, inputTankCapacityDouble);
            try{
                writeInFile();
            }catch (IOException e){
                ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", e.getMessage());
                errorPopUp.showAndWait();
            }

        });
    }

    private void changeOutputTankCapacity(){
        outputTankCapacity.setOnAction(event -> {
            outputTankCapacityDouble = changeValueInTextField(outputTankCapacity, outputTankCapacityDouble);
            try{
                writeInFile();
            }catch (IOException e){
                ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", e.getMessage());
                errorPopUp.showAndWait();
            }

        });
    }

    private void changePipeLineLength(){
        pipelineLength.setOnAction(event -> {
            pipelineLengthDouble = changeValueInTextField(pipelineLength, pipelineLengthDouble);
            try{
                writeInFile();
            }catch (IOException e){
                ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", e.getMessage());
                errorPopUp.showAndWait();
            }

        });
    }

    private void changePipeDiameter(){
        pipeDiameter.setOnAction(event -> {
            pipeDiameterDouble = changeValueInTextField(pipeDiameter, pipeDiameterDouble);
            try{
                writeInFile();
            }catch (IOException e){
                ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", e.getMessage());
                errorPopUp.showAndWait();
            }
        });
    }

    private void changePipeHeight(){
        pipeHeight.setOnAction(event -> {
            pipeHeightDouble = changeValueInTextField(pipeHeight, pipeHeightDouble);
            try{
                writeInFile();
            }catch (IOException e){
                ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", e.getMessage());
                errorPopUp.showAndWait();
            }
        });
    }

    private void changeOutputTankHeight(){
        outputTankHeight.setOnAction(event -> {
            outputTankHeightDouble = changeValueInTextField(outputTankHeight, outputTankHeightDouble);
            try{
                writeInFile();
            }catch (IOException e){
                ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", e.getMessage());
                errorPopUp.showAndWait();
            }
        });
    }

    private void changeInputTankHeight(){
        inputTankHeight.setOnAction(event -> {
            inputTankHeightDouble = changeValueInTextField(inputTankHeight, inputTankHeightDouble);
            try{
                writeInFile();
            }catch (IOException e){
                ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", e.getMessage());
                errorPopUp.showAndWait();
            }
        });
    }

    private void onChangeTextFields(){
        changePipeLineLength();
        changePipeDiameter();
        changePipeHeight();
        changeOutputTankHeight();
        changeInputTankHeight();
        changeInputTankCapacity();
        changeOutputTankCapacity();
    }

    private void loadPreviousPage()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/sample.fxml"));
        try {
            loader.load();
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

    public void setInitializeByMyController(boolean initializeByMyController) {
        isInitializeByMyController = initializeByMyController;
    }

    public void setInputTankCapacityDouble(double inputTankCapacityDouble) {
        this.inputTankCapacityDouble = inputTankCapacityDouble;
        inputTankCapacity.setText(String.valueOf(inputTankCapacityDouble));
    }

    public void setOutputTankCapacityDouble(double outputTankCapacityDouble) {
        this.outputTankCapacityDouble = outputTankCapacityDouble;
        outputTankCapacity.setText(String.valueOf(outputTankCapacityDouble));
    }

    public void setPipelineLengthDouble(double pipelineLengthDouble) {
        this.pipelineLengthDouble = pipelineLengthDouble;
        pipelineLength.setText(String.valueOf(pipelineLengthDouble));
    }

    public void setPipeDiameterDouble(double pipeDiameterDouble) {
        this.pipeDiameterDouble = pipeDiameterDouble;
        pipeDiameter.setText(String.valueOf(pipeDiameterDouble));
    }

    public void setPipeHeightDouble(double pipeHeightDouble) {
        this.pipeHeightDouble = pipeHeightDouble;
        pipeHeight.setText(String.valueOf(pipeHeightDouble));
    }

    public void setOutputTankHeightDouble(double outputTankHeightDouble) {
        this.outputTankHeightDouble = outputTankHeightDouble;
        outputTankHeight.setText(String.valueOf(outputTankHeightDouble));
    }

    public void setInputTankHeightDouble(double inputTankHeightDouble) {
        this.inputTankHeightDouble = inputTankHeightDouble;
        inputTankHeight.setText(String.valueOf(inputTankHeightDouble));
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private void writeInFile() throws IOException {

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            DecimalFormat format = new DecimalFormat("#.00");
            String pipeDiameterString = format.format(pipeDiameterDouble).replace(".",",");
            String pipeLineLegthString = format.format(pipelineLengthDouble).replace(".",",");
            String pipeHeightString = format.format(pipeHeightDouble).replace(".",",");
            String inputTankHeightString = format.format(inputTankHeightDouble).replace(".",",");
            String outputTankHeightString = format.format(outputTankHeightDouble).replace(".",",");
            String inputTankCapacityString = format.format(inputTankCapacityDouble).replace(".",",");
            String outputTankCapacityString = format.format(outputTankCapacityDouble).replace(".",",");
            String buffer = pipeDiameterString + ";" + pipeLineLegthString + ";" + pipeHeightString + ";" + inputTankHeightString + ";" + outputTankHeightString + ";" + inputTankCapacityString + ";" + outputTankCapacityString;
            fos.write(buffer.getBytes());
        } catch (IOException e) {
            throw new IOException("Error write in file");
        }
    }

    private double calculateWaterSpeed(){
        double WATER_PRESSURE = 38;
        double GRAVITY_ACCELERATION = 9.81;
        double SLIDING_FRICTION = 0.032;

        double TANK_HEIGHT_DIFFERENCE = inputTankHeightDouble - outputTankHeightDouble;
        double FINAL_WATER_PRESSURE = WATER_PRESSURE - TANK_HEIGHT_DIFFERENCE - pipeHeightDouble;
        double NUMERATOR = FINAL_WATER_PRESSURE * 2 * GRAVITY_ACCELERATION * pipeDiameterDouble;
        double DENOMINATOR = SLIDING_FRICTION * pipelineLengthDouble;
        double DIVIDE = NUMERATOR/DENOMINATOR;
        return Math.sqrt(DIVIDE);
    }

    private double calculateWaterConsumptionInMPerHour(){
        double SECOND_TO_HOUR = 3600;
        double WATER_SPEED_PER_HOUR = calculateWaterSpeed() * SECOND_TO_HOUR;
        double SENSOR_PIPE_DIAMETER = 0.01;
        double CIRCLE_AREA = SENSOR_PIPE_DIAMETER * SENSOR_PIPE_DIAMETER * Math.PI;
        return WATER_SPEED_PER_HOUR * CIRCLE_AREA;
    }
}

