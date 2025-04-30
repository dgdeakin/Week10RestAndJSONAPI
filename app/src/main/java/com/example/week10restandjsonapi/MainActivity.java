package com.example.week10restandjsonapi;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//Resources:
//https://google.github.io/volley/


public class MainActivity extends AppCompatActivity {


    TextView textView;

    String url = "https://www.google.com";
    String arrayUrl = "https://jsonplaceholder.typicode.com/todos";
    String objectUrl = "https://jsonplaceholder.typicode.com/todos/1";

    WebView webView;
    Button loadTodosButton, loadTodoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        webView = findViewById(R.id.webView);
        loadTodoButton = findViewById(R.id.buttonLoadTodo);
        loadTodosButton = findViewById(R.id.buttonLoadTodos);
        CheckBox stringRequestCheckBox = findViewById(R.id.stringRequestCheckBox);
        CheckBox jsonArrayRequestCheckBox = findViewById(R.id.jsonArrayRequestCheckBox);
        CheckBox jsonObjectRequestCheckBox = findViewById(R.id.jsonObjectRequestCheckBox);


        loadTodosButton.setOnClickListener(v -> {
            webView.loadUrl("https://jsonplaceholder.typicode.com/todos");
        });

        loadTodoButton.setOnClickListener(v -> {
            webView.loadUrl("https://jsonplaceholder.typicode.com/todos/1");
        });


        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                objectUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            textView.setText(response.getString("title"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                arrayUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject jsonObject = response.getJSONObject(2);
                            textView.setText(jsonObject.getString("title"));

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response.substring(0,500));
                        textView.setText(response.substring(0,500));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Checkbox listeners
        stringRequestCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                queue.add(stringRequest);
            }
        });

        jsonArrayRequestCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                queue.add(jsonArrayRequest);
            }
        });

        jsonObjectRequestCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                queue.add(jsonObjectRequest);
            }
        });

//        queue.add(stringRequest);
//        queue.add(jsonArrayRequest);
//        queue.add(jsonObjectRequest);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    }
}