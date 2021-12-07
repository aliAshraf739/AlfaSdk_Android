package com.example.alfasdk.Util;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/**
 * Developed by Hasham.Tahir on 1/27/2016.
 */
public class HSnackBar {


    public static void showMsg(View view, String msg) {

        try {
            Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
