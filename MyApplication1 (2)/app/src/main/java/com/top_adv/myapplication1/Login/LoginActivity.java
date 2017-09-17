package com.top_adv.myapplication1.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.top_adv.myapplication1.DataVO.ListVO;
import com.top_adv.myapplication1.DataVO.LoginResultVO;
import com.top_adv.myapplication1.DataVO.LoginVO;
import com.top_adv.myapplication1.DataVO.OrderVO;
import com.top_adv.myapplication1.DataVO.ParcelList;
import com.top_adv.myapplication1.DataVO.UserVO;
import com.top_adv.myapplication1.MainViewPage;
import com.top_adv.myapplication1.NetWork.AsyncCallBack;
import com.top_adv.myapplication1.NetWork.JsonParsing;
import com.top_adv.myapplication1.NetWork.NetWorkConnectionImpl;
import com.top_adv.myapplication1.R;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * LoginActivity.
 * LoginVO 생성후 WEB에 json.url형태로 전달.
 * Created by sst on 2016-11-09.
 */
public class LoginActivity extends AppCompatActivity implements LoginProcess {

    private EditText login_id_input;
    private EditText login_pass_input;
    private CheckBox login_auto_check;
    private Button btn_login_login;
    private TextView login_id_search;
    private TextView login_user_register;
    private TextView btn_login_notlogin;
    private UserVO userVo;

    private LoginVO loginVO;
    public ArrayList<ListVO> list;
    private ListVO listVO;
    public OrderVO orderVO;
    private ParcelList pLists;
    private static String ADVVO_TO_MAIN_VIEW_PAGE = "main_view_page.advVO";

    private JsonParsing jsonParsing;

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userVo = new UserVO();
        loginVO = new LoginVO();
        login_id_input = (EditText) findViewById(R.id.login_id_input);
        login_id_input.setHint("아이디");
        login_pass_input = (EditText) findViewById(R.id.login_pass_input);
        login_pass_input.setHint("비밀번호");
        login_auto_check = (CheckBox) findViewById(R.id.login_auto_check);
        login_auto_check.setText("자동 로그인");
        btn_login_login = (Button) findViewById(R.id.btn_login_login);
        btn_login_login.setText("로그인");
        login_id_search = (TextView) findViewById(R.id.login_id_search);
        login_id_search.setText("아이디/비밀번호 찾기");
        login_user_register = (TextView) findViewById(R.id.login_user_register);
        login_user_register.setText("회원가입");
        btn_login_notlogin = (TextView) findViewById(R.id.btn_login_notlogin);
        btn_login_notlogin.setText("비회원으로");

        setting = getSharedPreferences("setting", 0);
        editor = setting.edit();

