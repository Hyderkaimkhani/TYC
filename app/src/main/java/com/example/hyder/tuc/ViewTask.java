package com.example.hyder.tuc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import TYC.AppConstants;
import TYCAPI.ApiInvoker;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTask extends Fragment {

    LocalDatabase local;
    Global global;
    String result;

    public ViewTask() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_task, container, false);
        global = (Global) getActivity().getApplication();
        global.setActivity(getActivity());
        global.setContext(getActivity());


        return view;
    }
    public void GetUserInfo() {

        local = new LocalDatabase(getActivity());
        local.openDatabase();
        //   local.openDatabase();

        global.ApiInvoker.getResponse(AppConstants.GetTableURL + "tasks" + "?filter=AssignedTo=" + global.login.getEmail()+" or AssignedBy="+global.login.getEmail()+"",
                AppConstants.TokenHeader + global.mySessionId + "\n" + AppConstants.APIKey, null, new ApiInvoker.OnJSONResponseCallback() {
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


                        String Error = response.toString();
                        try {
                            Error = global.jsonError(Error);
                            global.alertOk("Error",Error);
                            //resetLogin();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

}
