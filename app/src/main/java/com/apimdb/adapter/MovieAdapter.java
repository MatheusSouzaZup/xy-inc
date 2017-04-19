package com.apimdb.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.apimdb.ExtendActivity;
import com.apimdb.MyApplication;
import com.apimdb.R;
import com.apimdb.domain.Movie;
import com.apimdb.persistencia.Controller;
import java.io.ByteArrayOutputStream;
import java.util.List;


/**
 * Created by Matheus on 19/03/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public static final String KEY_IMDBID = "imdbid";
    private List<Movie> myList;
    private LayoutInflater myLayoutInflater;
    private Context context;
    private Controller controllerDB;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;

    public MovieAdapter(List<Movie> l, Context c) {
        myList = l;
        myLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = c;
        controllerDB = new Controller(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = myLayoutInflater.inflate(R.layout.item_movie_card, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        mRequestQueue = MyApplication.getInstance().getRequestQueue();
        imageLoader = MyApplication.getInstance().getImageLoader();
            imageLoader.get(myList.get(position).getPoster(), ImageLoader.getImageListener(holder.networkImageView, 250, 250));
            holder.networkImageView.setImageUrl(myList.get(position).getPoster(), imageLoader);
            holder.networkImageView.setDefaultImageResId(R.drawable.notfound);
            holder.networkImageView.setErrorImageResId(R.drawable.notfound);



        holder.tvTitle.setText(myList.get(position).getTitle());
        holder.networkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(setupValues(holder,position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return myList.size();
    }
    public Intent setupValues(MyViewHolder holder,int position) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMDBID, myList.get(position).getImdbID());
        bundle.putInt("contextint",1);
        bundle.putInt("pos", position);
        bundle.putString("title", myList.get(position).getTitle());
        bundle.putSerializable("image", setupImage(holder));
        Intent intent = new Intent(context, ExtendActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
    public byte[] setupImage( MyViewHolder holder) {
        Drawable drawable;
        Bitmap bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        drawable = holder.networkImageView.getDrawable();
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        final byte[] bitMapData = stream.toByteArray();
        return bitMapData;
    }
    public void addListItem(Movie f, int position) {
        if (myList.contains(f) == false) {
            myList.add(f);
        }
        notifyItemInserted(position);
    }

}
