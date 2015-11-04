package lconde.vetech;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>
{

    private ArrayList<Evento> eventos;
    private int itemLayout;

    public EventAdapter(ArrayList<Evento> data, int itemLayout)
    {
        eventos= data;
        this.itemLayout=itemLayout;
    }




    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder viewHolder, int position)
    {
        Evento event= eventos.get(position);
        viewHolder.name.setText(event.getTitulo());
        viewHolder.fecha.setText(event.getFecha());
        viewHolder.lugar.setText(event.getLugar());

        switch (event.getId()){
            case 1:
                viewHolder.image.setImageResource(R.drawable.dogchow);
                break;
            case 2:
                viewHolder.image.setImageResource(R.drawable.caninata);
                break;
            case 3:
                viewHolder.image.setImageResource(R.drawable.expos);
                break;
            case 4:
                viewHolder.image.setImageResource(R.drawable.jornada);
                break;
            case 5:
                viewHolder.image.setImageResource(R.drawable.pedigree);
                break;
        }
        viewHolder.itemView.setTag(event);

    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener
    {

        public ImageView image;
        public TextView name;
        public TextView fecha;
        public TextView lugar;

        public ViewHolder(View itemView) {

            super(itemView);

            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.titulo);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            lugar = (TextView) itemView.findViewById(R.id.lugar);
        }

        @Override
        public void onClick(View view)
        {
            //Intent intent = new Intent(view.getContext(), Bio.class);
            //view.getContext().startActivity(intent);
        }

    }
}