        if(setting.getBoolean("isAutoLoginEnabled", false)) {
            getListData();
        }
    }

    public void onButtonClick_login(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login:
                userVo.setLogin_account(1);
                // 로그인 버튼 누를시에 UserVO에 데이터를 남기고. 이는 앱 실행동안만 유지되어야 한다.
                // 자동 로그인 체크를 할 경우 로그인 프로세스상에서 시스템 값으로 id.pass저장, auto플래그값 저장.
                // 로그인시에 웹에서 관련 데이터를 가져오는 기능또한 있어야겠다.

                String userID = login_id_input.getText().toString();
                Integer userPass = Integer.parseInt(login_pass_input.getText().toString());

                if(userID.length() <= 0)
                    Toast.makeText(LoginActivity.this, "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
                else if(userPass.toString().length() <= 0)
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                else {
                    loginVO.setUser_id(userID);
                    loginVO.setUser_password(userPass);

                    checkLoginAuthorization();
                }

                break;
            case R.id.btn_login_notlogin:
                // 비회원으로 로그인하기.
                // 비회원 로그인시 user정보 없이 로그인됨으로 list정보만 받아와진다.
                // 16.12.14 테스트용으로 1번user정보로 로그인, list+user 정보 받아오기

                /*loginVO = new LoginVO();
                loginVO.setUser_id("hong@naver.com");
                int pass = 123456;
                loginVO.setUser_password(pass);

                checkLoginAuthorization();*/

                getListData();



                break;
            case R.id.login_auto_check:
                // 자동 로그인은 앱자체 저장시켜서 계속 유지시켜야 한다.
                // 즉 앱이 꺼져도 남을 수 있도록 해줘야 한다. 일단은 임시적으로 설정.
                // 또한 이 값을 통해 앱 실행, 로그인창에 체크박스는 체크상태로 유지되어야 한다.
                // 해당 유저의 ID와 PASSWORD 또한 저장되어 있어야 한다.

                if(!login_auto_check.isChecked()) {
                    editor.putBoolean("isAutoLoginEnabled", true);
                    editor.commit();
                }
                else {
                    editor.putBoolean("isAutoLoginEnabled", false);
                    editor.commit();
                }

                break;
            case R.id.login_id_search:
                IdSearch();
                // 웹 페이지로 넘어가는 방식이 그나마 유용 할 듯 하다.
                // 앱 내부에서 처리하기엔 효율성이 떨어질 듯 하다.
                break;
            case R.id.login_user_register:
                UserRegister();
                // 회원가입 프로세스로 넘어간다.
                break;
        }
    }

    public void IdSearch() {

    }

    public void UserRegister() {
        // 회원가입 창으로 넘어가도록 한다.
        // 해당 액티비티or프래그먼트에서는 UserVO를 생성하여 웹으로 보낸다.
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void UserLogin() {
        // 입력받은 ID, PASSWORD 로 웹과 연결하여 로그인 허가를 받아온다. 이때 UserVO에 account를 1로 설정해준다.(기본값 0)
        // 불가일 경우 팝업메시지를 띄우고 로그인을 다시 진행한다.
        // 5번 이상 시도될 경우 경고 메시지를 표출한다.
    }

    @Override
    public void checkLoginAuthorization() {
        /*LoginNetWork loginNetWork = new LoginNetWork(new AsyncCallBack() {
            @Override
            public void onTaskDone(Object... params) {
                if (params != null) {
                    loginVO.setAuthor((String) params[0]);
                    if (loginVO.getAuthor().equals("approve")) {
                        getListData();
                    }
                }
            }
        }, LoginActivity.this);

        loginNetWork.setLoginVO(loginVO);
        loginNetWork.getResult();
        */
        jsonParsing = new JsonParsing(LoginResultVO.class);

        NetWorkConnectionImpl network = new NetWorkConnectionImpl(new AsyncCallBack() {
            @Override
            public void onTaskDone(Object... params) {
               //try {
                    if (params != null) {
                        //ArrayList<LoginResultVO> resultVO = jsonParsing.getResult(params);
                        // loginVO 데이터를 보냄=>데이터 체크(loginCheck)=>맞으면 approve 오고 이후
                        loginVO.setAuthor(params[0].toString());
                        if (loginVO.getAuthor().equals("success")) {
                            getListData();
                        } else if (loginVO.getAuthor().equals("fail")) {
                            Toast.makeText(getBaseContext(), "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "왜 안될까.......", Toast.LENGTH_SHORT).show();
                    }
                /*} catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }*/
            }
        }, LoginActivity.this) {};
        network.setJsonData(loginVO.getJsonObject());
        network.setMethod("login/loginProcess",true,false,"POST");
        network.getNetWorkResult();
    }

    @Override
    public void getListData() {
        /*ListNetWork listNetWork = new ListNetWork(new AsyncCallBack() {
            @Override
            public void onTaskDone(Object... params) {
                if (params != null) {
                    list = (ArrayList<ListVO>) params[0];
                    if (list != null) {
                        Intent intent = new Intent(getApplicationContext(), MainViewPage.class);
                        pLists = new ParcelList();
                        pLists.setList_lists(list);
                        intent.putExtra(ADVVO_TO_MAIN_VIEW_PAGE, pLists);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                }
            }
        }, LoginActivity.this);
        listNetWork.getResult();*/
        listVO = new ListVO();
        jsonParsing = new JsonParsing(ListVO.class);

        NetWorkConnectionImpl network = new NetWorkConnectionImpl(new AsyncCallBack() {
            @Override
            public void onTaskDone(Object... params) {
                try {
                    if(params != null) {
                        list = jsonParsing.getResult(params);

                        // Trade_status가 광고중인 제품만 보이기
                        /*
                        Iterator<ListVO> iter = list.iterator();
                        while(iter.hasNext()) {
                            ListVO vo = iter.next();
                            if(!vo.getTrade_status().equals("광고중"))
                                iter.remove();
                        }
                        */

                        for(int z=0; z<list.size(); z++){
                            Log.v("onTaskDone","list data : "+list.get(z).getAdv_title());
                        }
                        if (list != null) {
                            Intent intent = new Intent(getApplicationContext(), MainViewPage.class);
                            pLists = new ParcelList();
                            pLists.setList_lists(list);
                            intent.putExtra(ADVVO_TO_MAIN_VIEW_PAGE, pLists);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        }, LoginActivity.this) {};
        network.setMethod("android/getAdvList",false,false,"GET");
        network.getNetWorkResult();
    }

    @Override
    public void getUserListData() {

    }

    @Override
    public LoginVO getAutoLoginVO() {
        return null;
    }

    @Override
    public String passEncrypt(String pass, String key) throws Exception {
        return null;
    }

    @Override
    public String passDecrypt(String pass, String key) throws Exception {
        return null;
    }

    @Override
    public Boolean getAutoLogin() {
        return null;
    }

    @Override
    public void setAutoLogin(LoginVO loginVO) {

    }


}
