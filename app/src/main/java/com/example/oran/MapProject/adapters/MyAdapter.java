package com.example.oran.MapProject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oran.MapProject.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // The list of names which will appear in the RecyclerView
    private String[] namesList;

    private View.OnClickListener listener;

    // Just get the names and save them in a class variable
    public MyAdapter(String[] namesList, View.OnClickListener listener) {
        this.namesList = namesList;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_view, parent, false);

        v.setOnClickListener(this.listener);

        // Create a wrapper class which interacts with the recycler view on one hand
        // and holds your view single item view on the other hand
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.addPersonName(namesList[position]);
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return namesList.length;
    }


    // This inner class, holds the layout of a single item
    // THIS IS A WRAPPER CLASS
    // THIS CLASS INTERACTS WITH THE RECYCLER VIEW ON ONE SIDE, AND HOLDS YOUR
    // ACTUAL UI ITEM (WHICH YOU'VE DEFINED AND CREATED) ON THE OTHER SIDE (CLASS VARIABLE)
    public class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View singleItemView;

        // Ctor
        public MyViewHolder(View singleItemView) {
            super(singleItemView);
            // Stroring the ACTUAL item, wrapped by the ReyclerView view holder
            this.singleItemView = singleItemView;
        }

        public void addPersonName(String name) {
            TextView textView = (TextView) singleItemView.findViewById(R.id.user_name);
            textView.setText(name);
        }


    }
}
