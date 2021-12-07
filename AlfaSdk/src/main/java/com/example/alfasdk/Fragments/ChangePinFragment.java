package com.example.alfasdk.Fragments;


import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.HToast;


public class ChangePinFragment extends Fragment {

//    @BindView(R.id.edittext_new_pin)
    EditText edit_newPin;
//    @BindView(R.id.edittext_old_pin)
    EditText edit_oldPin;
//    @BindView(R.id.edittext_confirm_pin)
    EditText edit_confirmPin;

    Button button_change_pin;
    public ChangePinFragment() {
        // Required empty public constructor
    }

    public static ChangePinFragment newInstance() {
        return new ChangePinFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pin, container, false);
//        ButterKnife.bind(this, view);
        edit_newPin=view.findViewById(R.id.edittext_new_pin);
        edit_oldPin=view.findViewById(R.id.edittext_old_pin);
        edit_confirmPin=view.findViewById(R.id.edittext_confirm_pin);
        button_change_pin=view.findViewById(R.id.button_change_pin);

        button_change_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPin = edit_oldPin.getText().toString();
                String newPin = edit_newPin.getText().toString();
                String confirmPin = edit_confirmPin.getText().toString();


                if (!TextUtils.equals(oldPin, "")) {

                    if (!TextUtils.equals(newPin, "") && newPin.length() == 4) {

                        if (TextUtils.equals(newPin, confirmPin)) {

                            ((MyMainActivity) getActivity()).changePinRequest(oldPin, newPin);

                        } else {
                            HToast.showMsg(getActivity(), "Pins do not match.");
                        }
                    } else {
                        HToast.showMsg(getActivity(), "New Pin must be equal to 4 characters.");
                    }

                } else {
                    HToast.showMsg(getActivity(), "Please type your old pin.");
                }

            }
        });

        return view;
    }

//    @OnClick(R.id.button_change_pin)
//    public void submit(View view) {
//
//
//
//    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Change Pin");
        }
        super.onResume();
    }

}
