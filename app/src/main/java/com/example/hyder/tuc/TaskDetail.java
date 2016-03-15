package com.example.hyder.tuc;


import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TaskDetail extends Fragment {

    TextView subject,summary,startdate,enddate,assignedby,assignedto,status;
    String ID;



    public TaskDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        final LocalDatabase localdb = new LocalDatabase(getActivity());
        localdb.openDatabase();
        ID = getArguments().getString("ID");

        subject = (TextView) view.findViewById(R.id.subject);
        summary = (TextView) view.findViewById(R.id.summary);
        startdate = (TextView) view.findViewById(R.id.start_date);
        enddate = (TextView) view.findViewById(R.id.end_date);
        assignedto = (TextView) view.findViewById(R.id.assignedTo);
        assignedby = (TextView) view.findViewById(R.id.assignedBy);
        status = (TextView) view.findViewById(R.id.status);

        Cursor c = localdb.getTask(ID);
        if (c.moveToFirst())
        {//c.moveToNext();
            do {
                subject.setText (c.getString(0));
                summary.setText(c.getString(1));
                assignedby.setText(c.getString(2));
                assignedto.setText(c.getString(3));
                startdate.setText(c.getString(4));
                enddate.setText(c.getString(5));
                status.setText(c.getString(6));

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

}
