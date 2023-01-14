package com.example.meme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn,createBtn;
    Intent i;
    EditText userName,password;
    String user_name,pswd;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.loginbtn);
        createBtn = findViewById(R.id.createbtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(LoginActivity.this,AppActivity.class);
                if(isAuth()) {
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this, "Please check the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean isAuth() {
        userName = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        user_name = userName.getText().toString().trim();
        pswd = password.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        if(TextUtils.isEmpty(user_name)||TextUtils.isEmpty(pswd)) return false;
        progressDialog.setMessage("Please wait while login...");
        progressDialog.setTitle("Login");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(user_name,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    return;
                }
            }
        });
        return true;
    }
}