package com.example.hyder.tuc;

/**
 * Created by Hyder on 2/20/2016.
 */
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import TYC.Login;

public class  ViewMember extends Fragment {

  //  Login login = new Login();
    String email;
    Global global;
    ArrayList<String> Name;
    ArrayList<String> Email;
    ListView listView;
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
        listView = (ListView) rootView.findViewById(R.id.listView);
        Name = new ArrayList<>();
        Email = new ArrayList<>();

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
            global.GetUserInfo();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = Email.get(position).toString();
                global.selectedmember = email;
                Bundle bundle = new Bundle();

                bundle.putString("email",email);
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Profile profile = new Profile();
                profile.setArguments(bundle);
                fragmentTransaction.replace(R.id.view_member,profile);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
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

        return rootView;
    }
    public void DisplayList()
    {

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview,Name);

        //ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}