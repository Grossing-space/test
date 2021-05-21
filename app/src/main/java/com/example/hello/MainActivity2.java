package com.example.hello;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.time.LocalDate;

public class MainActivity2 extends AppCompatActivity implements Runnable {
    EditText money;
    TextView result;
    private static final String TAG = "MainActivity2";
    float dollarRate =0.0f;
    float poundRate =0.2f;
    float wonRate =150.3f;
    int time=0;
    Handler handler;
    @RequiresApi(api = Build.VERSION_CODES.O)
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
        String updateStr=SharedPreferences.getString("update_str","");
        Log.i(TAG,"oncreate:dollar="+dollarRate);
        Log.i(TAG,"oncreate: updatestr="+updateStr);

        LocalDate today=LocalDate.now();
        if(updateStr.equals(today.toString())){
            Log.i(TAG,"oncreate:日期相等，不再从网络获取数据");
        }
        else {
            Thread t=new Thread(this);
            t.start();

        }

        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    String val = (String)msg.obj;
                    Log.i(TAG,"handMessage:val="+val);
                    Toast.makeText(MainActivity2.this,"数据已更新",Toast.LENGTH_SHORT).show();
                   // result.setText(val);
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        if(time==0){
        Log.i(TAG,"run:....");
        URL url = null;
//        try {
//            url = new URL("https://www.usd-cny.com/icbc.htm");
//            HttpURLConnection http=(HttpURLConnection)url.openConnection();
//            InputStream in = http.getInputStream();
//
//            String html = inputStream2String(in);
//            Log.i(TAG,"run: html=" +html);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            Document doc= Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"title: "+doc.title());
            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
//            for(Element tr : trs){
//                Elements tds = tr.getElementsByTag("td");
//                if(tds.size()>0){
//                    String str = tds.get(0).text();
//                    String val = tds.get(5).text();
//                    Log.i(TAG,"run: td1= "+tds.get(0).text() +"=>"+tds.get(5).text());
//                }
//            }
            Element dd =doc.select("body > section > div > div > article > table > tbody > tr:nth-child(27) > td:nth-child(6)").first();
            Log.i(TAG,"run: 美元="+ dd.text());
            dollarRate=Float.parseFloat(dd.text());
            Element pp =doc.select("body > section > div > div > article > table > tbody > tr:nth-child(9) > td:nth-child(6)").first();
            Log.i(TAG,"run: 英镑="+ pp.text());
            poundRate=Float.parseFloat(pp.text());
            Element ww =doc.select("body > section > div > div > article > table > tbody > tr:nth-child(14) > td:nth-child(6)").first();
            Log.i(TAG,"run: 韩元="+ ww.text());
            wonRate=Float.parseFloat(ww.text());
            timecheck();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }}
    public void timecheck(){
        time=1;
        Handler handlertime= new Handler();
        handlertime.postDelayed(this, 24*60*60);
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
    public void setHandler(Handler handler){
        this.handler = handler;
    }
}