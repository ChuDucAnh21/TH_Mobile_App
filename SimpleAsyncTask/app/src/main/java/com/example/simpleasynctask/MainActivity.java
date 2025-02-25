package com.example.simpleasynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private WeakReference<TextView> mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void SimpleAsyncTask(TextView tv) {
        mTextView = new WeakReference<>(tv);
    }
    public void startTask(View view) {
    }

    public class MyAsyncTask
            extends AsyncTask <String,Integer, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            return null;
        }
    }
    public class SimpleAsyncTask extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... voids) {
            Random r = new Random();
            int n = r.nextInt(11);
            int s = n * 200;

            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Awake at last after sleeping for " + s + " milliseconds!";
        }
    }


}