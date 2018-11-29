package com.example.administrator.simplememo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.memo.pkg.R;

public class Read extends Activity implements Variable{

    String sGetValue[] = new String[2];

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read);

        final Intent intent = getIntent();
        sGetValue[FILENAME] = intent.getStringExtra("NAME");
        sGetValue[FILECONTENT] = intent.getStringExtra("CONTENT");
        TextView tvName = (TextView)findViewById(R.id.tv_readFilename);
        tvName.setText("Open file name : [ "+sGetValue[FILENAME]+" ]");
        TextView tvContent = (TextView)findViewById(R.id.tv_memo);
        tvContent.setText(sGetValue[FILECONTENT]);


        Button btnDel = (Button)findViewById(R.id.btn_del);
        btnDel.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                deleteFile(sGetValue[FILENAME]);
                setResult(RESULT_OK, intent.setAction("RESET"));
                finish();
            }

        });


        Button btnEdit = (Button)findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Read.this, Edit.class);
                intent.putExtra("isOpenEdit", true);
                intent.putExtra("FILENAME", sGetValue[FILENAME]);
                intent.putExtra("FILECONTENT", sGetValue[FILECONTENT]);
                startActivity(intent);
            }

        });
    }

}