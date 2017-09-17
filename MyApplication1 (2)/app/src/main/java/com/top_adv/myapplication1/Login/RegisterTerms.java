package com.top_adv.myapplication1.Login;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.top_adv.myapplication1.DataVO.FaqVO;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

/**
 * Created by EunBin Lee on 2017-04-18.
 */

public class RegisterTerms extends Fragment {

    private static String TAG = MainActivity.TAG;
    private String mObj;

    private TextView terms_bluemingo;
    private TextView terms_transaction;
    private LinearLayout terms_personal_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_register_terms, container, false);

        terms_bluemingo = (TextView) layout.findViewById(R.id.terms_bluemingo);
        terms_transaction = (TextView) layout.findViewById(R.id.terms_transaction);
        terms_personal_data = (LinearLayout) layout.findViewById(R.id.terms_personal_data);

        terms_bluemingo.setVisibility(View.GONE);
        terms_transaction.setVisibility(View.GONE);
        terms_personal_data.setVisibility(View.GONE);

        setTerms();

        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity()!=null && getActivity() instanceof RegisterActivity) {
        //    mObj = ((RegisterActivity) getActivity()).getTerms();

            //setViewVisibility(mObj.toString());
        }
    }

    public void setViewVisibility(String terms) {
        switch (terms) {
            case "블루밍고 이용약관":
                terms_bluemingo.setVisibility(View.VISIBLE);
                terms_transaction.setVisibility(View.GONE);
                terms_personal_data.setVisibility(View.GONE);
                break;
            case "전자금융거래이용약관":
                terms_bluemingo.setVisibility(View.GONE);
                terms_transaction.setVisibility(View.VISIBLE);
                terms_personal_data.setVisibility(View.GONE);
                break;
            case "개인정보 수집 및 이용":
                terms_bluemingo.setVisibility(View.GONE);
                terms_transaction.setVisibility(View.GONE);
                terms_personal_data.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setTerms() {
        BufferedReader in;
        Resources myResources = getResources();
        InputStream myFile = null;

        myFile = myResources.openRawResource(R.raw.term_bluemingo);

        StringBuffer strBuffer = new StringBuffer();
        String str = null;

        try {
            in = new BufferedReader(
                    new InputStreamReader(myFile, "UTF-8"));  // file이 utf-8 로 저장되어 있다면 "UTF-8"
            while( (str = in.readLine()) != null)             // file이 KSC5601로 저장되어 있다면 "KSC5601"
            {
                strBuffer.append(str + "\n");
            }
            in.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        terms_bluemingo.setText(strBuffer);

        myFile = myResources.openRawResource(R.raw.term_transaction);

        strBuffer = new StringBuffer();
        str = null;

        try {
            in = new BufferedReader(
                    new InputStreamReader(myFile, "UTF-8"));  // file이 utf-8 로 저장되어 있다면 "UTF-8"
            while( (str = in.readLine()) != null)             // file이 KSC5601로 저장되어 있다면 "KSC5601"
            {
                strBuffer.append(str + "\n");
            }
            in.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        terms_transaction.setText(strBuffer);
    }
}
