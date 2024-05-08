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
import POJOS.ExcepcionAMSound;
import POJOS.Usuario;
import POJOS.Voz;
import es.amsound.R;

public class AdaptadorRVIntegrantes extends RecyclerView.Adapter<AdaptadorRVIntegrantes.MyViewHolder> {

    private Context c;
    private ArrayList<Usuario> datos;
    private Agrupacion agrupacionPerteneciente;

    public AdaptadorRVIntegrantes(Context c, ArrayList<Usuario> datos, Agrupacion agrupacion) {
        this.c = c;
        this.datos = datos;
        this.agrupacionPerteneciente = agrupacion;
    }

    @NonNull
    @Override
    public AdaptadorRVIntegrantes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(c);
        View v = inf.inflate(R.layout.model_lista_agrupaciones, parent, false);
        return new AdaptadorRVIntegrantes.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRVIntegrantes.MyViewHolder holder, int position) {
        holder.imagen.setImageResource(R.drawable.ic_menu_camera);
        holder.titulo.setText(datos.get(position).getNombre());
        holder.subtitulo.setText(datos.get(position).getMail());
        holder.integrante = datos.get(position);
        holder.agrupacionPerteneciente = this.agrupacionPerteneciente;
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagen;
        private TextView titulo, subtitulo;
        private CardView cardV;
        private Usuario integrante;
        private Agrupacion agrupacionPerteneciente;
        private ArrayList<Voz> listaVoces;
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

                        Thread t1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
                                    listaVoces = c.leerVocesDeUsuario(integrante.getId(), agrupacionPerteneciente.getId());

                                } catch (ExcepcionAMSound ex) {

                                }
                            }
                        });

                        t1.start();
                        t1.join();

                        Intent intent= new Intent (c, es.amsound.PanelUsuario.class);
                        intent.putExtra("Usuario", integrante);
                        intent.putExtra("AgrupacionDeUsuario", agrupacionPerteneciente);
                        intent.putExtra("VocesDeUsuario", listaVoces);
                        c.startActivity(intent);

                    }catch (Exception ex){
                        Log.d("INTENT ERROR", ex.getMessage());
                    }
                }
            });
        }
    }
}
