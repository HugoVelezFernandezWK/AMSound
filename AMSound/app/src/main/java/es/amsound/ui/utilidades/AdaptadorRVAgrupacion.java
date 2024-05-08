package es.amsound.ui.utilidades;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import POJOS.Agrupacion;
import es.amsound.R;

public class AdaptadorRVAgrupacion extends RecyclerView.Adapter<AdaptadorRVAgrupacion.MyViewHolder> {
    private Context c;
    private ArrayList<Agrupacion> datos;

    public AdaptadorRVAgrupacion(Context c, ArrayList<Agrupacion> datos) {
        this.c = c;
        this.datos = datos;
    }

    @NonNull
    @Override
    public AdaptadorRVAgrupacion.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(c);
        View v = inf.inflate(R.layout.model_lista_agrupaciones, parent, false);
        return new AdaptadorRVAgrupacion.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRVAgrupacion.MyViewHolder holder, int position) {
        //holder.imagen.setImageURI(Uri.parse(datos.get(position).getFoto()));;
        String urlFoto = datos.get(position).getFoto();

        if(urlFoto==null){
            holder.imagen.setImageDrawable(c.getResources().getDrawable(R.drawable.ic_menu_gallery) );
        }else{
            holder.imagen.setImageDrawable(c.getResources().getDrawable(Integer.parseInt(datos.get(position).getFoto())) );
        }

        holder.titulo.setText(datos.get(position).getNombre());
        holder.subtitulo.setText(datos.get(position).getContacto());
        holder.agrupacion = datos.get(position);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagen;
        private TextView titulo, subtitulo;
        private CardView cardV;
        private Agrupacion agrupacion;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenAgrupacion);
            titulo = itemView.findViewById(R.id.tituloAgrupacion);
            subtitulo = itemView.findViewById(R.id.subtituloAgrupacion);

            cardV = itemView.findViewById(R.id.cardViewModeloRecurso);
            cardV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent= new Intent (c, es.amsound.Agrupacion.class);
                        intent.putExtra("DatosAgrupacion", agrupacion);
                        c.startActivity(intent);

                    }catch (Exception ex){
                        Log.d("INTENT ERROR", ex.getMessage());
                    }
                }
            });
        }
    }
}
