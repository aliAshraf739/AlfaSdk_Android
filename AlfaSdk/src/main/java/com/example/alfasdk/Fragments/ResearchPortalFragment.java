package com.example.alfasdk.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alfasdk.Const.Constants;
import com.example.alfasdk.Network.OnRestClientCallback;
import com.example.alfasdk.Network.RestClient;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.Alert;
import com.example.alfasdk.Util.HToast;
import com.example.alfasdk.Util.Loading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ResearchPortalFragment extends Fragment {

    private static final String TAG = "ResearchPortalDebug";
    private WebView webView;
    private Loading loading;
    private static final String KEY_SYMBOL ="key_symbol";
    private String symbolName =null;

    public ResearchPortalFragment() {
        // Required empty public constructor
    }

    public static ResearchPortalFragment newInstance(String symbolName) {
        Bundle extras = new Bundle();
        extras.putString(KEY_SYMBOL,symbolName);

        ResearchPortalFragment fragment = new ResearchPortalFragment();
                fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        symbolName = getArguments().getString(KEY_SYMBOL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loading = new Loading(getActivity(),"Loading..");

        webView = (WebView) inflater.inflate(R.layout.fragment_reseach_portal, container, false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new AppWebViewClient());
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(s));
                startActivity(i);
            }
        });


        Log.e(TAG, "Research Portal: ");

        return webView;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Explore Us");
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Constants.RESEARCH_PORTAL_URL.length() > 0) {
            String url=Constants.RESEARCH_PORTAL_URL;
                callingResearchPortalService(Constants.RESEARCH_PORTAL_URL);
        }
    }

//    public void callingResearchPortalService(String url) {
//
//        if (ConnectionDetector.getInstance(getActivity()).isConnectingToInternet()) {
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("username", "rashid.irshad");
//                jsonObject.put("client", Constants.RESESRCH_PORTAL_CLIENT);
//                jsonObject.put("password", "rashid123");
//                jsonObject.put("ip", Constants.RESESRCH_PORTAL_IP);
//
//                RestClient.postRequest("research_portal",
//                        getActivity(),
//                        url,
//                        jsonObject,
//                        new OnRestClientCallback() {
//                            @Override
//                            public void onRestSuccess(JSONObject response, String action) {
//                                Log.d(TAG, "onRestSuccess: ");
//
//                                try {
//                                    if (response.getString("response").equals("success")) {
//
//                                        String url = response.getString("link");
//                                        if (symbolName!=null){
//                                            url=url+ "&symbol=" + symbolName;
//                                            Log.d("PortalUrl",url);
//
//                                        }
//                                        webView.loadUrl(url);
//                                    } else {
//                                        Alert.show(getActivity() , getActivity().getString(R.string.app_name),response.getString("message"));
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    HToast.showMsg(getContext(), "Unable to connect to Trading Server please try later or check your network");
//                                }
//                            }
//
//                            @Override
//                            public void onRestError(Exception e, String action) {
//                                Log.d(TAG, "onRestError: exception: " + e.getMessage() + " action: " + action);
//                                Alert.showErrorAlert(getActivity());
//                            }
//                        }, false, "Please wait..");
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Alert.showErrorAlert(getContext());
//            }
//        }
//
//
//    }


    public void callingResearchPortalService(String url) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "rashid.irshad");
            jsonObject.put("client", Constants.RESESRCH_PORTAL_CLIENT);
            jsonObject.put("password", "rashid123");
            jsonObject.put("ip", Constants.RESESRCH_PORTAL_IP);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString() + " i am queen");
                        try {
                            if (response.getString("response").equals("success")) {

                                String url = response.getString("link");
                                if (symbolName!=null){
                                    url=url+ "&symbol=" + symbolName;
                                    Log.d("PortalUrl",url);

                                }
                                webView.loadUrl(url);
                            } else {
                                Alert.show(getActivity() , getActivity().getString(R.string.app_name),response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        // Adding request to request queue
        Volley.newRequestQueue(getContext()).add(jsonObjReq);

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
}