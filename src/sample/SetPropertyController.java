package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SetPropertyController {

    private double pipelineLengthDouble;

    private double pipeDiameterDouble;

    private double pipeHeightDouble;

    private double outputTankHeightDouble;

    private double inputTankHeightDouble;

    private boolean isInitializeByMyController = false;

    @FXML
    private TextField pipelineLength;

    @FXML
    private Button setPropertyButton;

    @FXML
    private TextField pipeDiameter;

    @FXML
    private TextField pipeHeight;

    @FXML
    private TextField outputTankHeight;

    @FXML
    private TextField inputTankHeight;

    @FXML
    private Button previousButton;

    @FXML
    public void initialize() {
        if (isInitializeByMyController){
            initializeProperty();
        }


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

