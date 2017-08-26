package com.example.monib.manuconnect;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
import com.example.monib.manuconnect.R;

import java.util.List;

/**
 * Created by imsaiful on 26/7/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHoldder> {


    private List<DataObject> list;
    private Context context;

    public MyAdapter(List<DataObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHoldder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);
        return new ViewHoldder(v);
    }

    @Override
    public void onBindViewHolder(ViewHoldder holder, int position) {

        DataObject dataObject = list.get(position);
        holder.title.setText(dataObject.getTitle());
        holder.date.setText("Published Date: " + dataObject.getDate());
        holder.cardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            Intent intent=new Intent(context,PostDetails.class);
                            intent.putExtra("Link",DataObject.getDesciption());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }

                        catch (Exception e)
                        {
                            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoldder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public CardView cardView;

        public ViewHoldder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView1);
            date = (TextView) itemView.findViewById(R.id.textView3);
            cardView=(CardView)itemView.findViewById(R.id.card_view);


        }




    }
}
