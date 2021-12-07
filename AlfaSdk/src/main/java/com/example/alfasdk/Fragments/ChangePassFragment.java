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
import com.example.alfasdk.Util.EnctyptionUtils;
import com.example.alfasdk.Util.HToast;


public class ChangePassFragment extends Fragment {

//    @BindView(R.id.edittext_new_pass)
    EditText edit_newPass;
//    @BindView(R.id.edittext_old_pass)
    EditText edit_oldPass;
//    @BindView(R.id.edittext_confirm_pass)
    EditText edit_confirmPass;
    String newpassEncoded,oldPassEncoded;
    EnctyptionUtils enctyptionUtils = new EnctyptionUtils();

    Button button_change_pass;
    public ChangePassFragment() {
        // Required empty public constructor
    }

    public static ChangePassFragment newInstance() {
        return new ChangePassFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
//        ButterKnife.bind(this, view);
        edit_newPass=view.findViewById(R.id.edittext_new_pass);
        edit_oldPass=view.findViewById(R.id.edittext_old_pass);
        edit_confirmPass=view.findViewById(R.id.edittext_confirm_pass);
        button_change_pass=view.findViewById(R.id.button_change_pass);

        button_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    newpassEncoded = enctyptionUtils.encrypt(edit_newPass.getText().toString());
                    oldPassEncoded = enctyptionUtils.encrypt(edit_oldPass.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String oldPassword = edit_oldPass.getText().toString();
                String newPassword = edit_newPass.getText().toString();
                String confirmPassword = edit_confirmPass.getText().toString();


                if (!TextUtils.equals(oldPassword, "")) {

                    if (!TextUtils.equals(newPassword, "") && newPassword.length() >= 8) {

                        if (TextUtils.equals(newPassword, confirmPassword)) {

                            ((MyMainActivity) getActivity()).changePasswordRequest(oldPassEncoded, newpassEncoded);

                        } else {
                            HToast.showMsg(getActivity(), "Passwords do not match.");
                        }
                    } else {
                        HToast.showMsg(getActivity(), "New password must be equal to 8 characters.");
                    }

                } else {
                    HToast.showMsg(getActivity(), "Please type your old password.");
                }


            }
        });
        return view;
    }

//    @OnClick(R.id.button_change_pass)
//    public void submit(View view) {
//
//    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Change Password");
        }
        super.onResume();
    }

}
