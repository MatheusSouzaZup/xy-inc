package com.apimdb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.apimdb.R;

/**
 * Created by ZUP on 24/03/2017.
 */


public class ViewHolderSaved extends RecyclerView.ViewHolder {
    public ImageView imMovie;
    public TextView tvTitle;
    public ImageButton imageButton;

    public ViewHolderSaved(View itemView) {
        super(itemView);

        imMovie = (ImageView) itemView.findViewById(R.id.my_image_saved);
        tvTitle = (TextView) itemView.findViewById(R.id.my_title_saved);
        imageButton = (ImageButton) itemView.findViewById(R.id.imageButton_saved);
    }
}
