package com.example.administrator.simplememo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.btn_new :
                intent = new Intent(this, Edit.class );
                intent.putExtra("수 정", false);
                startActivity(intent);
                break;
            case R.id.btn_open:
                intent = new Intent(this, OpenList.class);
                startActivity(intent);
                break;
            case R.id.btn_exit:
                finish();
                break;
        }
    }
}
