package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Config extends AppCompatActivity {
    private static final String TAG = "openConfig";
    EditText dollar_show,pound_show,won_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent intent=getIntent();
        float dollar2=intent.getFloatExtra("dollar_rate",0.0f);
        float pound2=intent.getFloatExtra("pound_rate",0.0f);
        float won2=intent.getFloatExtra("won_rate",0.0f);

        Log.i(TAG,"onCreate: dollar2="+dollar2);
        Log.i(TAG,"onCreate: pound2="+pound2);
        Log.i(TAG,"onCreate: won2="+won2);

        dollar_show=findViewById(R.id.dollar);
        dollar_show.setText(String.valueOf(dollar2));
        pound_show=findViewById(R.id.pound);
        pound_show.setText(String.valueOf(pound2));
        won_show=findViewById(R.id.won);
        won_show.setText(String.valueOf(won2));
    }
    public void Save(View btn){
        Intent intent=getIntent();
        float new_dollar=Float.parseFloat(dollar_show.getText().toString());
        float new_pound=Float.parseFloat(pound_show.getText().toString());
        float new_won=Float.parseFloat(won_show.getText().toString());

        Log.i(TAG,"new:"+new_dollar);
        Log.i(TAG,"new:"+new_pound);
        Log.i(TAG,"new:"+new_won);
        Bundle bdl= new Bundle();
        bdl.putFloat("new_dollar",new_dollar);
        bdl.putFloat("new_pound",new_pound);
        bdl.putFloat("new_won",new_won);
        intent.putExtras(bdl);

        setResult(2,intent);
        finish();
    }
}