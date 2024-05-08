package es.amsound.ui.utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import POJOS.Recurso;
import es.amsound.R;

public class AdaptadorRVRecurso extends RecyclerView.Adapter<AdaptadorRVRecurso.MyViewHolder> {
    private Context c;
    private ArrayList<Recurso> datos;

    public AdaptadorRVRecurso(Context c, ArrayList<Recurso> datos) {
        this.c = c;
        this.datos = datos;
    }

    @NonNull
    @Override
    public AdaptadorRVRecurso.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(c);
        View v = inf.inflate(R.layout.model_lista_recursos, parent, false);
        return new AdaptadorRVRecurso.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRVRecurso.MyViewHolder holder, int position) {

        String urlFoto = datos.get(position).getRuta();
        holder.imagen.setImageDrawable(c.getResources().getDrawable(R.drawable.ic_menu_gallery) );
        //holder.video.setVideoPath();
        holder.titulo.setText(datos.get(position).getTitulo());
        holder.subtitulo.setText(datos.get(position).getTexto());
        holder.recursos = datos.get(position);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagen;
        private VideoView video;
        private TextView titulo, subtitulo;
        private CardView cardV;
        private Recurso recursos;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imageView2);
            video = itemView.findViewById(R.id.videoView);
            titulo = itemView.findViewById(R.id.tituloAgrupacion);
            subtitulo = itemView.findViewById(R.id.subtituloAgrupacion);
            cardV = itemView.findViewById(R.id.cardViewModeloRecurso);
        }
    }
}
