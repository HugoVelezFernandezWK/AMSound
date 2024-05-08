package es.amsound.ui.usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import POJOS.Agrupacion;
import POJOS.ExcepcionAMSound;
import POJOS.Voz;
import es.amsound.R;
import es.amsound.ui.utilidades.ClienteC;

public class PanelUsuarioFragment extends Fragment {

    private MainViewModel mViewModel;

    private POJOS.Usuario usuario;
    private Agrupacion agrupacion;
    private ArrayList<Voz> listaVoces;

    private TextView titulo, subtitulo;
    private EditText nombre, mail, telf;
    private Button btnDesvincular;

    public static PanelUsuarioFragment newInstance() {
        return new PanelUsuarioFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel

        try {
            usuario = (POJOS.Usuario) getActivity().getIntent().getSerializableExtra("Usuario");
            agrupacion = (Agrupacion) getActivity().getIntent().getSerializableExtra("AgrupacionDeUsuario");
            listaVoces = (ArrayList<Voz>) getActivity().getIntent().getSerializableExtra("VocesDeUsuario");

        }catch(Exception ex){
            Log.d("INTENT ERROR", ex.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_usuario, container, false);

        titulo = root.findViewById(R.id.tituloPanelUsuario);
        subtitulo = root.findViewById(R.id.panelTextoUsuario);
        nombre = root.findViewById(R.id.panelNombreUsuario);
        mail = root.findViewById(R.id.panelCorreoUsuario);
        telf = root.findViewById(R.id.panelTelefonoUsuario);
        btnDesvincular = root.findViewById(R.id.btnDevincularUsuario);

        titulo.setText(usuario.getNombre());
        nombre.setText(usuario.getNombre());
        mail.setText(usuario.getMail());
        telf.setText(usuario.getTelefono());

        // Mostrar voces dentro de la agrupación
        int i = 0;
        while(i < listaVoces.size()){
            if(i == 0){
                subtitulo.setText(listaVoces.get(i).getNombre());
            }

            if(i != listaVoces.size() && i != 0){
                subtitulo.setText(subtitulo.getText().toString() + ", " + listaVoces.get(i).getNombre());
            }

            if(i == listaVoces.size() && listaVoces.size() > 1){
                subtitulo.setText(subtitulo.getText().toString() + " y " + listaVoces.get(i).getNombre());
            }

            i++;
        }

        btnDesvincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setTitle("Desvincular usuario");
                dialogo1.setMessage("¿estas seguro de desvincular el usuario " + usuario.getNombre() + " de la agrupación " + agrupacion.getNombre() + "?");
                dialogo1.setCancelable(true);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        try{
                            Thread t1 = new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        for(int i = 0; i < listaVoces.size(); i++){
                                            ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
                                            c.eliminarUsuarioDeVoz(usuario.getId(), listaVoces.get(i).getId());
                                        }

                                        try {
                                            Intent intent = new Intent (getContext(), es.amsound.Agrupacion.class);
                                            intent.putExtra("RecargarUsuarios", true);
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

        return root;
    }
}