package com.example.hyder.tuc;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import TYC.User;

public class SignUp extends AppCompatActivity {

    EditText Fname,Lname,Email,Company,Password,ConfirmPassword;
    String FName,LName,email,company,password,confirmPassword;
    Button btn_submit;
    Global global;
  //  RegisterUser user = new RegisterUser();

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
        global = (Global) getApplicationContext();
        global.setActivity(this);
        global.setContext(this);


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);


        Fname = (EditText) findViewById(R.id.et_Fname);
        Lname = (EditText) findViewById(R.id.et_Lname);
        Email = (EditText) findViewById(R.id.et_email);
        Company = (EditText) findViewById(R.id.et_company);
        Password = (EditText) findViewById(R.id.et_password);
        ConfirmPassword = (EditText) findViewById(R.id.et_confirmPasword);
        btn_submit = (Button) findViewById(R.id.btn_signup);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
       /* FName = Fname.getText().toString();
        LName=Lname.getText().toString();
        email=Email.getText().toString();
        company= Company.getText().toString();
        password=Password.getText().toString();
        confirmPassword = ConfirmPassword.getText().toString();*/
                FName = "amjad";
                LName="ali";
                email="115@tyc.com";
                company= "test";
                password="123456";
                confirmPassword ="123456";

        if(!password.equals(confirmPassword))
        {
            global.alertOk("Error","Password should be same");
        }
        else {
            global.registerUser.setEmail(email);
            global.registerUser.setFirstName(FName);
            global.registerUser.setLastName(LName);
            global.registerUser.setNewPassword(password);

            global.user.setEmail(email);
            global.user.setFName(FName);
            global.user.setLName(LName);
            global.user.setPassword(password);
            global.user.setCompany(company);
            global.user.setUserID("");
            global.user.setUserID("");
            global.user.setMgrID("");
            global.user.setRating("");

            try {
                global.getSession();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
});

    }
}
