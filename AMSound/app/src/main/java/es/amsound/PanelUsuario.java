package es.amsound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.amsound.ui.usuario.PanelUsuarioFragment;

public class PanelUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_usuario);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PanelUsuarioFragment.newInstance())
                    .commitNow();
        }
    }
}