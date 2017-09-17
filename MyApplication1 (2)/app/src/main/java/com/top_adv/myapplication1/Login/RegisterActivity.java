package com.top_adv.myapplication1.Login;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.top_adv.myapplication1.DataVO.UserVO;
import com.top_adv.myapplication1.MainActivity;
import com.top_adv.myapplication1.NetWork.AsyncCallBack;
import com.top_adv.myapplication1.NetWork.NetWorkConnectionImpl;
import com.top_adv.myapplication1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by EunBin Lee on 2017-03-16.
 */

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = MainActivity.TAG;
    private static final int PERMISSION_SEND_SMS = 1;

    private EditText register_id_input;
    private EditText register_pass_input;
    private EditText register_pass_check_input;
    private EditText register_name_input;
    private EditText register_phone_input;
    private Button register_phone_authentication;
    private Button btn_register;
    private TextView register_inform;
    private View register_terms;
    private TextView register_copyright;

    private RegisterTerms fmTerms;
    private String register_inform_text = "본인은 만 14세 이상이며, 블루밍고 이용약관, 전자금융거래이용약관, 개인정보 수집 및 이용, 개인정보 제공 내용을 확인하였으며 이에 동의합니다.";
    private String[] categoryTerm = {"블루밍고 이용약관", "전자금융거래이용약관", "개인정보 수집 및 이용", "개인정보 제공"};
    private String selectedTerm;
    private Context mContext;

    private UserVO userVO;
    private boolean isPhoneAuthentication = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        mContext = getBaseContext();

        Log.d(TAG,"RegisterActivity start");
        userVO = new UserVO();

        register_id_input = (EditText) findViewById(R.id.register_id_input);
        register_pass_input = (EditText) findViewById(R.id.register_pass_input);
        register_pass_check_input = (EditText) findViewById(R.id.register_pass_check_input);
        register_name_input = (EditText) findViewById(R.id.register_name_input);
        register_phone_input = (EditText) findViewById(R.id.register_phone_input);
        register_phone_authentication = (Button) findViewById(R.id.register_phone_authentication);
        btn_register = (Button) findViewById(R.id.btn_register);
        register_inform = (TextView) findViewById(R.id.register_inform);
        register_terms = findViewById(R.id.register_terms);
        register_copyright = (TextView) findViewById(R.id.register_copyright);

        fmTerms = new RegisterTerms();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.register_terms, fmTerms);
        fragmentTransaction.commit();

        register_id_input.setHint("아이디(이메일)");
        register_pass_input.setHint("비밀번호(영문 숫자 2가지 이상, 8~15자리)");
        register_pass_check_input.setHint("비밀번호 확인");
        register_name_input.setHint("이름");
        register_phone_input.setHint("휴대폰 번호");
        register_phone_authentication.setText("인증하기");
        btn_register.setText("가입하기");
        register_inform.setText(register_inform_text);
        register_terms.setVisibility(View.GONE);
        register_copyright.setText("블루밍고 Co.,Ltd.All right reserved.");

        /*
        ArrayList<Pattern> terms = new ArrayList<Pattern>();
        terms.add(Pattern.compile("블루밍고 이용약관"));
        terms.add(Pattern.compile("전자금융거래이용약관"));
        terms.add(Pattern.compile("개인정보 수집 및 이용"));
        terms.add(Pattern.compile("개인정보 제공"));

        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher matcher, String s) {
                return s;
            }
        };

        for(int i=0; i<terms.size(); i++)
            Linkify.addLinks(register_inform, terms.get(i), null, null, transformFilter);
        */

        Spannable spannable = Spannable.Factory.getInstance().newSpannable(register_inform_text);

        for(int i=0; i<4; i++) {
            inform_spannable(categoryTerm[i], spannable, i);
        }

        register_inform.setText(spannable);
        register_inform.setHighlightColor(Color.TRANSPARENT);
        register_inform.setMovementMethod(LinkMovementMethod.getInstance());


        //그그그ㅡㅡㅡ뭐야 그거 회원가입 정보 보내기ㅣ
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RegisterCheck()) {
                    userVO.setUser_id(register_id_input.getText().toString());
                    userVO.setUser_password(register_pass_input.getText().toString());
                    userVO.setUser_name(register_name_input.getText().toString());
                    userVO.setUser_phone(Integer.parseInt(register_phone_input.getText().toString()));

                    //IDOverlapCheck();
                    Register();
                }
            }
        });

        register_phone_authentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String smsNum = register_phone_input.getText().toString();
            }
        });

    }

    public String getTerms() {
        return selectedTerm;
    }

    public boolean RegisterCheck() {
        Log.d(TAG, "Register Check");

        if(register_id_input.getText().toString().equals("")) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(register_pass_input.getText().toString().equals("")) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(register_pass_check_input.getText().toString().equals("")) {
            Toast.makeText(this, "비밀번호 확인을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!register_pass_input.getText().toString().equals(register_pass_check_input.getText().toString())) {
            Toast.makeText(this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(register_name_input.getText().toString().equals("")) {
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(register_phone_input.getText().toString().equals("")) {
            Toast.makeText(this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!isPhoneAuthentication) {
            Toast.makeText(this, "휴대폰 인증을 해주세요.", Toast.LENGTH_SHORT).show();
            return true;
        } else
            return true;
    }

    public void IDOverlapCheck() {
        // 아이디 중복 확인
        JSONObject userID = new JSONObject();
        try {
            userID.put("userId", userVO.getUser_id());

            NetWorkConnectionImpl network = new NetWorkConnectionImpl(new AsyncCallBack() {
                @Override
                public void onTaskDone(Object... params) {
                    if(params != null) {
                        //Log.d(TAG, params[0].toString());
                    }
                }
            }, RegisterActivity.this) {};
            network.setJsonData(userID);
            network.setMethod("android/idCheck",true,false,"POST");
            network.getNetWorkResult();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Register() {
        NetWorkConnectionImpl network = new NetWorkConnectionImpl(new AsyncCallBack() {
            @Override
            public void onTaskDone(Object... params) {
                Intent intent = new Intent(RegisterActivity.this, RegisterCompleteActivity.class);
                startActivity(intent);
                finish();
            }
        }, RegisterActivity.this) {};
        network.setJsonData(userVO.getJsonObject());
        network.setMethod("android/createUser",true,false,"POST");
        network.getNetWorkResult();
    }

    private void inform_spannable(final String str, Spannable spannable, int i) {
        int start, end;

        start = register_inform_text.indexOf(str);
        end = start + str.length();

        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if(register_terms.getVisibility() == View.GONE) {
                    register_terms.setVisibility(View.VISIBLE);
                    fmTerms.setViewVisibility(str);
                } else if(register_terms.getVisibility() == View.VISIBLE) {
                    register_terms.setVisibility(View.GONE);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.linkColor = 0xff548ff4;
                super.updateDrawState(ds);
            }
        }, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }
}
