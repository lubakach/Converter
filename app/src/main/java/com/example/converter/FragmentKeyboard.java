package com.example.converter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public class FragmentKeyboard extends Fragment{

    MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View keyboard = inflater.inflate(R.layout.fragment_keyboard, container, false);
        keyboard.findViewById(R.id.Button0).setOnClickListener(item -> viewModel.addNum("0"));
        keyboard.findViewById(R.id.Button1).setOnClickListener(item -> viewModel.addNum("1"));
        keyboard.findViewById(R.id.Button2).setOnClickListener(item -> viewModel.addNum("2"));
        keyboard.findViewById(R.id.Button3).setOnClickListener(item -> viewModel.addNum("3"));
        keyboard.findViewById(R.id.Button4).setOnClickListener(item -> viewModel.addNum("4"));
        keyboard.findViewById(R.id.Button5).setOnClickListener(item -> viewModel.addNum("5"));
        keyboard.findViewById(R.id.Button6).setOnClickListener(item -> viewModel.addNum("6"));
        keyboard.findViewById(R.id.Button7).setOnClickListener(item -> viewModel.addNum("7"));
        keyboard.findViewById(R.id.Button8).setOnClickListener(item -> viewModel.addNum("8"));
        keyboard.findViewById(R.id.Button9).setOnClickListener(item -> viewModel.addNum("9"));
        keyboard.findViewById(R.id.ButtonDot).setOnClickListener(item -> viewModel.addDot());
        keyboard.findViewById(R.id.ButtonDelete).setOnClickListener(item -> viewModel.deleteSymbol());
        keyboard.findViewById(R.id.ButtonClear).setOnClickListener(item -> viewModel.deleteNumber());
        return keyboard;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

}