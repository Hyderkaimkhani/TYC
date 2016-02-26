package com.example.hyder.tuc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import TYC.Session;
import TYCAPI.ApiException;
import TYCAPI.ApiInvoker;
import cz.msebera.android.httpclient.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import TYC.AppConstants;
import TYC.Login;

/**
 * Created by hv on 2/23/16.
 */


public class Global extends Application implements AppConstants{

    private Context context = null;
    private Activity activity = null;
    String result = null;
    String Error = null;
    public static Session mySession;
    public static String mySessionId;

    Login login = new Login();
    ApiInvoker ApiInvoker = new ApiInvoker();

    public TextToSpeech textToSpeech;

    public Context getContext() {
        return context != null ? context : null;
    }

    public final void setActivity(Activity act) {
        this.activity = act;
    }

    public final void setContext(Context con) {
        context = con;
    }

    public void myToast(final String s){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public final Activity getActivity()
    {
        return activity;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(10000);
                urlc.connect();

                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
               // Log.e(LOG_TAG, "Error checking internet connection", e);
                myToast("Error checking internet connection");
            }
        } else {
           // Log.d(LOG_TAG, "No network available!");
            myToast("No network available!");
        }
        return false;
    }

    public void Login(String userID, String UserPass) {

        if(!userID.isEmpty() || !UserPass.isEmpty() )
        {
            login.setEmail(userID);
            login.setPassword(UserPass);

           /* JSONObject credential = new JSONObject();

            credential.put("email", userID);
            credential.put("password", UserPass);*/

            try {
                getSession();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else
        {
            alertOk("Error","Enter UserID or Password");
        }


    }


    public final void alertOk(String title, String message) {
        alertOk(title, message, getContext());
    }
    private final void alertOk(final String title, final String message, final Context con) {
        if(!((Activity) context).isFinishing())
        {
            //show dialog
            /*beep = MediaPlayer.create(this, R.raw.beep02);
            beep.setVolume(100, 100);
            beep.start();*/
            textToSpeech.speak(title, TextToSpeech.QUEUE_FLUSH, null);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(con)
                            .setIcon(R.drawable.alertinfo)
                            .setTitle(title)
                            .setMessage(message)
                            .setCancelable(false)
                            .setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.cancel();
                                        }
                                    }).create().show();


                }
            });

        }
    }

    public void getSession() throws JSONException {

        JSONObject credential = new JSONObject();

        credential.put("email",SessionEmail);
        credential.put("password", SessionPassword);

        try {
            StringEntity entity = new StringEntity(credential.toString());

            if (isNetworkAvailable())
            {
                try {
                    ApiInvoker.Post(SessionURL, APIKey, entity, ContentType, new ApiInvoker.OnJSONResponseCallback() {
                        @Override
                        public void onJSONSuccessResponse(boolean success, JSONObject response) throws JSONException {

                            result = response.toString();
                            if (result != null) {

                                try {
                                    mySession = (Session) ApiInvoker.deserialize(result, "",
                                            Session.class);
                                    mySessionId = mySession.getSession_id().toString();

                                    if (mySession != null) {
/*
                                        if (!googleApiClient.isConnected())
                                            googleApiClient.connect();*/

                                     //   sessionTask();

                                    } else {
                                        alertOk("Login Error", ApiInvoker.response + " Please retry.");
                                      //  resetLogin();
                                    }

                                } catch (ApiException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                alertOk("Info", "Please try again.");
                               // resetLogin();
                            }
                        }

                        @Override
                        public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error) {


                           Error = response.toString();
                            try {
                                Error = jsonError(Error);
                                alertOk("Error",Error);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                alertOk("Error", "Network not available. Please retry later.");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // return session;
    }
    public String jsonError(String res) throws JSONException {
        JSONObject jsonObj = new JSONObject(res);
        JSONObject jso = (JSONObject) jsonObj;
        //JSONObject company = (JSONObject) jso.get("error");
        JSONArray jsonArray = (JSONArray) jso.get("error");
        JSONObject val = (JSONObject) jsonArray.get(0);
        res = val.get("message").toString();
        return res;
    }
}
