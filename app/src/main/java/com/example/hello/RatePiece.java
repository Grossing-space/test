package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RatePiece extends AppCompatActivity  {
    TextView titlepiece,resultpiece;
    EditText inputpiece;
    float rate;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_piece);
//        Handler handler1=new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                if(msg.what==1){
//                    String ratetitle=(String)msg.obj;
//                    titlepiece=findViewById(R.id.title_piece);
//                    titlepiece.setText(ratetitle);
//                }
//                super.handleMessage(msg);
//            }
//        };
//
//        Handler handler2=new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                if(msg.what==2){
//                    String ratedetail=(String)msg.obj;
//                    rate=Float.parseFloat(ratedetail);
//                }
//                super.handleMessage(msg);
//            }
//        };
        Intent intent=getIntent();
        title= intent.getStringExtra("title");
//        rate=intent.getFloatExtra("detail",0.0f);
        titlepiece=findViewById(R.id.title_piece);
        titlepiece.setText(title);

    }

    public void piece(View piece){
        Intent intent=getIntent();
        String rate1=intent.getStringExtra("detail");
        rate=Float.parseFloat(String.valueOf(rate1));
        inputpiece=findViewById(R.id.input_piece);
        float input =Float.parseFloat(inputpiece.getText().toString());
        float result=input*rate;
        resultpiece=findViewById(R.id.result_piece);
        resultpiece.setText(String.valueOf(result));
    }


}