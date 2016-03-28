package com.example.hyder.tuc;

/**
 * Created by Hyder on 2/20/2016.
 */
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import TYC.AppConstants;
import TYCAPI.ApiInvoker;
import cz.msebera.android.httpclient.entity.StringEntity;

public class addmember extends Fragment {

    EditText searchbox;
    Button button;
    String email,result;
    Global global;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.addmember, container, false);


        global = (Global) getActivity().getApplication();
        global.setActivity(getActivity());
        global.setContext(getActivity());
        searchbox = (EditText) rootView.findViewById(R.id.searchbox);
        button = (Button) rootView.findViewById(R.id.btn_search);

        global.updateUser();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = searchbox.getText().toString();
               patchMgrID(email);

            }
        });

        return rootView;
    }

    public void patchMgrID(String email)
    {
        JSONObject mgrid = new JSONObject();
        try {
            mgrid.put("MgrID",global.login.getEmail());
            String json = "{\"resource\":["+mgrid.toString()+"]}";

            StringEntity entity = new StringEntity(json);

            //    String json2 = "{\"resource\":["+mgrid+"]}";

            global.ApiInvoker.patch(AppConstants.GetTableURL + "users" +"?filter=Email=" + email,
                    AppConstants.TokenHeader + global.mySessionId + "\n" + AppConstants.APIKey, entity, AppConstants.ContentType,
                    new ApiInvoker.OnJSONResponseCallback() {
                        @Override
                        public void onJSONSuccessResponse(boolean success, JSONObject response) {

                            result = response.toString();
                            Toast.makeText(getActivity(),"Succeded",Toast.LENGTH_SHORT).show();
                            searchbox.setText("");
                            global.GetUserInfo();
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

    }
}