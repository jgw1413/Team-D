package com.example.administrator.simplememo;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import org.memo.pkg.R;

public class Edit extends Activity implements Variable {

    Button btn = null;
    boolean isOpenEdit = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        final Intent intent = getIntent();
        isOpenEdit = intent.getBooleanExtra("isOpenEdit", false);

        if( isOpenEdit ) {

            EditText etFileName = (EditText)findViewById(R.id.et_filename);
            EditText etMemo = (EditText)findViewById(R.id.et_edit);
            etFileName.setText(intent.getStringExtra("FILENAME"));
            etMemo.setText(intent.getStringExtra("FILECONTENT"));

        }

        ButtonProc(R.id.btn_save);
        ButtonProc(R.id.btn_menu);

    }

    public void ButtonProc(final int id) {
        btn = (Button)findViewById(id);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.btn_save:     // 저장하기
                        SaveProc();
                        break;
                    case R.id.btn_menu:     // 메인으로 돌아가기
//		        	finish();
                        Intent intent = new Intent(Edit.this, Memo.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    public void SaveProc() {        // 파일이름 검사 함수
        EditText et = (EditText)findViewById(R.id.et_filename);
        String getFileName = et.getText().toString();

        if(getFileName.equals("")) {             // 파일이름 않적었을 때
            Intent intent = new Intent(Edit.this, Message.class);
            intent.putExtra("MESSAGE", NO_NAME);
            startActivity(intent);
        }
        else {      // 파일이름 적었을 때
            int iFileLeng = fileList().length;      // 적은 문자의 길이를 넘겨줌

            if( iFileLeng != 0 ) {      // 문자 길이가 0이 아닐 때
                boolean isAlreadyFile = false;
                String[] sFileList = fileList();
                for(int i = 0 ; i < iFileLeng ; i++) {      // 같은 이름의 파일이 존재하는지 비교
                    if( sFileList[i].equals(getFileName) ) {
                        isAlreadyFile = true;
                        break;
                    }
                }

                if(isAlreadyFile) {     // 같은 이름의 파일이 존재 할 때
                    Intent intent = new Intent(Edit.this, Message.class);
                    intent.putExtra("MESSAGE", ALREADY_FILE);
                    startActivity(intent);      // 파일 저장 안하고 메세지 인텐트만 띄움
                }
                else {      // 같은 이름의 파일 없을 때
                    FileSave(getFileName);      // 파일 저장
                }
            }
            else {      // 아무 문자도 안썻을 때
                FileSave(getFileName);      // 파일 저장
            }
        }
    }

    public void FileSave(String sName) {        // 파일저장 함수
        try {
            EditText et = (EditText)findViewById(R.id.et_edit);
            byte[] buffer = stringToByte(et.getText().toString());
            FileOutputStream fos = openFileOutput(sName, Context.MODE_PRIVATE);
            fos.write(buffer);
            fos.flush();
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Edit.this, Message.class);
        intent.putExtra("MESSAGE", SAVE_COMPLETE);
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( data.getAction().equals("ACTION_A")) {
            finish();
        }
    }

    public byte[] stringToByte(String sBuffer) {
        byte[] bBuffer = new byte[sBuffer.length()];
        for(int i = 0 ; i < sBuffer.length() ; i ++)
            bBuffer[i]=(byte)sBuffer.charAt(i);
        return bBuffer;
    }
}
