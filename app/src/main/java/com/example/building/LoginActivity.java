package com.example.building;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;

    TextView roleHeading;
    Button btnlogin;
    DBHelper DB;
    String value=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        if (extras != null) {
            value = extras.getString("role");
            //The key argument here must match that used in the other activity
        }
        roleHeading=(TextView) findViewById(R.id.roleTitle);
        roleHeading.setText(value);
        roleHeading.setTextSize(25);
        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        DB = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();



                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    int userID = DB.checkusernamepassword(user, pass, value);
                    if(userID != -1){

                        User userObject = DB.getUserDetails(userID);

                        // Store user details in SharedPreferences
                        saveUserDetails(userObject);
                        Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), SmartBuildAdminMenu.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void saveUserDetails(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("userID", user.getUserID());
        editor.putString("username", user.getUserName());
        editor.putString("designation", user.getDesignation());
        editor.putInt("blockID", user.getBlockID());
        editor.putString("emailID", user.getEmailId());
        editor.putString("phoneNumber", user.getPhoneNumber());
        // Add other user details as needed

        editor.apply();
    }
}