package com.jianlc.kzjsbridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button openWeb;
    private Button test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openWeb = (Button) findViewById(R.id.openwebview);
        openWeb.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openwebview:
                Intent intent = new Intent(this, WebviewActivity.class);
                this.startActivity(intent);
                break;
        }
    }
}
