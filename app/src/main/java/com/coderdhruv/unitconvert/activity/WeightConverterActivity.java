package com.coderdhruv.unitconvert.activity;

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
import com.coderdhruv.unitconvert.databinding.ActivityWeightConverterBinding;

import java.text.DecimalFormat;

public class WeightConverterActivity extends AppCompatActivity {

    private ActivityWeightConverterBinding binding;
    private String unit = "kg";
    private final String[] unitMap = {"kg", "gm", "mg", "lb", "ct"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeightConverterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        setNumberPadListeners();
        binding.clear.setOnClickListener(v -> clearLastCharacter());

        CustomDialog customDialog = new CustomDialog(this);

        binding.back.setOnClickListener(v -> {
            finish();
        });

        binding.unit1.setOnClickListener(v -> customDialog.UnitDialog(
                getResources().getStringArray(R.array.weight_array),
                unitMap,
                (selectedItem, selectedUnit) -> {
                    binding.weight1.setText(selectedItem + " >");
                    binding.weightUnit.setText(selectedUnit);
                    unit = selectedUnit;
                    convertWeightAndUpdate();
                }
        ));


        binding.unit2.setOnClickListener(v -> customDialog.UnitDialog(
                getResources().getStringArray(R.array.weight_array),
                unitMap,
                (selectedItem, selectedUnit) -> {
                    binding.weight2.setText(selectedItem + " >");
                    binding.weightUnit2.setText(selectedUnit);
                    convertWeightAndUpdate();
                }
        ));

        binding.weightNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertWeightAndUpdate();
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
        String currentText = binding.weightNumber.getText().toString();


        if (value.equals(".") && currentText.contains(".")) {
            return;
        }

        if (currentText.equals("0") && value.equals(".")) {
            currentText = "0";
        } else if (currentText.equals("0") && !value.equals(".")) {
            currentText = "";
        }


        if (currentText.length() + value.length() <= 10) {
            String newText = currentText + value;
            binding.weightNumber.setText(newText);
        }




    }


    private void clearLastCharacter() {
        String currentText = binding.weightNumber.getText().toString();

        if (!currentText.isEmpty()) {
            currentText = currentText.substring(0, currentText.length() - 1);
        }
        if (currentText.isEmpty()) {
            currentText = "0";
        }
        binding.weightNumber.setText(currentText);
    }


    private void convertWeightAndUpdate() {
        String inputText = binding.weightNumber.getText().toString();
        if (inputText.isEmpty() || unit.isEmpty()) {
            return;
        }

        double inputWeight;
        try {
            inputWeight = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            return;
        }

        Converter converter = new Converter();

        String targetUnit = binding.weightUnit2.getText().toString();
        double convertedWeight = converter.convertWeight(inputWeight, unit, targetUnit);


        String formattedWeight;
        if (convertedWeight >= 1) {
            formattedWeight = new DecimalFormat("#.####").format(convertedWeight);
        } else {
            formattedWeight = new DecimalFormat("0.########").format(convertedWeight);
        }

        binding.weightNumber2.setText(formattedWeight);
    }


}