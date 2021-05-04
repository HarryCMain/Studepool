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

public class regpass extends AppCompatActivity {

    Button RegBUT;
    EditText Email, PWD, Fname, Sname;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regpass);

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
            }
        });

    }

    public class Register extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

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
            Toast.makeText(regpass.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(regpass.this, z, Toast.LENGTH_SHORT).show();
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
                    Intent i = new Intent(getApplicationContext(), LoginPassanger.class);
                    startActivity(i);
                    setContentView(R.layout.activity_login_passanger);
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
            query = "insert into passenger values (NULL, '" + forename + "','" + surname + "','" + usernam + "','" + passwordd + "');";
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
