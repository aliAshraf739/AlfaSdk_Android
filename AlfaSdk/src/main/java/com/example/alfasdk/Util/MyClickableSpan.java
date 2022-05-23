package com.example.alfasdk.Util;

import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.alfasdk.WebViewActivity;

public class MyClickableSpan extends ClickableSpan{

    private final String TAG = "MyClickableSpan2";

    FragmentActivity requireActivity;
    String title;
    String link;
    int color;

    public MyClickableSpan(FragmentActivity requireActivity, String title, String link, int color){
        this.requireActivity = requireActivity;
        this.title = title;
        this.link = link;
        this.color = color;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setUnderlineText(true);
        ds.setColor(color);
    }

    @Override
    public void onClick(@NonNull View widget) {
        Log.e(TAG, "onClick: link" );
        Intent intent = new Intent(requireActivity, WebViewActivity.class);
        intent.putExtra("title", title);
        requireActivity.startActivity(intent);
    }

}
