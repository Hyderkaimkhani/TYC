package com.example.hyder.tuc;

/**
 * Created by Hyder on 2/20/2016.
 */
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import TYC.Login;

public class  ViewMember extends Fragment {

  //  Login login = new Login();
    String email;
    Global global;
    ArrayList<String> Name = new ArrayList<>();
    ArrayList<String> Email = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.view_member, container, false);

        final LocalDatabase localdb = new LocalDatabase(getActivity());

        localdb.openDatabase();

     //   email = login.getEmail();
     //   getActivity().global

        global = (Global) getActivity().getApplication();
        global.setActivity(getActivity());
        global.setContext(getActivity());

        email = global.login.getEmail();

        Cursor c = global.local.getEmployees(email);

        if (c.moveToFirst())
        {//c.moveToNext();
            do {
                Name.add (c.getString(0)+" "+c.getString(1));
                Email.add(c.getString(2));
               /* int i = c.getInt(2);
                Log.d("Job on date",""+i);*/

                //fare.add (c.getString(5));
                //     i++;
                //DisplayContact(c);
            }while (c.moveToNext());
            //   textView.setText(dates[i-1]);
            DisplayList();
            // DisplayContact();
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"No Employee",Toast.LENGTH_LONG).show();
        }

        return rootView;
    }
    public void DisplayList()
    {

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview,Name);

        ListView listView = (ListView) getActivity().findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}