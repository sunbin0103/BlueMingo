package com.top_adv.myapplication1.NetWork;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.top_adv.myapplication1.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sst on 2016-11-25.
 */
public abstract class NetWorkConnectionImpl<E> extends AsyncTask<
        JSONObject // .excute() 인수
        , String // 진행정보 publishProgress(), onProgressUpdate() 인수
        , String // doInBackground() 리턴 타입, onPostExcute() 인수
        > {
    private static String TAG = "NetWorkConnection";
    public static final String BASE_URL = "http://www.bluemingo.xyz/servlet/";
    public String URL;

    public JSONObject jsonData;
    public static HttpURLConnection conn;
    public Integer responseCode;

    public static int CONN_TIMEOUT = 15000;
    public static int READ_TIMEOUT = 10000;

    public Boolean DO_OUT_PUT = false;
    public Boolean INSTANCE_FOLLOW_REDIRECT = false;
    public String REQUEST_METHOD = "GET";
    public String REQUEST_PROPERTY_TYPE = "Content-Type";
    public String REQUEST_PROPERTY_VALUE = "application/json";

    public DialogNetWork dialogNetWork;
    public AppCompatActivity activity;

    // 콜백 메소드
    public AsyncCallBack delegate;

    public NetWorkConnectionImpl(AsyncCallBack listCallback, AppCompatActivity activity) {
        this.activity = activity;
        this.delegate = listCallback;
    }

    /** 기본 통신값은 GET
     * RequestProperty 기본값은 "Content-Type", "application/json"
     * @param url 요청할 url
     * @param doOutPut 입출력 선택
     * @param IFRedirect Redirect값 받을지 선택
     * @param r_method GET or POST 방식 선택
     */
    public void setMethod(String url, Boolean doOutPut, Boolean IFRedirect, String r_method) {
        setURL(url);
        setDoOutPut(doOutPut);
        setInstanceFollowRedirect(IFRedirect);
        setRequestMethod(r_method.toUpperCase());
        setRequestProperty("Content-Type", "application/json"); // 본 네트워크 기본 베이스.
    }

    public void getNetWorkResult() {
        try {
            if (DO_OUT_PUT) {
                Log.d(TAG, "getNetWorkResult post");
                this.execute(jsonData);
            } else {
                Log.d(TAG, "getNetWorkResult get");
                this.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * AsyncTask기반 통신부
     * Spring MVC Web과 통신이 실제로 이루어지는 부분.
     * 별도의 Thread상 동작을 위해 AsyncTask사용
     */
    @Override
    protected void onPreExecute() {
        if(activity != null){
            dialogNetWork = new DialogNetWork(activity);
            dialogNetWork.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            System.out.println("dialog bg transparent");
        }
        if(dialogNetWork != null) {
            dialogNetWork.show();
        }

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(JSONObject... params) {
        String result = null;
        final int taskCnt = 100;
        int error = 0;

        do{// 최초 접속 시도
            try {
                result = startConnection();
            } catch (IOException e) {
                e.printStackTrace();
                // 접속 에러 발생시 재접속시도, 에러코드에 따라 표현 문구 변경
                // 인터넷문제, 서버문제, 기타문제 접속지연등
                // 팝업창으로 해결.
                System.out.println("Connection Error!");
                error ++;
            }
            if(error > 2) {// 3회 재접속 시도
                // 팝업창 띄우는 시점
                // 재접속 재시도 : error = 0으로 초기화
                System.out.println("Connection retry : "+error);
                break;
            }
        }while(error != 0);


        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "onPostExecute");
        if(dialogNetWork != null) {
            dialogNetWork.dismiss();
        }
        super.onPostExecute(result);
        if (delegate != null){
            delegate.onTaskDone(result);
        }
    }

    /**
     * 목적별 통신값 설정부
     * 통합 통신 클래스로 사용하기 위해 목적별로 별도의 설정값 필요
     * 이를 위한 메소드 모음
     */
    //@Override
    public String startConnection() throws IOException {
        String result = null;
        String line = null;
        BufferedReader br = null;
        StringBuffer outPut = null;
        Log.d(TAG, "setConnection Url : " +BASE_URL+URL);
        Log.d(TAG, "setConnection DoOutPut : " + DO_OUT_PUT);
        Log.d(TAG, "setConnection Redirects : " + INSTANCE_FOLLOW_REDIRECT);
        Log.d(TAG, "setConnection Method : " + REQUEST_METHOD);
        Log.d(TAG, "setConnection Type+Property : " + REQUEST_PROPERTY_TYPE + REQUEST_PROPERTY_VALUE);

        java.net.URL postUrl = new URL(BASE_URL+URL);
        conn = (HttpURLConnection) postUrl.openConnection();
        if (REQUEST_METHOD == "POST") {
            Log.i(TAG, "conn post! ");
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
                /*conn.setDoOutput(DO_OUT_PUT);
                conn.setInstanceFollowRedirects(INSTANCE_FOLLOW_REDIRECT);
                conn.setRequestMethod(REQUEST_METHOD);
                conn.setRequestProperty(REQUEST_PROPERTY_TYPE, REQUEST_PROPERTY_VALUE);*/
        }else{
            conn.setDoOutput(false);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
        }

        //responseCode = conn.getResponseCode();
        // 연결 에러 체크
        //if (responseCode == conn.HTTP_OK) {
            Log.i(TAG, "conn Success! ");
            // 데이터 송신이 필요할때
            if (DO_OUT_PUT) {
                Log.i(TAG, "start outPutStream! : "+jsonData.toString());
                OutputStream os = conn.getOutputStream();
                os.write(jsonData.toString().getBytes());
                os.flush();
            }

            // 데이터 수신부
            Log.i(TAG, "start inPutStream! ");
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            outPut = new StringBuffer();
            while ((line = br.readLine()) != null) {
                outPut.append(line);
                Log.d(TAG, "line : " + line);
            }
            result = outPut.toString();
        //}


        conn.disconnect();
        Log.d(TAG, "conn End");
        return result;
    }

    //@Override
    public void setURL(String url) {
        this.URL = url;
    }

    //@Override
    public void setRequestMethod(String method) {
        this.REQUEST_METHOD = method;
    }

    //@Override
    public void setRequestProperty(String type, String value) {
        this.REQUEST_PROPERTY_TYPE = type;
        this.REQUEST_PROPERTY_VALUE = value;
    }

    //@Override
    public void setDoOutPut(Boolean value) {
        this.DO_OUT_PUT = value;
    }

    //@Override
    public void setInstanceFollowRedirect(Boolean value) {
        this.INSTANCE_FOLLOW_REDIRECT = value;
    }

    //@Override
    public void setJsonData(JSONObject jsonData) {
        this.jsonData = jsonData;
    }

    //@Override
    public void closeConnection() {
        conn.disconnect();
    }

    //@Override
    public Boolean getResponseCode() {
        return null;
    }


    /**
     * 구현부를 상속받은 목적통신 클래스에서 개별적으로 정의해서 쓸 JsonParssing에 사용될 JSONObject,ArrayList 리턴 메소드
     */
    //@Override
    public JSONObject getJsonObject(String result) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(result);
        return jsonObject;
    }

    //@Override
    public JSONArray getJsonArray(String result) throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray(result);
        /*for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
        }*/
        return jsonArray;
    }
}
