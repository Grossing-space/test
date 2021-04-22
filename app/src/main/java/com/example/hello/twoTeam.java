package com.example.hello;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class twoTeam extends AppCompatActivity {
    private static final String TAG = "score";
    int scorea=0;
    int scoreb=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_team);
    }
        private void showA(){
            Log.i(TAG,"show: score="+scorea);
            TextView show=findViewById(R.id.scorea);
            show.setText(String.valueOf(scorea));
        }
        public void btn3a(View v){
            scorea+=3;
            showA();
        }
        public void btn2a(View v){
            scorea+=2;
            showA();
        }
        public void btn1a(View v){
            scorea+=1;
            showA();
        }
        private void showB(){
            Log.i(TAG,"show: score="+scoreb);
            TextView show=findViewById(R.id.scoreb);
            show.setText(String.valueOf(scoreb));
        }
        public void btn3b(View v){
            scoreb+=3;
            showB();
        }
        public void btn2b(View v){
            scoreb+=2;
            showB();
        }
        public void btn1b(View v){
            scoreb+=1;
            showB();
        }
        public void reset(View v){
            scorea=0;
            scoreb=0;
            showA();
            showB();
        }

}