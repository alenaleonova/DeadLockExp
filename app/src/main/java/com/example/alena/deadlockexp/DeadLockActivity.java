package com.example.alena.deadlockexp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DeadLockActivity extends ActionBarActivity {
    final String TAG = "Activity";
    final Context context = this;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead_lock);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dead_lock, menu);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new Task1().execute("");
                new Task2().execute("");
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Task1 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            int i;
            for (i = 0; i < 5; i++) {
               notSafeMethod1();
            }
            return "";
        }
    }

    private class Task2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            int i;
            for (i = 0; i < 5; i++) {
                notSafeMethod2();
            }
            return "";
        }
    }

    private void notSafeMethod1() {
        synchronized (String.class) {
            Log.i(TAG, "Method1: lock on String.class object. About to lock Integer.class \n");

            synchronized (Integer.class) {
                Log.i(TAG, "Method1: lock on Integer.class object \n");
            }
        }
    }

    private void notSafeMethod2() {
        synchronized (Integer.class) {
            Log.i(TAG, "Method2: lock on Integer.class object. About to lock String.class \n");

            synchronized (String.class) {
                Log.i(TAG, "Method2: lock on String.class object \n");
            }
        }
    }
}