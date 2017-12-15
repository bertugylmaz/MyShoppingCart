package com.example.onurhuseyincantay.myshoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onurhuseyincantay.myshoppingcart.Model.User;
import com.example.onurhuseyincantay.myshoppingcart.Network.DataService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Map;

/**
 * Created by Bertug on 15/12/2017.
 */

public class LoginController extends AppCompatActivity {
    private User user;
    public EditText emailEditText;
    public EditText passwordEditText;
    public Button loginButton;
    public Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailEditText = (EditText) findViewById(R.id.login_email);
        passwordEditText = (EditText) findViewById(R.id.login_passsword);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                loginUser(name, password);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                createUser(name,password);
                emailEditText.setText("");
                passwordEditText.setText("");
            }
        });
    }

    public void createUser(final String n, String p){
        DataService.ds.mAuth.createUserWithEmailAndPassword(n,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String currentUserId = DataService.ds.mAuth.getCurrentUser().getUid().toString();

                    user = new User(currentUserId,n);
                    Map<String,Object> userValues = user.toMap();
                    DataService.ds.userRef.child(currentUserId).updateChildren(userValues);

                    Toast.makeText(LoginController.this, "Registiration Completed",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginController.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loginUser(String name, String password){
        DataService.ds.mAuth.signInWithEmailAndPassword(name,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent mainIntent = new Intent(LoginController.this, Main.class);
                    startActivity(mainIntent);
                }else {
                    Toast.makeText(LoginController.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        if(DataService.ds.mAuth.getCurrentUser() != null){
            Intent mainIntent = new Intent(LoginController.this, Main.class);
            startActivity(mainIntent);
        }
    }

}
