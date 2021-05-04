package com.main.stdpool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConfirmPassenger extends AppCompatActivity {
    TextView textview, textview2;
    Intent intent;
    String BID;
    ProgressBar progressBar;
    String Result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_driver);

        textview = (TextView) findViewById(R.id.textView);
        textview2 = (TextView) findViewById(R.id.textView2);

        intent = getIntent();
        BID = intent.getStringExtra("BID");
        Log.e("BID",BID);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Result = "Error";
        new Confirm().execute(BID);

    }

    public class Confirm extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        String BID;
        String Result;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String z) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ConfirmPassenger.this, z, Toast.LENGTH_SHORT).show();
            textview2.setText(z);
            if (isSuccess) {
                Toast.makeText(ConfirmPassenger.this, z, Toast.LENGTH_SHORT).show();
                textview2.setText(z);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String BID = params[0];

            if (BID == null) {
                z = "Passing Error";
                Log.e("Null Error", z);
            } else {
                ResultSet rs = null;
                Connection connection = null;
                Statement statement = null;
                String query = "";
                query = "select * from booking where bkno = '" + BID + "' ;";
                Log.e("Confirm query", query);
                String z = "Incorrect Credentials";
                isSuccess = false;
                try {
                    connection = ConnectionClass.getConnection();
                    statement = connection.createStatement();
                    rs = statement.executeQuery(query);
                    rs.last();
                    Integer bid = rs.getInt("bkno");
                    Integer drvno = rs.getInt("drvno");
                    Integer passno = rs.getInt("passno");
                    String origin = rs.getString("origin");
                    String destination = rs.getString("destination");
                    Result = "Booking: " + bid + " Confirmed! \n Your Driver:" + drvno + " \n is on their way to meet you at " + origin + " \n For their Journey to " + destination + ".";
                    Log.e("Result", Result);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.e("ERRO1", e.getMessage());

                }


            }
            return Result;
        }
    }
}
