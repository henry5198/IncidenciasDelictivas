package com.example.IncidenciasDelictivas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


class AdaptadorNoticias extends ArrayAdapter<Noticias> {


    public AdaptadorNoticias(Context context, ArrayList<Noticias> datos) {
        super(context, R.layout.ly_gridle, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.ly_gridle, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
        lblTitulo.setText(getItem(position).getTitulo());

        TextView lblSubtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
        lblSubtitulo.setText(getItem(position).getSubtitulo());

        TextView lblFecha = (TextView)item.findViewById(R.id.LblFechayCategoria);
        lblFecha.setText(getItem(position).getFecha());

        ImageView imageView = (ImageView)item.findViewById(R.id.imgNoticia);
        Glide.with(this.getContext())
               .load(getItem(position).getUrl())
                //.error(R.drawable.imgnotfound)
                .into(imageView);

        return(item);
    }
}
