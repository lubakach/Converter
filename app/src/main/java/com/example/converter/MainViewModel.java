package com.example.converter;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<String> numberInput = new MutableLiveData<>("");
    private final MutableLiveData<String> numberOutput = new MutableLiveData<>("");
    private float factor = 2;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getNumberInput() {
        return numberInput;
    }

    public LiveData<String> getNumberOutput() {
        return numberOutput;
    }

    public void convertValue() {
        if (numberInput.getValue().length() > 0) {
            numberOutput.postValue(Float.toString((float) (Float.parseFloat(Objects.requireNonNull(numberInput.getValue())) * factor)));
        }
    }

    public void prepareToConverting(String group, String item1, String item2) {
        if (item1.equals(item2)) {
            factor = 1;
        }
        else if (group.equals("weight")) {
            findCoefficientWeight(item1, item2);
        }
        else if (group.equals("distance")) {
            findCoefficientDistance(item1, item2);
        }
        else {
            findCoefficientSquare(item1, item2);
        }
        convertValue();
    }

    private void findCoefficientSquare(String item1, String item2) {
        switch (item1+item2) {
            case "m^2km^2": {
                factor = (float) 0.0000010;
                break;
            }
            case "m^2ha": {
                factor = (float) 0.0001;
                break;
            }
            case "km^2m^2": {
                factor = 1000000;
                break;
            }
            case "km^2ha": {
                factor = 100;
                break;
            }
            case "ham^2": {
                factor = 10000;
                break;
            }
            case "hakm^2": {
                factor = (float) 0.01;
                break;
            }
        }
    }

    private void findCoefficientDistance(String item1, String item2) {
        switch (item1+item2) {
            case "kmmile": {
                factor = (float) 0.62137119223733;
                break;
            }
            case "kmyard": {
                factor = (float) 1093.6132983377;
                break;
            }
            case "milekm": {
                factor = (float) 1.609344;
                break;
            }
            case "mileyard": {
                factor = 1760;
                break;
            }
            case "yardkm": {
                factor = (float) 0.0009144;
                break;
            }
            case "yardmile": {
                factor = (float) 0.00056818181818182;
                break;
            }
        }
    }

    private void findCoefficientWeight(String item1, String item2) {
        switch (item1+item2) {
            case "kglb": {
                factor = (float) 2.2046223302272;
                break;
            }
            case "kgoz": {
                factor = (float) 35.27396194958;
                break;
            }
            case "lbkg": {
                factor = (float) 0.45359243;
                break;
            }
            case "lboz": {
                factor = (float) 16.000002116438;
                break;
            }
            case "ozkg": {
                factor = (float) 0.028349523125;
                break;
            }
            case "ozlb": {
                factor = (float) 0.062499991732666;
                break;
            }
        }
    }

    public void addNum(String num) {
        numberInput.setValue(numberInput.getValue() + num);
        convertValue();
    }

    public void addDot() {
        if (!Objects.requireNonNull(numberInput.getValue()).contains("."))
        {
            numberInput.setValue(numberInput.getValue() + ".");
            convertValue();
        }
    }

    public void deleteSymbol() {
        if (Objects.requireNonNull(numberInput.getValue()).length() > 1) {
            numberInput.setValue(numberInput.getValue().
                    substring(0, numberInput.getValue().length() - 1));
            convertValue();
            return;
        }
        deleteNumber();
    }

    public void deleteNumber() {
        numberInput.setValue("");
        numberOutput.setValue("");
    }

    public void swapFields(Spinner spinner0, Spinner spinner1, Spinner spinner2) {
        String temp = numberInput.getValue();
        numberInput.postValue(numberOutput.getValue());
        numberOutput.postValue(temp);

        int i = spinner1.getSelectedItemPosition();
        int j = spinner2.getSelectedItemPosition();
        spinner1.setSelection((j + 1) % 3);
        spinner2.setSelection((i + 2) % 3);

        String group = spinner0.getSelectedItem().toString();
        String item1 = spinner1.getSelectedItem().toString();
        String item2 = spinner2.getSelectedItem().toString();
        prepareToConverting(group, item1, item2);
    }

    public void copy(ClipboardManager clipboardManager, boolean isInput) {
        ClipData clipData;
        clipData = ClipData.newPlainText("number", getCopyString(isInput));
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplication(),
                "Number copied successful", Toast.LENGTH_SHORT).show();
    }

    private String getCopyString(boolean isInput) {
        if (isInput) {
            return numberInput.getValue();
        }
        return numberOutput.getValue();
    }

}
