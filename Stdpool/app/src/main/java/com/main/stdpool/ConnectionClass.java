package com.main.stdpool;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*public String ip = "192.168.1.211:1433";
    public String classs = "net.sourceforge.jtds.jdbc.Driver";
    public String db = "mysql";
    public String un = "app@%";
    public String password = "pass";*/

public class ConnectionClass extends AppCompatActivity {
    /*public String ip = "comp-server.uhi.ac.uk";
    public String classs = "net.sourceforge.jtds.jdbc.Driver";
    public String db = "pe18011506";
    public String un = "pe18011506";
    public String password = "harrymain";


    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            Log.e("ConnURL",ConnURL);
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO1", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO2", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO3", e.getMessage());
        }
        return conn;
    }*/


	//static reference to itself jdbcdb
    private static ConnectionClass instance = new ConnectionClass();
    public static final String URL = "jdbc:mysql://comp-hons.uhi.ac.uk/pe18011506";
    public static final String USER = "pe18011506";
    public static final String PASSWORD = "harrymain";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

    //private constructor
    private ConnectionClass() {
        try {
            //Step 2: Load MySQL Java driver
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("ERRO2", e.getMessage());
        }
    }

    private Connection createConnection()
{

        Connection connection = null;
        try {
            //Step 3: Establish Java MySQL connection
            connection = DriverManager.getConnection(URL, USER,PASSWORD);

        } catch (SQLException e) {
           Log.e("ERROR: Unable to Connect to Database.",e.getMessage());
        }
        return connection;
}

    public static Connection getConnection()
    {
        return instance.createConnection();
    }
}



