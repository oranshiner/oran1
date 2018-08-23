package com.example.avi.advancedfragmentexample;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements Fragment1.IExternalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setTextInTextView() {
        TextView textView = (TextView) findViewById(R.id.text);

        // Means we're in a cellular (non tablet) mode
        if (textView == null){
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("key", "text");
            startActivity(intent);
        }
        else{
            // we're in a a Tablet mode
            textView.setText("Data has been loaded");
        }
    }
}
