package com.example.alfasdk.Models.AccountOpening;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("result")
	private String result;

	@SerializedName("responseCode")
	private String responseCode;

	public String getResult(){
		return result;
	}

	public String getResponseCode(){
		return responseCode;
	}
}