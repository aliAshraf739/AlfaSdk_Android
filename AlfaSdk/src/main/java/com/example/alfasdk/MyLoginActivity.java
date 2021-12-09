package com.example.alfasdk;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.MenuActivity;
import com.example.alfasdk.Const.ConnectionDetector;
import com.example.alfasdk.Const.Constants;
import com.example.alfasdk.Models.AccountOpening.AccountOpeningObject;
import com.example.alfasdk.Models.Event;
import com.example.alfasdk.Models.LoginModel.LoginResponse;
import com.example.alfasdk.Models.MarketModel.MarketResponse;
import com.example.alfasdk.Models.SymbolsModel.SymbolsResponse;
import com.example.alfasdk.Network.OnRestClientCallback;
import com.example.alfasdk.Network.RestClient;
import com.example.alfasdk.Util.EnctyptionUtils;
import com.example.alfasdk.Util.HSnackBar;
import com.example.alfasdk.Util.HToast;
import com.example.alfasdk.Util.Preferences;
import com.example.alfasdk.Util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MyLoginActivity extends BaseActivity implements SdkInterface {

    private static final String TAG = "LoginActivity";
    EditText etName;
    EditText etPass;
    TextView forgotPassword;
    TextView etServer;
    Button login_btn;
    Context context = MyLoginActivity.this;
    private Preferences preferences;
    String[] serverNameArray = new String[]{"Primary", "Secondary", "DR"};
    String[] serverUrlArray = new String[]{"terminal1.alfalahtrade.com", "terminal2.alfalahtrade.com", "terminal1.alfalahtrade.net"};
    String userEncoded;
    String passEncoded, passdecoded;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_login);

//        ButterKnife.bind(this);
//        if (BuildConfig.FLAVOR=="alfalahsec") {
            TextView etServer = (TextView) findViewById(R.id.login_server);
        etName=findViewById(R.id.login_name);
        etPass=findViewById(R.id.login_pass);
        forgotPassword=findViewById(R.id.tv_forgotPwd);
        etServer=findViewById(R.id.login_server);
        login_btn=findViewById(R.id.login_btn);
//        }
//        etName.setText("act01315");
//        etPass.setText("123456");
//        etName.setText("00022249");
//        etPass.setText("bipl1234");
//        etName.setText("00024639");
//        etPass.setText("pakipower1");


//
        etName.setText("Softech");
        etPass.setText("afs987");

//        preferences = StoreBox.create(this, Preferences.class);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.getBoolean("discon")) {
//                Alert.show(context, getString(R.string.app_name), extras.getString("message"));
            }
        }
//        forgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context, ForgetPasswordActivity.class));
//            }
//        });
//        registermeBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context, SignupActivity.class));
//            }
//        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callingloginservice(v);

            }
        });

        TextView finalEtServer = etServer;
        etServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    finalEtServer.setText("Primary");
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MyLoginActivity.this);
                    alert.setItems(serverNameArray, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finalEtServer.setText(serverNameArray[which]);
                            Constants.serverIpAddress = new String[]{serverUrlArray[which]};

                        }
                    });
