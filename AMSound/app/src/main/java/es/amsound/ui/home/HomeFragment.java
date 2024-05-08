package es.amsound.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import POJOS.Agrupacion;
import POJOS.Usuario;
import es.amsound.databinding.FragmentHomeBinding;
import es.amsound.ui.utilidades.AdaptadorRVAgrupacion;
import es.amsound.ui.utilidades.ClienteC;

public class HomeFragment extends Fragment {

    private
    FragmentHomeBinding binding;
    private Usuario usuarioRegistrado;
    private ArrayList<Agrupacion> agrupaciones;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        agrupaciones = new ArrayList<>();

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

        recargarLista();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void recogerDatosBD(){
        try {
            ClienteC c = new ClienteC(ClienteC.IP_SERVIDOR);
            usuarioRegistrado = c.leerUsuario(2);

            ClienteC c2 = new ClienteC(ClienteC.IP_SERVIDOR);
            agrupaciones = c2.leerAgrupacionesDeUsuario(usuarioRegistrado.getId());

        } catch (Exception ex){
            Log.d("ERROR BD", ex.toString());
            //Toast.makeText(getContext(), "No es posible cargar los datos necesarios", Toast.LENGTH_SHORT).show();
        }
    }

    private void recargarLista(){
        AdaptadorRVAgrupacion adaptador = new AdaptadorRVAgrupacion(getContext(), agrupaciones);
        binding.rvListaAgrupaciones.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvListaAgrupaciones.setAdapter(adaptador);
    }
}