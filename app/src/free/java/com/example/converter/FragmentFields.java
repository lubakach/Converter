package com.example.converter;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class FragmentFields extends Fragment implements AdapterView.OnItemSelectedListener{

    Spinner spinnerGroup, spinnerIn, spinnerOut;
    EditText editTextIn, editTextOut;
    MainViewModel viewModel;

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fields =  inflater.inflate(R.layout.fragment_fields, container, false);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);

        spinnerGroup = (Spinner) fields.findViewById(R.id.spinnerGroup);
        spinnerIn = (Spinner) fields.findViewById(R.id.spinnerInput);
        spinnerOut = (Spinner) fields.findViewById(R.id.spinnerOutput);

        ArrayAdapter<?> adapterGroup =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()),
                        R.array.Groups, android.R.layout.simple_spinner_item);
        adapterGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroup.setAdapter(adapterGroup);

        spinnerGroup.setOnItemSelectedListener(this);
        spinnerIn.setOnItemSelectedListener(this);
        spinnerOut.setOnItemSelectedListener(this);

        return fields;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == spinnerGroup) {
            String choose = adapterView.getItemAtPosition(i).toString();
            int spinnerInStr = 0, spinnerOutStr = 0;
            switch (choose) {
                case "distance": {
                    spinnerInStr = R.array.Distance;
                    spinnerOutStr = R.array.Distance2;
                    break;
                }
                case "weight": {
                    spinnerInStr = R.array.Weight;
                    spinnerOutStr = R.array.Weight2;
                    break;
                }
                case "square": {
                    spinnerInStr = R.array.Square;
                    spinnerOutStr = R.array.Square2;
                    break;
                }
            }

            ArrayAdapter<?> adapterInput =
                    ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()),
                            spinnerInStr, android.R.layout.simple_spinner_item);
            adapterInput.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerIn.setAdapter(adapterInput);

            ArrayAdapter<?> adapterOutput =
                    ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()),
                            spinnerOutStr, android.R.layout.simple_spinner_item);
            adapterOutput.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerOut.setAdapter(adapterOutput);
        }
        else {
            String choose = spinnerGroup.getSelectedItem().toString();
            String item1 = spinnerIn.getSelectedItem().toString();
            String item2 = spinnerOut.getSelectedItem().toString();
            viewModel.prepareToConverting(choose, item1, item2);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextIn = view.findViewById(R.id.editTextInput);
        editTextOut = view.findViewById(R.id.editTextOutput);
        viewModel.getNumberInput().observe(requireActivity(), value -> editTextIn.setText(value));
        viewModel.getNumberOutput().observe(requireActivity(), value -> editTextOut.setText(value));
    }

}