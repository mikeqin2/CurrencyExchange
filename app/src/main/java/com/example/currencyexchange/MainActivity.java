package com.example.currencyexchange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class MainActivity extends AppCompatActivity {

    private EditText amount;
    private Button convertToUSD;
    private Button convertToCNY;
    private Button convertToJPY;
    private TextView result;
    private double USDrate;
    private double CNYrate;
    private double JPYrate;

    public void getUSDRates(java.lang.String json) {
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(json).getAsJsonObject();
        JsonObject rates = result.get("rates").getAsJsonObject();
        USDrate = rates.get("USD").getAsDouble();
}

    public void getCNYRates(java.lang.String json) {
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(json).getAsJsonObject();
        JsonObject rates = result.get("rates").getAsJsonObject();
        CNYrate = rates.get("CNY").getAsDouble();
    }

    public void getJPYRates(java.lang.String json) {
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(json).getAsJsonObject();
        JsonObject rates = result.get("rates").getAsJsonObject();
        JPYrate = rates.get("JPY").getAsDouble();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = (EditText)findViewById(R.id.input);
        convertToUSD = (Button)findViewById(R.id.USD);
        convertToCNY = (Button)findViewById(R.id.CNY);
        convertToJPY = (Button)findViewById(R.id.JPY);
        result = (TextView)findViewById(R.id.OUTPUT);

        final TextView textView = (TextView) findViewById(R.id.text);

    // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =
                "http://data.fixer.io/api/latest?access_key=910a5e472267bbe380b6a887180ffc47&symbols=USD,CNY,JPY,&format=1";
    // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getUSDRates(response);
                        getCNYRates(response);
                        getJPYRates(response);
                        textView.setText(" ");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                textView.setText(" ");
            }
        });
        queue.add(stringRequest);

        convertToUSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double theAmount = Double.parseDouble(amount.getText().toString());
                double USD = theAmount * USDrate;
                result.setText("$： " + String.valueOf(USD));
            }
        });

        convertToCNY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double theAmount = Double.parseDouble(amount.getText().toString());
                double CNY = theAmount * CNYrate;
                result.setText("￥： " + String.valueOf(CNY));
            }
        });

        convertToJPY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double theAmount = Double.parseDouble(amount.getText().toString());
                double JPY = theAmount * JPYrate;
                result.setText("￥： " + String.valueOf(JPY));
            }
        });
    }
}
