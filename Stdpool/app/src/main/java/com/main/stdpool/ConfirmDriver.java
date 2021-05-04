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

public class ConfirmDriver extends AppCompatActivity {

    TextView textview, textview2;
    Intent intent;
    Integer DID;
    ProgressBar progressBar;
    String Result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_driver);

        textview = (TextView) findViewById(R.id.textView);
        textview2 = (TextView) findViewById(R.id.textView2);

        intent = getIntent();
        DID = intent.getIntExtra("UsrID",0);
        Log.e("DID",DID.toString());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Result = "Error";
        new Confirm().execute(DID.toString());

    }

    public class Confirm extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        String DID;
        String Result;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String z) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ConfirmDriver.this, z, Toast.LENGTH_SHORT).show();
            textview2.setText(z);
            if (isSuccess) {
                Toast.makeText(ConfirmDriver.this, z, Toast.LENGTH_SHORT).show();
                textview2.setText(z);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String DID = params[0];

            if (DID == null) {
                z = "Passing Error";
                Log.e("Null Error",z);
            } else {
                ResultSet rs = null;
                Connection connection = null;
                Statement statement = null;
                String query = "";
                query = "select * from booking where drvno = '" + DID + "' ;";
                Log.e("Confirm query",query);
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
                    Result = "Booking: " + bid + " Confirmed! \n Proceed to meet user:" + passno + " \n at " + origin + " \n For their Journey to " + destination + ".";
                    Log.e("Result",Result);
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
