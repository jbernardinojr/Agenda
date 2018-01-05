package br.com.example.jbsjunior.agenda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.example.jbsjunior.agenda.model.Prova;

/**
 * Created by root on 02/01/18.
 */

public class ListaProvasFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicosPort = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        Prova provaPortugues = new Prova("Portugues", "25/05/2016", topicosPort);

        List<String> topicosMat = Arrays.asList("Equacoes de segundo grau", "Trigonometria");
        Prova provaMatematica = new Prova("Matematica", "27/05/2016", topicosMat);

        final List<Prova> provas = Arrays.asList(provaPortugues,provaMatematica);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView list = (ListView) view.findViewById(R.id.provas_lista);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova provaSelecionada = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicou na prova de " + provaSelecionada.getMateria(), Toast.LENGTH_SHORT).show();

                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(provaSelecionada);
            }
        });


        return view;
    }
}
