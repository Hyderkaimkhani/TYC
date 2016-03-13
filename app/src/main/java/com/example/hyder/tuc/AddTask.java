package com.example.hyder.tuc;

/**
 * Created by Hyder on 2/20/2016.
 */
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import TYC.AppConstants;
import TYC.Loc;
import TYC.Task;
import TYCAPI.ApiInvoker;
import cz.msebera.android.httpclient.entity.StringEntity;

public class AddTask extends Fragment {
    EditText subject,summary,assigned_to,start_date,end_date;
    Button submit;
    String Subject,Summary,AssignedTo,StartDate,EndDate,result;
    ProgressBar progressBar;
    Task task;
    Global global;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_task, container, false);

        global = (Global) getActivity().getApplication();
        global.setActivity(getActivity());
        global.setContext(getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        subject = (EditText) rootView.findViewById(R.id.subject);
        summary = (EditText) rootView.findViewById(R.id.summary);
        assigned_to = (EditText) rootView.findViewById(R.id.assignedTo);
        end_date = (EditText) rootView.findViewById(R.id.enddate);
        start_date = (EditText) rootView.findViewById(R.id.startdate);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar2);

        submit = (Button) rootView.findViewById(R.id.btn_addtask);

        progressBar.setVisibility(View.INVISIBLE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                submit.setVisibility(View.INVISIBLE);

                Subject = subject.getText().toString();
                Summary = summary.getText().toString();
                AssignedTo = assigned_to.getText().toString();
                StartDate = start_date.getText().toString();
                EndDate = end_date.getText().toString();

                task = new Task();
                task.setSubject(Subject);
                task.setSummary(Summary);
                task.setAssignedTo(AssignedTo);
                task.setAssignedBy(global.login.getEmail());
                task.setDate(StartDate);
                task.setEndDate(EndDate);
                task.setStatus("Assigned");

                AddTask();
            }
        });



        return rootView;
    }

    public void AddTask()
    {
        /*loc = new Loc();
        loc.setCoordinates(getLoc(0,0));
        loc.setType("Point");
        user.setLoc(loc);*/
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(task);
        String json2 = "{\"resource\":["+json+"]}";

        StringEntity userEntity = null;
        try {
            userEntity = new StringEntity(json2);


            global.ApiInvoker.Post(AppConstants.GetTableURL+ "tasks", AppConstants.TokenHeader + global.mySessionId + "\n" + AppConstants.APIKey,
                    userEntity, AppConstants.ContentType, new ApiInvoker.OnJSONResponseCallback() {
                        @Override
                        public void onJSONSuccessResponse(boolean success, JSONObject response) throws JSONException {

                            result = response.toString();
                            submit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            global.alertOk("Success", "Your task is added successfully Sucessfully");

                            subject.setText("");
                            summary.setText("");
                            assigned_to.setText("");
                            start_date.setText("");
                            end_date.setText("");
                        }

                        @Override
                        public void onJSONFailureResponse(boolean success, JSONObject response, int statusCode, Throwable error) {
                            result = response.toString();
                            submit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            global.alertOk("Error","Task is not added.Please retry");

                            //resetSignUp();
                        }
                    });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}