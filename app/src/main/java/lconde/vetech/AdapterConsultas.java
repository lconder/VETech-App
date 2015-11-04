package lconde.vetech;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class AdapterConsultas extends RecyclerView.Adapter<AdapterConsultas.ViewHolder>
{
    private ArrayList<Consulta> consultas;
    private int itemLayout;

    public AdapterConsultas(ArrayList<Consulta> consultas, int itemLayout )
    {
        this.consultas=consultas;
        this.itemLayout=itemLayout;


    }

    @Override
    public AdapterConsultas.ViewHolder onCreateViewHolder(ViewGroup parent, int i)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterConsultas.ViewHolder viewHolder, int position)
    {
        Consulta consult=consultas.get(position);
        viewHolder.diagnostico.setText(consult.getDiagnostico());
        viewHolder.receta.setText(consult.getReceta());

        viewHolder.itemView.setTag(consult);

    }

    @Override
    public int getItemCount() {
        return consultas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener
    {


        public ImageView icono;
        public TextView diagnostico;
        public TextView receta;

        public ViewHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);
            icono = (ImageView) itemView.findViewById(R.id.icon);
            diagnostico = (TextView) itemView.findViewById(R.id.diagnostico);
            receta = (TextView) itemView.findViewById(R.id.receta);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
