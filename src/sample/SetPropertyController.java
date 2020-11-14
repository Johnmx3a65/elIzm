package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SetPropertyController {

    private double pipelineLengthDouble;

    private double pipeDiameterDouble;

    private double pipeHeightDouble;

    private double outputTankHeightDouble;

    private double inputTankHeightDouble;

    private boolean isInitializeByMyController = false;

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
    public void initialize() {
        if (isInitializeByMyController){
            initializeProperty();
            onActionPreviousButton();
            onChangeTextFields();
        }
    }

    private void onSetPropertyButtonAction(){
        setPropertyButton.setOnAction(event -> {

        });
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

    private void changeValueInTextField(TextField textField, double doubleValueOfTextFiled){
        try {
            double value = changingValueIsNotANumber(textField);
            if (value < 0){
                throw new IOException("This property can not be negative");
            }
            if(!textField.getId().equals("pipeHeight")){
                valueIsZero(textField);
            }
            doubleValueOfTextFiled = value;
        }catch (NumberFormatException | IOException e){
            ErrorPopUp errorPopUp = new ErrorPopUp("ERROR", e.getMessage());
            errorPopUp.showAndWait();
            textField.setText(String.valueOf(doubleValueOfTextFiled));
        }
    }

    private void changePipeLineLength(){
        pipelineLength.setOnAction(event -> {
            changeValueInTextField(pipelineLength, pipelineLengthDouble);
        });
    }

    private void changePipeDiameter(){
        pipeDiameter.setOnAction(event -> {
            changeValueInTextField(pipeDiameter, pipeDiameterDouble);
        });
    }

    private void changePipeHeight(){
        pipeHeight.setOnAction(event -> {
            changeValueInTextField(pipeHeight, pipeHeightDouble);
        });
    }

    private void changeOutputTankHeight(){
        outputTankHeight.setOnAction(event -> {
            changeValueInTextField(outputTankHeight, outputTankHeightDouble);
        });
    }

    private void changeInputTankHeight(){
        inputTankHeight.setOnAction(event -> {
            changeValueInTextField(inputTankHeight, inputTankHeightDouble);
        });
    }

    private void onChangeTextFields(){
        changePipeLineLength();
        changePipeDiameter();
        changePipeHeight();
        changeOutputTankHeight();
        changeInputTankHeight();
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

    private void onActionPreviousButton(){
        previousButton.setOnAction(event -> {
            try{
                loadPreviousPage();
                previousButton.getScene().getWindow().hide();
            }catch (IOException ignored){
            }
        });
    }

    public void setInitializeByMyController(boolean initializeByMyController) {
        isInitializeByMyController = initializeByMyController;
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

    private void initializeProperty(){
        System.out.println("Расход воды м^3/ч: " + calculateWaterConsumptionInMPerHour());
        System.out.println("Расход воды л/с: " + calculateWaterConsumptionInLPerSecond());
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

    private double calculateWaterConsumptionInLPerSecond(){
        return calculateWaterConsumptionInMPerHour() * 1000 / 3600;
    }



}

