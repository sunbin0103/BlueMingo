package com.top_adv.myapplication1.NetWork;

import android.util.Log;

import com.top_adv.myapplication1.DataVO.Ref_listVO;
import com.top_adv.myapplication1.R;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sst on 2017-03-06.
 * Object로 들어온 값을 설정된 익명의VO에 맞춰서 자동으로 파싱함
 */
public class JsonParsing<E> {

    private String TAG = "JsonParsing";
    protected Class<E> voType;
    private ArrayList<E> lists;

    public JsonParsing(Class<E> voType){
        this.voType = voType;

    }

    public E get(){
        try{
            return voType.newInstance();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<E> getResult(Object...params) throws JSONException, IOException{
        Log.d(TAG,(String)params[0]);
        lists = new ArrayList<E>();
        E rvo = null;
        JSONObject jsonObj = null;
        JSONArray jsonArray = new JSONArray((String)params[0]);
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObj = jsonArray.getJSONObject(i);
            //Log.d(TAG, "jsonObj.get : "+jsonObj.get("item_id"));
            rvo  = doParsing(jsonObj);
            lists.add(rvo);
        }
        return lists;
    }

    private E doParsing(JSONObject jsonObject) throws JSONException, IOException{
        String method_name = null;
        Field fld = null;
        E mvo = null;

        try{
            Class cls = Class.forName(voType.getName());
            Log.d(TAG, "Class.forName(voType.getName() : "+Class.forName(voType.getName()));

            Method methList[] = cls.getDeclaredMethods();
            Field fieldList[] = cls.getDeclaredFields();
            String fldName = null;
            mvo = get();
            for(int i=0;i<methList.length;i++){
                method_name = methList[i].getName();
                if(method_name.contains("set")){
                    fld = valueSearch(fieldList, method_name);
                    if(fld != null) {
                        fldName = fld.getName();

                        System.out.println("method : " + method_name + "/ value : " + jsonObject.get(fldName));
                        System.out.println("type : " + fld.getType() + "/ name : " + fldName);

                        if (jsonObject.get(fld.getName()) != null) {
                            if (fld.getType() == Integer.class) {
                                methList[i].invoke(mvo, Integer.parseInt(jsonObject.get(fldName).toString()));
                            } else if (fld.getType() == String.class) {
                                methList[i].invoke(mvo, jsonObject.get(fldName).toString());
                            } else if (fld.getType() == Long.class) {
                                methList[i].invoke(mvo, Long.valueOf(jsonObject.get(fldName).toString()));
                            } else if (fld.getType() == ArrayList.class) {
                                methList[i].invoke(mvo, jsonObject.get(fldName).toString());
                            } else {
                                methList[i].invoke(mvo, jsonObject.get(fldName).toString());
                            }
                        }
                    }
                }
            }

        }catch(Throwable e){
            System.err.println(e);
        }
        return mvo;
    }


    private Field valueSearch(Field[] fieldList, String method_name){
        String value = method_name.substring(3).toLowerCase();
        for(int i=0; i<fieldList.length; i++){
            if(fieldList[i].getName().equals(value)){
                return  fieldList[i];
            }
        }
        return null;
    }

}
