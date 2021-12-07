package com.example.alfasdk.Network;


import com.example.alfasdk.Const.Constants;
import com.example.alfasdk.Models.AccountOpening.AccountOpeningObject;
import com.example.alfasdk.Models.AccountOpening.AccountOpeningRequestResponse;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public class RetrofitApi {

    private static Api api = null;

    public static Api getService() {
        if (api == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(15, TimeUnit.SECONDS);
            httpClient.connectTimeout(15, TimeUnit.SECONDS);
            httpClient.addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY));

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.ACCOUNT_OPENING_BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            api = retrofit.create(Api.class);
        }
        return api;
    }

    public interface Api {
        @Headers("Content-Type: application/json")
        @POST("saveClientAcc")
        Call<AccountOpeningRequestResponse> createAccount(@Body AccountOpeningObject requestBody);

    }

}
