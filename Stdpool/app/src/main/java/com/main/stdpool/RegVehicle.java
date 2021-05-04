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

public class RegVehicle extends AppCompatActivity {

    Button RegBUT;
    EditText Email, PWD, Fname, Sname;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_vehicle);

        RegBUT = (Button) findViewById(R.id.RegBUT);
        Email = (EditText) findViewById(R.id.e1);
        PWD = (EditText) findViewById(R.id.e2);
        Fname = (EditText) findViewById(R.id.e3);
        Sname = (EditText) findViewById(R.id.e4);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        RegBUT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Register reg = new Register();
                reg.execute("");

                GetDID g = new GetDID();
                g.execute();
            }
        });

    }

    public class Register extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;
        String VID;
        Boolean complete = false;

        String usernam = Email.getText().toString();
        String passwordd = PWD.getText().toString();
        String forename = Fname.getText().toString();
        String surname = Sname.getText().toString();

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String z) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(RegVehicle.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(RegVehicle.this, z, Toast.LENGTH_SHORT).show();
                complete = true;
            }
        }

        @Override
        protected String doInBackground(String... params) {

            if (usernam.trim().equals("") || passwordd.trim().equals("") || forename.trim().equals("") || surname.trim().equals("")) {
                z = "Please enter Email, Password, Forename & Surname";
            } else {
                isSuccess = Reg(usernam, passwordd, forename, surname);
                if (isSuccess = true) {
                    z = "Registration Successful";
                } else {
                    z = "haha nope!";
                }

            }
            return z;
        }

        public boolean Reg(String usernam, String passwordd, String forename, String surname) {
            ResultSet rs = null;
            Connection connection = null;
            Statement statement = null;
            String query = "";
            query = "insert into vehicle values (NULL, '" + forename + "','" + surname + "','" + usernam + "','" + passwordd + "');";
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



    public class GetDID extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        String VID;
        String Result;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String z) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(RegVehicle.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(RegVehicle.this, z, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), RegDriver.class);
                Log.e("RESULT FINAL",Result);
                i.putExtra("VID",Result);
                startActivity(i);
                setContentView(R.layout.activity_reg_driver);
            }
        }

        @Override
        protected String doInBackground(String... params) {



                ResultSet rs = null;
                Connection connection = null;
                Statement statement = null;
                String query = "";
                query = "select * from vehicle;";
                Log.e("Confirm query",query);
                String z = "Incorrect Credentials";
                isSuccess = false;
                try {
                    connection = ConnectionClass.getConnection();
                    statement = connection.createStatement();
                    rs = statement.executeQuery(query);
                    rs.last();
                    Integer VID = rs.getInt("vehid");
                    Result = VID.toString();
                    Log.e("Result",Result);
                    connection.close();
                    isSuccess = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.e("ERRO1", e.getMessage());

                }

            return Result;
        }
    }

}
