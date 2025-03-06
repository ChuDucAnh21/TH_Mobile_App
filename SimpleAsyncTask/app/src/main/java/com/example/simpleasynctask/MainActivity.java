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
import java.net.URL;
import java.util.Random;
private class DownloadFilesTask extends AsyncTask<URL, Integer, Long>
public class MainActivity extends AppCompatActivity {

//    private static final String TEXT_STATE = "currentText";
//    private TextView mTextView = null;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mTextView = (TextView) findViewById(R.id.textView1);
//        if(savedInstanceState!=null){
//            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }
}
//
//    public void startTask (View view) {
//        mTextView.setText(R.string.napping);
//    //    new SimpleAsyncTask(mTextView).execute();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(TEXT_STATE, mTextView.getText().toString());
//    }
