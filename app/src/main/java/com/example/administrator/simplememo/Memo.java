package com.example.administrator.simplememo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import org.memo.pkg.R;

public class Memo extends Activity {
    /** Called when the activity is first created. */
    Button btn = null;
    Intent intent = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonProc(R.id.btn_new);
        ButtonProc(R.id.btn_open);
        ButtonProc(R.id.btn_exit);
    }

    public void ButtonProc(final int id){
        btn = (Button)findViewById(id);
        btn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.btn_new:      // 작성 화면으로 이동 Edit
                        intent = new Intent(Memo.this, Edit.class);
                        intent.putExtra("isOpenEdit", false);
                        startActivity(intent);
                        break;
                    case R.id.btn_open:     // 리스트 화면으로 이동 OpenList
                        intent = new Intent(Memo.this, OpenList.class);
                        startActivity(intent);
                        break;
                    case R.id.btn_exit:     // 앱 종료
                        finish();
                        break;
                }
            }
        });
    }
}
