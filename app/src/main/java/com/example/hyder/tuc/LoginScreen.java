package com.example.hyder.tuc;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

public class LoginScreen extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView signup;
    EditText userid,password;
    String UserID,UserPass;
    Button btn_login;
    ProgressBar progressBar;
    private static Global global;
    public boolean login = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "Hello");

        signup = (TextView) findViewById(R.id.tv_signup);
        userid = (EditText) findViewById(R.id.et_userid);
        password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.Login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("SignIn");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        //int color = R.color.colorPrimary;
        collapsingToolbarLayout.setContentScrimColor(getColor(getApplicationContext(),R.color.colorPrimary));
        //  collapsingToolbarLayout.setStatusBarScrimColor(Color.RED);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.hide();

        global = (Global) getApplicationContext();
        global.setActivity(LoginScreen.this);
        global.setContext(LoginScreen.this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this,SignUp.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = true;
               UserID = userid.getText().toString();
               UserPass = password.getText().toString();
               /* if(str_user.equals("") || str_pass.equals(""))
                {

                }*/

                btn_login.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                global.Login(UserID,UserPass);



            //    Intent intent = new Intent(LoginScreen.this,MainActivity.class);
             //   startActivity(intent);
            }
        });



    }

    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }
    }

