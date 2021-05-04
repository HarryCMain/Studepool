package com.main.stdpool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadingPassenger extends AppCompatActivity {

    Integer PID;
    Intent intent;
    ProgressBar progressBar;
    String Result;
    Button BUTCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_passenger);
        BUTCheck = (Button) findViewById(R.id.BUTcheck);
        intent = getIntent();
        PID = intent.getIntExtra("UsrID", 0);
        Log.e("PID start", PID.toString());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        // Search s = new Search();
        // s.execute(PID.toString());


        BUTCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search s = new Search();
                s.execute(PID.toString());
            }
        });

    }


    public class Search extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        String PID;


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String z) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(LoadingPassenger.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(LoadingPassenger.this, z, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), ConfirmPassenger.class);
                Log.e("Final Search BID",Result);
                i.putExtra("BID", Result);
                startActivity(i);
                setContentView(R.layout.activity_confirm_passenger);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String PID = params[0];

            if (PID == null) {
                z = "Passing Error";
                Log.e("Null Error", z);
            } else {
                ResultSet rs = null;
                Connection connection = null;
                Statement statement = null;
                String query = "";
                query = "select bkno from booking where passno = '" + PID + "' and drvno is not null ;";
                Log.e("Confirm query", query);
                String z = "Incorrect Credentials";
                isSuccess = false;
                try {
                    connection = ConnectionClass.getConnection();
                    statement = connection.createStatement();
                    rs = statement.executeQuery(query);
                    rs.last();
                    Integer bid = rs.getInt("bkno");
                    Result = bid.toString();
                    Log.e("Result", Result);
                    if(Result!=null){
                        isSuccess=true;
                    }
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

