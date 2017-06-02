package br.com.example.jbsjunior.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.example.jbsjunior.agenda.model.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        Intent intent = getIntent();
        Prova prova = (Prova) intent.getParcelableExtra("prova");

        TextView materia = (TextView) findViewById(R.id.tv_detalhes_prova_materia);
        TextView data = (TextView) findViewById(R.id.tv_detalhes_prova_data);

        ListView listTopicos = (ListView) findViewById(R.id.lv_detalhes_prova_topicos);

        materia.setText(prova.getMateria());
        data.setText(prova.getData());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
        prova.getTopicos());

        listTopicos.setAdapter(adapter);


    }
}
