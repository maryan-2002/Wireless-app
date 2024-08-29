package com.example.wirelesscalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SecondChoice extends AppCompatActivity {
    String[] bandwidth = {"Hz", "kHz", "MHz", "GHz"};
    String[] what_to_calculate = {"Number of bits per Resource Element", "Number of bits per OFDM Symbol", "Number of bits per OFDM Resource Block", "Maximum Rate"};
    String[] qam_type = {"16-QAM", "64-QAM", "256-QAM", "1024-QAM"};
    String[] block_duration = {"s", "ms", "us", "ns", "min", "hr"};
    private Spinner bandwidth_spinner, subcarrier_spacing_spinner, block_duration_spinner, QAM_type_spinner, what_to_calculate_spinner;
    private EditText bandwidthInput, subCarrierSpacingInput, ofdmSymbolsInput, numParallelBlocksInput, blockDurationInput;
    private TextView outputTextView;
    private Button buttonOutput;


    double bandwidthInputValue, subCarrierSpacingInputValue, ofdmSymbolsInputValue, numParallelBlocksInputValue, blockDurationInputValue, qamInputValue;
    double numBitsPerResourceElement, numBitsPerOFDMSymbol, numBitsPerOFDMResourceBlock, maximumRate;
    String bandwidthInputUnit, subCarrierSpacingInputUnit, blockDurationInputUnit, qamInputType;
    String whatToCalculate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_choice);

        bandwidthInput = findViewById(R.id.bandwidth_input);
        bandwidth_spinner = findViewById(R.id.bandwidth_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, bandwidth);
        bandwidth_spinner.setAdapter(adapter);
        subCarrierSpacingInput = findViewById(R.id.subcarrier_spacing_input);
        subcarrier_spacing_spinner = findViewById(R.id.subcarrier_bandwidth_spinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, bandwidth);
        subcarrier_spacing_spinner.setAdapter(adapter2);
        ofdmSymbolsInput = findViewById(R.id.ofdm_symbols_input);
        numParallelBlocksInput = findViewById(R.id.num_parallel_blocks_input);
        blockDurationInput = findViewById(R.id.block_duration_input);
        block_duration_spinner = findViewById(R.id.block_duration_spinner);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item, block_duration);
        block_duration_spinner.setAdapter(adapter3);
        QAM_type_spinner = findViewById(R.id.QAM_type_spinner);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, R.layout.spinner_item, qam_type);
        QAM_type_spinner.setAdapter(adapter4);
        what_to_calculate_spinner = findViewById(R.id.what_to_calculate_spinner);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, R.layout.spinner_item, what_to_calculate);
        what_to_calculate_spinner.setAdapter(adapter5);

        outputTextView = findViewById(R.id.output_textview);
        buttonOutput = findViewById(R.id.button_output);


        buttonOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateOutput();
            }
        });
    }

    private void calculateOutput() {
if (isAnyFieldEmpty()) {
            Toast.makeText(this, "Please fill the required fields.", Toast.LENGTH_LONG).show();
            return;
        }

        bandwidthInputValue = Double.parseDouble(bandwidthInput.getText().toString());
        subCarrierSpacingInputValue = Double.parseDouble(subCarrierSpacingInput.getText().toString());
        ofdmSymbolsInputValue = Double.parseDouble(ofdmSymbolsInput.getText().toString());
        numParallelBlocksInputValue = Double.parseDouble(numParallelBlocksInput.getText().toString());
        blockDurationInputValue = Double.parseDouble(blockDurationInput.getText().toString());
        bandwidthInputUnit = bandwidth_spinner.getSelectedItem().toString();
        subCarrierSpacingInputUnit = subcarrier_spacing_spinner.getSelectedItem().toString();
        blockDurationInputUnit = block_duration_spinner.getSelectedItem().toString();
        qamInputType = QAM_type_spinner.getSelectedItem().toString();
        whatToCalculate = what_to_calculate_spinner.getSelectedItem().toString();

        if (bandwidthInputUnit.equals("Hz")) {
            bandwidthInputValue = bandwidthInputValue;
        } else if (bandwidthInputUnit.equals("kHz")) {
            bandwidthInputValue = bandwidthInputValue * Math.pow(10, 3);
        } else if (bandwidthInputUnit.equals("MHz")) {
            bandwidthInputValue = bandwidthInputValue * Math.pow(10, 6);
        } else if (bandwidthInputUnit.equals("GHz")) {
            bandwidthInputValue = bandwidthInputValue * Math.pow(10, 9);
        }

        if (subCarrierSpacingInputUnit.equals("Hz")) {
            subCarrierSpacingInputValue = subCarrierSpacingInputValue;
        } else if (subCarrierSpacingInputUnit.equals("kHz")) {
            subCarrierSpacingInputValue = subCarrierSpacingInputValue * Math.pow(10, 3);
        } else if (subCarrierSpacingInputUnit.equals("MHz")) {
            subCarrierSpacingInputValue = subCarrierSpacingInputValue * Math.pow(10, 6);
        } else if (subCarrierSpacingInputUnit.equals("GHz")) {
            subCarrierSpacingInputValue = subCarrierSpacingInputValue * Math.pow(10, 9);
        }


        if (blockDurationInputUnit.equals("s")) {
            blockDurationInputValue = blockDurationInputValue;
        } else if (blockDurationInputUnit.equals("ms")) {
            blockDurationInputValue = blockDurationInputValue * Math.pow(10, -3);
        } else if (blockDurationInputUnit.equals("us")) {
            blockDurationInputValue = blockDurationInputValue * Math.pow(10, -6);
        } else if (blockDurationInputUnit.equals("ns")) {
            blockDurationInputValue = blockDurationInputValue * Math.pow(10, -9);
        } else if (blockDurationInputUnit.equals("min")) {
            blockDurationInputValue = blockDurationInputValue * 60;
        } else if (blockDurationInputUnit.equals("hr")) {
            blockDurationInputValue = blockDurationInputValue * 3600;
        }

        if (qamInputType.equals("16-QAM")) {
            qamInputValue = 16;
        } else if (qamInputType.equals("64-QAM")) {
            qamInputValue = 64;
        } else if (qamInputType.equals("256-QAM")) {
            qamInputValue = 256;
        } else if (qamInputType.equals("1024-QAM")) {
            qamInputValue = 1024;
        }

        numBitsPerResourceElement =  Math.log(qamInputValue) / Math.log(2);
        numBitsPerOFDMSymbol = Math.ceil(numBitsPerResourceElement * (bandwidthInputValue/subCarrierSpacingInputValue));
        numBitsPerOFDMResourceBlock = numBitsPerOFDMSymbol * ofdmSymbolsInputValue;
        maximumRate = numBitsPerOFDMResourceBlock * numParallelBlocksInputValue / blockDurationInputValue;

        if (whatToCalculate.equals("Number of bits per Resource Element")) {
            outputTextView.setText("Number of bits per Resource Element: " + numBitsPerResourceElement);
        } else if (whatToCalculate.equals("Number of bits per OFDM Symbol")) {
            outputTextView.setText("Number of bits per OFDM Symbol: " + numBitsPerOFDMSymbol);
        } else if (whatToCalculate.equals("Number of bits per OFDM Resource Block")) {
            outputTextView.setText("Number of bits per OFDM Resource Block: " + numBitsPerOFDMResourceBlock);
        } else if (whatToCalculate.equals("Maximum Rate")) {
            outputTextView.setText("Maximum Rate: " + maximumRate + " bps");
        }




    }

    private boolean isAnyFieldEmpty() {
        boolean isEmpty = false;
        if (bandwidthInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(bandwidthInput);
        }

        if (subCarrierSpacingInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(subCarrierSpacingInput);
        }

        if (ofdmSymbolsInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(ofdmSymbolsInput);
        }

        if (numParallelBlocksInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(numParallelBlocksInput);
        }

        if (blockDurationInput.getText().toString().isEmpty()) {
            isEmpty = true;
        } else {
            clearError(blockDurationInput);
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