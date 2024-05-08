package es.amsound;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import es.amsound.ui.agrupacion.AgrupacionFragment;

public class Agrupacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrupacion);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AgrupacionFragment.newInstance())
                    .commitNow();
        }
    }
}