package com.example.samsung.memoapptest01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mmMemoEdit = null;
    TextFileManager mTextFileManager = new TextFileManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mmMemoEdit = (EditText) findViewById(R.id.memo_edit);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            // 1. 파일에 저장된 메모 텍스트 파일 불러오기
            case R.id.load_btn: {
                String memoData = mTextFileManager.load();
                mmMemoEdit.setText(memoData);

                Toast.makeText(this, "불러오기 완료함!", Toast.LENGTH_LONG).show();
                break;
            }

            // 2. 에디트텍스트에 입력된 메모를 텍스트 파일로 저장하기
            case R.id.save_btn: {
                String memoData = mMemoEdit.getText().toString();
                mTextFileManager.save(memoData);
                mMemoEdit.setText("");

                Toast.makeText(this, "저장 완료함!", Toast.LENGTH_LONG).show();
                break;
            }

            // 3. 저장된 메모 파일 삭제하기
            case R.id.delete_btn: {
                mTextFileManager.delete();
        mMemoEdit.setText("");

        Toast.makeText(this, "삭제 완료함!", Toast.LENGTH_LONG).show();
        }
        }
        }
        }