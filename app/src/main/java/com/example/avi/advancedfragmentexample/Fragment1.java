package com.example.avi.advancedfragmentexample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment1 extends Fragment {

    private IExternalActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_fragment1, container, false);
        Button button = (Button) fragmentView.findViewById(R.id.button1);

        // The fragment delegates the click event, to the activity
        // Because in this specific excercise, we've decided that the button's behavior context related
        // context related = the activity decides (the behavior can change in different activities)
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.setTextInTextView();

            }
        });
        return fragmentView;
    }

    @Override
    // The context is in fact the activity which hosts the fragment
    // This function is being called after the activity is being created
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof IExternalActivity) {
            // If the activity which holds the current fragment, obeys to the rules in the
            // "contract", defined in the interface ("IExternalActivity"), then we save a
            // reference to the external activity, in order to call it, each time the button
            // had been pressed
            this.parentActivity = (IExternalActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IExternalActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentActivity = null;
    }

    public interface IExternalActivity {
        void setTextInTextView();
    }
}
