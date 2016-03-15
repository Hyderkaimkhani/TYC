package com.example.hyder.tuc;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import TYC.AppConstants;
import TYCAPI.ApiInvoker;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTask extends Fragment {

    LocalDatabase local;
    Global global;
    String result;
    ListView listView;
    ArrayList<String> Subject;
    ArrayList<String> ID;

    public ViewTask() {
       // Global global;
        /*global = (Global) getActivity().getApplication();
        global.setActivity(getActivity());
        global.setContext(getActivity());

        local = new LocalDatabase(getActivity());
        local.openDatabase();
       GetUserInfo();*/
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
        listView = (ListView) view.findViewById(R.id.listView2);

        Subject  = new ArrayList<>();
        ID  = new ArrayList<>();
        GetUserInfo();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Id = ID.get(position).toString();
                global.selectedmember = Id;
                Bundle bundle = new Bundle();

                bundle.putString("ID",Id);
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TaskDetail taskDetail = new TaskDetail();
                taskDetail.setArguments(bundle);
                fragmentTransaction.replace(R.id.taskview,taskDetail);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //  Log.i(tag, "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    //  Log.i(tag, "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        return view;
    }
    public void GetUserInfo() {

        local = new LocalDatabase(getActivity());
        local.openDatabase();
        //   local.openDatabase();

        global.ApiInvoker.getResponse(AppConstants.GetTableURL + "tasks" + "?filter=(AssignedTo=" + global.login.getEmail()+") or (AssignedBy="+global.login.getEmail()+")",
                AppConstants.TokenHeader + global.mySessionId + "\n" + AppConstants.APIKey, null, new ApiInvoker.OnJSONResponseCallback() {
                    @Override
                    public void onJSONSuccessResponse(boolean success, JSONObject response) throws JSONException {

                        String id,subject,summary,assigned_to,assigned_by,start_date,end_date,status;

                        result = response.toString();
                        if (result.length()>15)
                        {

                            //    user = ((User) JSONParse.parseJSON(result, User.class).get(0));


                            JSONArray tasks = response.getJSONArray("resource");
                            for (int i=0; i<tasks.length(); i++) {
                                JSONObject task = tasks.getJSONObject(i);
                                id = task.getString("_id");
                                subject = task.getString("Subject");
                                summary = task.getString("Summary");
                                assigned_to = task.getString("AssignedTo");
                                assigned_by = task.getString("AssignedBy");
                                start_date = task.getString("Date");
                                end_date = task.getString("EndDate");
                                status = task.getString("Status");


                                ID.add(id);
                                Subject.add(subject);

                                local.InsertTask(id,subject,summary,assigned_by,assigned_to,status,start_date,end_date);
                            }

                            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview,Subject);
                            listView.setAdapter(adapter);
                        }

         /*               if (result.length()>15)
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
                        startActivity(intent);*/

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