//                    alert.show();

            }
        });
    }

    public void callingloginservice(View view,String token,String bankAcctNo,String cnic) {

        try {
            EnctyptionUtils enctyptionUtils = new EnctyptionUtils();

        } catch (Exception e) {
            e.printStackTrace();
        }

            final JsonObject login_obj = new JsonObject();

            login_obj.addProperty("MSGTYPE", Constants.LOGIN_MESSAGE_IDENTIFIER);
            login_obj.addProperty("token", token);
            login_obj.addProperty("bankAcctNo", bankAcctNo);
            login_obj.addProperty("cnic", cnic);

                Constants.KASB_API_LOGIN.length();

                JSONObject jsonObject = new JSONObject();

                if (Constants.KASB_API_LOGIN !=null && !Constants.KASB_API_LOGIN.isEmpty()){
                    try {
                        jsonObject.put("userId", "user");

                        RestClient.postRequest("login",
                                context,
                                Constants.KASB_API_LOGIN,
                                jsonObject,
                                new OnRestClientCallback() {
                                    @Override
                                    public void onRestSuccess(JSONObject response, String action) {

//                                    Log.d("Call","response: "+response);
                                        try {
                                            if (response.getString("code").equals("200")) {

                                                String ip = response.getString("ip");
                                                String port = response.getString("port");
                                                Constants.serverIpAddress = new String[]{ip};
                                                if (port.contains(",")) {
                                                    String[] portsArray = port.split(",");
                                                    Constants.ports = new int[portsArray.length];
                                                    for (int i = 0; i < Constants.ports.length; i++) {
                                                        Constants.ports[i] = Integer.parseInt(portsArray[i]);
                                                    }
                                                } else
                                                    Constants.ports[0] = Integer.parseInt(port);

                                                connectWithMessageServer(login_obj);

                                            }
                                        } catch (Exception e) {
                                            Log.e(TAG, "Catch: onRestSuccess: ");
                                            e.printStackTrace();
                                            HToast.showMsg(context, "Unable to connect to Trading Server please try later or check your network");
                                        }
                                    }

                                    @Override
                                    public void onRestError(Exception e, String action) {

//                                        Alert.showErrorAlert(context);
                                        Log.e(TAG,"onRestError: ");
                                    }
                                }, false, "Fetching Server IP's");

                    } catch (JSONException e) {
                        e.printStackTrace();
//                        Alert.showErrorAlert(context);
                        Log.d("Call","JSONException: ");
                    }
                }
                else{
                    connectWithMessageServer(login_obj);
                }



//            else {
//                try {
//                    HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }


//        }
//        else {
//
//            HSnackBar.showMsg(view, "Please enter username and password.");
//        }

    }

    private void connectWithMessageServer(final JsonObject login_obj) {
        Log.d(TAG, "connectWithMessageServer");

        connectMessageServer();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<Integer, String> map = new HashMap<>();
                map.put(1, Constants.LOGIN_MESSAGE_IDENTIFIER);
                map.put(2, login_obj.toString());
                write(map, true);
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.logOut) {
            Util.logOut = false;
//            Alert.show(context, getString(R.string.app_name), Util.logOutString);
        }

        enableReconnect = false;
    }

    @Override
    public void onMessageReceived(String action, String resp) {

        String symbolResult = null;
        String loginResult = null;
        Gson gson = new Gson();

        JsonParser jsonParser = new JsonParser();

        try {
            JsonObject jsonObject = jsonParser.parse(resp).getAsJsonObject();

            String MSGTYPE = jsonObject.get("response").getAsJsonObject().get("MSGTYPE").getAsString();
            String error = jsonObject.get("error").getAsString();
            String code = jsonObject.get("code").getAsString();

            Log.d(TAG, "MSGTYPE: " + MSGTYPE);

            if (code.equals("200") && error.equals("")) {

                switch (MSGTYPE) {

                    case Constants.LOGIN_MESSAGE_RESPONSE: {

                        LoginResponse result = gson.fromJson(resp, LoginResponse.class);
                        Log.d("Call","response result: "+result);


                        if (result != null) {

                            if (result.getCode().equals("200")) {
                                SharedPreferences  mPrefs = getSharedPreferences("appData",MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                Gson gson1 = new Gson();
                                loginResult = gson1.toJson(result);
                                Constants.LOGIN_RESPONSE=loginResult;
                                prefsEditor.putString("loginResult", loginResult);
                                prefsEditor.putString("username","Demo320");
                                prefsEditor.putString("password","Demo123");
                                prefsEditor.commit();
                                prefsEditor.apply();

                                if (result.getResponse().isShowSecLevPswd() && result.getResponse().getUsertype() == 1) {

                                    try {
                                        String encodedPass = result.getResponse().getSecondLevelPassword();


                                        if (encodedPass.equals("")) {

                                            preferences.setDecryptedPassword(encodedPass);
//                                            startActivity(new Intent(context, EncryptedPasswordActivity.class));
                                            finish();
                                        } else {
                                            EnctyptionUtils enctyptionUtils = new EnctyptionUtils();
                                            passdecoded = enctyptionUtils.decrypt(result.getResponse().getSecondLevelPassword());
                                            Log.d("passworddecoded", passdecoded);
                                            preferences.setDecryptedPassword(passdecoded);
                                            preferences.setClientCode(result.getResponse().getClient());
//                                            startActivity(new Intent(context, EncryptedPasswordActivity.class));
                                            finish();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                } else if (result.getResponse().getChangePassword() !=null && result.getResponse().getChangePassword().equals("true")) {
                                    preferences.setUserId(result.getResponse().getUserId());
//                                    startActivity(new Intent(LoginActivity.this, ChangePasswordActivity.class));
                                } else {
//      FOR ZAFAR SECURITIES
                                    getMarket();
                                }
                                Event.add(context, new Event(System.currentTimeMillis(), result.getResponse().getUserId() + " logged in successfully."));
                                getMarket();
                            } else {

//                                Alert.show(LoginActivity.this, "", result.getError());
                            }


                        } else {
                            Log.d(TAG, "Response :: LoginResponse null ");
                        }

                    }
                    break;


                    case Constants.SYMBOL_MESSAGE_RESPONSE: {

                        SymbolsResponse result = gson.fromJson(resp, SymbolsResponse.class);

                        if (result != null) {

                            if (result.getCode().equals("200")) {

                                SharedPreferences  mPrefs = getSharedPreferences("appData",MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                Gson gson1 = new Gson();
                                 symbolResult = gson1.toJson(result);
                                 Constants.SYMBOL_RESPONSE=symbolResult;
                                prefsEditor.putString("symbolResult", symbolResult);
                                prefsEditor.apply();
//                                preferences.setSymbolResult(gson.toJson(result));

//                                getMarket();

                            } else {

//                                Alert.show(context, "", result.getError());
                            }


                        } else {
                            Log.d(TAG, "Response :: SymbolsResponse null ");
                        }
                    }
                    break;

                    case Constants.SUBSCRIPTION_LIST_REQUEST_RESPONSE: {

                        MarketResponse result = gson.fromJson(resp, MarketResponse.class);

                        if (result != null) {


                            if (result.getCode().equals("200")) {
                                SharedPreferences  mPrefs = getSharedPreferences("appData",MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                Gson gson1 = new Gson();
                                String marketResponse = gson1.toJson(result);
                                Constants.MARKET_RESPONSE=marketResponse;
                                prefsEditor.putString("marketResult", marketResponse);
                                prefsEditor.apply();

//                                preferences.setMarketResult(gson.toJson(result));



                                Intent intent = new Intent(context, MyMainActivity.class);
                                intent.putExtra("marketResponse", Constants.MARKET_RESPONSE);
                                intent.putExtra("symbolResult", Constants.SYMBOL_RESPONSE);
                                intent.putExtra("loginResult", Constants.LOGIN_RESPONSE);
                                startActivity(intent);

                            } else {

//                                Alert.show(context, "", result.getError());
                            }


                        } else {
                            Log.d(TAG, "Response :: MarketResponse null ");
                        }
                    }
                    break;
                }

            } else {
//                Alert.show(context, getString(R.string.app_name), error);
            }


        } catch (JsonSyntaxException e) {
            e.printStackTrace();
//            Alert.showErrorAlert(context);


        }


    }

    private void getSymbolsFromServer() {

        JsonObject login_obj = new JsonObject();

        login_obj.addProperty("MSGTYPE", Constants.SYMBOL_MESSAGE_IDENTIFIER);
//        login_obj.addProperty("userId", user);
//        login_obj.addProperty("pswd", pas);
        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SYMBOL_MESSAGE_IDENTIFIER);
            map.put(2, login_obj.toString());

            write(map, true);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void getMarket() {
        LoginResponse loginResponse;

        Gson gson = new Gson();
        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
        String loginJson =Constants.LOGIN_RESPONSE;
        loginResponse = gson.fromJson(loginJson, LoginResponse.class);


        String clientcode = loginResponse.getResponse().getClient();
        String userId = loginResponse.getResponse().getUserId();

        Log.d("clientcode", "clientcode: " + "clientcode");

        JsonObject login_obj = new JsonObject();

        login_obj.addProperty("MSGTYPE", Constants.SUBSCRIPTION_LIST_REQUEST_IDENTIFIER);
//        login_obj.addProperty("userId", user);
        login_obj.addProperty("userId", userId);
        login_obj.addProperty("client", clientcode);

        connectMessageServer();
        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SUBSCRIPTION_LIST_REQUEST_IDENTIFIER);
            map.put(2, login_obj.toString());

            write(map, true);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void login(View view,String token,String bankAcctNo,String cnic) {
        callingloginservice(view,token,bankAcctNo,cnic);
    }

    @Override
    public void createAccount(AccountOpeningObject accountOpeningObject) {
//        Intent intent = new Intent(context, AccountOpeningActivity.class);
//        intent.putExtra("accountOpeningObject", accountOpeningObject);
//        startActivity(intent);

        Intent intent = new Intent();
        intent.setClassName("com.example.alfasdk", "AccountOpeningActivity");
        intent.putExtra("accountOpeningObject", accountOpeningObject);
        startActivity(intent);
    }

    @Override
    public void createAccount() {
        Intent intent = new Intent(context, AccountOpeningActivity.class);
        startActivity(intent);
    }

}

