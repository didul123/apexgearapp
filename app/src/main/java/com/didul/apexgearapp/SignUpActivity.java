package com.didul.apexgearapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etPhone, etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();

                // Send data to PHP script (sign_up.php) via HTTP POST
                new SignUpTask().execute(name, email, password, phone, address);
            }
        });
    }

    private class SignUpTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String email = params[1];
            String password = params[2];
            String phone = params[3];
            String address = params[4];
            String response = ""; // Initialize the response variable

            try {
                // Prepare URL for local PHP server (without SSL)
                URL url = new URL("http://192.168.1.78/apexgear/sign_up.php"); // Use 10.0.2.2 to refer to localhost in Android Emulator
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                // Prepare POST data
                String data = "name=" + name + "&email=" + email + "&password=" + password + "&phone=" + phone + "&address=" + address;
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

            return response; // Return the response to onPostExecute
        }

        @Override
        protected void onPostExecute(String result) {
            // Handle the response and show result
            Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}
