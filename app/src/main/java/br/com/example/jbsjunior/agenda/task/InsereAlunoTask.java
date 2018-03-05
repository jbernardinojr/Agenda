package br.com.example.jbsjunior.agenda.task;

import android.content.Context;
import android.os.AsyncTask;

import br.com.example.jbsjunior.agenda.WebClient;
import br.com.example.jbsjunior.agenda.converter.AlunoConverter;
import br.com.example.jbsjunior.agenda.model.Aluno;

/**
 * Created by jose.bernardino on 05/03/2018.
 */

public class InsereAlunoTask extends AsyncTask {
    private final Aluno aluno;
    private Context mContext;

    public InsereAlunoTask(Aluno aluno, Context context) {
        this.aluno = aluno;
        this.mContext = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String jsonCompleto = new AlunoConverter().convertToJsonCompleto(aluno);
        new WebClient(mContext).insere(jsonCompleto);

        return null;
    }
}
