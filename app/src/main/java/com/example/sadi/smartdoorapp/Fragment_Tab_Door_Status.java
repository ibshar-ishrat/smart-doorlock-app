package com.example.sadi.smartdoorapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Sadia Sami on 6/21/2016.
 */
public class Fragment_Tab_Door_Status extends Fragment
{
    private String pinCode;
    View rootview;

    private static TextView tv_Door_Status;
    private static Switch led1;
    public static String IP_ADDRESS = Main_ScreenActivity.IP_ADDRESS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.door_status, container, false);

        tv_Door_Status =(TextView) rootview.findViewById(R.id.textview_Door_Status);

        GetDataJSON_Door_Status g = new GetDataJSON_Door_Status();
        g.execute();

        /********************************/
         /*    Define all the buttons    */
        /********************************/

        led1 = (Switch) rootview.findViewById(R.id.Led1);

        /*******************************************************/
         /*  Set an onclick/onchange listener for every button  */
        /*******************************************************/

        led1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    /* Switch is led 1 */

                    //Open Alert Dialog and asks for PIN CODE When USER clicks on OK to
                    //save Entered Pin in variable pinCode
                    //verify pin code and calls function Background_get()

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Enter PINCODE to Unlock Door");

                    // Set up the input
                    final EditText input = new EditText(getActivity());

                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            pinCode = input.getText().toString().trim();

                            //dialog.cancel();
                            //Verify Pin Code and do necessary action
                            GetDataJSON_PIN g = new GetDataJSON_PIN();
                            g.execute();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            led1.setChecked(false);
                            //can you call method here getData or whatever method you created wo uper horaha call GetDatJson wala wahan mene connet out kiya ye dialogcancel
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
                else
                {
                    new Background_get().execute("led1=0");
                }
            }
        });

        return rootview;
    }

    public void Go_To_Door_Info(View view)
    {

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //   getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //   if (id == R.id.action_settings) {
        //     return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    /*****************************************************/
       /*  This is a background process for connecting      */
      /*   to the arduino server and sending               */
     /*    the GET request withe the added data           */

    /*****************************************************/


    private class Background_get extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                /* Change the IP to the IP you set in the arduino sketch */
                URL url = new URL("http://" + IP_ADDRESS + "/?" + params[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder result = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");

                in.close();
                connection.disconnect();

                return result.toString();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }

    class GetDataJSON_PIN extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args)
        {
            String url = "http://"+IP_ADDRESS+"/db_ver_PIN.php?MAC="+Utils.getMACAddress("wlan0");

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
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONArray retrievedArray = null;

            String pinCode_out="";
            try {
                JSONObject jsonObj = new JSONObject(result);
                retrievedArray = jsonObj.getJSONArray("result");

                // for(int i=0;i<retrievedArray.length();i++)
                {
                    JSONObject c = retrievedArray.getJSONObject(0);
                    pinCode_out = c.getString("PIN_Code");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (pinCode.matches(pinCode_out))
            {
                //dialog.cancel();

                new Background_get().execute("led1=1");

                Toast.makeText(getActivity(), "Door has been opened!", Toast.LENGTH_SHORT).show();

                led1.setChecked(false);

                new Background_get().execute("led1=0");
            }
            else
            {
                //dialog.cancel();
                Toast.makeText(getActivity(), "Invalid PIN Code!", Toast.LENGTH_SHORT).show();
                led1.setChecked(false);
            }
        }
    }

    class GetDataJSON_Door_Status extends AsyncTask<String, String, String> {

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
            String url = "http://"+IP_ADDRESS+"/db_Status_Tab.php?MAC="+Utils.getMACAddress("wlan0");

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
            String doorStatus_out = "";
            try
            {
                JSONObject jsonObj = new JSONObject(result);
                retrievedArray = jsonObj.getJSONArray("result");

                // for(int i=0;i<retrievedArray.length();i++)
                {
                    JSONObject c = retrievedArray.getJSONObject(0);
                    doorStatus_out= c.getString("Status");

                    tv_Door_Status.setText(doorStatus_out);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            //pDialog.dismiss();
        }
    }
}