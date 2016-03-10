package com.example.hyder.tuc;

import android.app.FragmentManager;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Profile extends Fragment {

    TextView name,email,company,rating;
    String Name,Email,Company,Rating;

    //private OnFragmentInteractionListener mListener;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) view.findViewById(R.id.name);
        email= (TextView) view.findViewById(R.id.email);
        company = (TextView) view.findViewById(R.id.company);
        rating = (TextView) view.findViewById(R.id.rating);
        Email = getArguments().getString("email");
        final LocalDatabase localdb = new LocalDatabase(getActivity());
        localdb.openDatabase();

        Cursor c =localdb.getProfile(Email);

        if (c.moveToFirst())
        {//c.moveToNext();
            do {
                name.setText (c.getString(0)+" "+c.getString(1));
                email.setText(c.getString(2));
                company.setText(c.getString(3));
                rating.setText(c.getString(4));

            }while (c.moveToNext());

        }
        else{
           // Toast.makeText(getActivity().getApplicationContext(),"No Employee",Toast.LENGTH_LONG).show();
        }

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
                } else {
                    return false;
                }
            }
        });
        return view;

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
