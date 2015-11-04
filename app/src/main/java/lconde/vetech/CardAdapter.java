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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>
{

    ArrayList<Perros> perros;
    private int itemLayout;
    Activity context;
    int tab;


    public CardAdapter(ArrayList<Perros> data, int itemLayout, Activity context,int tab)
    {
        perros =data;
        this.itemLayout=itemLayout;
        this.context=context;
        this.tab = tab;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(itemLayout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v,this.tab);
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
        viewHolder.sexo = perro.isSexo();
        viewHolder.adoptado = perro.isAdoptado();
        viewHolder.perdido = perro.isPerdido();
        viewHolder.id_perro = perro.getIdPerro();
        viewHolder.id_dueno = perro.getDueno();
        viewHolder.consultas=perro.getConsultas();
        viewHolder.descripcion=perro.getDescripcion();
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
        public Boolean sexo;
        public Boolean adoptado;
        public Boolean perdido;
        public String id_perro;
        public String descripcion;
        public ArrayList<Consulta> consultas;
        int tab;
        public String id_dueno;


        public ViewHolder(View itemView, int tab)
        {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvMovie = (TextView)itemView.findViewById(R.id.tv_movie);
            itemView.setOnClickListener(this);
            this.tab = tab;
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = null;
            switch (this.tab)
            {
                case 1:
                    intent = new Intent(v.getContext(), Bio.class);
                    break;
                case 2:
                    intent = new Intent(v.getContext(), perdido.class);
                    break;
                case 3:
                    intent = new Intent(v.getContext(), adoptado.class);
                    break;
                default:
                    break;

            }

            intent.putExtra("Nombre",nombre);
            intent.putExtra("Imagen",imagen);
            intent.putExtra("Raza",raza);
            intent.putExtra("Sexo",sexo);
            intent.putExtra("Adoptado",adoptado);
            intent.putExtra("Perdido",perdido);
            intent.putExtra("Id_Perro", id_perro);
            intent.putExtra("Descripcion",descripcion);
            intent.putExtra("IdDueno",id_dueno);
            intent.putParcelableArrayListExtra("consultas", consultas);
            v.getContext().startActivity(intent);
        }

    }


}