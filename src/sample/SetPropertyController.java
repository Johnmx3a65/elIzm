package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SetPropertyController {

    private double pipelineLengthDouble;

    private double pipeDiameterDouble;

    private double pipeHeightDouble;

    private double outputTankHeightDouble;

    private double inputTankHeightDouble;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private final TextField pipelineLength;

    @FXML
    private Button setPropertyButton;

    @FXML
    private final TextField pipeDiameter;

    @FXML
    private final TextField pipeHeight;

    @FXML
    private final TextField outputTankHeight;

    @FXML
    private final TextField inputTankHeight;

    @FXML
    private Button previousButton;

    public SetPropertyController(){
        pipelineLength = new TextField();
        pipeDiameter = new TextField();
        pipeHeight = new TextField();
        outputTankHeight = new TextField();
        inputTankHeight = new TextField();
    }

    @FXML
    public void initialize() {
        initializeProperty();

    }

    public void setPipelineLengthDouble(double pipelineLengthDouble) {
        this.pipelineLengthDouble = pipelineLengthDouble;
    }

    public void setPipeDiameterDouble(double pipeDiameterDouble) {
        this.pipeDiameterDouble = pipeDiameterDouble;
    }

    public void setPipeHeightDouble(double pipeHeightDouble) {
        this.pipeHeightDouble = pipeHeightDouble;
    }

    public void setOutputTankHeightDouble(double outputTankHeightDouble) {
        this.outputTankHeightDouble = outputTankHeightDouble;
    }

    public void setInputTankHeightDouble(double inputTankHeightDouble) {
        this.inputTankHeightDouble = inputTankHeightDouble;
    }

    private void initializeProperty(){
        pipelineLength.setText(String.valueOf(pipelineLengthDouble));
        pipeDiameter.setText(String.valueOf(pipeDiameterDouble));
        pipeHeight.setText(String.valueOf(pipeHeightDouble));
        outputTankHeight.setText(String.valueOf(outputTankHeightDouble));
        inputTankHeight.setText(String.valueOf(inputTankHeightDouble));
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

