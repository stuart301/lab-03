package com.example.listycitylab3;

import android.app.Dialog;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    private boolean isEdit = false;
    private int editPosition = -1;
    private String initialName = "";
    private String initialProvince = "";

    public AddCityFragment() {}

    public AddCityFragment(City city, int position) {
        this.isEdit = true;
        this.editPosition = position;
        this.initialName = city.getName();
        this.initialProvince = city.getProvince();
    }

    interface AddCityDialogListener {
        void addCity(City city);

    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (isEdit) {
            editCityName.setText(initialName);
            editProvinceName.setText(initialProvince);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(isEdit ? "Edit City" : "Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "Save" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    City updated = new City(cityName, provinceName);

                    if (isEdit) {

                        ((MainActivity) requireActivity()).updateCityAt(editPosition, updated);
                    } else {

                        listener.addCity(updated);
                    }
                })
                .create();
    }
}