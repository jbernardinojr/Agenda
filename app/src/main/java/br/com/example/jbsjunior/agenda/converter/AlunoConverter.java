package br.com.example.jbsjunior.agenda.converter;

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

    public String convertToJsonCompleto(Aluno aluno) {

        JSONStringer js = new JSONStringer();

        try {
            js.object().key("nome").value(aluno.getName())
                    .key("endereco").value(aluno.getAddress())
                    .key("site").value(aluno.getEmail())
                    .key("telefone").value(aluno.getPhone())
                    .key("nota").value(aluno.getNota())
                    .endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();

    }
}
