package com.top_adv.myapplication1.Login;

import com.top_adv.myapplication1.DataVO.LoginVO;

/**
 * Created by sst on 2016-12-01.
 */
public interface LoginProcess {

    /**
     * WEB접속후 Login 권한값 받아옴
     * @param id
     * @param pass
     */
    public void checkLoginAuthorization();

    /**
     * SharedPreperence 값으로 저장된 LoginVO 리턴
     * @return
     */
    public LoginVO getAutoLoginVO();

    // 암호화 메소드
    public String passEncrypt(String pass, String key)throws Exception;
    // 복호화 메소드
    public String passDecrypt(String pass, String key)throws Exception;

    // SharePreperence 값으로 저장된 AutoLogin Boolean 값 리턴
    public Boolean getAutoLogin();
    // ShatePreperence 값으로 자동 로그인 기능을 위한 id,pass,boolean값 설정
    public void setAutoLogin(LoginVO loginVO);


    //NetWork Method
    public void getListData();
    public void getUserListData();



}
