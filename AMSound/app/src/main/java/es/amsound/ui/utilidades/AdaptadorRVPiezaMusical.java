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

import POJOS.PiezaMusical;
import es.amsound.R;

public class AdaptadorRVPiezaMusical extends RecyclerView.Adapter<AdaptadorRVPiezaMusical.MyViewHolder> {
    private Context c;
    private ArrayList<PiezaMusical> datos;

    public AdaptadorRVPiezaMusical(Context c, ArrayList<PiezaMusical> datos) {
        this.c = c;
        this.datos = datos;
    }

    @NonNull
    @Override
    public AdaptadorRVPiezaMusical.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(c);
        View v = inf.inflate(R.layout.model_lista_agrupaciones, parent, false);
        return new AdaptadorRVPiezaMusical.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRVPiezaMusical.MyViewHolder holder, int position) {
        holder.imagen.setImageResource(R.drawable.ic_menu_camera);
        holder.titulo.setText(datos.get(position).getNombre());
        holder.subtitulo.setText(datos.get(position).getAutor());
        holder.piezasMusicales = datos.get(position);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagen;
        private TextView titulo, subtitulo;
        private CardView cardV;
        private PiezaMusical piezasMusicales;
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
                        Intent intent= new Intent (c, es.amsound.PiezaMusical.class);
                        intent.putExtra("DatosPiezaMusical", piezasMusicales);
                        c.startActivity(intent);

                    }catch (Exception ex){
                        Log.d("INTENT ERROR", ex.getMessage());
                    }
                }
            });
        }
    }
}
