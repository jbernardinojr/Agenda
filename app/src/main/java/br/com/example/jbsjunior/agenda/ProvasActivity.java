package br.com.example.jbsjunior.agenda;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.example.jbsjunior.agenda.model.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.frame_principal, new ListaProvasFragment());
        if (isLandScape()) {
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }
        tx.commit();
    }

    private boolean isLandScape() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova provaSelecionada) {
        FragmentManager fm = getSupportFragmentManager();
        DetalhesProvaFragment detalhesProvaFragment;

        if (!isLandScape()) {
            detalhesProvaFragment = new DetalhesProvaFragment();
            Bundle parametros = new Bundle();
            parametros.putParcelable("prova", provaSelecionada);
            detalhesProvaFragment.setArguments(parametros);

            FragmentTransaction tx = fm.beginTransaction();
            tx.replace(R.id.frame_principal, detalhesProvaFragment);
            tx.addToBackStack(null);
            tx.commit();
        } else {
            detalhesProvaFragment = (DetalhesProvaFragment)
                    fm.findFragmentById(R.id.frame_secundario);
            detalhesProvaFragment.populaCampo(provaSelecionada);

        }
    }
}
