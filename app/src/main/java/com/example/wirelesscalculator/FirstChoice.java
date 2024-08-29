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

public class FirstChoice extends AppCompatActivity {
    String[] bandwidth = {"Hz", "kHz", "MHz", "GHz"};
    String[] what_to_calculate = {"Sampling Frequency", "Quantization Levels", "Source Encoder Input Rate", "Source Encoder Output Rate", "Channel Encoder Output Rate", "Interleaver Output Rate"};
    private Spinner bandwidth_spinner, what_to_calculate_spinner;
    private EditText bandwidthInput, bitDepthInput, sourceEncoderCompressionRateInput, channelEncoderRateInput, interleaverInput;
    private TextView outputTextView;
    private Button buttonOutput;

    double samplingFrequency, quantizationLevels, inputBitRateSourceEncoder, outputBitRateSourceEncoder, outputBitRateChannelEncoder, outputBitRateInterleaver;
    double bandwidthInputValue, bitDepthInputValue, sourceEncoderCompressionRateInputValue, channelEncoderRateInputValue, interleaverInputValue;
    String bandwidthInputUnit;
    String whatToCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_choice);
        bandwidth_spinner = findViewById(R.id.bandwidth_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, bandwidth);
        bandwidth_spinner.setAdapter(adapter);
        what_to_calculate_spinner = findViewById(R.id.what_to_calculate_spinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, what_to_calculate);
        what_to_calculate_spinner.setAdapter(adapter2);
        bandwidthInput = findViewById(R.id.bandwidth_input);
        bitDepthInput = findViewById(R.id.bit_depth_input);
        sourceEncoderCompressionRateInput = findViewById(R.id.source_encoder_compression_rate_input);
        channelEncoderRateInput = findViewById(R.id.channel_encoder_rate_input);
        interleaverInput = findViewById(R.id.interleaver_input);
        outputTextView = findViewById(R.id.output_textview);
        buttonOutput = findViewById(R.id.button_output);

        buttonOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
    }

    public void calculate() {
        if (isAnyFieldEmpty()) {
            Toast.makeText(this, "Please fill the required fields.", Toast.LENGTH_LONG).show();
            return;
        }

        bandwidthInputValue = Double.parseDouble(bandwidthInput.getText().toString());
        bitDepthInputValue = Double.parseDouble(bitDepthInput.getText().toString());
        sourceEncoderCompressionRateInputValue = Double.parseDouble(sourceEncoderCompressionRateInput.getText().toString());
        channelEncoderRateInputValue = Double.parseDouble(channelEncoderRateInput.getText().toString());
        interleaverInputValue = Double.parseDouble(interleaverInput.getText().toString());
        bandwidthInputUnit = bandwidth_spinner.getSelectedItem().toString();

        if (bandwidthInputUnit.equals("Hz")) {
            bandwidthInputValue = bandwidthInputValue;
        } else if (bandwidthInputUnit.equals("kHz")) {
            bandwidthInputValue = bandwidthInputValue * Math.pow(10, 3);
        } else if (bandwidthInputUnit.equals("MHz")) {
            bandwidthInputValue = bandwidthInputValue * Math.pow(10, 6);
        } else if (bandwidthInputUnit.equals("GHz")) {
            bandwidthInputValue = bandwidthInputValue * Math.pow(10, 9);
        }

        samplingFrequency = 2 * bandwidthInputValue;
        quantizationLevels = Math.pow(2, bitDepthInputValue);
        inputBitRateSourceEncoder = samplingFrequency * bitDepthInputValue;
        outputBitRateSourceEncoder = inputBitRateSourceEncoder * sourceEncoderCompressionRateInputValue;

        if (inputBitRateSourceEncoder < outputBitRateSourceEncoder) {
            showError(sourceEncoderCompressionRateInput, "Input Bit Rate of Source Encoder should be greater than Output Bit Rate of Source Encoder");
        } else {
            outputBitRateChannelEncoder = outputBitRateSourceEncoder / channelEncoderRateInputValue;
            outputBitRateInterleaver = outputBitRateChannelEncoder;
            clearError(sourceEncoderCompressionRateInput);
        }

whatToCalculate = what_to_calculate_spinner.getSelectedItem().toString();
        if (whatToCalculate.equals("Sampling Frequency")) {
            outputTextView.setText("Sampling Frequency: " + samplingFrequency + " Hz");
        } else if (whatToCalculate.equals("Quantization Levels")) {
            outputTextView.setText("Quantization Levels: " + quantizationLevels);
        } else if (whatToCalculate.equals("Source Encoder Input Rate")) {
            outputTextView.setText("Source Encoder Input Rate: " + inputBitRateSourceEncoder + " bps");
        } else if (whatToCalculate.equals("Source Encoder Output Rate")) {
            outputTextView.setText("Source Encoder Output Rate: " + outputBitRateSourceEncoder + " bps");
        } else if (whatToCalculate.equals("Channel Encoder Output Rate")) {
            outputTextView.setText("Channel Encoder Output Rate: " + outputBitRateChannelEncoder + " bps");
        } else if (whatToCalculate.equals("Interleaver Output Rate")) {
            outputTextView.setText("Interleaver Output Rate: " + outputBitRateInterleaver + " bps");
        }

    }

    private boolean isAnyFieldEmpty() {
        boolean isEmpty = false;

        if (bandwidthInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(bandwidthInput);
        }

        if (bitDepthInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(bitDepthInput);
        }

        if (sourceEncoderCompressionRateInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(sourceEncoderCompressionRateInput);
        }

        if (channelEncoderRateInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(channelEncoderRateInput);
        }

        if (interleaverInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(interleaverInput);
        }

        return isEmpty;
    }

    private void showError(EditText inputField, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        inputField.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
    }

    private void clearError(EditText inputField) {
        inputField.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

}
