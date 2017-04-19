package com.apimdb.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.apimdb.ExtendActivity;
import com.apimdb.R;
import com.apimdb.SavedActivity;
import com.apimdb.connection.Utils;
import com.apimdb.domain.Movie;
import com.apimdb.persistencia.Controller;
import com.apimdb.persistencia.CreateDataBase;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.apimdb.R.id.imageButton;

/**
 * Created by ZUP on 24/03/2017.
 */

public class MovieSavedAdapter extends RecyclerView.Adapter<ViewHolderSaved> {

    private List<Movie> myList;
    private LayoutInflater myLayoutInflater;
    private Context context;
    private Controller controller;
    private AlertDialog.Builder dialog;
    private boolean verify;
    public MovieSavedAdapter(List<Movie> l, Context c, boolean verify){
        myList = l;
        myLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = c;
        controller = new Controller(context);
        this.verify = verify;
    }
    @Override
    public ViewHolderSaved onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = myLayoutInflater.inflate(R.layout.item_movie_card_saved,parent,false);
        ViewHolderSaved mvh = new ViewHolderSaved(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final ViewHolderSaved holder, final int position) {

            Bitmap img = myList.get(position).getImagem();
            holder.imMovie.setImageBitmap(img != null ? img : Utils.getInstance().convert(context, R.drawable.notfound));
            holder.tvTitle.setText(myList.get(position).getTitle());


            holder.imMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(verify == true) {
                            context.startActivity(setupvalues(holder, position));
                        }
                        else{
                            context.startActivity(setupvaluesfromdefault(holder,position));
                        }
                }
            });
        if(verify==true) {
            holder.imageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Confirmar Exclusao");
                    dialog.setMessage("Confirma a Exclusao?");

                    dialog.setNegativeButton("Nao",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }
                    );
                    dialog.setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final String where = CreateDataBase.tabela.TITLE + "=" + "'" + myList.get(position).getTitle() + "'";
                                    controller.DeletaDados(CreateDataBase.NOME_TABELA, where);
                                    Toast.makeText(context, "Removed!", Toast.LENGTH_SHORT).show();
                                    ((SavedActivity) context).refresh();

                                }
                            });
                    dialog.create();
                    dialog.show();


                }
            });
        }
        else {
            holder.imageButton.setVisibility(View.GONE);
        }
    }
    private Intent setupvalues(ViewHolderSaved holder,int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("contextint", 2);
        bundle.putString("imdbid", myList.get(position).getImdbID());
        bundle.putString("title", myList.get(position).getTitle());
        bundle.putString("infos",myList.get(position).toString());
        bundle.putString("url", myList.get(position).toString());
        bundle.putSerializable("image", setupimage(holder));
        Intent intent = new Intent(context, ExtendActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
    private Intent setupvaluesfromdefault(ViewHolderSaved holder, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("contextint", 1);
        bundle.putString("imdbid", myList.get(position).getImdbID());
        bundle.putString("title", myList.get(position).getTitle());
        bundle.putString("infos",myList.get(position).toString());
        bundle.putSerializable("image", setupimage(holder));
        Intent intent = new Intent(context, ExtendActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
    private byte[] setupimage(ViewHolderSaved holder) {
        Drawable drawable;
        Bitmap bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        drawable = holder.imMovie.getDrawable();
        bitmap = ((BitmapDrawable)drawable).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
        final byte[] bitMapData = stream.toByteArray();
        return bitMapData;
    }
    @Override
    public int getItemCount() {
        return myList.size();
    }
    public void addListItem(Movie f, int position){
        notifyItemInserted(position);
    }


}
