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

public class RegDriver extends AppCompatActivity {

    Button RegBUT;
    EditText Email, PWD, Fname, Sname;
    ProgressBar progressBar;
    Intent intent;
    String VID;

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
        intent = getIntent();
        VID = intent.getStringExtra("VID");
        Log.e("START VID",VID);

        progressBar.setVisibility(View.GONE);

        RegBUT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Register reg = new Register();
                reg.execute(VID);
            }
        });

    }

    public class Register extends AsyncTask<String, String, String> {

        String VID;

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
            Toast.makeText(RegDriver.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(RegDriver.this, z, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), LoginDriver.class);
                startActivity(i);
                setContentView(R.layout.activity_login_driver);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String VID = params[0];
            Log.e("ASYNC VID",VID);

            if (usernam.trim().equals("") || passwordd.trim().equals("") || forename.trim().equals("") || surname.trim().equals("")) {
                z = "Please enter Email, Password, Forename & Surname";
            } else {
                isSuccess = Reg(usernam, passwordd, forename, surname, VID);
                if (isSuccess = true) {
                    z = "Registration Successful";
                } else {
                    z = "haha nope!";
                }

            }
            return z;
        }

        public boolean Reg(String usernam, String passwordd, String forename, String surname, String VID) {
            ResultSet rs = null;
            //Integer tempVID = intent.getIntExtra("VID",0);
           // VID = tempVID.toString(tempVID);
            Log.e("Final VID",VID);
            Connection connection = null;
            Statement statement = null;
            String query = "";
            query = "insert into driver values (NULL, '" + forename + "','" + surname + "','" + usernam + "','" + passwordd + "','" + VID + "');";
            Log.e("Query",query);
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
