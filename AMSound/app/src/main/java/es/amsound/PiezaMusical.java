package es.amsound;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import es.amsound.ui.pieza_musical.PiezaMusicalFragment;

public class PiezaMusical extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pieza_musical);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PiezaMusicalFragment.newInstance())
                    .commitNow();
        }
    }
}