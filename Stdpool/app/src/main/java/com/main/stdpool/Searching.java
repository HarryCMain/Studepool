package com.main.stdpool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Searching extends AppCompatActivity {

   Integer DID;
   Intent intent;
   ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        intent = getIntent();
        DID = intent.getIntExtra("UsrID",0);
        Log.e("DID start",DID.toString());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Search s = new Search();
        s.execute();

    }

    public class Search extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String z) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Searching.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(Searching.this, z, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), ConfirmDriver.class);
                Log.e("Final Search DID",DID.toString());
                i.putExtra("UsrID", DID);
                startActivity(i);
                setContentView(R.layout.activity_confirm_driver);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            if (DID == null ) {
                z = "Passing Error";
            } else {
                isSuccess = SRH(DID);
                if (isSuccess = true) {
                    z = "Search Successful";
                } else {
                    z = "haha nope!";
                }

            }
            return z;
        }

        public boolean SRH(Integer DID) {
            ResultSet rs = null;
            Connection connection = null;
            Statement statement = null;
            String query = "";
            query = "update booking set drvno = '"+ DID +"' where drvno is null and passno is not null;";
            boolean successful = false;
            try {
                connection = ConnectionClass.getConnection();
                statement = connection.createStatement();
                statement.executeUpdate(query);
                successful = true;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                Log.e("ERRO1", e.getMessage());
            }
            return successful;
        }

    }
}
