package com.example.hello;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity implements Runnable {
    EditText money;
    TextView result;
    private static final String TAG = "MainActivity2";
    float dollarRate =0.0f;
    float poundRate =0.2f;
    float wonRate =150.3f;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        money=findViewById(R.id.inputmoney);
        result=findViewById(R.id.result);
        SharedPreferences SharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);

        dollarRate= SharedPreferences.getFloat("dollarrate",0.0f);
        poundRate= SharedPreferences.getFloat("poundrate",0.0f);
        wonRate= SharedPreferences.getFloat("wonrate",0.0f);

        Thread t=new Thread(this);
        t.start();

        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    String val = (String)msg.obj;
                    Log.i(TAG,"handMessage:val="+val);
                    result.setText(val);
                }
                super.handleMessage(msg);
            }
        };
    }

    public void click(View btn){
        float r=0.0f;
        if(btn.getId()==R.id.dollar){
            r=dollarRate;
        }
        else if(btn.getId()==R.id.pound){
            r=poundRate;
        }
        else {
            r=wonRate;
        }
        String str=money.getText().toString();
        if(str!=null&&str.length()>0){
            float re=Float.parseFloat(str)*r;
            result.setText(String.valueOf(re));
        }
        else{
            Toast.makeText(this,"请输入人民币金额",Toast.LENGTH_SHORT).show();
        }
    }
    public void openConfig(View btn) {
        Intent config = new Intent(this, Config.class);
        config.putExtra("dollar_rate", dollarRate);
        config.putExtra("pound_rate", poundRate);
        config.putExtra("won_rate", wonRate);

        Log.i(TAG, "openConfig:dollarRate:" + dollarRate);
        Log.i(TAG, "openConfig:poundRate:" + poundRate);
        Log.i(TAG, "openConfig:wonRate:" + wonRate);
        startActivityForResult(config, 1);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(requestCode==1 && resultCode==2){
            Bundle bundle= data.getExtras();
            dollarRate= bundle.getFloat("new_dollar",0.0f);
            poundRate= bundle.getFloat("new_pound",0.0f);
            wonRate= bundle.getFloat("new_won",0.0f);

            SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putFloat("dollarrate",dollarRate);
            editor.putFloat("poundrate",poundRate);
            editor.putFloat("wonrate",wonRate);
            editor.apply();

            Log.i(TAG, "onActivityResult:dollarRate:" + dollarRate);
            Log.i(TAG, "onActivityResult:poundRate:" + poundRate);
            Log.i(TAG, "onActivityResult:wonRate:" + wonRate);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        Log.i(TAG,"run:....");
        URL url = null;
        try {
            url = new URL("https://www.usd-cny.com/icbc.htm");
            HttpURLConnection http=(HttpURLConnection)url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(TAG,"run: html=" +html);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private String inputStream2String(InputStream inputStream) throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out =new StringBuilder();
        Reader in =new InputStreamReader(inputStream,"gb2312");
        while (true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}