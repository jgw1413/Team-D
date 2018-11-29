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

        if(getFileName.equals("")) {             // 아무것도 안적고 세이브 했을 때
            Intent intent = new Intent(Edit.this, Message.class);
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

                if(isAlreadyFile) {
                    Intent intent = new Intent(Edit.this, Message.class);
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
