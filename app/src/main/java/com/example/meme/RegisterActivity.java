package com.example.meme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText userName,password,cpassword,email;
    Button createbtn;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    ProgressDialog progressDialog;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        createbtn = findViewById(R.id.createbtn);
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
    }

    private void create() {
        userName = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        email = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        String emailPattern = "[azA-Z0-9._-]+@[a-z]+\\.+[1-z]+";
        String user = userName.getText().toString().trim();
        String pswd = password.getText().toString().trim();
        String cpwd = cpassword.getText().toString().trim();
        String gmail = email.getText().toString().trim();
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pswd) || TextUtils.isEmpty(cpwd) || TextUtils.isEmpty(gmail)){
            Toast.makeText(this, "Please enter the required fields", Toast.LENGTH_SHORT).show();
        }
        else if(pswd.equals(cpwd)){
            if(gmail.matches(emailPattern)){
                progressDialog.setMessage("Please wait while Registration...");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.show();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                User new_user = new User(pswd,gmail);
                databaseReference.child(user).setValue(new_user);
                Toast.makeText(RegisterActivity.this,"registered successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
                email.setError("Enter valid email");
            }
        }else{
            password.setText("");
            cpassword.setText("");
            Toast.makeText(this, "Please enter the same password for both fields", Toast.LENGTH_SHORT).show();
        }
    }
}