package com.example.wirelesscalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FifthChoice extends AppCompatActivity {

    // Define arrays for spinners
    String cityAreaUnits[] = {"sq km", "sq meter", "sq mile"};
    String callDurationUnits[] = {"sec", "min", "hour"};
    String numberOfCallsUnits[] = {"per sec", "per min", "per hour", "per day", "per week", "per month"};
    String minimumSIRUnits[] = {"dB", "dBm", "Watt"};
    String referenceDistanceUnits[] = {"m", "km", "mile"};
    String referencePowerUnits[] = {"dB", "dBm", "Watt"};
    String receiverSensitivityUnits[] = {"dB", "dBm", "Watt", "mWatt", "uWatt"};
    String whatToCalculate[] = {"Maximum distance between base stations", "Maximum cell size (hexagon)", "Number of cells in the area", "Traffic load for the system", "Traffic load per cell", "Number of cells in each cluster", "Minimum number of carriers for QoS"};
    int ValidNumberOfCells [] = {1, 3, 4, 7, 9, 12, 13, 16, 19, 21, 28};

    // Declare UI components
    private EditText timeSlotsPerCarrierInput, cityAreaInput, numberOfSubscribersInput, numberOfCallsInput,
            callDurationInput, callDropProbabilityInput, minimumSIRInput, referenceDistanceInput,
            referencePowerInput, lossExponentInput, receiverSensitivityInput, coCellsInput;
    private Spinner cityAreaUnitSpinner, callDurationUnitSpinner, numberOfCallsUnitSpinner, minimumSIRUnitSpinner,
            referenceDistanceUnitSpinner, referencePowerUnitSpinner, receiverSensitivityUnitSpinner, whatToCalculateSpinner;
    private TextView outputTextView;
    private Button buttonOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_choice);

        // Initialize EditText fields
        timeSlotsPerCarrierInput = findViewById(R.id.time_slots_per_carrier_input);
        cityAreaInput = findViewById(R.id.city_area_input);
        numberOfSubscribersInput = findViewById(R.id.number_of_subscribers_input);
        numberOfCallsInput = findViewById(R.id.number_of_calls_input);
        callDurationInput = findViewById(R.id.call_duration_input);
        callDropProbabilityInput = findViewById(R.id.call_drop_probability_input);
        minimumSIRInput = findViewById(R.id.minimum_SIR_input);
        referenceDistanceInput = findViewById(R.id.reference_distance_input);
        referencePowerInput = findViewById(R.id.reference_power_input);
        lossExponentInput = findViewById(R.id.loss_exponent_input);
        receiverSensitivityInput = findViewById(R.id.receiver_sensitivity_input);
        coCellsInput = findViewById(R.id.co_cells_input);

        // Initialize Spinners
        cityAreaUnitSpinner = findViewById(R.id.city_area_unit_spinner);
        callDurationUnitSpinner = findViewById(R.id.call_duration_unit_spinner);
        numberOfCallsUnitSpinner = findViewById(R.id.number_of_calls_unit_spinner);
        minimumSIRUnitSpinner = findViewById(R.id.minimum_SIR_unit_spinner);
        referenceDistanceUnitSpinner = findViewById(R.id.reference_distance_unit_spinner);
        referencePowerUnitSpinner = findViewById(R.id.reference_power_unit_spinner);
        receiverSensitivityUnitSpinner = findViewById(R.id.receiver_sensitivity_unit_spinner);
        whatToCalculateSpinner = findViewById(R.id.what_to_calculate_spinner);

        // Initialize Output TextView and Button
        outputTextView = findViewById(R.id.output_textview);
        buttonOutput = findViewById(R.id.button_output);

        // Set up Spinners' Adapters
        ArrayAdapter<String> cityAreaUnitAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, cityAreaUnits);
        cityAreaUnitSpinner.setAdapter(cityAreaUnitAdapter);

        ArrayAdapter<String> callDurationUnitAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, callDurationUnits);
        callDurationUnitSpinner.setAdapter(callDurationUnitAdapter);

        ArrayAdapter<String> numberOfCallsUnitAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, numberOfCallsUnits);
        numberOfCallsUnitSpinner.setAdapter(numberOfCallsUnitAdapter);

        ArrayAdapter<String> minimumSIRUnitAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, minimumSIRUnits);
        minimumSIRUnitSpinner.setAdapter(minimumSIRUnitAdapter);

        ArrayAdapter<String> referenceDistanceUnitAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, referenceDistanceUnits);
        referenceDistanceUnitSpinner.setAdapter(referenceDistanceUnitAdapter);

        ArrayAdapter<String> referencePowerUnitAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, referencePowerUnits);
        referencePowerUnitSpinner.setAdapter(referencePowerUnitAdapter);

        ArrayAdapter<String> receiverSensitivityUnitAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, receiverSensitivityUnits);
        receiverSensitivityUnitSpinner.setAdapter(receiverSensitivityUnitAdapter);

        ArrayAdapter<String> whatToCalculateAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, whatToCalculate);
        whatToCalculateSpinner.setAdapter(whatToCalculateAdapter);

        // Set up Button click listener
        buttonOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateOutput();
            }
        });
    }

    private void calculateOutput() {
        // Initialize variables to store parsed values
        double timeSlotsPerCarrier = 0.0;
        double cityArea = 0.0;
        double numberOfSubscribers = 0.0;
        double numberOfCalls = 0.0;
        double callDuration = 0.0;
        double callDropProbability = 0.0;
        double minimumSIR = 0.0;
        double referenceDistance = 0.0;
        double referencePower = 0.0;
        double lossExponent = 0.0;
        double receiverSensitivity = 0.0;
        double coCells = 0.0;
        double qualityOfService = 0.0;

        try {
            timeSlotsPerCarrier = Double.parseDouble(timeSlotsPerCarrierInput.getText().toString());
            cityArea = Double.parseDouble(cityAreaInput.getText().toString());
            numberOfSubscribers = Double.parseDouble(numberOfSubscribersInput.getText().toString());
            numberOfCalls = Double.parseDouble(numberOfCallsInput.getText().toString());
            callDuration = Double.parseDouble(callDurationInput.getText().toString());
            callDropProbability = Double.parseDouble(callDropProbabilityInput.getText().toString());
            minimumSIR = Double.parseDouble(minimumSIRInput.getText().toString());
            referenceDistance = Double.parseDouble(referenceDistanceInput.getText().toString());
            referencePower = Double.parseDouble(referencePowerInput.getText().toString());
            lossExponent = Double.parseDouble(lossExponentInput.getText().toString());
            receiverSensitivity = Double.parseDouble(receiverSensitivityInput.getText().toString());
            coCells = Double.parseDouble(coCellsInput.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        // Validate input values
        if (timeSlotsPerCarrier <= 0 ||
                cityArea <= 0 ||
                numberOfSubscribers <= 0 ||
                numberOfCalls <= 0 ||
                callDuration <= 0 ||
                callDropProbability < 0 || callDropProbability > 1 ||
                minimumSIR <= 0 ||
                referenceDistance <= 0 ||
                lossExponent < 0 ||
                receiverSensitivity <= 0 ||
                coCells <= 0 ){
            outputTextView.setText("Please enter valid positive values for all inputs.");
            return;
        }

        // Get selected units
        String cityAreaUnit = cityAreaUnitSpinner.getSelectedItem().toString();
        String callDurationUnit = callDurationUnitSpinner.getSelectedItem().toString();
        String numberOfCallsUnit = numberOfCallsUnitSpinner.getSelectedItem().toString();
        String minimumSIRUnit = minimumSIRUnitSpinner.getSelectedItem().toString();
        String referenceDistanceUnit = referenceDistanceUnitSpinner.getSelectedItem().toString();
        String referencePowerUnit = referencePowerUnitSpinner.getSelectedItem().toString();
        String receiverSensitivityUnit = receiverSensitivityUnitSpinner.getSelectedItem().toString();
        String whatToCalculate = whatToCalculateSpinner.getSelectedItem().toString();

        // Convert units to common units
        switch (cityAreaUnit) {
            case "sq km":
                cityArea *= 1000000;
                break;
            case "sq mile":
                cityArea *= 2590000;
                break;
        }

        // Convert call duration to min
        switch (callDurationUnit) {
            case "sec":
                callDuration /= 60;
                break;
            case "hour":
                callDuration *= 60;
                break;
        }

        // Convert number of calls to per min
        switch (numberOfCallsUnit) {
            case "per sec":
                numberOfCalls *= 60;
                break;
            case "per hour":
                numberOfCalls /= 60;
                break;
            case "per day":
                numberOfCalls /= (60 * 24);
                break;
            case "per week":
                numberOfCalls /= (60 * 24 * 7);
                break;
            case "per month":
                numberOfCalls /= (60 * 24 * 30);
                break;
        }

        // Convert minimum SIR to watt
        switch (minimumSIRUnit) {
            case "dB":
                minimumSIR = Math.pow(10, minimumSIR / 10);
                break;
            case "dBm":
                minimumSIR = Math.pow(10, (minimumSIR + 30) / 10);
                break;
        }

        // Convert reference distance to meter
        switch (referenceDistanceUnit) {
            case "km":
                referenceDistance *= 1000;
                break;
            case "mile":
                referenceDistance *= 1609.34;
                break;
        }

        // Convert reference power to watt
        switch (referencePowerUnit) {
            case "dB":
                referencePower = Math.pow(10, referencePower / 10);
                break;
            case "dBm":
                referencePower = Math.pow(10, (referencePower + 30) / 10);
                break;
        }

        // Convert receiver sensitivity to watt
        switch (receiverSensitivityUnit) {
            case "dB":
                receiverSensitivity = Math.pow(10, receiverSensitivity / 10);
                break;
            case "dBm":
                receiverSensitivity = Math.pow(10, (receiverSensitivity + 30) / 10);
                break;
            case "mWatt":
                receiverSensitivity /= 1000;
                break;
            case "uWatt":
                receiverSensitivity /= 1000000;
                break;
        }

        // Calculate output based on selected option
        double maxDistance = referenceDistance * Math.pow((referencePower / receiverSensitivity), 1 / lossExponent);
        double maxCellSize = 3 * Math.sqrt(3) * Math.pow(maxDistance, 2) / 2;
        double numberOfCells = Math.ceil(cityArea / maxCellSize);
        double trafficLoad = (numberOfSubscribers * numberOfCalls * callDuration);
        double trafficLoadPerCell = trafficLoad / numberOfCells;
        int numberOfCellsInCluster = (int)Math.ceil(Math.pow((minimumSIR * coCells), 2 / lossExponent) / 3);
        boolean isValidNumberOfCellsInCluster = false;
        for (int i = 0; i < ValidNumberOfCells.length; i++) {
            if (numberOfCellsInCluster == ValidNumberOfCells[i]) {
                isValidNumberOfCellsInCluster = true;
                break;
            }
        }

        double minimumCarriers = findChannels(trafficLoadPerCell, callDropProbability);

        switch (whatToCalculate) {
            case "Maximum distance between base stations":
                outputTextView.setText("Maximum distance between base stations: " + maxDistance + " meters");
                break;
            case "Maximum cell size (hexagon)":
                outputTextView.setText("Maximum cell size (hexagon): " + maxCellSize + " sq meters");
                break;
            case "Number of cells in the area":
                outputTextView.setText("Number of cells in the area: " + numberOfCells);
                break;
            case "Traffic load for the system":
                outputTextView.setText("Traffic load for the system: " + trafficLoad + " Erlangs");
                break;
            case "Traffic load per cell":
                outputTextView.setText("Traffic load per cell: " + trafficLoadPerCell + " Erlangs");
                break;
            case "Number of cells in each cluster":
                if (isValidNumberOfCellsInCluster) {
                    outputTextView.setText("Number of cells in each cluster: " + numberOfCellsInCluster);
                } else {
                    outputTextView.setText("Invalid number of cells in each cluster. Please enter a valid number.");
                }
                break;
            case "Minimum number of carriers for QoS":
                outputTextView.setText("Minimum number of carriers for QoS: " + minimumCarriers);
                break;
        }
    }

    private double erlangB(double E, int C) {
        double numerator = Math.pow(E, C) / factorial(C);
        double denominator = 0;
        for (int k = 0; k <= C; k++) {
            denominator += Math.pow(E, k) / factorial(k);
        }
        return numerator / denominator;
    }

    private double factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    private int findChannels(double E, double B) {
        int C = 1;
        while (true) {
            double blockingProbability = erlangB(E, C);
            if (blockingProbability <= B) {
                return C;
            }
            C++;
        }
    }
}
