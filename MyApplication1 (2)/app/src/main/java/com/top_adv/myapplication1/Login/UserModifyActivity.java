package com.top_adv.myapplication1.Login;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.top_adv.myapplication1.R;

/**
 * Created by EunBin Lee on 2017-04-10.
 */

public class UserModifyActivity extends AppCompatActivity {

    private TextView modify_pass_now;
    private EditText modify_pass_now_edit;
    private TextView modify_pass_new;
    private EditText modify_pass_new_edit;
    private TextView modify_pass_check;
    private EditText modify_pass_check_edit;
    private Button btn_modify_pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pass_modify);

        modify_pass_now = (TextView) findViewById(R.id.modify_pass_now);
        modify_pass_now_edit = (EditText) findViewById(R.id.modify_pass_now_edit);
        modify_pass_new = (TextView) findViewById(R.id.modify_pass_new);
        modify_pass_new_edit = (EditText) findViewById(R.id.modify_pass_new_edit);
        modify_pass_check = (TextView) findViewById(R.id.modify_pass_check);
        modify_pass_check_edit = (EditText) findViewById(R.id.modify_pass_check_edit);
        btn_modify_pass = (Button) findViewById(R.id.btn_modify_pass);

        modify_pass_now.setText("현재 비밀번호");
        modify_pass_new.setText("변경 비밀번호");
        modify_pass_check.setText("변경 비밀번호 확인");
        btn_modify_pass.setText("수정하기");

        btn_modify_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modify_pass_new.getText().equals(modify_pass_check.getText())) {
                    // 변경 비번 저장
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UserModifyActivity.this);
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.setMessage("현재 비밀번호 또는 변경 비밀번호가 적절하지 않습니다.");
                    dialog.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);         //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);         //홈 아이콘을 숨김처리합니다.
        actionBar.setElevation(0);                          //그림자 없애기.
        actionBar.setBackgroundDrawable(new ColorDrawable(0x00000000));

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.action_bar_user, null);

        TextView title = (TextView) actionbar.findViewById(R.id.action_bar_user_name);
        title.setText("이름");

        actionBar.setCustomView(actionbar);
        return true;
    }

    public void userTopClick (View view) {
        switch (view.getId()) {
            case R.id.btn_user_back:
                finish();
                break;
        }
    }
}
