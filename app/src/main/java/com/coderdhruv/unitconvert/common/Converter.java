package com.coderdhruv.unitconvert.common;

public class Converter {


    public double convertWeight(double value, String fromUnit, String toUnit) {
        // Conversion factors
        double factorKgToG = 1000.0;
        double factorKgToMg = 1_000_000.0;
        double factorKgToLb = 2.20462;
        double factorKgToCt = 5000.0; // 1 kg = 5000 carats

        double kgValue = value;

        // Convert from current unit to kilograms
        switch (fromUnit) {
            case "gm":
                kgValue /= factorKgToG;
                break;
            case "mg":
                kgValue /= factorKgToMg;
                break;
            case "lb":
                kgValue /= factorKgToLb;
                break;
            case "ct":
                kgValue /= factorKgToCt;
                break;

        }

        // Convert from kilograms to target unit
        switch (toUnit) {
            case "gm":
                return kgValue * factorKgToG;
            case "mg":
                return kgValue * factorKgToMg;
            case "lb":
                return kgValue * factorKgToLb;
            case "ct":
                return kgValue * factorKgToCt;
            default:
                return kgValue; // kg
        }
    }

    public double convertLength(double value, String fromUnit, String toUnit) {
        // Conversion factors to meter
        double factorKmToM = 1000.0;
        double factorCmToM = 0.01;
        double factorInToM = 0.0254;
        double factorFtToM = 0.3048;
        double factorYdToM = 0.9144;

        double meterValue = value;

        // Convert from current unit to meters
        switch (fromUnit) {
            case "km":
                meterValue *= factorKmToM;
                break;
            case "cm":
                meterValue *= factorCmToM;
                break;
            case "in":
                meterValue *= factorInToM;
                break;
            case "ft":
                meterValue *= factorFtToM;
                break;
            case "yd":
                meterValue *= factorYdToM;
                break;
        }

        // Convert from meters to target unit
        switch (toUnit) {
            case "km":
                return meterValue / factorKmToM;
            case "cm":
                return meterValue / factorCmToM;
            case "in":
                return meterValue / factorInToM;
            case "ft":
                return meterValue / factorFtToM;
            case "yd":
                return meterValue / factorYdToM;
            default:
                return meterValue; // m
        }
    }
}
