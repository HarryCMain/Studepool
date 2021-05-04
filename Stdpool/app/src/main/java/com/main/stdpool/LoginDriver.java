package com.main.stdpool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDriver extends AppCompatActivity {

    ConnectionClass connectionClass;
    Button proceedbut;
    Button RegBUT;
    EditText Uname, PWD;
    ProgressBar progressBar;
    Integer UID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_driver);
        // connectionClass = new ConnectionClass();
        proceedbut = (Button) findViewById(R.id.proceedbut);
        RegBUT = (Button) findViewById(R.id.RegBUT);
        Uname = (EditText) findViewById(R.id.Email);
        PWD = (EditText) findViewById(R.id.e2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);


        proceedbut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckLogin checklogin = new CheckLogin();
                checklogin.execute("");
            }
        });

        RegBUT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegVehicle.class);
                startActivity(i);
                setContentView(R.layout.activity_reg_vehicle);
            }
        });


        ImageButton backBUT = findViewById(R.id.backBUT);
        backBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                setContentView(R.layout.activity_main);
            }
        });
    }

    public class CheckLogin extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        String usernam = Uname.getText().toString();
        String passwordd = PWD.getText().toString();

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String z) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(LoginDriver.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(LoginDriver.this, z, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Searching.class);
                i.putExtra("UsrID", UID);
                startActivity(i);
                setContentView(R.layout.activity_searching);
            }
        }

        @Override
        protected String doInBackground(String... params) {


            if (usernam.trim().equals("") || passwordd.trim().equals("")) {
                z = "Please enter username and password";
            } else {
                ResultSet rs = null;
                Connection connection = null;
                Statement statement = null;
                String query = "";
                query = "select drvno from driver where email = '" + usernam + "' and  upass = '" + passwordd + "';";
                String z = "Incorrect Credentials";
                isSuccess = false;
                try {
                    connection = ConnectionClass.getConnection();
                    statement = connection.createStatement();
                    rs = statement.executeQuery(query);
                    while (rs.next()) {
                        z = "Login Succesful";
                        isSuccess = true;
                        UID = rs.getInt("drvno");
                        Log.e("PASSNO",UID.toString());
                    }
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.e("ERRO1", e.getMessage());
                }

            }
            return z;
        }


    }
}

