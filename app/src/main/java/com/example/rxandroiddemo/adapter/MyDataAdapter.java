package com.example.rxandroiddemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rxandroiddemo.R;
import com.example.rxandroiddemo.model.Android;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altaf.sil on 12/14/17.
 */

public class MyDataAdapter extends  RecyclerView.Adapter<MyDataAdapter.MyViewHolder> {

    private List<Android> aList;

    private Context mContext;

    public MyDataAdapter(Context context) {
        this.mContext = context;
        this.aList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Android android = aList.get(position);
        holder.tvName.setText(android.getName());
        holder.tvApi.setText(android.getApi());
        holder.tvVersion.setText(android.getVer());
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }


    public void updateList(List<Android> andLists){
       aList.addAll(andLists);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvApi, tvVersion;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvApi = (TextView) view.findViewById(R.id.tvApi);
            tvVersion = (TextView) view.findViewById(R.id.tvVersion);
        }
    }


}
