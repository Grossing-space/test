
package com.example.hello;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);


        EditText tem = findViewById(R.id.tem);
        String str = tem.getText().toString();

        Button btn = findViewById(R.id.sub);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double t=Double.parseDouble(str);
            }
        });

    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: 123");
    }
}