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
import java.sql.Statement;
import java.sql.SQLException;

public class LoginPassanger extends AppCompatActivity {

    ConnectionClass connectionClass;
    Button proceedbut;
    Button RegBUT;
    EditText Uname, PWD;
    ProgressBar progressBar;
    Integer UID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_passanger);
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
                Intent i = new Intent(getApplicationContext(), regpass.class);
                startActivity(i);
                setContentView(R.layout.activity_regpass);
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
            Toast.makeText(LoginPassanger.this,z, Toast.LENGTH_SHORT).show();
            if(isSuccess) {
                Toast.makeText(LoginPassanger.this,z,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), PassMenu.class);
                i.putExtra("UsrID",UID);
                startActivity(i);
                setContentView(R.layout.activity_pass_menu);
            }
        }

        @Override
        protected String doInBackground(String... params) {


            if (usernam.trim().equals("") || passwordd.trim().equals("")) {
                z = "Please enter username and password";
            } else {/*
                    try {
                        con = connectionClass.getConnection();
                        if (con == null) {
                            z = "Check network connection";
                        } else {
                           query = "select * from passenger where email = '" + usernam + "' and  upass = '" + passwordd + "';";
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery(query);
                            if (rs.next()) {
                                z = "Login Successful";
                                isSuccess = true;
                                con.close();
                            } else {
                                z = "Invalid Cerdentials!";
                                isSuccess = false;
                                con.close();
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.e("ERRO1", e.getMessage());
                    }
            }*/
                ResultSet rs = null;
                Connection connection = null;
                Statement statement = null;
                String query="";
                query = "select passno from passenger where email = '" + usernam + "' and  upass = '" + passwordd + "';";
                String z = "Incorrect Credentials";
                isSuccess = false;
                try {
                    connection = ConnectionClass.getConnection();
                    statement = connection.createStatement();
                    rs = statement.executeQuery(query);
                    while (rs.next())
                    {
                        z = "Login Succesful";
                        isSuccess = true;
                        UID = rs.getInt("passno");
                    }
                    connection.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                    Log.e("ERRO1", e.getMessage());
                }

            }
            return z;
        }

       /* public String Login(String usernam,String passwordd){
            ResultSet rs = null;
            Connection connection = null;
            Statement statement = null;
            String query="";
            query = "select * from passenger where email = '" + usernam + "' and  upass = '" + passwordd + "';";
            boolean successful = false;
            try {
                connection = ConnectionClass.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(query);
               if (rs.next())
                {
                 successful = true;
                }
                connection.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                Log.e("ERRO1", e.getMessage());
            }
            return successful;
        }*/


    }
}