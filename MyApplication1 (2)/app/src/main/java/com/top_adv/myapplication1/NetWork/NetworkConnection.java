package com.top_adv.myapplication1.NetWork;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface NetworkConnection<E> {

	public void getResult();

	/**
	 * 구현부를 상속받은 목적통신 클래스에서 개별적으로 정의해서 쓸 JsonParssing함수
     */
	public E jsonParssing_one(JSONObject jsonObj)throws JSONException;
	public ArrayList<E> jsonParssing_list(JSONArray jsonArray)throws JSONException;

	public void setJsonData(JSONObject jsonData);
	public void setURL(String url);
	public void setRequestMethod(String method);
	public void setRequestProperty(String type, String value);
	public void setDoOutPut(Boolean value);
	public void setInstanceFollowRedirect(Boolean value);

	public JSONObject getJsonObject(String result)throws JSONException, IOException;
	public JSONArray getJsonArray(String result)throws JSONException, IOException;

	//public Boolean checkConnection();
	public void closeConnection();
	public Boolean getResponseCode();


	public String startConnection()throws IOException ;
	/**
	 * 통신값 설정후 실행
	 * URL 통신 리턴값 String값 그대로 리턴
	 */
}
