package com.example.hyder.tuc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import TYC.Loc;
import TYC.Session;
import TYC.Task;
import TYC.User;
import TYCAPI.ApiException;
import TYCAPI.ApiInvoker;
import cz.msebera.android.httpclient.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import TYC.AppConstants;
import TYC.Login;

/**
 * Created by hv on 2/23/16.
 */


public class Global extends Application implements AppConstants,LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private Context context = null;
    private Activity activity = null;

    public LocalDatabase local;
    public User user = new User();
    public Loc loc = new Loc();
    public static Session mySession;
    public static String mySessionId;
    public boolean loginuser = false;
    public TextToSpeech textToSpeech;
    public static String selectedmember;

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private LocationRequest mLocationRequest;

    String result = null;
    String Error = null;
    RegisterUser registerUser = new RegisterUser();
    Login login = new Login();
    ApiInvoker ApiInvoker = new ApiInvoker();
    String FName,LName,email,company,password,confirmPassword;

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

    public ArrayList<Double> getLoc(final double longitude, final double latitude) {
        return new ArrayList<Double>() {{
            add(longitude);
            add(latitude);
        }};
    }
/*    public void Login(String userID, String UserPass) {

        if(!userID.isEmpty() || !UserPass.isEmpty() )
        {
            login.setEmail(userID);
            login.setPassword(UserPass);

           *//* JSONObject credential = new JSONObject();

            credential.put("email", userID);
            credential.put("password", UserPass);*//*

              // LoginUser();


        }
        else
        {
            alertOk("Error", "Enter UserID or Password");
            resetLogin();
        }


    }*/


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
      /*      textToSpeech.speak(title, TextToSpeech.QUEUE_FLUSH, null);*/
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

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public void initApp() {

        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();
        mGoogleApiClient.connect();
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
                                        RegisterUser();
/*
                                        if (!googleApiClient.isConnected())
                                            googleApiClient.connect();*/
                                       /* if (loginuser == true) {
                                            GetUserInfo();
                                            LoginUser();
                                        }
                                       else{
                                            RegisterUser();
                                        }*/

                                    } else {
                                        alertOk("SignUp Error", ApiInvoker.response + " Please retry.");
                                        resetSignUp();
                                    }

                                } catch (ApiException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                alertOk("Info", "Please try again.");
                                resetSignUp();
                            }
                        }

                        @Override
                        public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error) {

                           Error = response.toString();
                            try {
                                Error = jsonError(Error);
                                alertOk("Error",Error);
                                resetSignUp();
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
                resetSignUp();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // return session;
    }

/*
    public void patchMgrID(String email)
    {
        JSONObject mgrid = new JSONObject();
        try {
            mgrid.put("MgrID",login.getEmail());
            String json = "{\"resource\":["+mgrid.toString()+"]}";

            StringEntity entity = new StringEntity(json);

        //    String json2 = "{\"resource\":["+mgrid+"]}";

        ApiInvoker.patch(GetTableURL + "users" +"?filter=Email=" + email,
                AppConstants.TokenHeader + mySessionId + "\n" + AppConstants.APIKey, entity, AppConstants.ContentType,
                new ApiInvoker.OnJSONResponseCallback() {
                    @Override
                    public void onJSONSuccessResponse(boolean success, JSONObject response) {

                        result = response.toString();
                        Toast.makeText(getActivity(),"Succeded",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error) {
                        result = response.toString();
                        Toast.makeText(getActivity(),"Failure",Toast.LENGTH_SHORT).show();
                    }
                });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }*/

    public void GetUserInfo() {

        local = new LocalDatabase(this);
        local.openDatabase();
     //   local.openDatabase();

        ApiInvoker.getResponse(GetTableURL + "Users" + "?filter=MgrID=" + login.getEmail() + "",
                AppConstants.TokenHeader + mySessionId + "\n" + AppConstants.APIKey, null, new ApiInvoker.OnJSONResponseCallback() {
                    @Override
                    public void onJSONSuccessResponse(boolean success, JSONObject response) throws JSONException {

                        String id,email,Fname,Lname,Company,MgrID,Rating,lat,lng;

                        result = response.toString();
                        if (result.length()>15)
                        {

                           //    user = ((User) JSONParse.parseJSON(result, User.class).get(0));


                                JSONArray employees = response.getJSONArray("resource");
                                for (int i=0; i<employees.length(); i++) {
                                    JSONObject person = employees.getJSONObject(i);
                                    id = person.getString("_id");
                                    email = person.getString("Email");
                                    Fname = person.getString("FName");
                                    Lname = person.getString("LName");
                                    Company = person.getString("Company");
                                    MgrID = person.getString("MgrID");
                                    Rating = person.getString("Rating");
                                    JSONArray location = person.getJSONObject("loc").getJSONArray("coordinates");;
                                    lat = location.get(0).toString();
                                    lng = location.get(1).toString();


                                    local.InsertEmployee(id,email,Fname,Lname,Company,MgrID,Rating,lat,lng);
                                }
                        }
                        // if no data found just go to main activity
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                    }

                    @Override
                    public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error) {

                        Error = response.toString();
                        try {
                            Error = jsonError(Error);
                            alertOk("Error",Error);
                            resetLogin();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void GetUser() {

        local = new LocalDatabase(this);
        local.openDatabase();
        //   local.openDatabase();

        ApiInvoker.getResponse(GetTableURL + "Users" + "?filter=Email=" + login.getEmail() + "",
                AppConstants.TokenHeader + mySessionId + "\n" + AppConstants.APIKey, null, new ApiInvoker.OnJSONResponseCallback() {
                    @Override
                    public void onJSONSuccessResponse(boolean success, JSONObject response) throws JSONException, ApiException {

                        result = response.toString();
                        if (result.length()>15)
                        {

                            user = ((User) JSONParse.parseJSON(result, User.class).get(0));
                         /*   loc = new Loc();
                            loc.setCoordinates(getLoc(currentlocation.getLongitude(),currentlocation.getLatitude()));
                            loc.setType("Point");
                            user.setLoc(loc);
*/
                          //  updateUser();

                         //   JSONArray employees = response.getJSONArray("resource");

                        }
                        // if no data found just go to main activity
                        else {
                            alertOk("Error", "Please login again.");
                            resetLogin();
                        }
                    }

                    @Override
                    public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error) {

                        Error = response.toString();
                        try {
                            Error = jsonError(Error);
                            alertOk("Error",Error);
                            resetLogin();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void updateUser(){

        loc = new Loc();
        loc.setCoordinates(getLoc(mCurrentLocation.getLongitude(),mCurrentLocation.getLatitude()));
        loc.setType("Point");
        user.setLoc(loc);
       // JSONObject mgrid = new JSONObject();
        try {
           // mgrid.put("MgrID",login.getEmail());
           // String json = "{\"resource\":["+mgrid.toString()+"]}";

          //  StringEntity entity = new StringEntity(json);

            //    String json2 = "{\"resource\":["+mgrid+"]}";

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(user);
            StringEntity userEntity = new StringEntity(json);

            ApiInvoker.patch(GetTableURL + "users" + "/" + user.getId(),
                    AppConstants.TokenHeader+ mySessionId + "\n" + APIKey, userEntity, ContentType,
                    new ApiInvoker.OnJSONResponseCallback() {
                        @Override
                        public void onJSONSuccessResponse(boolean success, JSONObject response) {

                            result = response.toString();
                            Toast.makeText(getActivity(),"Succeded",Toast.LENGTH_SHORT).show();
                           // searchbox.setText("");
                        }

                        @Override
                        public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error) {
                            result = response.toString();
                            Toast.makeText(getActivity(),"Failure",Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    public String jsonError(String res) throws JSONException {



        JSONObject jsonObj = new JSONObject(res);
        JSONObject error  = jsonObj.getJSONObject("error");
        res = error.getString("message");

    /*    JSONObject jso = (JSONObject) jsonObj;
        //JSONObject company = (JSONObject) jso.get("error");
        JSONArray jsonArray = (JSONArray) jso.get("error");
        JSONObject val = (JSONObject) jsonArray.get(0);
        res = val.get("message").toString();*/
        return res;
    }


    public void LoginUser(String userID, String UserPass)
    {
        if(!userID.isEmpty() || !UserPass.isEmpty() ) {
            login.setEmail(userID);
            login.setPassword(UserPass);

            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(login);
            StringEntity LoginEntity = null;
         //   currentlocation = mapFragment.mCurrentLocation;
            try {
                LoginEntity = new StringEntity(json);
                ApiInvoker.Post(SessionURL, APIKey, LoginEntity, ContentType, new ApiInvoker.OnJSONResponseCallback() {
                    @Override
                    public void onJSONSuccessResponse(boolean success, JSONObject response) throws JSONException {

                        result = response.toString();
                        if (result.length() > 15) {

                            try {
                                mySession = (Session) ApiInvoker.deserialize(result, "",
                                        Session.class);
                                mySessionId = mySession.getSession_id().toString();

                                if (mySession != null) {



                                    GetUser();

                                    GetUserInfo();    // get all users that has MGRID == loginID
                                      //  user = ()
                               /*         if (!googleApiClient.isConnected())
                                            googleApiClient.connect();*/

                                    initApp();

                                } else {
                                    alertOk("Login Error", ApiInvoker.response + " Please retry.");
                                    resetLogin();
                                }

                            } catch (ApiException e) {
                                e.printStackTrace();
                            }

                        } else {
                            alertOk("Info", "Please try again.");
                            resetLogin();
                        }

                    }

                    @Override
                    public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error) {
                    //    Error = response.toString();
                        //   Error = jsonError(Error);
                        alertOk("Error", "Try Again");
                        resetLogin();
                    }
                });


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else {
            alertOk("Error", "Enter UserID or Password");
            resetLogin();
        }
    }


    public void RegisterUser() {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(registerUser);
        try {
            StringEntity userEntity = new StringEntity(json);

        ApiInvoker.Post("https://df-track-your-circle.enterprise.dreamfactory.com/api/v2/user/register", AppConstants.TokenHeader + mySessionId +
                "\n" + AppConstants.APIKey, userEntity, AppConstants.ContentType, new ApiInvoker.OnJSONResponseCallback() {
            @Override
            public void onJSONSuccessResponse(boolean success, JSONObject response) throws JSONException {

                result = response.toString();
                if (result != null) {

                    PostUser();
                  //  resetLogin();

                }

            }

            @Override
            public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error) {
                result = response.toString();
                try {
                    Error = jsonError(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertOk("Error", Error);
                resetSignUp();
            }
        });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void PostUser()
    {
        loc = new Loc();
        loc.setCoordinates(getLoc(0,0));
        loc.setType("Point");
        user.setLoc(loc);
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(user);
        String json2 = "{\"resource\":["+json+"]}";

        StringEntity userEntity = null;
        try {
            userEntity = new StringEntity(json2);


            ApiInvoker.Post(GetTableURL+ "users", TokenHeader + mySessionId + "\n" + APIKey,
                    userEntity, ContentType, new ApiInvoker.OnJSONResponseCallback() {
                        @Override
                        public void onJSONSuccessResponse(boolean success, JSONObject response) throws JSONException {

                            result = response.toString();
                            alertOk("Alert","You are registered Sucessfully");
                        }

                        @Override
                        public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error) {
                            result = response.toString();
                            alertOk("Error","Please retry");
                            resetSignUp();
                        }
                    });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void resetLogin()
    {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                 //   getActivity().invalidateOptionsMenu();

                    ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.INVISIBLE);

                    Button btnLogin = (Button) activity.findViewById(R.id.Login);
                    btnLogin.setVisibility(View.VISIBLE);

                   /* Button register = (Button) activity.findViewById(R.id.registerbtn);
                    register.setVisibility(View.VISIBLE);*/
                }


        });
    }

    public void resetSignUp()
    {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //   getActivity().invalidateOptionsMenu();

                ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.signup_progressBar);
                progressBar.setVisibility(View.INVISIBLE);

                Button btnsignup = (Button) activity.findViewById(R.id.btn_signup);
                btnsignup.setVisibility(View.VISIBLE);

                   /* Button register = (Button) activity.findViewById(R.id.registerbtn);
                    register.setVisibility(View.VISIBLE);*/
            }


        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mCurrentLocation == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
