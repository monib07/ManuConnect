package com.example.monib.manuconnect;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row,parent,false);
        return new ViewHoldder(v);
    }

    @Override
    public void onBindViewHolder(ViewHoldder holder, int position) {

        DataObject dataObject=list.get(position);
        holder.title.setText(dataObject.getTitle());
        holder.desc.setText(dataObject.getDesciption());

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHoldder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView desc;

        public ViewHoldder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.textView1);
            desc=(TextView)itemView.findViewById(R.id.textView2);

        }
    }

}
