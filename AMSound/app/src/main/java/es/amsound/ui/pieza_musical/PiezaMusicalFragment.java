package es.amsound.ui.pieza_musical;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import POJOS.ExcepcionAMSound;
import POJOS.PiezaMusical;
import POJOS.Recurso;
import POJOS.Voz;
import es.amsound.R;
import es.amsound.ui.utilidades.AdaptadorRVRecurso;
import es.amsound.ui.utilidades.ClienteC;

public class PiezaMusicalFragment extends Fragment {

    private PiezaMusicalViewModel mViewModel;

    // View
    private RecyclerView rv;
    private TextView titulo, texto;
    private CardView s1, s2, s3;
    private ImageView btnmenuSettings, btnmenuUpload, archivoTV;
    private EditText nombreET, autorET, textoET, pTituloRecurso, pTextoRecurso;
    private Button botonActualizar, btnCrearRecurso, btnEliminarPieza;
    private Spinner opcionesVoz;

    // Datos
    private POJOS.PiezaMusical pieza;
    private ArrayList<Recurso> recursos;
    private ArrayList<Voz> voces;
    private Uri newImageURI;

    // Otros
    private boolean[] visualizarDatos = {true, false, false}; // Visualiza los distintos paneles o no

    public static PiezaMusicalFragment newInstance() {
        return new PiezaMusicalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PiezaMusicalViewModel.class);
        // TODO: Use the ViewModel

        // Captar el objeto con los datos de la pieza enviados desde el adaptador
        try {
            pieza = (POJOS.PiezaMusical) getActivity().getIntent().getSerializableExtra("DatosPiezaMusical");

        }catch(Exception ex){
            Log.d("INTENT ERROR", ex.getMessage());
        }

