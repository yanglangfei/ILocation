package com.yf.ilocation.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yf.ilocation.R;
import com.yf.ilocation.model.Place;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */

public class PAdapter extends RecyclerView.Adapter<PAdapter.MyHolder> {


    private final List<Place> places;
    private final Context context;

    public PAdapter(List<Place> places, Context context) {
        this.places=places;
        this.context=context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ui_place_item,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        View view=holder.itemView;
        TextView name= (TextView) view.findViewById(R.id.tv_name);
        TextView address= (TextView) view.findViewById(R.id.tv_address);
        TextView phone= (TextView) view.findViewById(R.id.tv_phone);
        TextView tv_dis= (TextView) view.findViewById(R.id.tv_dis);
        name.setText(places.get(position).getName());
        address.setText(places.get(position).getAddress());
        phone.setText("联系方式:"+places.get(position).getTel());
        tv_dis.setText(places.get(position).getDistance()+"米");
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class  MyHolder extends  RecyclerView.ViewHolder{
        View itemView;

        public MyHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
        }
    }
}
