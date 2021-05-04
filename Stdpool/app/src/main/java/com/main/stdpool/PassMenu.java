package com.main.stdpool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PassMenu extends AppCompatActivity {

    Button GoBUT;
    EditText StLCN,EndLCN;
    ProgressBar progressBar;
    Integer UID;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_menu);
        int PLACE_PICKER_REQUEST = 1;
        int REQUEST_PLACE_PICKER = 1;
        GoBUT = (Button) findViewById(R.id.GoBUT);
        intent = getIntent();
        UID = intent.getIntExtra("UsrID",0);
        StLCN = (EditText) findViewById(R.id.StLCN);
        EndLCN = (EditText) findViewById(R.id.EndLCN);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        //String Uname = "test@passenger.com";
        Log.e("UsrID", String.valueOf(UID));
        //String PWD = "testpass";


        GoBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Process p = new Process();
                p.execute(UID.toString());
            }
        });
    }



    public class Process extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        Integer UsrID = UID;
        String St = StLCN.getText().toString();
        String End = EndLCN.getText().toString();

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String z) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(PassMenu.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(PassMenu.this, z, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), LoadingPassenger.class);
                i.putExtra("UsrID",UsrID);
                startActivity(i);
                setContentView(R.layout.activity_loading_passenger);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            if (UsrID.equals("") || St.trim().equals("") || End.trim().equals("") ) {
                z = "Please enter Values";
            } else {
                isSuccess = CreateBooking(UsrID,St,End);
                if (isSuccess = true) {
                    z = "Booking Placed";

                } else {
                    z = "haha nope!";
                }

            }
            return z;
        }

        public boolean CreateBooking(Integer UsrID, String St, String End) {
            ResultSet rs = null;
            Connection connection = null;
            Statement statement = null;
            String query = "";
            query = "insert into booking values (NULL,NULL, '" + UsrID + "','" + St + "','" + End + "');";
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

