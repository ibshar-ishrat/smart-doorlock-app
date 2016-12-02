package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main_ScreenActivity extends AppCompatActivity {
    // main screen is Login Page here in this class we will initialize some variables

    DatabaseHelper myDB; //For SQLite DB

    private static EditText username;
    private static EditText password;

    private static String sUsername;
    private static String sPassword;

    private static String username_out;
    private static String password_out;
    private static String email_out;

    private static TextView attempts;
    private static Button login_btn;
    int attempt_counter = 3;


    public static String IP_ADDRESS = "192.168.0.105";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__screen);

     // For SQLite DB implementation
        myDB = new DatabaseHelper(this);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        //LoginButton();
    }
    // making method for Login button in which we will cast all variables declared above i.e username, password, attempts
    public void LoginButton(View view)
    {
        username = (EditText) findViewById(R.id.editText_usereg);
        password = (EditText) findViewById(R.id.editText_passreg);

        sUsername = username.getText().toString().trim();
        sPassword = password.getText().toString().trim();


        if( sUsername.matches("sadia") && sPassword.matches("sami"))
        {
            Intent i = new Intent("com.example.sadi.smartdoorapp.sidePanel");
            startActivity(i);
        }

        else
        {
            Toast.makeText(Main_ScreenActivity.this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
        }
        GetDataJSON_Login g = new GetDataJSON_Login();
        g.execute();

       // attempts =(TextView) findViewById(R.id.textView_attemptcount);
        login_btn= (Button) findViewById(R.id.button_login);

        //= (TextView) findViewById(R.id.textView_createAcc);
        final TextView signup =(TextView) findViewById(R.id.textView_signup);
        final TextView forget_password =(TextView) findViewById(R.id.textView_forgetPas);

       //attempts.setText(Integer.toString(attempt_counter));
    }

    public void Signup(View view)
    {
        Intent i2 = new Intent(Main_ScreenActivity.this,User_Registration.class);
        startActivity(i2);
    }

    public void ForgetPW(View view)
    {
        Intent i2 = new Intent(Main_ScreenActivity.this,Forget_Password.class);
        startActivity(i2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main__screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class GetDataJSON_Login extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute()
        {
            /*super.onPreExecute();
            pDialog = new ProgressDialog(User_Registration2.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }
        @Override
        protected String doInBackground(String... args)
        {
            String url = "http://"+IP_ADDRESS+"/db_ver_Login.php?MAC="+Utils.getMACAddress("wlan0");

            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpGet httppost = new HttpGet(url);

            // Depends on your web service
            httppost.setHeader("Content-type", "application/json");

            InputStream inputStream = null;
            String result = null;

            try
            {
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                inputStream = entity.getContent();
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                result = sb.toString().trim();
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }

            finally
            {
                try
                {
                    if (inputStream != null) inputStream.close();
                }
                catch (Exception squish)
                {
                }
            }

            /*SharedPreferences query_result = getSharedPreferences("query", 0);
            SharedPreferences.Editor edit = query_result.edit();

            edit.putString(result_tag, result);

            edit.commit();*/

            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            JSONArray retrievedArray = null;

            try
            {
                JSONObject jsonObj = new JSONObject(result);
                retrievedArray = jsonObj.getJSONArray("result");

                // for(int i=0;i<retrievedArray.length();i++)
                {
                    JSONObject c = retrievedArray.getJSONObject(0);
                    email_out= c.getString("Email_Address");

                    c = retrievedArray.getJSONObject(0);

                    c = retrievedArray.getJSONObject(0);
                    username_out = c.getString("Username");

                    c = retrievedArray.getJSONObject(0);
                    password_out = c.getString("PW");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            /*if( !( sUsername.matches(email_out) || sUsername.matches(username_out) ) || !(sPassword.matches(password_out)) )
            {
                Toast.makeText(Main_ScreenActivity.this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
            }

            else
            {
                Intent i = new Intent("com.example.sadi.smartdoorapp.sidePanel");
                startActivity(i);

                EditText text = (EditText) findViewById(R.id.editText_passreg);
                text.setText("");
            }*/

            //pDialog.dismiss();
        }
    }
}