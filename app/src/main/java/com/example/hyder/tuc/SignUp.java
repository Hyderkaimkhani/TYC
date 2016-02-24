package com.example.hyder.tuc;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    EditText Fname,Lname,Email,Company,Password,ConfirmPassword;
    String FName,LName,email,company,password,confirmPassword;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);

        mTitleTextView.setText("Sign Up");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);


        Fname = (EditText) findViewById(R.id.et_Fname);
        Lname = (EditText) findViewById(R.id.et_Lname);
        Email = (EditText) findViewById(R.id.et_email);
        Company = (EditText) findViewById(R.id.et_company);
        Password = (EditText) findViewById(R.id.et_password);
        ConfirmPassword = (EditText) findViewById(R.id.et_confirmPasword);

        btn_submit = (Button) findViewById(R.id.btn_signup);

    }
}
