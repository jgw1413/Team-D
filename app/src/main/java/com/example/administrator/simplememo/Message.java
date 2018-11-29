package com.example.administrator.simplememo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.memo.pkg.R;

public class Message extends Activity implements Variable{
    TextView tv = null;
    Button okBtn = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        final Intent intent = getIntent();
        final int getValue = intent.getIntExtra("MESSAGE", DEFAULT_VALUE);

        tv = (TextView)findViewById(R.id.tv_error);

        String sMsg = "";

        switch(getValue){       // 세이브 눌렀을 때
            case NO_NAME:
                sMsg="Empty file name.";
                break;
            case ALREADY_FILE:
                sMsg="The file has the same name.";
                break;
            case SAVE_COMPLETE:
                sMsg="Save Complete.";
                break;
            case DEL_COMPLETE:
                sMsg=intent.getStringExtra("DEL_OK")+" file DEL Complete.";
                break;
        }

        tv.setText(sMsg);

        okBtn = (Button)findViewById(R.id.btn_warningOk);
        okBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                switch(getValue){
                    case SAVE_COMPLETE:
//		        	setResult(RESULT_OK, intent.setAction("ACTION_A"));
                        Intent intent = new Intent(Message.this, Memo.class);
                        startActivity(intent);
                        break;
                    case NO_NAME:
                    case ALREADY_FILE:
                    case DEL_COMPLETE:
                        finish();
                        break;
                }
            }
        });
    }
}