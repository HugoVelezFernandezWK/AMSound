package es.amsound.ui.agrupacion;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import POJOS.Agrupacion;
import POJOS.ExcepcionAMSound;
import POJOS.PiezaMusical;
import POJOS.Usuario;
import POJOS.Voz;
import es.amsound.R;
import es.amsound.ui.utilidades.AdaptadorRVIntegrantes;
import es.amsound.ui.utilidades.AdaptadorRVPiezaMusical;
import es.amsound.ui.utilidades.AdaptadorRVVoz;
import es.amsound.ui.utilidades.ClienteC;

public class AgrupacionFragment extends Fragment {

    private AgrupacionViewModel mViewModel;

    // View
    private RecyclerView rv;
    private TextView tituloAgrupacion, textoAgrupacion,  integrantesTV, vocesTV, repertorioTV;

    // Datos
    private Agrupacion agrupacion;
    private ArrayList<Usuario> datosIntegrantes;
    private ArrayList<Voz> datosVoces;
    private ArrayList<PiezaMusical> datosPiezasMusicales;

    public static AgrupacionFragment newInstance() {
        return new AgrupacionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AgrupacionViewModel.class);

        // TODO: Use the ViewModel

        // Captar el objeto con los datos de la agrupacion enviados desde el adaptador de Agrupaciones
        try {
            agrupacion = (Agrupacion) getActivity().getIntent().getSerializableExtra("DatosAgrupacion");

        }catch(Exception ex){
            Log.d("INTENT ERROR", ex.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_agrupacion, container, false);

        tituloAgrupacion = root.findViewById(R.id.panelTituloPiezaMusical);
        textoAgrupacion = root.findViewById(R.id.textoAgrupacion);
        rv = root.findViewById(R.id.rvListarecursos);
        integrantesTV = root.findViewById(R.id.seccionIntegrantes);
        vocesTV = root.findViewById(R.id.seccionVoces);
        repertorioTV = root.findViewById(R.id.seccionRepertorio);

        // Titulo de la agrupacion
        tituloAgrupacion.setText(agrupacion.getNombre());
        textoAgrupacion.setText(agrupacion.getTexto());

        // Distintos menús
        integrantesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integrantesTV.setBackgroundColor(Color.parseColor("#DDDDDD"));
                vocesTV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                repertorioTV.setBackgroundColor(Color.parseColor("#FFFFFF"));

                // Cargar los datos si no se han gargado ya
                if(datosIntegrantes == null){
                    recogerDatosIntegrantes();
                }

                recargarListaIntegrantes();
            }
        });

        vocesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integrantesTV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                vocesTV.setBackgroundColor(Color.parseColor("#DDDDDD"));
                repertorioTV.setBackgroundColor(Color.parseColor("#FFFFFF"));

                // Cargar los datos si no se han gargado ya
                if(datosVoces == null){
                    recogerDatosVoces();
                }

                recargarListaVoces();
            }
        });

        repertorioTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integrantesTV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                vocesTV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                repertorioTV.setBackgroundColor(Color.parseColor("#DDDDDD"));

                // Cargar los datos si no se han gargado ya
                if(datosPiezasMusicales == null){
                    recogerDatosPiezasMusicales();
                }

                recargarListaPiezasMusicales();
            }
        });

        try{
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    recogerDatosBD();
                }
            });

            t1.start();
            t1.join();

        }catch (Exception ex){

        }

        recargarListaPiezasMusicales();
        return root;
    }

    private void recogerDatosBD(){
        try {
            ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
            datosPiezasMusicales = c.leerPiezasDeAgrupacion(agrupacion.getId());

        } catch (Exception ex){
            Log.d("ERROR BD", ex.toString());
            //Toast.makeText(getContext(), "No es posible cargar los datos necesarios", Toast.LENGTH_SHORT).show();
        }
    }

    private void recogerDatosIntegrantes(){
        try{
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
                        datosIntegrantes = c.leerUsuariosDeAgrupacion(agrupacion.getId());

                    }catch (ExcepcionAMSound ex){

                    }
                }
            });

            t1.start();
            t1.join();

        }catch (Exception ex){

        }
    }

    private void recargarListaIntegrantes(){
        AdaptadorRVIntegrantes adaptador = new AdaptadorRVIntegrantes(getContext(), datosIntegrantes, agrupacion);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adaptador);
    }

    private void recogerDatosVoces(){
        try{
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
                        datosVoces = c.leerVocesDeAgrupacion(agrupacion.getId());

                    }catch (ExcepcionAMSound ex){

                    }
                }
            });

            t1.start();
            t1.join();

        }catch (Exception ex){

        }
    }

    private void recargarListaVoces(){
        AdaptadorRVVoz adaptador = new AdaptadorRVVoz(getContext(), datosVoces);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adaptador);
    }

    private void recogerDatosPiezasMusicales(){
        try{
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
                        datosPiezasMusicales = c.leerPiezasDeAgrupacion(agrupacion.getId());

                    }catch (ExcepcionAMSound ex){

                    }
                }
            });

            t1.start();
            t1.join();

        }catch (Exception ex){

        }
    }

    private void recargarListaPiezasMusicales(){
        AdaptadorRVPiezaMusical adaptador = new AdaptadorRVPiezaMusical(getContext(), datosPiezasMusicales);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adaptador);
    }

}