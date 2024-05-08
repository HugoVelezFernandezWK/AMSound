package es.amsound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.amsound.ui.voz.VozFragment;

public class Voz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voz);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, VozFragment.newInstance())
                    .commitNow();
        }
    }
}