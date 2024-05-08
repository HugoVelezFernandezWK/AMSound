package es.amsound.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import POJOS.ExcepcionAMSound;
import POJOS.Usuario;
import es.amsound.R;
import es.amsound.ui.utilidades.ClienteC;

public class Settings extends Fragment {

    private SettingsViewModel mViewModel;

    // View
    private EditText nombre, mail, pass1, pass2, telf;
    private TextView tv;

    public static Settings newInstance() {
        return new Settings();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        nombre = root.findViewById(R.id.panelNombreUsuario);
        mail = root.findViewById(R.id.panelCorreoUsuario);
        pass1 = root.findViewById(R.id.settingsContrasenaUsuario);
        pass2 = root.findViewById(R.id.settingsContrasenaUsuario2);
        telf = root.findViewById(R.id.panelTelefonoUsuario);
        conexionDBTest();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void conexionDBTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ClienteC c = new ClienteC("192.168.1.75");
                    Usuario usuario = c.leerUsuario(2);
                    nombre.setText(usuario.getNombre());
                    mail.setText(usuario.getMail());
                    telf.setText(usuario.getTelefono());

                } catch (ExcepcionAMSound e) {

                }
            }
        }).start();
    }

}