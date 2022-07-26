package com.example.alfasdk.Models.AccountOpening;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AccountOpeningRequestResponse{

	@SerializedName("invalidSet")
	private List<String> invalidSet;

	@SerializedName("response")
	private Response response;

	@SerializedName("missingSet")
	private List<String> missingSet;

	@SerializedName("code")
	private String code;

	public List<String> getInvalidSet(){
		return invalidSet;
	}

	public Response getResponse(){
		return response;
	}

	public List<String> getMissingSet(){
		return missingSet;
	}

	public String getCode() {
		return code;
	}
}