package com.uxair.alert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Electricity extends AppCompatActivity {

public class MockApiCall extends AsyncTask<String, Void, String>{

    EditText editTextUserData = findViewById(R.id.editTextElectricUnits);
    TextView textViewDesc = findViewById(R.id.textViewElectricDesc);
    TextView textViewResult = findViewById(R.id.textViewElectricResult);

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while(data != -1){
                char current = (char) data;
                result += current;
                data = reader.read();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            String userInput = editTextUserData.getText().toString();
            if(userInput == null || userInput == "" || userInput.isEmpty()){
                textViewDesc.setText("Please Enter consumed units in field above.");
                Toast.makeText(getApplicationContext(), "Please Enter Consumed Units", Toast.LENGTH_SHORT).show();
            }else {
                int userInputInteger = Integer.parseInt(userInput);


                if (userInputInteger > 0 && userInputInteger <= 200) {
                    String unitprice = jsonObject.getString("classA");
                    float perunit = Float.parseFloat(unitprice);
                    float total = perunit * userInputInteger;
                    textViewResult.setText(String.valueOf(total) + " PKR");
                } else if (userInputInteger >= 201 && userInputInteger <= 300) {
                    String unitprice = jsonObject.getString("classB");
                    float perunit = Float.parseFloat(unitprice);
                    float total = perunit * userInputInteger;
                    textViewResult.setText(String.valueOf(total) + " PKR");
                } else if (userInputInteger >= 301 && userInputInteger <= 700) {
                    String unitprice = jsonObject.getString("classC");
                    float perunit = Float.parseFloat(unitprice);
                    float total = perunit * userInputInteger;
                    textViewResult.setText(String.valueOf(total) + " PKR");
                } else if (userInputInteger >= 700) {
                    String unitprice = jsonObject.getString("classD");
                    double perunit = Float.parseFloat(unitprice);
                    double total = perunit * userInputInteger;
                    textViewResult.setText(String.valueOf(total) + " PKR");
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
                textViewDesc.setText("Result");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }


    }
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);

        Button btnElectricCal = findViewById(R.id.btnElectricCal);
        MockApiCall obj1 = new MockApiCall();



        btnElectricCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj1.textViewDesc.setText("Calculating...");
                obj1.execute("https://62f1afebb1098f150803e5ed.mockapi.io/electricity/1");
            }
        });



    }
}