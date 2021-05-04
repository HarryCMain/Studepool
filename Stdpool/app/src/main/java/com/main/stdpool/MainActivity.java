package com.main.stdpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button mDriver, mPassenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDriver = (Button) findViewById(R.id.driver);
        mPassenger = (Button) findViewById(R.id.passenger);


        mPassenger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LoginPassanger.class);
                startActivity(i);
                setContentView(R.layout.activity_login_passanger);
            }
        });

        mDriver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LoginDriver.class);
                startActivity(i);
                setContentView(R.layout.activity_login_driver);
            }
        });
    }

}
