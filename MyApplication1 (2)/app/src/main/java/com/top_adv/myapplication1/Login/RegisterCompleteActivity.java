package com.top_adv.myapplication1.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.top_adv.myapplication1.R;

/**
 * Created by EunBin Lee on 2017-04-21.
 */

public class RegisterCompleteActivity extends AppCompatActivity {

    TextView register_complete;
    Button btn_register_complete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_complete);

        register_complete = (TextView) findViewById(R.id.register_complete);
        btn_register_complete = (Button) findViewById(R.id.btn_register_complete);

        register_complete.setText("회원가입 완료!");
        btn_register_complete.setText("로그인 바로 가기");

        btn_register_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
