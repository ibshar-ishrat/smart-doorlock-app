package com.example.sadi.smartdoorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class Forget_Password extends AppCompatActivity
{
    public static TextView secQ1;
    public static EditText secA1;
    public static TextView secQ2;
    public static EditText secA2;

    public static String sSecQ1;
    public static String sSecQ2;

    public static String sSecA1;
    public static String sSecA2;

    public static String SecA1_out;
    public static String SecA2_out;

    public static String IP_ADDRESS = Main_ScreenActivity.IP_ADDRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_verification);

        secQ1=(TextView) findViewById(R.id.textView_ques_ver);
        secQ2=(TextView) findViewById(R.id.textView_ques_ver2);

        secA1=(EditText)findViewById(R.id.editText_Answer_ver1);
        secA2=(EditText)findViewById(R.id.editText_answer_ver2);

        //************* NOTE: bring questions from pi and show them on text view of questions *****************//
        GetDataJSON_SecQ g = new GetDataJSON_SecQ();
        g.execute();
    }

    public void Button_verify (View view)
    {
        sSecA1 =  secA1.getText().toString().trim();
        sSecA2 =  secA2.getText().toString().trim();

        GetDataJSON_SecA g = new GetDataJSON_SecA();
        g.execute();
    }

    class GetDataJSON_SecQ extends AsyncTask<String, String, String> {

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
            String url = "http://"+IP_ADDRESS+"/db_ver_secQ.php?MAC="+Utils.getMACAddress("wlan0");

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
                    sSecQ1= c.getString("Question_1");

                    c = retrievedArray.getJSONObject(0);
                    sSecQ2 = c.getString("Question_2");

                    secQ1.setText(sSecQ1);
                    secQ2.setText(sSecQ2);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            //pDialog.dismiss();
        }
    }

    class GetDataJSON_SecA extends AsyncTask<String, String, String> {

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
            String url = "http://"+IP_ADDRESS+"/db_ver_secA.php?MAC="+Utils.getMACAddress("wlan0");

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
                    SecA1_out= c.getString("Answer_1");

                    c = retrievedArray.getJSONObject(0);
                    SecA2_out = c.getString("Answer_2");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            if( !(sSecA1.matches(SecA1_out)) || !(sSecA2.matches(SecA2_out)) )
            {
                if ( !(sSecA1.matches(SecA1_out)) )
                {
                    secA1.setError("Wrong Answer!");
                }

                if ( !(sSecA2.matches(SecA2_out)) )
                {
                    secA2.setError("Wronng Answer!");
                }
            }

            else
            {
                Intent next1 = new Intent("com.example.sadi.smartdoorapp.Reset_Password");
                startActivity(next1);
            }

            //pDialog.dismiss();
        }
    }
}
