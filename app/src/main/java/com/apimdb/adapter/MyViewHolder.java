package com.apimdb.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Network;
import com.android.volley.toolbox.NetworkImageView;
import com.apimdb.R;

/**
 * Created by ZUP on 20/03/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView tvTitle;
    public NetworkImageView networkImageView;
    public MyViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.my_title);
        networkImageView = (NetworkImageView) itemView.findViewById(R.id.my_image);
    }
}