package com.example.alfasdk;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alfasdk.Util.Loading;

public class WebViewActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvTitle;

    private WebView webView;
    private Loading loading;

    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setUpToolbar();
        setUpWebView();
        loadHtmlFile();
    }


    private void setUpToolbar() {
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);

        title = getIntent().getStringExtra("title")+"";
        tvTitle.setText(title);
        ivBack.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpWebView() {
        loading = new Loading(this,"Loading..");
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new AppWebViewClient());
        webView.setDownloadListener((s, s1, s2, s3, l) -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(s));
            startActivity(i);
        });

    }

    private void loadHtmlFile() {
        if(title.equals(getString(R.string.termsOfService))){
            webView.loadUrl("file:///android_asset/terms_and_conditions.html");
        }
        else if(title.equals(getString(R.string.privacyPolicy))){
            webView.loadUrl("file:///android_asset/terms_and_conditions.html");
        }
    }


    private class AppWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loading.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loading.cancel();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            loading.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

}