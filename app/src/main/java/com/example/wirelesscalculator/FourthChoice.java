package com.example.wirelesscalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FourthChoice extends AppCompatActivity {

    String csmaTypes[] = {"Unslotted Nonpersistent CSMA", "Slotted Nonpersistent CSMA", "Slotted 1-persistent CSMA", "Unslotted 1-persistent CSMA"};
    String dataRates[] = {"bps", "Kbps", "Mbps", "Gbps"};
    String propagationDelays[] = {"sec", "msec", "µsec", "nsec"};
    String frameSizes[] = {"bits", "Kbits", "Mbits", "Gbits"};
    String frameRates[] = {"fps", "Kfps", "Mfps", "Gfps"};

    private Spinner csmaTypesSpinner, dataRateSpinner, propagationDelaySpinner, frameSizeSpinner, frameRateSpinner;
    private EditText dataRateInput, propagationDelayInput, frameSizeInput, frameRateInput;
    private TextView outputTextView;
    private Button buttonOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_choice);

        // Initialize the spinners
        csmaTypesSpinner = findViewById(R.id.csma_types_spinner);
        dataRateSpinner = findViewById(R.id.data_rate_spinner);
        propagationDelaySpinner = findViewById(R.id.propagation_delay_spinner);
        frameSizeSpinner = findViewById(R.id.frame_size_spinner);
        frameRateSpinner = findViewById(R.id.frame_rate_spinner);

        // Initialize the EditTexts
        dataRateInput = findViewById(R.id.data_rate_input);
        propagationDelayInput = findViewById(R.id.propagation_delay_input);
        frameSizeInput = findViewById(R.id.frame_size_input);
        frameRateInput = findViewById(R.id.frame_rate_input);

        // Initialize the TextView
        outputTextView = findViewById(R.id.output_textview);

        // Initialize the Button
        buttonOutput = findViewById(R.id.button_output);

        // Set the spinners' adapters
        ArrayAdapter<String> csmaTypesAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, csmaTypes);
        csmaTypesSpinner.setAdapter(csmaTypesAdapter);

        ArrayAdapter<String> dataRatesAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, dataRates);
        dataRateSpinner.setAdapter(dataRatesAdapter);

        ArrayAdapter<String> propagationDelaysAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, propagationDelays);
        propagationDelaySpinner.setAdapter(propagationDelaysAdapter);

        ArrayAdapter<String> frameSizesAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, frameSizes);
        frameSizeSpinner.setAdapter(frameSizesAdapter);

        ArrayAdapter<String> frameRatesAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, frameRates);
        frameRateSpinner.setAdapter(frameRatesAdapter);

        buttonOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateThroughput();
            }
        });
    }

    private void calculateThroughput() {
        if (isAnyFieldEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String csmaType = csmaTypesSpinner.getSelectedItem().toString();
            double dataRate = Double.parseDouble(dataRateInput.getText().toString());
            double propagationDelay = Double.parseDouble(propagationDelayInput.getText().toString());
            double frameSize = Double.parseDouble(frameSizeInput.getText().toString());
            double frameRate = Double.parseDouble(frameRateInput.getText().toString());

            //convert data rate to bps
            switch (dataRateSpinner.getSelectedItem().toString()) {
                case "Kbps":
                    dataRate *= 1000;
                    break;
                case "Mbps":
                    dataRate *= 1000000;
                    break;
                case "Gbps":
                    dataRate *= 1000000000;
                    break;
            }

            //convert propagation delay to sec
            switch (propagationDelaySpinner.getSelectedItem().toString()) {
                case "msec":
                    propagationDelay /= 1000;
                    break;
                case "µsec":
                    propagationDelay /= 1000000;
                    break;
                case "nsec":
                    propagationDelay /= 1000000000;
                    break;
            }

            //convert frame size to bits
            switch (frameSizeSpinner.getSelectedItem().toString()) {
                case "Kbits":
                    frameSize *= 1000;
                    break;
                case "Mbits":
                    frameSize *= 1000000;
                    break;
                case "Gbits":
                    frameSize *= 1000000000;
                    break;
            }

            //convert frame rate to fps
            switch (frameRateSpinner.getSelectedItem().toString()) {
                case "Kfps":
                    frameRate *= 1000;
                    break;
                case "Mfps":
                    frameRate *= 1000000;
                    break;
                case "Gfps":
                    frameRate *= 1000000000;
                    break;
            }
            double numerator, denominator;
            double T = frameSize / dataRate;
            double alpha = propagationDelay / T;
            double G = frameRate * T;
            double S = 0;

            switch (csmaType) {
                case "Unslotted Nonpersistent CSMA":
                    numerator = G * Math.exp(-2 * alpha * T);
                    denominator = G * (1 + (2 * alpha)) + Math.exp(-2 * alpha * G);
                    S = numerator / denominator;
                    break;
                case "Slotted Nonpersistent CSMA":
                    numerator = alpha * G * Math.exp(-2 * alpha * T);
                    denominator = (G * (1 - (Math.exp(-2 * alpha * G)) + alpha));
                    S = numerator / denominator;
                    break;
                case "Unslotted 1-persistent CSMA":
                    numerator = G * (1 + G + alpha * G * (1 + G +(alpha * G / 2))) * Math.exp(-G * (1 + 2 * alpha));
                    denominator = G * (1 + 2 * alpha) - (1 - Math.exp(-2 * alpha * G)) + (1 + alpha * G) * Math.exp(-G * (1 + alpha));
                    S = numerator / denominator;
                    break;
                case "Slotted 1-persistent CSMA":
                    numerator = G * (1 + alpha - Math.exp(-alpha * G)) * Math.exp(-G * (1 + alpha));
                    denominator = (1 + alpha) * (1 - Math.exp(-alpha * G)) + alpha * Math.exp(-G * (1 + alpha));
                    S = numerator / denominator;
                    break;
            }

            outputTextView.setText(String.format("Throughput: %.4f", S));
        } catch (NumberFormatException e) {
            outputTextView.setText("Invalid input. Please enter valid numbers.");
        }
    }
    private boolean isAnyFieldEmpty() {
        boolean isEmpty = false;

        if (dataRateInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(dataRateInput);
        }

        if (propagationDelayInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(propagationDelayInput);
        }

        if (frameSizeInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(frameSizeInput);
        }

        if (frameRateInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(frameRateInput);
        }

        return isEmpty;

    }

    private void clearError(EditText editText) {
        editText.setError(null);
    }

}
