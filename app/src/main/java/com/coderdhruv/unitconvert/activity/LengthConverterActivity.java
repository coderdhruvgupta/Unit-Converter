package com.coderdhruv.unitconvert.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.coderdhruv.unitconvert.R;
import com.coderdhruv.unitconvert.common.Converter;
import com.coderdhruv.unitconvert.common.CustomDialog;
import com.coderdhruv.unitconvert.common.OnItemSelectedListener;
import com.coderdhruv.unitconvert.databinding.ActivityLengthConverterBinding;

import java.text.DecimalFormat;

public class LengthConverterActivity extends AppCompatActivity {

    private ActivityLengthConverterBinding binding;
    private String unit = "in"; // Default unit
    private final String[] unitMap = {"km", "m", "cm", "in", "ft", "yd"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLengthConverterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        setNumberPadListeners();
        binding.clear.setOnClickListener(v -> clearLastCharacter());

        CustomDialog customDialog = new CustomDialog(this);

        binding.back.setOnClickListener(v -> {
            finish();
        });

        binding.unit1.setOnClickListener(v -> customDialog.UnitDialog(
                getResources().getStringArray(R.array.length_array),
                unitMap,
                (selectedItem, selectedUnit) -> {
                    binding.length1.setText(selectedItem + " >");
                    binding.lengthUnit.setText(selectedUnit);
                    unit = selectedUnit;
                    convertLengthAndUpdate();
                }
        ));


        binding.unit2.setOnClickListener(v -> customDialog.UnitDialog(
                getResources().getStringArray(R.array.length_array),
                unitMap,
                (selectedItem, selectedUnit) -> {
                    binding.length2.setText(selectedItem + " >");
                    binding.lengthUnit2.setText(selectedUnit);
                    convertLengthAndUpdate();
                }
        ));

        binding.lengthNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertLengthAndUpdate();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setNumberPadListeners() {
        View.OnClickListener numberClickListener = v -> {
            String number = ((View) v).getTag().toString();
            addToEditText(number);
        };

        binding.one.setTag("1");
        binding.two.setTag("2");
        binding.three.setTag("3");
        binding.four.setTag("4");
        binding.five.setTag("5");
        binding.six.setTag("6");
        binding.seven.setTag("7");
        binding.eight.setTag("8");
        binding.nine.setTag("9");
        binding.zero.setTag("0");
        binding.point.setTag(".");

        binding.one.setOnClickListener(numberClickListener);
        binding.two.setOnClickListener(numberClickListener);
        binding.three.setOnClickListener(numberClickListener);
        binding.four.setOnClickListener(numberClickListener);
        binding.five.setOnClickListener(numberClickListener);
        binding.six.setOnClickListener(numberClickListener);
        binding.seven.setOnClickListener(numberClickListener);
        binding.eight.setOnClickListener(numberClickListener);
        binding.nine.setOnClickListener(numberClickListener);
        binding.zero.setOnClickListener(numberClickListener);
        binding.point.setOnClickListener(numberClickListener);
    }


    private void addToEditText(String value) {
        String currentText = binding.lengthNumber.getText().toString();

        if (value.equals(".") && currentText.contains(".")) {
        }

        if (currentText.equals("0") && value.equals(".")) {
            currentText = "0";
        } else if (currentText.equals("0") && !value.equals(".")) {
            currentText = "";
        }


        if (currentText.length() + value.length() <= 10) {
            String newText = currentText + value;
            binding.lengthNumber.setText(newText);
        }



    }


    private void clearLastCharacter() {
        String currentText = binding.lengthNumber.getText().toString();

        if (!currentText.isEmpty()) {
            currentText = currentText.substring(0, currentText.length() - 1);
        }
        if (currentText.isEmpty()) {
            currentText = "0";
        }
        binding.lengthNumber.setText(currentText);
    }


    private void convertLengthAndUpdate() {
        String inputText = binding.lengthNumber.getText().toString();
        if (inputText.isEmpty() || unit.isEmpty()) {
            return;
        }
        double inputLength;
        try {
            inputLength = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            return;
        }

        Converter converter = new Converter();
        String targetUnit = binding.lengthUnit2.getText().toString();

        double convertedLength = converter.convertLength(inputLength, unit, targetUnit);

        String formattedLength;
        if (convertedLength >= 1) {
            formattedLength = new DecimalFormat("#.####").format(convertedLength);
        } else {

            formattedLength = new DecimalFormat("0.########").format(convertedLength);
        }
        binding.lengthNumber2.setText(formattedLength);
    }


}