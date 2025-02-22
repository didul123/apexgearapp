package com.didul.apexgearapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.didul.apexgearapp.R;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignInActivity extends AppCompatActivity {

    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Send data to PHP script (sign_in.php) via HTTP POST
                new SignInTask().execute(email, password);
            }
        });
    }

    private class SignInTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            String response = ""; // Initialize the response variable

            try {
                // Prepare URL for local PHP server (use 10.0.2.2 to refer to localhost in Android Emulator)
                URL url = new URL("http://192.168.1.78/apexgear/sign_in.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                // Prepare POST data
                String data = "email=" + email + "&password=" + password;
                OutputStream os = connection.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                // Read response from PHP script
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                int inputStreamData;
                while ((inputStreamData = reader.read()) != -1) {
                    response += (char) inputStreamData;
                }

                reader.close();
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                response = "Error: " + e.getMessage(); // Handle the error if any
            }

            return response; // Return the response
        }

        @Override
        protected void onPostExecute(String result) {
            // Handle the response and show result
            Toast.makeText(SignInActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}
