package com.example.sadi.smartdoorapp;

/**
 * Created by Ibshar Ishrat on 26/10/2016.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.sadi.smartdoorapp.GetImages.bitmaps;

public class ViewFullImage extends AppCompatActivity {
    public static String IP_ADDRESS = Main_ScreenActivity.IP_ADDRESS;
    public static final String GET_IMAGE_URL = "http://" + IP_ADDRESS + "/db_Single_Activity.php";
    private ImageView imageView;
    static String activityinStr;
    public static GetImages getActivity;
    String mac_addr = "C4:3A:BE:53:F5:D1";
    String ip_addr = "192.168.8.10";
    String door = "my door";
    String FILENAME = "credFile.txt";
    Button bUnlock;
    String macFromFile;
    String ipAddFromFile;
    String doorFromFile;
    String[] piInfo;

    FileOutputStream outputStream;


    public String pinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full);
        bUnlock = (Button) findViewById(R.id.buttonIgnore);
        try
        {
            outputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try {
            outputStream.write(mac_addr.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(ip_addr.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(door.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream fis = null;
        try {
            fis = openFileInput("credFile.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            piInfo = sb.toString().split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }


        getSingleActivity();
    }
    public void Ignore(View view)
    {
        Intent menu = new Intent(ViewFullImage.this,sidePanel.class);
        startActivity(menu);
    }
    public void Unlock(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewFullImage.this);

        builder.setTitle("Enter PINCODE to Unlock Door");
        final EditText input = new EditText(ViewFullImage.this);

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
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //can you call method here getData or whatever method you created wo uper horaha call GetDatJson wala wahan mene connet out kiya ye dialogcancel
                dialog.cancel();
            }
        });

        builder.show();

    }
   public void getSingleActivity()
    {
         class GetSingleActivity extends AsyncTask<String,String,String>
            {
                @Override
                protected void onPreExecute()
                {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... strings)
                {
                    BufferedReader bufferedReader = null;
                    try
                    {
                        URL url = new URL(strings[0]);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();

                        bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        String json;
                        while((json = bufferedReader.readLine())!= null)
                        {
                            sb.append(json + "\n");
                        }

                        activityinStr = sb.toString();

                    }
                    catch(Exception e)
                    {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                        return null;
                    }
                    return activityinStr;
                }
                @Override
                protected void onPostExecute(String s)
                {
                    s = activityinStr;
                    super.onPostExecute(s);
                    getActivity = new GetImages(s);
                    getActivity();
                }

            }
            GetSingleActivity ga = new GetSingleActivity();
            ga.execute(GET_IMAGE_URL);
        }

    private void getActivity()
    {
        class GetActivity extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids)
            {
                try
                {
                   getActivity.getAllImages();

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void v)
            {
                super.onPostExecute(v);
                imageView = (ImageView) findViewById(R.id.imageViewFull);
                if(bitmaps!=null)
                {
                    imageView.setImageBitmap(bitmaps[0]);
                }
                else
                {
                    System.out.println("No Image to show");
                }

            }
        }
       GetActivity getActivity = new GetActivity();
      getActivity.execute();
    }
}
