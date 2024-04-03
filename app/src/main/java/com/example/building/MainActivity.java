package com.example.building;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;

public class MainActivity extends AppCompatActivity {

    Button smartBuildBtn;

    EditText username, password, repassword;
    Button signup, signinAsAdmin,signinAsManager,signinAsUser;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_build_index);



//        username = (EditText) findViewById(R.id.username);
//        password = (EditText) findViewById(R.id.password);
//        repassword = (EditText) findViewById(R.id.repassword);
//        signup = (Button) findViewById(R.id.btnsignup);
        signinAsAdmin = (Button) findViewById(R.id.btnAdmin);
        signinAsManager=(Button) findViewById(R.id.btnManager);
        signinAsUser=(Button) findViewById(R.id.btnUser);
        DB = new DBHelper(this);

//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String user = username.getText().toString();
//                String pass = password.getText().toString();
//                String repass = repassword.getText().toString();
//
//                if(user.equals("")||pass.equals("")||repass.equals(""))
//                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
//                else{
//                    if(pass.equals(repass)){
//                        Boolean checkuser = DB.checkusername(user);
//                        if(checkuser==false){
//                            Boolean insert = DB.insertData(user, pass);
//                            if(insert==true){
//                                Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
//                                startActivity(intent);
//                            }else{
//                                Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        else{
//                            Toast.makeText(MainActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Toast.makeText(MainActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
//                    }
//                } }
//        });
//
        signinAsManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("role","Manager");
                startActivity(intent);
            }
        });
//
        signinAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("role","Admin");
                startActivity(intent);
            }
        });
        signinAsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("role","User");
                startActivity(intent);
            }
        });
//
////        smartBuildBtn = findViewById(R.id.sbBtn);
//        smartBuildBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("hello", "button clicked");
//                Intent intent = new Intent(getApplicationContext(), SmartBuildAdminMenu.class);
//                //intent.putExtra("role","User");
//                startActivity(intent);
//                //Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}