        recursos = new ArrayList<>();
        voces = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pieza_musical, container, false);

        titulo = root.findViewById(R.id.panelTituloPiezaMusical);
        texto = root.findViewById(R.id.panelTextoPiezaMusical);
        rv = root.findViewById(R.id.rvListarecursos);
        s1 = root.findViewById(R.id.s1);
        s2 = root.findViewById(R.id.s2);
        s3 = root.findViewById(R.id.s3);
        btnmenuSettings = root.findViewById(R.id.menuSettings);
        btnmenuUpload = root.findViewById(R.id.menuUpload);

        nombreET = root.findViewById(R.id.panelNombreUsuario);
        autorET = root.findViewById(R.id.panelCorreoUsuario);
        textoET = root.findViewById(R.id.panelTelefonoUsuario);
        botonActualizar = root.findViewById(R.id.piezaMusicalBtnActualizar);
        btnEliminarPieza = root.findViewById(R.id.piezaMusicalBtnEliminarPieza);

        titulo.setText(pieza.getNombre());

        texto.setText(pieza.getTexto());
        texto.setText(texto.getText() + "\n");
        texto.setText(texto.getText() + pieza.getAutor());
        texto.setText(texto.getText() + "\n");
        texto.setText(texto.getText() + pieza.getDeAgrupacion().getNombre());

        nombreET.setText(pieza.getNombre());
        autorET.setText(pieza.getAutor());
        textoET.setText(pieza.getTexto());

        pTituloRecurso = root.findViewById(R.id.anadirRecursoPiezaMusicalTitulo);
        pTextoRecurso = root.findViewById(R.id.anadirRecursoPiezaMusicalTexto);
        opcionesVoz = root.findViewById(R.id.anadirRecursoPiezaVoces);

        archivoTV = root.findViewById(R.id.archivoRegistro);
        btnCrearRecurso = root.findViewById(R.id.btnCrearRecurso);

        btnCrearRecurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Thread t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            // Recoger la voz seleccionada
                            Voz voz = null;
                            for(int i = 0; i < voces.size(); i++){
                                if(voces.get(i).getNombre() == (String) opcionesVoz.getSelectedItem()){
                                    voz = voces.get(i);
                                    break;
                                }
                            }

                            Recurso r = new Recurso(
                                    pTituloRecurso.getText().toString(),
                                    newImageURI.toString(),
                                    pTextoRecurso.getText().toString(),
                                    voz,
                                    pieza
                            );

                            insertarRecurso(r);

                            visualizarDatos[0] = true;
                            visualizarDatos[1] = false;
                            visualizarDatos[2] = false;
                            mostrarSecciones();
                        }
                    });

                    t1.start();
                    t1.join();

                }catch (Exception ex){

                }
            }
        });

        btnmenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visualizarDatos[0] = false;
                visualizarDatos[1] = true;
                visualizarDatos[2] = false;
                mostrarSecciones();
            }
        });

        btnEliminarPieza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setTitle("Eliminar Pieza Musical");
                dialogo1.setMessage("¿Estas seguro de eliminar la pieza musical?  Se perderán todos los datos relacionado esta.");
                dialogo1.setCancelable(true);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        try{
                            Thread t1 = new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
                                        c.eliminarPiezaMusical(pieza.getId());

                                        try {
                                            Intent intent = new Intent (getContext(), es.amsound.Agrupacion.class);
                                            intent.putExtra("RecargarPiezasMusicales", true);
                                            getContext().startActivity(intent);

                                        }catch (Exception ex){
                                            Log.d("INTENT ERROR", ex.getMessage());
                                        }

                                    }catch (ExcepcionAMSound ex){

                                    }
                                }
                            });

                            t1.start();
                            t1.join();

                        }catch (Exception ex){

                        }
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
            }
        });

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Thread t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            PiezaMusical p = pieza;
                            p.setNombre(nombreET.getText().toString());
                            p.setTexto(textoET.getText().toString());
                            p.setAutor(autorET.getText().toString());

                            try {
                                ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
                                c.modificarPiezaMusical(p.getId(), p);

                            }catch (ExcepcionAMSound ex){

                            }
                        }
                    });

                    t1.start();
                    t1.join();

                }catch (Exception ex){

                }

                pieza.setNombre(nombreET.getText().toString());
                pieza.setAutor(autorET.getText().toString());
                pieza.setTexto(textoET.getText().toString());

                titulo.setText(pieza.getNombre());
                texto.setText(pieza.getTexto());
                texto.setText(texto.getText() + "\n");
                texto.setText(texto.getText() + pieza.getAutor());
                texto.setText(texto.getText() + "\n");
                texto.setText(texto.getText() + pieza.getDeAgrupacion().getNombre());

                visualizarDatos[0] = true;
                visualizarDatos[1] = false;
                visualizarDatos[2] = false;
                mostrarSecciones();
            }
        });

        btnmenuUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visualizarDatos[0] = false;
                visualizarDatos[1] = false;
                visualizarDatos[2] = true;
                mostrarSecciones();
            }
        });

        titulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visualizarDatos[0] = true;
                visualizarDatos[1] = false;
                visualizarDatos[2] = false;
                mostrarSecciones();
            }
        });

        archivoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
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

        vocesDisponibles();
        mostrarSecciones();
        recargarListarecursos();
        return root;
    }

    private void recogerDatosBD(){
        try {
            ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
            recursos = c.leerRecursosDePiezaMusical(pieza.getId());

            ClienteC c2 = new ClienteC(ClienteC.IP_SERVIDOR);
            voces = c2.leerVocesDeAgrupacion(pieza.getDeAgrupacion().getId());

        } catch (Exception ex){
            Log.d("ERROR BD", ex.toString());
        }
    }

    private void insertarRecurso(Recurso r){
        try {
            ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
            int ra = c.insertarRecurso(r);

            if(ra!=0){
                //Toast.makeText(getContext(), "Se ha agregado el recurso " + r.getTitulo(), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex){
            Log.d("ERROR BD", ex.toString());
        }
    }

    private void recargarListarecursos(){
        AdaptadorRVRecurso adaptador = new AdaptadorRVRecurso(getContext(), recursos);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adaptador);
    }

    private void vocesDisponibles(){
        ArrayList<String> nombreVoces = new ArrayList<>();
        for(Voz voz : voces){
            nombreVoces.add(voz.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombreVoces);
        opcionesVoz.setAdapter(adapter);
    }

    private void mostrarSecciones(){
        if(visualizarDatos[0]){s1.setVisibility(View.VISIBLE);}else{s1.setVisibility(View.GONE);}
        if(visualizarDatos[1]){s2.setVisibility(View.VISIBLE);}else{s2.setVisibility(View.GONE);}
        if(visualizarDatos[2]){s3.setVisibility(View.VISIBLE);}else{s3.setVisibility(View.GONE);}
    }

    private void cargarImagen(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(i.createChooser(i, "Seleccione el archivo"), 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Uri path = data.getData();
            newImageURI = path;
            archivoTV.setImageURI(path);
        }
    }

}