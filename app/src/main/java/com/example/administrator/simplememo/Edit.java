package com.example.administrator.simplememo;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
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
                    case R.id.btn_save:
                        SaveProc();
                        break;
                    case R.id.btn_menu:
//		        	finish();
                        Intent intent = new Intent(Edit.this, Memo.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    public void SaveProc(){
        EditText et = (EditText)findViewById(R.id.et_filename);
        String getFileName = et.getText().toString();

        if(getFileName.equals("")) {             // 파일이름 안적고 세이브 했을 때
            //Intent intent = new Intent(Edit.this, Message.class);
            Intent intent = new Intent();
            intent.setAction("andbook.example.implicitintents.TEST1");
            intent.putExtra("MESSAGE", NO_NAME);
            startActivity(intent);
        }
        else {      // 한글자라도 적었을 때
            int iFileLeng = fileList().length;

            if( iFileLeng != 0 ) {
                boolean isAlreadyFile = false;
                String[] sFileList = fileList();
                for(int i = 0 ; i < iFileLeng ; i++) {
                    if( sFileList[i].equals(getFileName) ) {
                        isAlreadyFile = true;
                        break;
                    }
                }

                if(isAlreadyFile) {     // 같은 이름의 파일이 존재 할 때
                    //Intent intent = new Intent(Edit.this, Message.class);
                    Intent intent = new Intent();
                    intent.setAction("andbook.example.implicitintents.TEST1");
                    intent.putExtra("MESSAGE", ALREADY_FILE);
                    startActivity(intent);
                }
                else {
                    FileSave(getFileName);
                }
            }
            else {
                FileSave(getFileName);
            }
        }
    }

    public void FileSave(String sName) {
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
        //Intent intent = new Intent(Edit.this, Message.class);
        Intent intent = new Intent();
        intent.setAction("andbook.example.implicitintents.TEST1");
        intent.putExtra("MESSAGE", SAVE_COMPLETE);
        startActivityForResult(intent, 1);      // 저장한 내용 결과 값 리턴
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                //if (data.getAction().equals("ACTION_A")) {
                //finish();
                //}
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                //만약 반환값이 없을 경우의 코드를 여기에 작성하세요.
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        }
    }


    public byte[] stringToByte(String sBuffer) {
        byte[] bBuffer = new byte[sBuffer.length()];
        for(int i = 0 ; i < sBuffer.length() ; i ++)
            bBuffer[i]=(byte)sBuffer.charAt(i);
        return bBuffer;
    }
}
