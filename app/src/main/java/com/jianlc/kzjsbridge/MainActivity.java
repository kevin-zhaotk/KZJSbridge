package com.jianlc.kzjsbridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jianlc.kzjsbridge.dproxy.DProxyHandler;
import com.jianlc.kzjsbridge.dproxy.IProxyInterface;

import java.lang.reflect.Proxy;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button openWeb;
    private Button test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openWeb = (Button) findViewById(R.id.openwebview);
        test = (Button) findViewById(R.id.button2);
        test.setOnClickListener(this);
        openWeb.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openwebview:
                Intent intent = new Intent(this, WebviewActivity.class);
                this.startActivity(intent);
                break;
            case R.id.button2:
                char[] buffer = {0x0505, 0x0a0a, 0x0505, 0x0a0a};
                //expendColumn(buffer, 2, 102);

                // doProxyMethod();

                print(1);
                print("1234567");
                print(new UserInfo("zhao", 18));
                break;
        }
    }

    private void doProxyMethod() {
        IProxyInterface pInstance = (IProxyInterface) Proxy.newProxyInstance(this.getClassLoader(), new Class[] {IProxyInterface.class}, new DProxyHandler());
        pInstance.getUid("kevin");
    }

    private <E> void print(E param) {
        Log.d("XXX", "--->print: " + param.toString());
        Log.d("XXX", param.getClass().getName());
        printValue(param);
    }


    private <T> void printValue(T value) {
        Log.d("XXX", value.getClass().getName());
    }
    public void expendColumn(char[] buffer, int columns, int slant) {


        int extension = 0;
        int shift = 0;
        if (slant - 100 >= 0) {
            extension = 8;
            shift = slant - 100;
        }
        if (extension <= 0) {
            return;
        }
        // CharArrayWriter writer = new CharArrayWriter();

        int charsPerColumn = buffer.length/columns;
        int columnH = charsPerColumn * 16;
        int afterColumns = columns * 8 + (shift > 0 ? (shift - 1 + columnH) : 0);
        char[] mBuffer = new char[afterColumns * charsPerColumn];
        Log.i("XXX", "--->charsPerColumn: " + charsPerColumn + "  columnH: " + columnH + "  afterColumns: " + afterColumns);
        // extension
        for (int i = 0; i < afterColumns; i++) {

            for (int j = 0; j < columnH; j++) {
                int rowShift = shift > 0 ? (shift + j - 1) : 0;

                if (i - rowShift < 0) {
                    continue;
                }
                int origin = (i - rowShift)/8;
                int remainder = (i - rowShift)%8;
                //Log.i("XXX", "--->rowShift: " + rowShift + "  origin: " + origin + "  remainder: " + remainder);
                // only remainder equals 0 means there is a origin value corresponding to new buffer
                //Log.i("XXX", "--->index = " + (origin * charsPerColumn + j/16));


                if (remainder == 0) {
                    int bit = 15 - j%16;
                    int index = (origin + j/16);
                    if (index >= buffer.length) {
                        continue;
                    }
                    char data = buffer[origin + j/16];
                    Log.i("XXX", "--->data: " + String.format("0x%x",(int)data) + " bit: " + bit);
                    if ((data & (0x001 << bit)) > 0) {


                        mBuffer[i * charsPerColumn + j/16] |= (0x001 << bit);
                    }

                }

            }
        }

    }
}
