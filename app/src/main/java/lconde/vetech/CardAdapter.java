package lconde.vetech;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    ArrayList<Perros> perros;
    private int itemLayout;
    Activity context;


    public CardAdapter(ArrayList<Perros> data, int itemLayout, Activity context)
    {
        perros =data;
        this.itemLayout=itemLayout;
        this.context=context;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(itemLayout, viewGroup, false);



        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        Perros perro = perros.get(i);
        viewHolder.tvMovie.setText(perro.getNombre());
        Picasso.with(context).load(perro.getPhoto()).into(viewHolder.imgThumbnail);
        viewHolder.nombre=perro.getNombre();
        viewHolder.imagen = perro.getPhoto();
        viewHolder.raza = perro.getRaza();
        viewHolder.sexo = perro.getSexo();
    }

    @Override
    public int getItemCount() {
        return perros.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener
    {

        public ImageView imgThumbnail;
        public TextView tvMovie;
        public String nombre;
        public String imagen;
        public  String raza;
        public String sexo;


        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvMovie = (TextView)itemView.findViewById(R.id.tv_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(v.getContext(), Bio.class);
            intent.putExtra("Nombre",nombre);
            intent.putExtra("Imagen",imagen);
            intent.putExtra("Raza",raza);
            intent.putExtra("Sexo",sexo);
            //Toast.makeText(v.getContext(),info,Toast.LENGTH_SHORT).show();
            v.getContext().startActivity(intent);
        }

    }


}