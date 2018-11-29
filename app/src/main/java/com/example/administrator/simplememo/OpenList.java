package com.example.administrator.simplememo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OpenList extends ListActivity implements Variable{
    /** Called when the activity is first created. */
    String[] mFileList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if( fileList().length != 0 ) {
            mFileList = fileList();
            this.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mFileList));

        }
        else {

        }




    }

    byte[] buffer;
    String[] sReadFile = new String[2];
    public void onListItemClick(ListView lv, View v, int position, long id){

        setReadFile(position);

        Intent intent = new Intent(OpenList.this, Read.class);
        intent.putExtra("NAME", sReadFile[FILENAME]);
        intent.putExtra("CONTENT", sReadFile[FILECONTENT]);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if( data.getAction().equals("RESET")){
            mFileList = fileList();
            this.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mFileList));

            //���� ����� Ȯ�ν����ֱ�.
            Intent intent = new Intent(OpenList.this, Message.class);
            intent.putExtra("MESSAGE", DEL_COMPLETE);
            intent.putExtra("DEL_OK", sReadFile[FILENAME]);
            startActivity(intent);
        }

    }


    public void setReadFile(int pos){
        sReadFile[FILENAME] = mFileList[pos];
        try {
            FileInputStream fis = openFileInput(mFileList[pos]);
            buffer = new byte[fis.available()];
            fis.read(buffer, 0, fis.available());
            sReadFile[FILECONTENT] = byteToString(buffer);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String byteToString(byte buffer[]) {
        char[] cBuffer = new char[buffer.length];
        for(int i = 0 ; i < buffer.length ; i ++)
            cBuffer[i]=(char)buffer[i];
        return new String(cBuffer);
    }
}
