package com.example.wirelesscalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdChoice extends AppCompatActivity {

    String[] powerUnit = {"dB", "dBm", "Watt"};
    String[] frequencyUnit = {"Hz", "KHz", "MHz", "GHz"};
    String[] dataRateUnit = {"bps", "Kbps", "Mbps", "Gbps"};
    String[] temperatureUnit = {"K", "C", "F"};
    String[] modulationType = {"BPSK/QPSK", "8-PSK", "16-PSK"};

    double pathLoss, frequency, transmitAntennaGain, receiveAntennaGain, dataRate, antennaFeedLineLoss, anotherLosses, fadeMargin;
    double transmitGainAmplifier, receiverGainAmplifier, noiseFigure, noiseTemperature, linkMargin;
    String outputUnit;

    private EditText pathLossInput, frequencyInput, transmitAntennaGainInput, receiveAntennaGainInput;
    private EditText dataRateInput, antennaFeedLineLossInput, anotherLossesInput, fadeMarginInput;
    private EditText transmitGainAmplifierInput, receiverGainAmplifierInput, noiseFigureInput, noiseTemperatureInput, linkMarginInput;
    private Spinner pathLossSpinner, frequencySpinner, transmitAntennaGainSpinner, receiveAntennaGainSpinner;
    private Spinner dataRateSpinner, antennaFeedLineLossSpinner, anotherLossesSpinner, fadeMarginSpinner;
    private Spinner transmitGainAmplifierSpinner, receiverGainAmplifierSpinner, noiseFigureSpinner, noiseTemperatureSpinner, linkMarginSpinner;
    private Spinner modulationTypeSpinner, bitErrorRateSpinner, outputUnitSpinner;
    private TextView outputTextView;
    private Button buttonOutput;

    // Map to store modulation schemes data
    private Map<String, List<BERData>> modulationSchemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_choice);

        // Initialize map
        modulationSchemes = new HashMap<>();
        initializeModulationSchemes();

        // Initialize all UI components
        initializeUI();

        // Set up listeners
        setupListeners();
    }

    private void initializeUI() {
        pathLossInput = findViewById(R.id.path_loss_input);
        frequencyInput = findViewById(R.id.frequency_input);
        transmitAntennaGainInput = findViewById(R.id.transmit_antenna_gain_input);
        receiveAntennaGainInput = findViewById(R.id.receive_antenna_gain_input);
        dataRateInput = findViewById(R.id.data_rate_input);
        antennaFeedLineLossInput = findViewById(R.id.antenna_feed_line_loss_input);
        anotherLossesInput = findViewById(R.id.another_losses_input);
        fadeMarginInput = findViewById(R.id.fade_margin_input);
        receiverGainAmplifierInput = findViewById(R.id.receiver_gain_amplifier_input);
        transmitGainAmplifierInput = findViewById(R.id.transmit_gain_amplifier_input);
        noiseFigureInput = findViewById(R.id.noise_figure_input);
        noiseTemperatureInput = findViewById(R.id.noise_temperature_input);
        linkMarginInput = findViewById(R.id.link_margin_input);

        pathLossSpinner = findViewById(R.id.path_loss_spinner);
        frequencySpinner = findViewById(R.id.frequency_spinner);
        transmitAntennaGainSpinner = findViewById(R.id.transmit_antenna_gain_spinner);
        receiveAntennaGainSpinner = findViewById(R.id.receive_antenna_gain_spinner);
        dataRateSpinner = findViewById(R.id.data_rate_spinner);
        antennaFeedLineLossSpinner = findViewById(R.id.antenna_feed_line_loss_spinner);
        anotherLossesSpinner = findViewById(R.id.another_losses_spinner);
        fadeMarginSpinner = findViewById(R.id.fade_margin_spinner);
        receiverGainAmplifierSpinner = findViewById(R.id.receiver_gain_amplifier_spinner);
        transmitGainAmplifierSpinner = findViewById(R.id.transmit_gain_amplifier_spinner);
        noiseFigureSpinner = findViewById(R.id.noise_figure_spinner);
        noiseTemperatureSpinner = findViewById(R.id.noise_temperature_spinner);
        linkMarginSpinner = findViewById(R.id.link_margin_spinner);

        modulationTypeSpinner = findViewById(R.id.modulation_type_spinner);
        bitErrorRateSpinner = findViewById(R.id.bit_error_rate_spinner);
        outputUnitSpinner = findViewById(R.id.output_unit_spinner);

        outputTextView = findViewById(R.id.output_textview);
        buttonOutput = findViewById(R.id.button_output);

        // Initialize spinners with adapters
        ArrayAdapter<String> pathLossAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        pathLossSpinner.setAdapter(pathLossAdapter);

        ArrayAdapter<String> frequencyAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, frequencyUnit);
        frequencySpinner.setAdapter(frequencyAdapter);

        ArrayAdapter<String> transmitAntennaGainAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        transmitAntennaGainSpinner.setAdapter(transmitAntennaGainAdapter);

        ArrayAdapter<String> receiveAntennaGainAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        receiveAntennaGainSpinner.setAdapter(receiveAntennaGainAdapter);

        ArrayAdapter<String> dataRateAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, dataRateUnit);
        dataRateSpinner.setAdapter(dataRateAdapter);

        ArrayAdapter<String> antennaFeedLineLossAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        antennaFeedLineLossSpinner.setAdapter(antennaFeedLineLossAdapter);

        ArrayAdapter<String> anotherLossesAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        anotherLossesSpinner.setAdapter(anotherLossesAdapter);

        ArrayAdapter<String> fadeMarginAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        fadeMarginSpinner.setAdapter(fadeMarginAdapter);

        ArrayAdapter<String> receiverGainAmplifierAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        receiverGainAmplifierSpinner.setAdapter(receiverGainAmplifierAdapter);

        ArrayAdapter<String> noiseFigureAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        noiseFigureSpinner.setAdapter(noiseFigureAdapter);

        ArrayAdapter<String> noiseTemperatureAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, temperatureUnit);
        noiseTemperatureSpinner.setAdapter(noiseTemperatureAdapter);

        ArrayAdapter<String> linkMarginAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        linkMarginSpinner.setAdapter(linkMarginAdapter);

        ArrayAdapter<String> modulationAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, modulationType);
        modulationTypeSpinner.setAdapter(modulationAdapter);

        ArrayAdapter<String> outputUnitAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        outputUnitSpinner.setAdapter(outputUnitAdapter);

        ArrayAdapter<String> transmitGainAmplifierAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, powerUnit);
        transmitGainAmplifierSpinner.setAdapter(transmitGainAmplifierAdapter);
    }

    private void setupListeners() {
        modulationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedModulation = modulationType[position];
                List<BERData> berDataList = modulationSchemes.get(selectedModulation);

                if (berDataList != null) {
                    List<String> berStrings = new ArrayList<>();
                    for (BERData berData : berDataList) {
                        berStrings.add("BER:10^-" + berData.getBerExponent());
                    }
                    ArrayAdapter<String> berAdapter = new ArrayAdapter<>(ThirdChoice.this, R.layout.spinner_item, berStrings);
                    bitErrorRateSpinner.setAdapter(berAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        buttonOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if any field is empty before proceeding to calculate
                if (isAnyFieldEmpty()) {
                    Toast.makeText(ThirdChoice.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        calculateOutput();
                    } catch (Exception e) {
                        Toast.makeText(ThirdChoice.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void calculateOutput() {
        try {
            // Retrieve values from EditText fields
            pathLoss = Double.parseDouble(pathLossInput.getText().toString());
            frequency = Double.parseDouble(frequencyInput.getText().toString());
            transmitAntennaGain = Double.parseDouble(transmitAntennaGainInput.getText().toString());
            receiveAntennaGain = Double.parseDouble(receiveAntennaGainInput.getText().toString());
            dataRate = Double.parseDouble(dataRateInput.getText().toString());
            antennaFeedLineLoss = Double.parseDouble(antennaFeedLineLossInput.getText().toString());
            anotherLosses = Double.parseDouble(anotherLossesInput.getText().toString());
            fadeMargin = Double.parseDouble(fadeMarginInput.getText().toString());
            receiverGainAmplifier = Double.parseDouble(receiverGainAmplifierInput.getText().toString());
            transmitGainAmplifier = Double.parseDouble(transmitGainAmplifierInput.getText().toString());
            noiseFigure = Double.parseDouble(noiseFigureInput.getText().toString());
            noiseTemperature = Double.parseDouble(noiseTemperatureInput.getText().toString());
            linkMargin = Double.parseDouble(linkMarginInput.getText().toString());

            // Retrieve units from Spinners
            String pathLossUnit = pathLossSpinner.getSelectedItem().toString();
            String frequencyUnit = frequencySpinner.getSelectedItem().toString();
            String transmitAntennaGainUnit = transmitAntennaGainSpinner.getSelectedItem().toString();
            String receiveAntennaGainUnit = receiveAntennaGainSpinner.getSelectedItem().toString();
            String dataRateUnit = dataRateSpinner.getSelectedItem().toString();
            String antennaFeedLineLossUnit = antennaFeedLineLossSpinner.getSelectedItem().toString();
            String anotherLossesUnit = anotherLossesSpinner.getSelectedItem().toString();
            String fadeMarginUnit = fadeMarginSpinner.getSelectedItem().toString();
            String receiverGainAmplifierUnit = receiverGainAmplifierSpinner.getSelectedItem().toString();
            String transmitGainAmplifierUnit = transmitGainAmplifierSpinner.getSelectedItem().toString();
            String noiseFigureUnit = noiseFigureSpinner.getSelectedItem().toString();
            String noiseTemperatureUnit = noiseTemperatureSpinner.getSelectedItem().toString();
            String linkMarginUnit = linkMarginSpinner.getSelectedItem().toString();
            String modulationType = modulationTypeSpinner.getSelectedItem().toString();
            String bitErrorRate = bitErrorRateSpinner.getSelectedItem().toString();
            outputUnit = outputUnitSpinner.getSelectedItem().toString();

            // Convert values to standard units
            pathLoss = convertToDb(pathLoss, pathLossUnit);
            frequency = convertFrequency(frequency, frequencyUnit);
            transmitAntennaGain = convertToDb(transmitAntennaGain, transmitAntennaGainUnit);
            receiveAntennaGain = convertToDb(receiveAntennaGain, receiveAntennaGainUnit);
            dataRate = convertDataRate(dataRate, dataRateUnit);
            antennaFeedLineLoss = convertToDb(antennaFeedLineLoss, antennaFeedLineLossUnit);
            anotherLosses = convertToDb(anotherLosses, anotherLossesUnit);
            fadeMargin = convertToDb(fadeMargin, fadeMarginUnit);
            receiverGainAmplifier = convertToDb(receiverGainAmplifier, receiverGainAmplifierUnit);
            transmitGainAmplifier = convertToDb(transmitGainAmplifier, transmitGainAmplifierUnit);
            noiseFigure = convertToDb(noiseFigure, noiseFigureUnit);
            noiseTemperature = convertTemperature(noiseTemperature, noiseTemperatureUnit);
            linkMargin = convertToDb(linkMargin, linkMarginUnit);

            double EbNo = extractEbNoValue(modulationType, bitErrorRate);

            // Compute power received
            double K = 1.38 * Math.pow(10, -23);
            double Kdb = 10 * Math.log10(K);
            double temperatureDb = 10 * Math.log10(noiseTemperature);
            double dataRateDb = 10 * Math.log10(dataRate);
            double powerReceived = linkMargin + Kdb + temperatureDb + noiseFigure + dataRateDb + EbNo;
            double powerTransmitted = powerReceived + pathLoss + antennaFeedLineLoss + anotherLosses + fadeMargin - transmitAntennaGain - receiveAntennaGain - receiverGainAmplifier - transmitGainAmplifier;

            runOnUiThread(() -> {
                // Update UI with the result
                if (outputUnit.equals("dB")) {
                    outputTextView.setText(String.valueOf(powerTransmitted));
                } else if (outputUnit.equals("dBm")) {
                    outputTextView.setText(String.valueOf(powerTransmitted + 30));
                } else if (outputUnit.equals("Watt")) {
                    outputTextView.setText(String.valueOf(Math.pow(10, powerTransmitted / 10)));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                Toast.makeText(ThirdChoice.this, "Error in calculation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private double extractEbNoValue(String modulationType, String bitErrorRate) {
        List<BERData> berDataList = modulationSchemes.get(modulationType);
        for (BERData berData : berDataList) {
            if (("BER:10^-" + berData.getBerExponent()).equals(bitErrorRate)) {
                return berData.getEbNo();
            }
        }
        throw new IllegalArgumentException("Invalid BER value selected");
    }



    private void initializeModulationSchemes() {
        modulationSchemes.put("BPSK/QPSK", createBERDataList(
                new BERData(10.5, 6),
                new BERData(8.3, 4),
                new BERData(4.0, 2)
        ));
        modulationSchemes.put("8-PSK", createBERDataList(
                new BERData(14.0, 6),
                new BERData(12.0, 4),
                new BERData(7.0, 2)
        ));
        modulationSchemes.put("16-PSK", createBERDataList(
                new BERData(18.0, 6),
                new BERData(16.0, 4),
                new BERData(11.0, 2)
        ));
    }

    private List<BERData> createBERDataList(BERData... berValues) {
        return Arrays.asList(berValues);
    }

//
//    private void calculateOutput() {
//        try {
//            // Retrieve values from EditText fields
//            pathLoss = Double.parseDouble(pathLossInput.getText().toString());
//            frequency = Double.parseDouble(frequencyInput.getText().toString());
//            transmitAntennaGain = Double.parseDouble(transmitAntennaGainInput.getText().toString());
//            receiveAntennaGain = Double.parseDouble(receiveAntennaGainInput.getText().toString());
//            dataRate = Double.parseDouble(dataRateInput.getText().toString());
//            antennaFeedLineLoss = Double.parseDouble(antennaFeedLineLossInput.getText().toString());
//            anotherLosses = Double.parseDouble(anotherLossesInput.getText().toString());
//            fadeMargin = Double.parseDouble(fadeMarginInput.getText().toString());
//            receiverGainAmplifier = Double.parseDouble(receiverGainAmplifierInput.getText().toString());
//            transmitGainAmplifier = Double.parseDouble(transmitGainAmplifierInput.getText().toString());
//            noiseFigure = Double.parseDouble(noiseFigureInput.getText().toString());
//            noiseTemperature = Double.parseDouble(noiseTemperatureInput.getText().toString());
//            linkMargin = Double.parseDouble(linkMarginInput.getText().toString());
//
//            // Retrieve units from Spinners
//            String pathLossUnit = pathLossSpinner.getSelectedItem().toString();
//            String frequencyUnit = frequencySpinner.getSelectedItem().toString();
//            String transmitAntennaGainUnit = transmitAntennaGainSpinner.getSelectedItem().toString();
//            String receiveAntennaGainUnit = receiveAntennaGainSpinner.getSelectedItem().toString();
//            String dataRateUnit = dataRateSpinner.getSelectedItem().toString();
//            String antennaFeedLineLossUnit = antennaFeedLineLossSpinner.getSelectedItem().toString();
//            String anotherLossesUnit = anotherLossesSpinner.getSelectedItem().toString();
//            String fadeMarginUnit = fadeMarginSpinner.getSelectedItem().toString();
//            String receiverGainAmplifierUnit = receiverGainAmplifierSpinner.getSelectedItem().toString();
//            String transmitGainAmplifierUnit = transmitGainAmplifierSpinner.getSelectedItem().toString();
//            String noiseFigureUnit = noiseFigureSpinner.getSelectedItem().toString();
//            String noiseTemperatureUnit = noiseTemperatureSpinner.getSelectedItem().toString();
//            String linkMarginUnit = linkMarginSpinner.getSelectedItem().toString();
//            String modulationType = modulationTypeSpinner.getSelectedItem().toString();
//            String bitErrorRate = bitErrorRateSpinner.getSelectedItem().toString();
//            outputUnit = outputUnitSpinner.getSelectedItem().toString();
//
//            // Convert values to standard units
//            pathLoss = convertToDb(pathLoss, pathLossUnit);
//            frequency = convertFrequency(frequency, frequencyUnit);
//            transmitAntennaGain = convertToDb(transmitAntennaGain, transmitAntennaGainUnit);
//            receiveAntennaGain = convertToDb(receiveAntennaGain, receiveAntennaGainUnit);
//            dataRate = convertDataRate(dataRate, dataRateUnit);
//            antennaFeedLineLoss = convertToDb(antennaFeedLineLoss, antennaFeedLineLossUnit);
//            anotherLosses = convertToDb(anotherLosses, anotherLossesUnit);
//            fadeMargin = convertToDb(fadeMargin, fadeMarginUnit);
//            receiverGainAmplifier = convertToDb(receiverGainAmplifier, receiverGainAmplifierUnit);
//            transmitGainAmplifier = convertToDb(transmitGainAmplifier, transmitGainAmplifierUnit);
//            noiseFigure = convertToDb(noiseFigure, noiseFigureUnit);
//            noiseTemperature = convertTemperature(noiseTemperature, noiseTemperatureUnit);
//            linkMargin = convertToDb(linkMargin, linkMarginUnit);
//
//            double EbNo = extractEbNoValue(bitErrorRate);
//
//
//            // Compute power received
//            double K = 1.38 * Math.pow(10, -23);
//            double Kdb = 10 * Math.log10(K);
//            double temperatureDb = 10 * Math.log10(noiseTemperature);
//            double dataRateDb = 10 * Math.log10(dataRate);
//            double powerReceived = linkMargin + Kdb + temperatureDb + noiseFigure + dataRateDb + EbNo;
//            Toast.makeText(ThirdChoice.this, "linkMargin: " + linkMargin , Toast.LENGTH_SHORT).show();
//            Toast.makeText(ThirdChoice.this, "Kdb: " + Kdb , Toast.LENGTH_SHORT).show();
//            Toast.makeText(ThirdChoice.this, "temperatureDb: " + temperatureDb , Toast.LENGTH_SHORT).show();
//            Toast.makeText(ThirdChoice.this, "noiseFigure: " + noiseFigure , Toast.LENGTH_SHORT).show();
//            Toast.makeText(ThirdChoice.this, "dataRateDb: " + dataRateDb , Toast.LENGTH_SHORT).show();
//            Toast.makeText(ThirdChoice.this, "EbNo: " + EbNo , Toast.LENGTH_SHORT).show();
//            Toast.makeText(ThirdChoice.this, "The power received is: " + powerReceived, Toast.LENGTH_SHORT).show();
//            double powerTransmitted = powerReceived + pathLoss + antennaFeedLineLoss + anotherLosses + fadeMargin - transmitAntennaGain - receiveAntennaGain - receiverGainAmplifier - transmitGainAmplifier;
//
//            runOnUiThread(() -> {
//                // Update UI with the result
//                Toast.makeText(ThirdChoice.this, "The power transmitted is: " + powerTransmitted, Toast.LENGTH_SHORT).show();
//                if (outputUnit.equals("dB")) {
//                    outputTextView.setText(String.valueOf(powerTransmitted));
//                } else if (outputUnit.equals("dBm")) {
//                    outputTextView.setText(String.valueOf(powerTransmitted + 30));
//                } else if (outputUnit.equals("Watt")) {
//                    outputTextView.setText(String.valueOf(Math.pow(10, powerTransmitted / 10)));
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            runOnUiThread(() -> {
//                Toast.makeText(ThirdChoice.this, "Error in calculation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            });
//        }
//    }

    private double extractEbNoValue(String bitErrorRate) {
        String[] parts = bitErrorRate.split(":");
        return Double.parseDouble(parts[0].split(" ")[1]);

    }

    private double convertToDb(double value, String unit) {
        if (unit.equals("dB")) {
            return value;
        } else if (unit.equals("dBm")) {
            return value - 30;
        } else if (unit.equals("Watt")) {
            return 10 * Math.log10(value);
        }
        return value;
    }

    private double convertFrequency(double value, String unit) {
        if (unit.equals("Hz")) {
            return value;
        } else if (unit.equals("KHz")) {
            return value * Math.pow(10, 3);
        } else if (unit.equals("MHz")) {
            return value * Math.pow(10, 6);
        } else if (unit.equals("GHz")) {
            return value * Math.pow(10, 9);
        }
        return value;
    }

    private double convertDataRate(double value, String unit) {
        if (unit.equals("bps")) {
            return value;
        } else if (unit.equals("Kbps")) {
            return value * Math.pow(10, 3);
        } else if (unit.equals("Mbps")) {
            return value * Math.pow(10, 6);
        } else if (unit.equals("Gbps")) {
            return value * Math.pow(10, 9);
        }
        return value;
    }

    private double convertTemperature(double value, String unit) {
        if (unit.equals("K")) {
            return value;
        } else if (unit.equals("C")) {
            return value + 273.15;
        } else if (unit.equals("F")) {
            return (value - 32) * 5 / 9 + 273.15;
        }
        return value;
    }


    private boolean isAnyFieldEmpty() {
        // Check if any required field is empty
        boolean isEmpty = false;

        if (pathLossInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (frequencyInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (transmitAntennaGainInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (receiveAntennaGainInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (dataRateInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (antennaFeedLineLossInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (anotherLossesInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (fadeMarginInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (receiverGainAmplifierInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (transmitGainAmplifierInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (noiseFigureInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (noiseTemperatureInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        if (linkMarginInput.getText().toString().isEmpty()) {
            isEmpty = true;
        }

        return isEmpty;
    }
        public class BERData {
            private double ebNo;
            private int berExponent;

            public BERData(double ebNo, int berExponent) {
                this.ebNo = ebNo;
                this.berExponent = berExponent;
            }

            @Override
            public String toString() {
                return String.format("BER:10^-%d -> Eb/No: %.1f dB", berExponent, ebNo);
            }

            public double getEbNo() {
                return ebNo;
            }

            public int getBerExponent() {
                return berExponent;
            }
        }




    }
