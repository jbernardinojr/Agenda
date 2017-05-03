package br.com.example.jbsjunior.agenda;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.example.jbsjunior.agenda.model.Aluno;

/**
 * Created by junior on 03/05/17.
 */

public class AlunoConverter {

    public String convertToJson(List<Aluno> alunos) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : alunos) {
                js.object();
                js.key("nome").value(aluno.getName());
                js.key("nota").value(aluno.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }

}
